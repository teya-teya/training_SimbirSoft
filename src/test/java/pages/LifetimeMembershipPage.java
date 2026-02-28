package pages;

import base.WebChecks;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LifetimeMembershipPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebChecks webChecks;

    public LifetimeMembershipPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.webChecks = new WebChecks(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "section h1.elementor-heading-title")
    public WebElement title;

    @Step("Проверить отображение заголовка 'LIFETIME MEMBERSHIP CLUB'")
    public void checkTitle() {
        webChecks.checkTextOnElement(title, "LIFETIME MEMBERSHIP CLUB");
    }

}
