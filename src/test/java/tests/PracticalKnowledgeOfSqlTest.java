package tests;

import base.WebChecks;
import base.WebSteps;
import enums.URL;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.PracticalKnowledgeOfSqlPage;
import utils.CookieUtils;

@Epic("UI тесты")
@Feature("Авторизация с куками")
public class PracticalKnowledgeOfSqlTest extends BaseTest{
    WebChecks webChecks;
    WebSteps webSteps;
    PracticalKnowledgeOfSqlPage sqlPage;

    @BeforeMethod
    void setUp() {
        webChecks = new WebChecks(driver, wait);
        webSteps = new WebSteps(driver, wait);
        sqlPage = new PracticalKnowledgeOfSqlPage(driver, wait);

        webSteps.goToPageFullLink(URL.SQL_EX.getUrl());
    }

    @AfterClass(alwaysRun = true)
    public void clearCookies() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
        }
    }

    @Story("Авторизация на странице 'Практическое владение языком SQL'")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка авторизации с куками")
    void authorizationWithCookies() {
        if (CookieUtils.cookieFileExists()) {
            Allure.step("Авторизация через куки");
            sqlPage.loadCookiesAndRefresh(URL.SQL_EX);
        } else {
            Allure.step("Авторизация через форму и сохранение куки");
            sqlPage.authorization(System.getenv("LOGIN"), System.getenv("PASSWORD2"));
            CookieUtils.saveCookies(driver);
        }

        sqlPage.checkAuthorizationSuccess("мягкая булочка");
    }
}
