package tests;

import base.WebChecks;
import base.WebSteps;
import enums.URL;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AuthorizationPage;
import utils.RandomUtils;

@Epic("UI тесты")
@Feature("Авторизация")
@Test(description = "Проверка авторизации")
public class AuthorizationTest extends BaseTest {
    WebChecks webChecks;
    WebSteps webSteps;
    AuthorizationPage authorizationPage;

    @BeforeMethod
    void setUp() {
        webChecks = new WebChecks(driver, wait);
        webSteps = new WebSteps(driver, wait);
        authorizationPage = new AuthorizationPage(driver, wait);

        webSteps.goToPage(URL.AUTHORIZATION.getUrl());
    }

    @Story("Проверка видимости полей ввода на странице авторизации")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка полей ввода")
    void checkLoginInputFields() {
        webChecks.checkElementVisible(authorizationPage.inputUsername, "поле 'Username'")
                .checkElementVisible(authorizationPage.inputPassword, "поле 'Password'")
                .checkElementDisable(authorizationPage.btnLogin, "кнопка 'Login'");
    }

    @Story("Авторизация пользователя с корректными данными")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка успешной авторизации")
    void checkSuccessfulAuthorization() {
        authorizationPage.authorization(System.getenv("USERNAME"), System.getenv("PASSWORD"));
        webChecks.checkTextOnElement(authorizationPage.messageSuccess, "сообщение","You're logged in!!");
    }

    @Story("Авторизация пользователя с не корректными данными")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка авторизации с невалидными данными")
    void checkAuthorizationWithInvalidCredentials() {
        authorizationPage.authorization(RandomUtils.username(), RandomUtils.password());
        webChecks.checkTextOnElement(authorizationPage.messageError, "сообщение","Username or password is incorrect");
    }

    @Story("Успешный выход пользователя после авторизации")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка успешного разлогирования")
    void checkSuccessfulLogoutAfterAuthorization() {
        authorizationPage.authorization(System.getenv("USERNAME"), System.getenv("PASSWORD"));
        webChecks.checkTextOnElement(authorizationPage.messageSuccess, "сообщение","You're logged in!!");
        webSteps.clickOnElement(authorizationPage.btnLogout, "кнопка 'Logout'");
        webChecks.checkElementVisible(authorizationPage.inputUsername, "поле 'Username'")
                .checkElementVisible(authorizationPage.inputPassword, "поле 'Password'");
    }
}
