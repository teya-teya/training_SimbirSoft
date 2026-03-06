package utils;

import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class TestListener implements ITestListener {
    private static final Set<String> failedTests = new HashSet<>();

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Запуск теста: {} [Поток: {}]",
                result.getName(), Thread.currentThread().getId());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Тест успешен: {} [Поток: {}]",
                result.getName(), Thread.currentThread().getId());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("Тест упал: {} [Поток: {}]",
                result.getName(), Thread.currentThread().getId());

        ITestContext context = result.getTestContext();
        WebDriver driver = (WebDriver) context.getAttribute("driver");

        if (driver != null) {
            saveScreenshot(driver);
        }

        if (result.getThrowable() != null) {
            log.error("Причина падения:", result.getThrowable());
        }

        try {
            String failedTest = result.getTestClass().getName() + "#" + result.getMethod().getMethodName();

            File file = new File("target/failed-tests.txt");
            file.getParentFile().mkdirs();

            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(failedTest + System.lineSeparator());
            }

        } catch (IOException e) {
            log.error("Ошибка записи упавшего теста", e);
        }
    }

    @Attachment(value = "Скриншот при падении", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        Screenshot screenshot = new AShot().takeScreenshot(driver);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(screenshot.getImage(), "PNG", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании скриншота", e);
        }
    }
}