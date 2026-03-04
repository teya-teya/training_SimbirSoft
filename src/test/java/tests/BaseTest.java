package tests;

import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.TestListener;

import java.nio.file.Paths;
import java.time.Duration;

@Slf4j
@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();

    protected WebDriver driver;
    protected WebDriverWait wait;

    static {
        String resultsDir = Paths.get(System.getProperty("user.dir"), "target", "allure-results").toString();
        System.setProperty("allure.results.directory", resultsDir);
    }

    public WebDriver getDriver() {
        return driverThread.get();
    }

    public WebDriverWait getWait() {
        return waitThread.get();
    }

    @BeforeClass(alwaysRun = true)
    @Step("Открыть браузер")
    public void setupClass(ITestContext context) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{
                "enable-automation", "enable-logging"
        });

        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        WebDriverWait webDriverWait = new WebDriverWait(webDriver,
                Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("timeout"))));

        driverThread.set(webDriver);
        waitThread.set(webDriverWait);

        this.driver = webDriver;
        this.wait = webDriverWait;

        context.setAttribute("driver", webDriver);
        log.info("✅ Браузер запущен");
    }

    @AfterClass(alwaysRun = true)
    @Step("Закрыть браузер")
    public void tearDownClass() {
        WebDriver webDriver = driverThread.get();
        if (webDriver != null) {
            try {
                webDriver.quit();
            } catch (Exception e) {
                log.error("Ошибка при закрытии браузера: {}", e.getMessage());
            } finally {
                driverThread.remove();
                waitThread.remove();
                this.driver = null;
                this.wait = null;
            }
        }
    }
}