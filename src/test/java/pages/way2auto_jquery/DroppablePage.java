package pages.way2auto_jquery;

import base.WebSteps;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class DroppablePage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;

    public DroppablePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        this.webSteps = new WebSteps(driver, wait);
    }

    @FindBy(css = "iframe[src='droppable/default.html']")
    public WebElement iframe;

    @FindBy(id = "draggable")
    public WebElement draggable;

    @FindBy(id = "droppable")
    public WebElement droppable;

    @Step("Перетащить элемент в принимающий элемент")
    public DroppablePage moveElement() {
        webSteps.dragAndDrop(draggable, droppable);

        return this;
    }

    @Step("Проверить, что текст в принимающим элементе изменился")
    public void checkChangeText(String initialText) {
        String expectedText = draggable.getText();
        Assert.assertNotEquals(expectedText, initialText,
                "Текст в принимающем элементе должен был измениться");
    }
}
