package pages;

import components.Banner;
import components.Footer;
import components.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage {
    public WebDriver driver;
    public WebDriverWait wait;

    public final Header header;
    public final Banner banner;
    public final Footer footer;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
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

    public WebElement getReadMoreButtonByCourseText(String courseName) {
        for (WebElement course : courses) {
            if (course.getText().contains(courseName)) {
                return course.findElement(By.cssSelector("a.elementor-button"));
            }
        }
        return null;
    }

}
