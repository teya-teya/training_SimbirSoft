package pages;

import base.WebSteps;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AuthorizationPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;

    public AuthorizationPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.webSteps = new WebSteps(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "username")
    public WebElement inputUsername;

    @FindBy(id = "password")
    public WebElement inputPassword;

    @FindBy(css = "input[id*='input_username']")
    public WebElement inputUsernameDescription;

    @FindBy(css = "[ng-click='Auth.login()'")
    public WebElement btnLogin;

    @FindBy(css = "h1 + p")
    public WebElement messageSuccess;

    @FindBy(css = "[ng-if='Auth.error']")
    public WebElement messageError;

    @FindBy(css = "a[href='#/login']")
    public WebElement btnLogout;

    @Step("Авторизация пользователя")
    public void authorization(String username, String password) {
        webSteps.fillInput(inputUsername, username)
                .fillInput(inputPassword, password)
                .fillInput(inputUsernameDescription, username)
                .clickOnElement(btnLogin);
    }
}
