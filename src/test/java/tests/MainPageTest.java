package tests;

import base.WebChecks;
import base.WebSteps;
import enums.CONTACTS;
import enums.URL;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LifetimeMembershipPage;
import pages.MainPage;

import java.util.ArrayList;
import java.util.List;

@Epic("UI тесты")
@Feature("Главная страница")
@Test(description = "Главная страница")
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

        mainPage.closeBanner();
    }

    @Story("Открытие главной страницы и проверка отображения основных блоков")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка открытия страницы")
    void checkOpenMainPage() {
        webChecks.checkElementVisible(mainPage.header.contactsBlock, "блок с контактами")
                .checkElementVisible(mainPage.header.navBar, "навигационное меню")
                .checkElementsVisible(mainPage.courses)
                .checkElementVisible(mainPage.footer.footerElement, "футер");

        webSteps.waitElementNotVisible(mainPage.locatorLearnMore, mainPage.btnLearnMore, "кнопка 'Learn More'");

        webChecks.checkElementVisible(mainPage.btnRegisterNow, "кнопка 'Register Now'");
    }

    @Story("Проверка блока контактов и ссылок в хедере")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка хедера с контактной информацией")
    void checkHeaderContactsAndSocialLinks() {
        List<String> contacts = new ArrayList<>();
        for (CONTACTS contact : CONTACTS.values()) {
            contacts.add(contact.getValue());
        }
        webChecks.checkTextOnElementCollection(mainPage.header.contacts, "контакты", contacts)
                .checkElementVisible(mainPage.header.getLinkSocialsPage("Facebook"), "ссылка на 'Facebook'")
                .checkElementVisible(mainPage.header.getLinkSocialsPage("Linkedin"), "ссылка на 'Linkedin'")
                .checkElementVisible(mainPage.header.getLinkSocialsPage("Google"), "ссылка на 'Google'")
                .checkElementVisible(mainPage.header.getLinkSocialsPage("YouTube"), "ссылка на 'YouTube'");
    }

    @Story("Проверка футера: контакты и информация")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка футера")
    void checkFooterContactsAndInformation() {
        webChecks.checkElementVisible(mainPage.footer.footerElement, "футер")
                .checkTextOnElement(mainPage.footer.contacts.get(0), "адрес",
                        "Way2Automation\n" +
                                "CDR Complex, 3rd Floor, Naya Bans Market, Sector 15, Noida, Near sec-16 Metro Station")
                .checkTextOnElement(mainPage.footer.contacts.get(1), "телефон 1", CONTACTS.TEL_1_2.getValue())
                .checkTextOnElement(mainPage.footer.contacts.get(2), "телефон 2", CONTACTS.TEL_2_2.getValue())
                .checkTextOnElement(mainPage.footer.contacts.get(3), "электронная почта 1", CONTACTS.EMAIL.getValue())
                .checkTextOnElement(mainPage.footer.contacts.get(4), "электронная почта 2", CONTACTS.EMAIL_2.getValue());
    }

    @Story("Проверка видимости навигационного меню при скроллинге")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка меню навигации при скроллинге")
    void checkNavigationMenuVisibilityOnScroll() {
        webSteps.scrollToElement(mainPage.footer.footerElement, "футер");
        webChecks.checkElementVisible(mainPage.header.navBar, "навигационное меню");
    }

    @Story("Переход на страницу курса через кнопку 'Read More'")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка перехода по меню навигации на другие страницы")
    void checkNavigationToCoursePageFromMainPage() {
        webSteps.clickOnElement(mainPage.getReadMoreButtonByCourseText("Lifetime Membership"), "кнопка 'ReadMore' курса 'Lifetime Membership'");
        webChecks.checkTextOnElement(lifetimeMembershipPage.title, "заголовок", "LIFETIME MEMBERSHIP CLUB")
                .checkNavigateTo(URL.LIFETIME_MEMBERSHIP);
    }
}
