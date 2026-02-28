package tests;

import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import utils.ConfigReader;

import java.time.Duration;

@Listeners({AllureTestNg.class})
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    @Step("Открыть браузер и главную страницу")
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver,
                Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("timeout"))));

        driver.get(ConfigReader.getProperty("base.url"));
    }

    @AfterMethod
    protected void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
