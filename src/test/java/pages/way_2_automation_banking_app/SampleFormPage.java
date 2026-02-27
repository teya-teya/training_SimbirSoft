package pages.way_2_automation_banking_app;

import base.WebSteps;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SampleFormPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;

    public SampleFormPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.webSteps = new WebSteps(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "gender")
    public WebElement dpdGender;

    @FindBy(name = "hobbies")
    public List<WebElement> hobbies;

    @FindBy(id = "successMessage")
    public WebElement successMessage;

    @FindBy(id = "about")
    public WebElement about;

    @FindBy(xpath = "//button[text()='Register']")
    public WebElement btnRegister;

    /**
     * Метод для получения чекбокса с хобби
     * @param value - хобби
     * @return чекбокс
     */
    private WebElement getCheckbox(String value) {
        for (WebElement checkbox : hobbies) {
            if (checkbox.getAttribute("value").equals(value)) {
                return checkbox;
            }
        }
        return null;
    }

    @Step("Заполнить форму регистрации Sample Form и нажать кнопку 'Register'")
    public void fillSampleFormForUser(String firstName, String lastName, String email,
                                                String password, String gender, String hobby, String infoAbout) {
        webSteps.fillInput(webSteps.getInput("First Name"), firstName)
                .fillInput(webSteps.getInput("Last Name"), lastName)
                .fillInput(webSteps.getInput("Email"), email)
                .fillInput(webSteps.getInput("Password"), password)
                .selectOptionByText(dpdGender, gender)
                .clickOnElement(getCheckbox(hobby))
                .fillInput(about, infoAbout)
                .clickOnElement(btnRegister);
    }
}
