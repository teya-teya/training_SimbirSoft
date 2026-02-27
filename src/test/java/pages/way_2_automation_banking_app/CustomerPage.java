package pages.way_2_automation_banking_app;

import base.WebChecks;
import base.WebSteps;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

public class CustomerPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;
    public WebChecks webChecks;

    public CustomerPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.webSteps = new WebSteps(driver, wait);
        this.webChecks = new WebChecks(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "userSelect")
    public WebElement dpdUserSelect;

    @FindBy(xpath = "//button[text()='Login']")
    public WebElement btnLogin;

    @Step("Авторизоваться пользователем {fullName}")
    public void login(String fullName) {
        WaitHelper.waitForVisible(wait, dpdUserSelect);
        webSteps.selectOptionByText(dpdUserSelect, fullName)
                .clickOnElement(btnLogin);
    }
}
