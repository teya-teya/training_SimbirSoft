package pages.way_2_automation_banking_app;

import base.WebSteps;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.WaitHelper;

import java.util.List;

public class TransactionsPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;

    public TransactionsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.webSteps = new WebSteps(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "table")
    public WebElement table;

    @FindBy(css = "[ng-click='reset()']")
    public WebElement btnReset;

    @FindBy(css = "[ng-click='back()']")
    public WebElement btnBack;

    @Step("Получить строки в таблице транзакций")
    public List<WebElement> getRows() {
        return driver.findElements(By.cssSelector("[id^='anchor']:not(#anchor)"));
    }

    @Step("Получить ячейку в таблице транзакций")
    public WebElement getTransactionCell(int rowIndex, int colIndex) {
        WebElement row = getRows().get(rowIndex);
        return row.findElements(By.tagName("td")).get(colIndex);
    }

    @Step("Получить ячейку в таблице транзакций с текстом '{text}'")
    public List<WebElement> getCellsByText(String text) {
        return driver.findElements(By.xpath(String.format("//td[normalize-space()='%s']", text)));
    }

    @Step("Проверить баланс")
    public void checkBalance(int balance) {
        List<WebElement> rows = getRows();
        WaitHelper.waitForElementsVisible(wait, rows);
        int calculatedBalance = 0;

        for (WebElement row : rows) {

            String amountText = row.findElement(By.xpath("./td[2]")).getText().trim();
            String type = row.findElement(By.xpath("./td[3]")).getText().trim();

            int amount = Integer.parseInt(amountText);

            if (type.equalsIgnoreCase("Credit")) {
                calculatedBalance += amount;
            } else if (type.equalsIgnoreCase("Debit")) {
                calculatedBalance -= amount;
            }
        }

        Assert.assertEquals(calculatedBalance, balance,
                "Баланс не совпадает! Ожидалось: " + balance + ", рассчитано: " + calculatedBalance);
    }
}
