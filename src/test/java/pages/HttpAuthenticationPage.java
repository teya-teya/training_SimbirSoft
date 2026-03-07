package pages;

import base.WebChecks;
import base.WebSteps;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class HttpAuthenticationPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;
    public WebChecks webChecks;

    public HttpAuthenticationPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.webSteps = new WebSteps(driver, wait);
        this.webChecks = new WebChecks(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "displayImage")
    public WebElement buttonDisplayImage;

    @FindBy(id = "downloadImg")
    public WebElement authenticatedImage;

    @Step("Нажать на кнопку 'DISPLAY IMAGE'")
    public HttpAuthenticationPage scrollAndClickBtnDisplayImage() {
        webSteps.scrollToElement(buttonDisplayImage)
                .clickOnElement(buttonDisplayImage);

        return this;
    }

    @Step("Пройти авторизацию через Basic Auth с логином {login}")
    public HttpAuthenticationPage enableBasicAuth(String login, String password) {
        try {
            Thread.sleep(2000);

            Robot robot = new Robot();
            StringSelection loginSelection = new StringSelection(login);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(loginSelection, null);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(200);

            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(200);

            StringSelection passwordSelection = new StringSelection(password);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(passwordSelection, null);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(200);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    @Step("Проверка успешной авторизации")
    public void checkSuccessAuthentication() {
        webChecks.checkElementVisible(authenticatedImage);
    }
}

