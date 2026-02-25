package base;

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

    public WebSteps goToPage(String url) {
        driver.navigate().to(ConfigReader.getProperty("base.url") + url);

        return this;
    }

    public WebSteps clickOnElement(WebElement element) {
        WaitHelper.waitForClickable(wait, element);
        actions.moveToElement(element).click().perform();

        return this;
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public WebSteps selectOptionByText(WebElement dpd, String option) {
        clickOnElement(dpd);
        Select select = new Select(dpd);
        select.selectByVisibleText(option);

        return this;
    }

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

    public String getLongestValue(List<WebElement> elements) {
        return elements.stream()
                .map(el -> el.getAttribute("value"))
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    public void clickOkInAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

    }

    public WebSteps refreshPage() {
        driver.navigate().refresh();

        return this;
    }

    public void waitElementNotVisible(By locator, WebElement element) {
        if (webChecks.isPresent(locator)) {
            WaitHelper.waitForNotVisible(wait, element);
        }
    }
}
