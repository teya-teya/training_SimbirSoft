package base;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.WaitHelper;

import java.util.Comparator;
import java.util.List;

public class WebSteps {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;
    public WebChecks webChecks;

    public WebSteps(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.actions = new Actions(driver);
        this.webChecks = new WebChecks(driver, wait);
    }

    @Step("Перейти на страницу {url}")
    public WebSteps goToPage(String url) {
        driver.navigate().to(ConfigReader.getProperty("base.url") + url);

        return this;
    }

    @Step("Кликнуть на элемент '{description}'")
    public WebSteps clickOnElement(WebElement element, String description) {
        clickOnElement(element);

        return this;
    }

    @Step("Кликнуть на элемент")
    public WebSteps clickOnElement(WebElement element) {
        WaitHelper.waitForClickable(wait, element);
        actions.moveToElement(element).click().perform();

        return this;
    }

    @Step("Проскролить к элементу '{description}'")
    public void scrollToElement(WebElement element, String description) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    @Step("Выбрать опцию в дропдауне")
    public WebSteps selectOptionByText(WebElement dpd, String option) {
        clickOnElement(dpd);
        Select select = new Select(dpd);
        select.selectByVisibleText(option);

        return this;
    }

    @Step("Получить поле")
    public WebElement getInput(String label) {
        return driver.findElement(By.xpath("//label[contains(text(),'%s')]/following-sibling::input".formatted(label)));
    }

    @Step("Заполнить поле значением '{value}'")
    public WebSteps fillInput(WebElement input, String value) {
        WaitHelper.waitForVisible(wait, input);
        input.clear();
        input.sendKeys(value);

        return this;
    }

    @Step("Заполнить поле {description} значением '{value}'")
    public void fillInput(WebElement input, String value, String description) {
        fillInput(input, value);
    }

    @Step("Очистить поле")
    public void cleanInput(WebElement input) {
        WaitHelper.waitForVisible(wait, input);
        input.clear();
    }

    @Step("Получить самое длинное слово из коллекции")
    public String getLongestValue(List<WebElement> elements) {
        return elements.stream()
                .map(el -> el.getAttribute("value"))
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    @Step("Нажать кнопку 'Ок' в браузерном уведомлении")
    public void clickOkInAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

    }

    @Step("Обновить страницу")
    public WebSteps refreshPage() {
        driver.navigate().refresh();

        return this;
    }

    @Step("Если элемент '{description}' видим, дождаться, когда он пропадет")
    public void waitElementNotVisible(By locator, WebElement element, String description) {
        if (webChecks.isPresent(locator)) {
            WaitHelper.waitForNotVisible(wait, element);
        }
    }
}
