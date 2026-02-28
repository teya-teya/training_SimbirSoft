package pages.way_2_automation_banking_app;

import base.WebSteps;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        this.webSteps = new WebSteps(driver, wait);
    }

    @FindBy(xpath = "//a[text()='Sample Form']")
    public WebElement btnSampleForm;

    @FindBy(xpath = "//button[text()='Customer Login']")
    public WebElement btnCustomerLogin;

    @FindBy(xpath = "//button[text()='Bank Manager Login']")
    public WebElement btnBankManagerLogin;

    @Step("Нажать кнопку 'Sample Form'")
    public void clickBtnSampleForm() {
        webSteps.clickOnElement(btnSampleForm);
    }

    @Step("Нажать кнопку 'Bank Manager Login'")
    public void clickBtnBankManagerLogin() {
        webSteps.clickOnElement(btnBankManagerLogin);
    }

    @Step("Нажать кнопку 'Customer Login'")
    public LoginPage clickBtnCustomerLogin() {
        webSteps.clickOnElement(btnCustomerLogin);

        return this;
    }
}
