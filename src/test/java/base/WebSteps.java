package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

public class WebSteps {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public WebSteps(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.actions = new Actions(driver);
    }

    public void clickOnElement(WebElement element) {
        WaitHelper.waitForClickable(wait, element);
        actions.moveToElement(element).click().perform();
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }
}
