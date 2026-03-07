package tests;

import base.WebChecks;
import base.WebSteps;
import enums.URL;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LifetimeMembershipPage;
import pages.MainPage;

@Epic("UI тесты")
@Feature("Главная страница")
public class MainPageTest extends BaseTest {
    MainPage mainPage;
    WebChecks webChecks;
    WebSteps webSteps;
    LifetimeMembershipPage lifetimeMembershipPage;

    @BeforeMethod
    void setUp() {
        webChecks = new WebChecks(driver, wait);
        mainPage = new MainPage(driver, wait);
        webSteps = new WebSteps(driver, wait);
        lifetimeMembershipPage = new LifetimeMembershipPage(driver, wait);
        webSteps.goToPage("");
        mainPage.closeBanner();
    }

    @Story("Открытие главной страницы и проверка отображения основных блоков")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка открытия страницы")
    void checkOpenMainPage() {
        mainPage.checkInfo()
                .checkNavBar()
                .checkCourses()
                .checkFooter()
                .checkBtnRegisterNow();
    }

    @Story("Проверка блока контактов и ссылок в хедере")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка хедера с контактной информацией")
    void checkHeaderContactsAndSocialLinks() {
        mainPage.checkContacts()
                .checkSocialPage("Facebook")
                .checkSocialPage("Linkedin")
                .checkSocialPage("Google")
                .checkSocialPage("YouTube");
    }

    @Story("Проверка футера: контакты и информация")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка футера")
    void checkFooterContactsAndInformation() {
        mainPage.checkContactFooter();
    }

    @Story("Проверка видимости навигационного меню при скроллинге")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка меню навигации при скроллинге")
    void checkNavigationMenuVisibilityOnScroll() {
        mainPage.scrollToFooter()
                .checkNavBar();
    }

    @Story("Переход на страницу курса через кнопку 'Read More'")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка перехода по меню навигации на другие страницы")
    void checkNavigationToCoursePageFromMainPage() {
        mainPage.clickBtnReadMoreForLifetimeMembership();
        lifetimeMembershipPage.checkTitle();
        webChecks.checkNavigateTo(URL.LIFETIME_MEMBERSHIP);
    }
}
