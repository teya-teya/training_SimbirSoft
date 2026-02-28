package pages;

import base.WebChecks;
import base.WebSteps;
import components.Banner;
import components.Footer;
import components.Header;
import enums.CONTACTS;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class MainPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebChecks webChecks;
    public WebSteps webSteps;
    public final Header header;
    public final Banner banner;
    public final Footer footer;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        this.webChecks = new WebChecks(driver, wait);
        this.webSteps = new WebSteps(driver, wait);
        this.header = new Header();
        PageFactory.initElements(driver, this.header);
        this.banner = new Banner();
        PageFactory.initElements(driver, this.banner);
        this.footer = new Footer();
        PageFactory.initElements(driver, this.footer);
    }

    @FindBy(css = "section[data-id='5b4952c1'] div[data-element_type='column']")
    public List<WebElement> courses;

    @FindBy(xpath = "//div[contains(@class, 'swiper-slide-active')]//a[text()='Register Now']")
    public WebElement btnRegisterNow;

    @FindBy(xpath = "//div[contains(@class, 'swiper-slide-active')]//a[text()='Learn More']")
    public WebElement btnLearnMore;

    public By locatorLearnMore = By.xpath("//div[contains(@class, 'swiper-slide-active')]//a[text()='Learn More']");

    @Step("Кликнуть на кнопку 'Read More' курса '{courseName}'")
    public WebElement getReadMoreButtonByCourseText(String courseName) {
        for (WebElement course : courses) {
            if (course.getText().contains(courseName)) {
                return course.findElement(By.cssSelector("a.elementor-button"));
            }
        }
        return null;
    }

    @Step("Закрыть баннер")
    public void closeBanner() {
        if (webChecks.isPresent(banner.bannerLocator)) {
            webSteps.clickOnElement(banner.btnX);
        }
    }

    @Step("Проверить, что блок с контактами отображается")
    public MainPage checkInfo() {
        webChecks.checkElementVisible(header.contactsBlock);

        return this;
    }

    @Step("Проверить, что навигационное меню отображается")
    public MainPage checkNavBar() {
        webChecks.checkElementVisible(header.navBar);

        return this;
    }

    @Step("Проверить, что курсы отображаются")
    public MainPage checkCourses() {
        webChecks.checkElementsVisible(courses);

        return this;
    }

    @Step("Проверить, что футер отображается")
    public MainPage checkFooter() {
        webChecks.checkElementVisible(footer.footerElement);

        return this;
    }

    @Step("Проверить, что кнопка 'Register Now' отображается")
    public void checkBtnRegisterNow() {
        webSteps.waitElementNotVisible(locatorLearnMore, btnLearnMore);
        webChecks.checkElementVisible(btnRegisterNow);
    }

    @Step("Проверить, что отображаются контактные данные")
    public MainPage checkContacts() {
        List<String> contacts = new ArrayList<>();
        for (CONTACTS contact : CONTACTS.values()) {
            contacts.add(contact.getValue());
        }
        webChecks.checkTextOnElementCollection(header.contacts, contacts);

        return this;
    }

    @Step("Проверить, что отображается ссылка на {}")
    public MainPage checkSocialPage(String socialPage) {
        webChecks.checkElementVisible(header.getLinkSocialsPage(socialPage));

        return this;
    }

    @Step("Проверить, что отображается футер с контактной информацией")
    public void checkContactFooter() {
        webChecks.checkElementVisible(footer.footerElement)
                .checkTextOnElement(footer.contacts.get(0), "Way2Automation\n" +
                                "CDR Complex, 3rd Floor, Naya Bans Market, Sector 15, Noida, Near sec-16 Metro Station")
                .checkTextOnElement(footer.contacts.get(1), CONTACTS.TEL_1_2.getValue())
                .checkTextOnElement(footer.contacts.get(2),  CONTACTS.TEL_2_2.getValue())
                .checkTextOnElement(footer.contacts.get(3),  CONTACTS.EMAIL.getValue())
                .checkTextOnElement(footer.contacts.get(4), CONTACTS.EMAIL_2.getValue());
    }

    @Step("Проскролить к футеру")
    public MainPage scrollToFooter() {
        webSteps.scrollToElement(footer.footerElement);

        return this;
    }

    @Step("Нажать кнопку 'ReadMore' курса 'Lifetime Membership'")
    public void clickBtnReadMoreForLifetimeMembership() {
        webSteps.clickOnElement(getReadMoreButtonByCourseText("Lifetime Membership"));
    }
}
