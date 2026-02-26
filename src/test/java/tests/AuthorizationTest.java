package tests;

import base.WebChecks;
import base.WebSteps;
import enums.URL;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AuthorizationPage;
import utils.RandomUtils;

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

    @Test(description = "Проверка полей ввода")
    void checkLoginInputFields() {
        webChecks.checkElementVisible(authorizationPage.inputUsername)
                .checkElementVisible(authorizationPage.inputPassword)
                .checkElementDisable(authorizationPage.btnLogin);
    }

    @Test(description = "Проверка успешной авторизации")
    void checkSuccessfulAuthorization() {
        authorizationPage.authorization(System.getenv("USERNAME"), System.getenv("PASSWORD"));
        webChecks.checkTextOnElement(authorizationPage.messageSuccess, "You're logged in!!");
    }

    @Test(description = "Проверка авторизации с невалидными данными")
    void checkAuthorizationWithInvalidCredentials() {
        authorizationPage.authorization(RandomUtils.username(), RandomUtils.password());
        webChecks.checkTextOnElement(authorizationPage.messageError, "Username or password is incorrect");
    }

    @Test(description = "Проверка успешного разлогирования")
    void checkSuccessfulLogoutAfterAuthorization() {
        authorizationPage.authorization(System.getenv("USERNAME"), System.getenv("PASSWORD"));
        webChecks.checkTextOnElement(authorizationPage.messageSuccess, "You're logged in!!");
        webSteps.clickOnElement(authorizationPage.btnLogout);
        webChecks.checkElementVisible(authorizationPage.inputUsername)
                .checkElementVisible(authorizationPage.inputPassword);
    }
}
