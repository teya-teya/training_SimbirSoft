package tests;

import base.WebChecks;
import base.WebSteps;
import enums.URL;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AuthorizationPage;
import utils.ConfigReader;
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
    void test_04_1() {
        webChecks.checkElementVisible(authorizationPage.inputUsername)
                .checkElementVisible(authorizationPage.inputPassword)
                .checkElementDisable(authorizationPage.btnLogin);
    }

    @Test(description = "Проверка успешной авторизации")
    void test_04_2() {
        authorizationPage.authorization(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
        webChecks.checkTextOnElement(authorizationPage.messageSuccess, "You're logged in!!");
    }

    @Test(description = "Проверка авторизации с невалидными данными")
    void test_04_3() {
        authorizationPage.authorization(RandomUtils.username(), RandomUtils.password());
        webChecks.checkTextOnElement(authorizationPage.messageError, "Username or password is incorrect");
    }

    @Test(description = "Проверка успешного разлогирования")
    void test_04_4() {
        authorizationPage.authorization(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
        webChecks.checkTextOnElement(authorizationPage.messageSuccess, "You're logged in!!");
        webSteps.clickOnElement(authorizationPage.btnLogout);
        webChecks.checkElementVisible(authorizationPage.inputUsername)
                .checkElementVisible(authorizationPage.inputPassword);
    }
}
