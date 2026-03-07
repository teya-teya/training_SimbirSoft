package base;

import enums.URL;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

@Slf4j
public class WebChecks {
    public WebDriver driver;
    public WebDriverWait wait;

    public WebChecks(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isPresent(By locator) {
        try {
            return !driver.findElements(locator).isEmpty();
        } catch (TimeoutException e) {
            log.warn("Таймаут при проверке элемента, вероятно страница долго грузится");
            return false;
        }
    }

    public WebChecks checkElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(element.isDisplayed(), "Элемент не отображается");

        return this;
    }

    public void checkElementNotVisible(By locator, int timeoutSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        boolean invisible = customWait.until(
                ExpectedConditions.invisibilityOfElementLocated(locator)
        );

        Assert.assertTrue(invisible, "Элемент все еще виден: " + locator);
    }

    public void checkElementNotPresent(List<WebElement> elements, String text) {
        Assert.assertTrue(elements.isEmpty(), "Элемент с текстом '" + text + "' отображается");
    }

    public void checkElementsVisible(List<WebElement> elements) {
        if (elements.isEmpty()) {
            Assert.fail("Список элементов пустой");
        }
        wait.until(ExpectedConditions.visibilityOf(elements.get(0)));
        for (WebElement elem : elements) {
            Assert.assertTrue(elem.isDisplayed(), "Элемент не отображается");
        }

    }

    public WebChecks checkTextOnElement(WebElement element, String expectedText) {
        wait.until(ExpectedConditions.visibilityOf(element));
        String actualText = element.getText();

        Assert.assertTrue(actualText.contains(expectedText),
                "Элемент содержит: '" + actualText + "', а не ожидаемый текст: '" + expectedText + "'");

        return this;
    }

    public void checkTextOnElementCollection(List<WebElement> elements, List<String> expectedTexts) {
        if (elements.isEmpty()) {
            Assert.fail("Список элементов пустой");
        }
        wait.until(ExpectedConditions.visibilityOf(elements.get(0)));

        for (int i = 0; i < elements.size(); i++) {
            String actualText = elements.get(i).getText();
            Assert.assertTrue(actualText.contains(expectedTexts.get(i)),
                    "Элемент содержит: '" + actualText + "', а не ожидаемый текст: '" + expectedTexts.get(i) + "'");
        }

    }

    @Step("Проверить переход на страницу '{url}'")
    public void checkNavigateTo(URL url) {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, ConfigReader.getProperty("base.url") + url.getUrl(),
                " Перехода на " + url.getDescription() + "не произошло, открыта страница " + currentUrl);
    }

    @Step("Проверить переход на страницу '{url}'")
    public void checkNavigate(URL url) {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, url.getUrl(),
                " Перехода на " + url.getDescription() + "не произошло, открыта страница " + currentUrl);
    }

    public void checkElementDisable(WebElement element) {
        Assert.assertNotNull(element.getAttribute("disabled"), "Элемент должен быть disabled");
    }


    @Step("Проверить наличие вертикального скролла: ожидаемое значение — {expected}")
    public void checkVerticalScroll(boolean expected) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        boolean actual = (Boolean) js.executeScript(
                "return document.documentElement.scrollHeight > document.documentElement.clientHeight;"
        );

        Assert.assertEquals(actual, expected,
                "Ожидали наличие скролла: " + expected + ", фактически: " + actual);
    }

    @Step("Проверить фокус на элементе — ожидаемое значение: {expected}")
    public WebChecks checkElementFocus(WebElement element, boolean expected) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement activeElement = (WebElement) js.executeScript(
                "return document.activeElement;"
        );

        boolean actual = element.equals(activeElement);

        Assert.assertEquals(actual, expected,
                "Ожидали фокус: " + expected + ", фактически: " + actual);

        return this;
    }

    @Step("Проверить, что открыто {countTabs} вкладки браузера")
    public void checkCountTabsBrowser(int countTabs) {
        int actualCount = driver.getWindowHandles().size();
        Assert.assertEquals(actualCount, countTabs,
                "Вкладок должно быть " + countTabs + ", но их " + actualCount);
    }
}
