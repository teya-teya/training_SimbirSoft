package tests;

import base.WebSteps;
import enums.URL;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HttpAuthenticationPage;

public class HttpAuthenticationTest extends BaseTest{
    WebSteps webSteps;
    HttpAuthenticationPage httpAuthenticationPage;

    @BeforeMethod
    void setUp() {
        webSteps = new WebSteps(driver, wait);
        httpAuthenticationPage = new HttpAuthenticationPage(driver, wait);

        webSteps.goToPageFullLink(URL.HTTP_AUTHENTICATION.getUrl());
    }

    @Story("Проверка базовой аутентификации")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка успешной авторизации через Basic Auth")
    void checkSuccessfulBasicAuth() {
        httpAuthenticationPage.scrollAndClickBtnDisplayImage()
                .enableBasicAuth(System.getenv("LOGIN_HTTP"), System.getenv("PASSWORD_HTTP"))
                .checkSuccessAuthentication();
    }
}
