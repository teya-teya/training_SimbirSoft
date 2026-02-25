package tests;

import base.WebChecks;
import base.WebSteps;
import enums.URL;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.way_2_automation_banking_app.*;
import utils.RandomUtils;
import utils.WaitHelper;

@Test(description = "Страница Way2Automation Banking App")
public class Way2AutomationBankingAppTest extends BaseTest {
    LoginPage loginPage;
    WebChecks webChecks;
    WebSteps webSteps;
    SampleFormPage sampleFormPage;
    ManagerPage managerPage;
    CustomerPage customerPage;
    AccountPage accountPage;
    TransactionsPage transactionsPage;
    String[] nameParts;

    @BeforeMethod
    void setUp() {
        webChecks = new WebChecks(driver, wait);
        loginPage = new LoginPage(driver, wait);
        webSteps = new WebSteps(driver, wait);
        sampleFormPage = new SampleFormPage(driver, wait);
        managerPage = new ManagerPage(driver, wait);
        customerPage = new CustomerPage(driver, wait);
        accountPage = new AccountPage(driver, wait);
        transactionsPage = new TransactionsPage(driver, wait);
        nameParts = RandomUtils.fullName().split(" ");

        webSteps.goToPage(URL.LOGIN.getUrl());
    }

    @Test(description = "Проверка регистрации с корректными данными")
    void test_05_1() {
        webSteps.clickOnElement(loginPage.btnSampleForm);

        String longestValue = webSteps.getLongestValue(sampleFormPage.hobbies);

        sampleFormPage.fillSampleFormForUser(nameParts[0], nameParts[1], RandomUtils.email(), RandomUtils.password(),
                "Other", "Sports", longestValue);
        webChecks.checkTextOnElement(sampleFormPage.successMessage, "User registered successfully!");

    }

    @Test(description = "Проверка добавления покупателя")
    void test_05_2_1() {
        webSteps.clickOnElement(loginPage.btnBankManagerLogin)
                .clickOnElement(managerPage.btnAddCustomer);

        managerPage.addCustomer(nameParts[0], nameParts[1], RandomUtils.postCode());
    }

    @Test(description = "Проверка создания учетной записи")
    void test_05_2_2() {
        webSteps.clickOnElement(loginPage.btnBankManagerLogin)
                .clickOnElement(managerPage.btnAddCustomer);

        managerPage.addCustomer(nameParts[0], nameParts[1], RandomUtils.postCode());
        managerPage.openAccount(nameParts[0] + " " + nameParts[1], "Dollar");
    }

    @DataProvider(name = "depositData")
    public Object[][] depositData() {
        return new Object[][]{
                {100321, "Deposit Successful"},
                {0, null}
        };
    }

    @Test(description = "Проверка пополнения счета", dataProvider = "depositData")
    void test_05_3_1(int amount, String expectedMessage) {
        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Rupee");

        webSteps.goToPage(URL.LOGIN.getUrl())
                .clickOnElement(loginPage.btnCustomerLogin);

        customerPage.login(nameParts[0] + " " + nameParts[1]);

        accountPage.deposit(amount)
                .checkMessage(expectedMessage);
    }

    @DataProvider(name = "withdrawData")
    public Object[][] withdrawData() {
        return new Object[][]{
                {true, null},
                {false, 1000000}
        };
    }

    @Test(description = "Проверка спешного снятия средств")
    public void test_05_3_3() {

        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Pound");

        webSteps.goToPage(URL.LOGIN.getUrl())
                .clickOnElement(loginPage.btnCustomerLogin);

        customerPage.login(nameParts[0] + " " + nameParts[1]);

        accountPage.deposit(RandomUtils.getRandomNum(10, 999999));

        int balance = accountPage.getBalance();
        int amount = RandomUtils.getRandomNum(1, balance);

        accountPage.withdrawl(String.valueOf(amount));

        webChecks.checkTextOnElement(accountPage.message, "Transaction successful");

        webSteps.refreshPage()
                .clickOnElement(accountPage.btnTransactions);
        WaitHelper.waitForVisible(wait, transactionsPage.table);

        webChecks.checkTextOnElement(transactionsPage.getTransactionCell(1, 1), String.valueOf(amount));
    }

    @Test(description = "Проверка неуспешного снятия средств")
    public void test_05_3_4() {

        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Pound");

        webSteps.goToPage(URL.LOGIN.getUrl())
                .clickOnElement(loginPage.btnCustomerLogin);

        customerPage.login(nameParts[0] + " " + nameParts[1]);

        accountPage.deposit(RandomUtils.getRandomNum(10, 999999));

        int amount = 1000000;
        String textAmount = String.valueOf(amount);

        accountPage.withdrawl(textAmount);

        webChecks.checkTextOnElement(accountPage.message, "Transaction Failed. You can not withdraw amount more than the balance.");

        webSteps.refreshPage()
                .clickOnElement(accountPage.btnTransactions);
        WaitHelper.waitForVisible(wait, transactionsPage.table);

        webChecks.checkElementNotPresent(transactionsPage.getCellsByText(textAmount), textAmount);
    }

    @Test(description = "Проверка баланса")
    void test_05_3_5() {
        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Pound");
        webSteps.goToPage(URL.LOGIN.getUrl())
                .clickOnElement(loginPage.btnCustomerLogin);
        customerPage.login(nameParts[0] + " " + nameParts[1]);
        accountPage.deposit(RandomUtils.getRandomNum(10, 999999));

        int balance = accountPage.getBalance();
        int amount = RandomUtils.getRandomNum(1, balance);

        accountPage.withdrawl(String.valueOf(amount));
        webChecks.checkTextOnElement(accountPage.message, "Transaction successful");
        balance = accountPage.getBalance();

        webSteps.refreshPage()
                .clickOnElement(accountPage.btnTransactions);
        WaitHelper.waitForVisible(wait, transactionsPage.table);
        transactionsPage.checkBalance(balance);
    }

    @Test(description = "Проверка снятия оставшихся средств")
    void test_05_3_6() {
        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Dollar");
        webSteps.goToPage(URL.LOGIN.getUrl())
                .clickOnElement(loginPage.btnCustomerLogin);
        customerPage.login(nameParts[0] + " " + nameParts[1]);
        accountPage.deposit(RandomUtils.getRandomNum(10, 999999));

        int balance = accountPage.getBalance();
        int amount = RandomUtils.getRandomNum(1, balance);

        accountPage.withdrawl(String.valueOf(amount));
        webChecks.checkTextOnElement(accountPage.message, "Transaction successful");
        balance = accountPage.getBalance();

        accountPage.withdrawl(String.valueOf(balance));
        webChecks.checkTextOnElement(accountPage.message, "Transaction successful");
        webChecks.checkTextOnElement(accountPage.balance, "0");
    }

    @Test(description = "Проверка очистки истории транзакций")
    void test_05_3_7() {
        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Dollar");
        webSteps.goToPage(URL.LOGIN.getUrl())
                .clickOnElement(loginPage.btnCustomerLogin);
        customerPage.login(nameParts[0] + " " + nameParts[1]);
        accountPage.deposit(RandomUtils.getRandomNum(10, 999999));

        int balance = accountPage.getBalance();
        int amount = RandomUtils.getRandomNum(1, balance);

        accountPage.withdrawl(String.valueOf(amount));
        webChecks.checkTextOnElement(accountPage.message, "Transaction successful");
        webSteps.refreshPage()
                .clickOnElement(accountPage.btnTransactions);

        WaitHelper.waitForVisible(wait, transactionsPage.table);

        webSteps.clickOnElement(transactionsPage.btnReset);
        webChecks.checkElementNotPresent(transactionsPage.getRows(), "Credit или Debit");

        webSteps.clickOnElement(transactionsPage.btnBack);
        webChecks.checkTextOnElement(accountPage.balance, "0");
    }

    @Test(description = "Проверка удаление покупателя")
    void test_05_4() {
        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Rupee");
        webSteps.clickOnElement(managerPage.btnCustomers)
                .fillInput(managerPage.inputSearchCustomer, nameParts[0]);
        managerPage.checkSearch(nameParts[0])
                .deleteCustomerByFirstName(nameParts[0]);
        webSteps.cleanInput(managerPage.inputSearchCustomer);
        managerPage.assertNameNotPresent(nameParts[0]);
    }

}
