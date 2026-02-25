package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
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
