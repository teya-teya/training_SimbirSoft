package base;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.WaitHelper;

import java.util.ArrayList;
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

    @Step("Перейти на страницу {url}")
    public void goToPageFullLink(String url) {
        driver.navigate().to(url);
    }

    public WebSteps clickOnElement(WebElement element) {
        WaitHelper.waitForClickable(wait, element);
        actions.moveToElement(element).click().perform();

        return this;
    }

    public WebSteps scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        return this;
    }

    public WebSteps selectOptionByText(WebElement dpd, String option) {
        clickOnElement(dpd);
        Select select = new Select(dpd);
        select.selectByVisibleText(option);

        return this;
    }

    /**
     * Метод для получения полей по заголовку на ним
     *
     * @param label - текст заголовка над полем
     * @return поле
     */
    public WebElement getInput(String label) {
        return driver.findElement(By.xpath("//label[contains(text(),'%s')]/following-sibling::input".formatted(label)));
    }

    public WebSteps fillInput(WebElement input, String value) {
        WaitHelper.waitForVisible(wait, input);
        input.clear();
        input.sendKeys(value);

        return this;
    }

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
    public WebSteps clickOkInAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        return this;
    }

    @Step("Заполнить поле текстом '{text}' в браузерном уведомлении")
    public WebSteps fillInputAlert(String text) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);

        return this;
    }

    @Step("Обновить страницу")
    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void waitElementNotVisible(By locator, WebElement element) {
        if (webChecks.isPresent(locator)) {
            WaitHelper.waitForNotVisible(wait, element);
        }
    }

    @Step("Убрать фокус с текущего элемента")
    public void removeFocus() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.activeElement.blur();");
    }

    @Step("Переключится на iFrame")
    public WebSteps switchIframe(WebElement iframe) {
        driver.switchTo().frame(iframe);

        return this;
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        actions.dragAndDrop(source, target).perform();
    }

    @Step("Получить текст из элемента")
    public String getText(WebElement element) {
        WaitHelper.waitForVisible(wait, element);
        return element.getText();
    }

    @Step("Переключиться на вкладку {tab} в браузере")
    public void switchToTab(int tab) {
        WaitHelper.waitForNumberOfTabs(wait, tab);

        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tab - 1));
    }
}
