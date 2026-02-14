package utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WaitHelper {

    public static void waitForVisible(WebDriverWait wait, WebElement element) {
        wait.until(d -> element.isDisplayed());
    }

    public static void waitForClickable(WebDriverWait wait, WebElement element) {
        wait.until(d -> element.isDisplayed() && element.isEnabled());
    }

    public static void waitForNotVisible(WebDriverWait wait, WebElement element) {
        wait.until(d -> ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForElementsVisible(WebDriverWait wait, List<WebElement> elements) {
        wait.until(d -> !elements.isEmpty() && elements.get(0).isDisplayed());
    }
}
