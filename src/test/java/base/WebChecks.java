package base;

import enums.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigReader;

import java.util.List;

import static java.lang.Thread.sleep;

public class WebChecks {
    public WebDriver driver;
    public WebDriverWait wait;

    public WebChecks(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public WebChecks checkElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(element.isDisplayed(), "Элемент не отображается");

        return this;
    }

    public void checkElementNotVisible(WebElement element) throws InterruptedException {
        sleep(1000);
        Assert.assertFalse(element.isDisplayed(), "Элемент отображается");
    }

    public void assertElementNotPresent(List<WebElement> elements, String text) {
        Assert.assertTrue(elements.isEmpty(), "Элемент с текстом '" + text + "' отображается");
    }

    public WebChecks checkElementsVisible(List<WebElement> elements) {
        if (elements.isEmpty()) {
            Assert.fail("Список элементов пустой");
        }
        wait.until(ExpectedConditions.visibilityOf(elements.get(0)));
        for(WebElement elem : elements) {
            Assert.assertTrue(elem.isDisplayed(), "Элемент не отображается");
        }

        return this;
    }


    public WebChecks checkTextOnElement(WebElement element, String expectedText) {
        wait.until(ExpectedConditions.visibilityOf(element));
        String actualText = element.getText();

        Assert.assertTrue(actualText.contains(expectedText),
                "Элемент содержит: '" + actualText + "', а не ожидаемый текст: '" + expectedText + "'");

        return this;
    }

    public WebChecks checkTextOnElementCollection(List<WebElement> elements, List<String> expectedTexts) {
        if (elements.isEmpty()) {
            Assert.fail("Список элементов пустой");
        }
        wait.until(ExpectedConditions.visibilityOf(elements.get(0)));

        for (int i = 0; i < elements.size(); i++) {
            String actualText = elements.get(i).getText();
            Assert.assertTrue(actualText.contains(expectedTexts.get(i)),
                    "Элемент содержит: '" + actualText + "', а не ожидаемый текст: '" + expectedTexts.get(i) + "'");
        }

        return this;
    }

    public void checkNavigateTo(URL url) {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, ConfigReader.getProperty("base.url") + url.getUrl(),
                " Перехода на " + url.getDescription() + "не произошло");
    }

    public void checkElementDisable(WebElement element) {
        Assert.assertNotNull(element.getAttribute("disabled"), "Элемент должен быть disabled");
    }

}
