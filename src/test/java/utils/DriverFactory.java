package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverFactory {

    public static WebDriver createDriver() throws Exception {

        String browser = ConfigReader.getProperty("browser");
        String grid = ConfigReader.getProperty("grid");

        if (grid != null && grid.equalsIgnoreCase("true")) {
            return createRemoteDriver(browser);
        } else {
            return createLocalDriver(browser);
        }
    }

    private static WebDriver createLocalDriver(String browser) {

        String path = System.getProperty("user.dir") + "/drivers/";

        switch (browser.toLowerCase()) {

            case "firefox":
                System.setProperty("webdriver.gecko.driver", path + "geckodriver.exe");
                return new FirefoxDriver();

            case "edge":
                System.setProperty("webdriver.edge.driver", path + "msedgedriver.exe");
                return new EdgeDriver();

            case "ie":
                System.setProperty("webdriver.ie.driver", path + "IEDriverServer.exe");

                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                ieOptions.ignoreZoomSettings();
                ieOptions.introduceFlakinessByIgnoringSecurityDomains();

                return new InternetExplorerDriver(ieOptions);

            default:
                System.setProperty("webdriver.chrome.driver", path + "chromedriver.exe");

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");

                return new ChromeDriver(chromeOptions);
        }
    }

    private static WebDriver createRemoteDriver(String browser) throws Exception {

        URL gridUrl = new URL(ConfigReader.getProperty("grid.url"));

        switch (browser.toLowerCase()) {

            case "firefox":
                return new RemoteWebDriver(gridUrl, new FirefoxOptions());

            case "edge":
                return new RemoteWebDriver(gridUrl, new EdgeOptions());

            case "ie":
                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                ieOptions.ignoreZoomSettings();
                ieOptions.introduceFlakinessByIgnoringSecurityDomains();
                return new RemoteWebDriver(gridUrl, ieOptions);

            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                return new RemoteWebDriver(gridUrl, chromeOptions);
        }
    }
}