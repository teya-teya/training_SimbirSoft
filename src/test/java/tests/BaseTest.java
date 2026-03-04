package tests;

import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import utils.ConfigReader;
import utils.TestListener;

import java.net.URL;
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
        log.info("📁 Allure results directory: {}", resultsDir);

        log.info("🔑 USERNAME = {}", System.getenv("USERNAME"));
        log.info("🔑 PASSWORD = {}", System.getenv("PASSWORD") != null ? "задан" : "null");
    }

    public WebDriver getDriver() {
        return driverThread.get();
    }

    public WebDriverWait getWait() {
        return waitThread.get();
    }

    @BeforeClass(alwaysRun = true)
    @Step("Открыть браузер через Selenium Grid")
    public void setupClass(ITestContext context) throws Exception {
        log.info("🚀 Запуск браузера через Grid в потоке: {}", Thread.currentThread().getId());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation", "enable-logging"});
        options.setCapability("browserName", "chrome"); // 🔹 обязательно для Grid

        URL hubUrl = new URL(ConfigReader.getProperty("grid.url"));
        WebDriver webDriver = new RemoteWebDriver(hubUrl, options);

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        WebDriverWait webDriverWait = new WebDriverWait(webDriver,
                Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("timeout"))));

        driverThread.set(webDriver);
        waitThread.set(webDriverWait);

        this.driver = webDriver;
        this.wait = webDriverWait;

        context.setAttribute("driver", webDriver);
        log.info("✅ Браузер запущен через Grid");
    }

    @AfterClass(alwaysRun = true)
    @Step("Закрыть браузер")
    public void closeClass() {
        WebDriver webDriver = driverThread.get();
        if (webDriver != null) {
            try {
                webDriver.quit();
                log.info("✅ Браузер закрыт в потоке: {}", Thread.currentThread().getId());
            } catch (Exception e) {
                log.error("❌ Ошибка при закрытии браузера: {}", e.getMessage());
            } finally {
                driverThread.remove();
                waitThread.remove();
                this.driver = null;
                this.wait = null;
            }
        }
    }
}