package pages.way2auto_jquery;

import base.WebSteps;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FramesWindowsPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;

    public FramesWindowsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        this.webSteps = new WebSteps(driver, wait);
    }

    @FindBy(xpath = "//a[text()='New Browser Tab']")
    public WebElement linkNewBrowserTab;

    @Step("Нажать на ссылку 'New Browser Tab'")
    public void clickLinkNewBrowserTab() {
        webSteps.clickOnElement(linkNewBrowserTab);
    }
}
