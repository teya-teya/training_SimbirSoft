package base;

import enums.URL;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

public class WebChecks {
    public WebDriver driver;
    public WebDriverWait wait;

    public WebChecks(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Проверить, что элемент существует на странице")
    public boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    @Step("Проверить, что элемент '{description}' отображается на странице")
    public WebChecks checkElementVisible(WebElement element, String description) {
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(element.isDisplayed(), "Элемент не отображается");

        return this;
    }

    @Step("Проверить, что элемент не отображается на странице")
    public void checkElementNotVisible(By locator, int timeoutSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        boolean invisible = customWait.until(
                ExpectedConditions.invisibilityOfElementLocated(locator)
        );

        Assert.assertTrue(invisible, "Элемент все еще виден: " + locator);
    }

    @Step("Проверить, что элемент '{description}' не существует на странице")
    public void checkElementNotPresent(List<WebElement> elements, String description, String text) {
        Assert.assertTrue(elements.isEmpty(), "Элемент с текстом '" + text + "' отображается");
    }

    @Step("Проверить, что элементы '{description}' отображаются на странице")
    public WebChecks checkElementsVisible(List<WebElement> elements) {
        if (elements.isEmpty()) {
            Assert.fail("Список элементов пустой");
        }
        wait.until(ExpectedConditions.visibilityOf(elements.get(0)));
        for (WebElement elem : elements) {
            Assert.assertTrue(elem.isDisplayed(), "Элемент не отображается");
        }

        return this;
    }

    @Step("Проверить, что элемент '{description}' содержит текст '{expectedText}'")
    public WebChecks checkTextOnElement(WebElement element, String description, String expectedText) {
        wait.until(ExpectedConditions.visibilityOf(element));
        String actualText = element.getText();

        Assert.assertTrue(actualText.contains(expectedText),
                "Элемент содержит: '" + actualText + "', а не ожидаемый текст: '" + expectedText + "'");

        return this;
    }

    @Step("Проверить, что элементы '{description}' содержат '{expectedText}'")
    public WebChecks checkTextOnElementCollection(List<WebElement> elements, String description, List<String> expectedTexts) {
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

    @Step("Проверить переход на страницу'{url}'")
    public void checkNavigateTo(URL url) {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, ConfigReader.getProperty("base.url") + url.getUrl(),
                " Перехода на " + url.getDescription() + "не произошло, открыта страница " + currentUrl);
    }

    @Step("Проверить, что элемент '{description}' не активен")
    public void checkElementDisable(WebElement element, String description) {
        Assert.assertNotNull(element.getAttribute("disabled"), "Элемент должен быть disabled");
    }

}
