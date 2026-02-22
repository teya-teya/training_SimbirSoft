package pages.way_2_automation_banking_app;

import base.WebSteps;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

public class AccountPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;

    public AccountPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.webSteps = new WebSteps(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div.box.borderM")
    public WebElement welcomeMessage;

    @FindBy(css = "button[ng-click='transactions()']")
    public WebElement btnTransactions;

    @FindBy(css = "button[ng-click='deposit()']")
    public WebElement btnDeposit;

    @FindBy(css = "button[ng-click='withdrawl()']")
    public WebElement btnWithdrawl;

    @FindBy(xpath = "//button[text()='Deposit']")
    public WebElement buttonDeposit;

    @FindBy(xpath = "//button[text()='Withdraw']")
    public WebElement buttonWithdraw;

    @FindBy(css = "span[ng-show='message']")
    public WebElement message;

    @FindBy(xpath = "(//div[@ng-hide=\"noAccount\"]/strong)[2]")
    public WebElement balance;

    public Integer getBalance() {
        WaitHelper.waitForVisible(wait, balance);
        return Integer.parseInt(balance.getText());
    }

    public void deposit(int amount) {
        webSteps.clickOnElement(btnDeposit);
        WaitHelper.waitForVisible(wait, buttonDeposit);
        webSteps.fillInput(webSteps.getInput("Amount to be Deposited"), String.valueOf(amount))
                .clickOnElement(buttonDeposit);
    }

    public void withdrawl(String amount) {
        webSteps.clickOnElement(btnWithdrawl);
        WaitHelper.waitForVisible(wait, buttonWithdraw);
        webSteps.fillInput(webSteps.getInput("Amount to be Withdrawn"), amount)
                .clickOnElement(buttonWithdraw);
    }
}
