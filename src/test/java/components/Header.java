package components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

public class Header {
    @FindBy(css = "div[data-section='section-above-header-builder'] div.ast-builder-grid-row")
    public WebElement contactsBlock;

    @FindBy(css = "div[data-id='df805ff'] ul li a")
    public List<WebElement> contacts;

    @FindBy(css = "div[data-section='section-hb-social-icons-1'] a")
    public List<WebElement> linksSocialPage;

    @FindBy(xpath = "(//div[@data-section='section-primary-header-builder']//div[contains(@class,'ast-builder-grid-row')])[1]")
    public WebElement navBar;

    public WebElement getLinkSocialsPage(String socialPage) {
        return linksSocialPage.stream()
                .filter(link -> socialPage.equals(link.getAttribute("aria-label")))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Ссылка соцсети не найдена: " + socialPage));
    }

}
