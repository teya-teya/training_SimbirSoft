package pages.way2auto_jquery;

import base.WebChecks;
import base.WebSteps;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;
    public WebChecks webChecks;

    public AlertPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        this.webSteps = new WebSteps(driver, wait);
        this.webChecks = new WebChecks(driver, wait);
    }

    @FindBy(xpath = "//a[text()='Input Alert']")
    public WebElement tabInputAlert;

    @FindBy(css = "iframe[src='alert/input-alert.html']")
    public WebElement iframe;

    @FindBy(xpath = "//button[text()='Click the button to demonstrate the Input box.']")
    public WebElement buttonDemonstrate;

    @FindBy(id = "demo")
    public WebElement blockText;

    @Step("Нажать на вкладку 'Input Alert'")
    public AlertPage clickTabInputAlert() {
        webSteps.clickOnElement(tabInputAlert);

        return this;
    }

    @Step("Нажать на кнопку 'Input Alert'")
    public AlertPage clickButtonDemonstrate() {
        webSteps.switchIframe(iframe)
                .clickOnElement(buttonDemonstrate);

        return this;
    }

    @Step("Заполнить поле браузерного уведомления и нажать кнопку 'Ок'")
    public AlertPage fillAndClickOkAlert(String name) {
        webSteps.fillInputAlert(name)
                .clickOkInAlert();

        return this;
    }

    @Step("Проверить, что текстовом блоке отображается текст 'Hello {name}! How are you today?'")
    public void checkTextInBlock(String name) {
        webChecks.checkTextOnElement(blockText, "Hello " + name + "! How are you today?");
    }
}
