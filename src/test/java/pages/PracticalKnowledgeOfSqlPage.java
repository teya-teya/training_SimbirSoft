package pages;

import base.WebChecks;
import base.WebSteps;
import enums.URL;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CookieUtils;

public class PracticalKnowledgeOfSqlPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;
    public WebChecks webChecks;

    public PracticalKnowledgeOfSqlPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.webSteps = new WebSteps(driver, wait);
        this.webChecks = new WebChecks(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "form[name='frmlogin'] input[name='login']")
    public WebElement inputLogin;

    @FindBy(css = "form[name='frmlogin'] input[name='psw']")
    public WebElement inputPassword;

    @FindBy(css = "form[name='frmlogin'] input[value='Вход']")
    public WebElement btnSubmit;

    @FindBy(css = "b a[href='/personal.php']")
    public WebElement username;

    @Step("Авторизация пользователя {login}")
    public void authorization(String login, String password) {
        webSteps.fillInput(inputLogin, login)
                .fillInput(inputPassword, password)
                .clickOnElement(btnSubmit);
    }

    @Step("Загрузить куки из файла и обновить страницу")
    public void loadCookiesAndRefresh(URL expectedUrl) {
        webChecks.checkNavigate(expectedUrl);
        CookieUtils.loadCookies(driver);
        webSteps.refreshPage();
    }

    @Step("Проверить, что пользователь {usernameText} успешно авторизован")
    public void checkAuthorizationSuccess(String usernameText) {
        webChecks.checkTextOnElement(username, usernameText);
    }
}
