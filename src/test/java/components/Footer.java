package components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Footer {

    @FindBy(css = "div[data-elementor-type='footer'")
    public WebElement footerElement;

    @FindBy(css = "div[data-id='695441a0'] li")
    public List<WebElement> contacts;

}
