package tests;

import base.WebChecks;
import base.WebSteps;
import enums.CONTACTS;
import enums.URL;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LifetimeMembershipPage;
import pages.MainPage;

import java.util.ArrayList;
import java.util.List;

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

    @Test(description = "Проверка открытия страницы")
    void test_01_1() {
        webChecks.checkElementVisible(mainPage.header.contactsBlock)
                .checkElementVisible(mainPage.header.navBar)
                .checkElementsVisible(mainPage.courses)
                .checkElementVisible(mainPage.footer.footerElement);

        webSteps.waitElementNotVisible(mainPage.locatorLearnMore, mainPage.btnLearnMore);

        webChecks.checkElementVisible(mainPage.btnRegisterNow);
    }

    @Test(description = "Проверка хедера с контактной информацией")
    void test_01_2() {
        List<String> contacts = new ArrayList<>();
        for (CONTACTS contact : CONTACTS.values()) {
            contacts.add(contact.getValue());
        }
        webChecks.checkTextOnElementCollection(mainPage.header.contacts, contacts)
                .checkElementVisible(mainPage.header.getLinkSocialsPage("Facebook"))
                .checkElementVisible(mainPage.header.getLinkSocialsPage("Linkedin"))
                .checkElementVisible(mainPage.header.getLinkSocialsPage("Google"))
                .checkElementVisible(mainPage.header.getLinkSocialsPage("YouTube"));
    }

    @Test(description = "Проверка футера")
    void test_01_4() {
        webChecks.checkElementVisible(mainPage.footer.footerElement)
                .checkTextOnElement(mainPage.footer.contacts.get(0),
                        "Way2Automation\n" +
                                "CDR Complex, 3rd Floor, Naya Bans Market, Sector 15, Noida, Near sec-16 Metro Station")
                .checkTextOnElement(mainPage.footer.contacts.get(1), CONTACTS.TEL_1_2.getValue())
                .checkTextOnElement(mainPage.footer.contacts.get(2), CONTACTS.TEL_2_2.getValue())
                .checkTextOnElement(mainPage.footer.contacts.get(3), CONTACTS.EMAIL.getValue())
                .checkTextOnElement(mainPage.footer.contacts.get(4), CONTACTS.EMAIL_2.getValue());
    }

    @Test(description = "Проверка меню навигации при скроллинге")
    void test_02() {
        webSteps.scrollToElement(mainPage.footer.footerElement);
        webChecks.checkElementVisible(mainPage.header.navBar);
    }

    @Test(description = "Проверка перехода по меню навигации на другие страницы")
    void test_03() {
        webSteps.clickOnElement(mainPage.getReadMoreButtonByCourseText("Lifetime Membership"));
        webChecks.checkTextOnElement(lifetimeMembershipPage.title, "LIFETIME MEMBERSHIP CLUB")
                .checkNavigateTo(URL.LIFETIME_MEMBERSHIP);
    }
}
