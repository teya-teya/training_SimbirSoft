package pages.way_2_automation_banking_app;

import base.WebSteps;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.WaitHelper;

import java.util.List;

import static enums.URL.ADD_CUSTOMER;

public class ManagerPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public WebSteps webSteps;

    public ManagerPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.webSteps = new WebSteps(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='center']/button[contains(text(), 'Add Customer')]")
    public WebElement btnAddCustomer;

    @FindBy(xpath = "//button[contains(text(), 'Open Account')]")
    public WebElement btnOpenAccount;

    @FindBy(xpath = "//button[contains(text(), 'Customers')]")
    public WebElement btnCustomers;

    @FindBy(xpath = "//form/button[text()='Add Customer']")
    public WebElement btnAddCustomerForForm;

    @FindBy(id = "userSelect")
    public WebElement dpdUserSelect;

    @FindBy(id = "currency")
    public WebElement dpdCurrency;

    @FindBy(xpath = "//button[text()='Process']")
    public WebElement btnProcess;

    @FindBy(css = "[placeholder='Search Customer']")
    public WebElement inputSearchCustomer;

    @Step("Добавить покупателя {firstNane} {lastName}")
    public void addCustomer(String firstNane, String lastName, String postCode) {
        WaitHelper.waitForVisible(wait, btnAddCustomerForForm);

        webSteps.fillInput(webSteps.getInput("First Name"), firstNane)
                .fillInput(webSteps.getInput("Last Name"), lastName)
                .fillInput(webSteps.getInput("Post Code"), postCode)
                .clickOnElement(btnAddCustomerForForm)
                .clickOkInAlert();

    }

    @Step("Создать аккаунт покупателя {fullName}")
    public void openAccount(String fullName, String currency) {
        webSteps.clickOnElement(btnOpenAccount)
                .selectOptionByText(dpdUserSelect, fullName)
                .selectOptionByText(dpdCurrency, currency)
                .clickOnElement(btnProcess)
                .clickOkInAlert();
    }

    @Step("Создать аккаунт покупателя {fullName}")
    public ManagerPage createCustomerWithAccount(String firstNane, String lastName, String postCode, String currency) {
        webSteps.goToPage(ADD_CUSTOMER.getUrl());
        addCustomer(firstNane, lastName, postCode);
        openAccount(firstNane + " " + lastName, currency);

        return this;
    }

    @Step("Удалить покупателя {firstName}")
    public ManagerPage deleteCustomerByFirstName(String firstName) {
        WebElement deleteBtn = driver.findElement(By.xpath(
                "//table//tbody/tr[td[1][text()='" + firstName + "']]//button[text()='Delete']"
        ));

        webSteps.clickOnElement(deleteBtn);

        return this;
    }

    /**
     * Метод для получения строк в таблице
     * @return строки таблицы
     */
    private List<WebElement> getRows() {
        return driver.findElements(By.cssSelector("table tbody tr"));
    }

    @Step("Нажать кнопку 'Add Customer'")
    public ManagerPage clickBtnAddCustomer() {
        webSteps.clickOnElement(btnAddCustomer);

        return this;
    }

    @Step("Нажать кнопку 'Customers'")
    public ManagerPage clickBtnCustomer() {
        webSteps.clickOnElement(btnCustomers);

        return this;
    }

    @Step("Очистить поле поиска покупателей")
    public ManagerPage cleanInputSearchCustomer() {
        webSteps.cleanInput(inputSearchCustomer);

        return this;
    }

    @Step("Заполнить поле поиска покупателей текстом {text}")
    public ManagerPage fillInputSearchCustomer(String text) {
        webSteps.fillInput(inputSearchCustomer, text);

        return this;
    }

    @Step("Проверить поиск покупателя по имени {name}")
    public ManagerPage checkSearch(String name) {
        List<WebElement> rows = getRows();
        Assert.assertEquals(rows.size(), 1, "Ожидалось ровно одна строка после поиска");

        WebElement firstCell = rows.get(0).findElement(By.cssSelector("td:first-child"));
        Assert.assertEquals(firstCell.getText().trim(), name, "First Name не совпадает");

        return this;
    }

    @Step("Проверить, что в списке нет покупателя по имени {name}")
    public void checkNameNotPresent(String name) {
        List<WebElement> rows = getRows();

        for (WebElement row : rows) {
            WebElement firstCell = row.findElement(By.cssSelector("td:first-child"));
            String cellText = firstCell.getText().trim();
            Assert.assertNotEquals(cellText, name,
                    "Имя '" + name + "' всё ещё присутствует в таблице!");
        }
    }
}
