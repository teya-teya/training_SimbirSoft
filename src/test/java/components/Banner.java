package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Banner {

    public final By bannerLocator = By.cssSelector("div.dialog-widget-content");

    @FindBy(css = "div.dialog-close-button")
    public WebElement btnX;
}

