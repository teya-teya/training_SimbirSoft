package utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WaitHelper {

    @Step("Дождаться, когда элемент '{description}' будет видим")
    public static void waitForVisible(WebDriverWait wait, WebElement element, String description) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    @Step("Дождаться, когда элемент '{description}' будет видим")
    public static void waitForVisible(WebDriverWait wait, WebElement element) {
        waitForVisible(wait, element, "Без описания");
    }

    @Step("Дождаться кликабельности элемента")
    public static void waitForClickable(WebDriverWait wait, WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    @Step("Дождаться, когда элемент не будет отображаться")
    public static void waitForNotVisible(WebDriverWait wait, WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    @Step("Дождаться, когда элементы будут видимы")
    public static void waitForElementsVisible(WebDriverWait wait, List<WebElement> elements) {
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }
}
