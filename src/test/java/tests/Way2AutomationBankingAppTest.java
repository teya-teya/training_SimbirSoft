package tests;

import base.WebChecks;
import base.WebSteps;
import enums.URL;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.way_2_automation_banking_app.*;
import utils.RandomUtils;
import utils.WaitHelper;

@Epic("UI тесты")
@Feature("Way2Automation Banking App")
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

    @Story("Регистрация пользователя с корректными данными на Sample Form")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка регистрации с корректными данными")
    void checkUserRegistrationWithValidData() {
        loginPage.clickBtnSampleForm();

        String longestValue = webSteps.getLongestValue(sampleFormPage.hobbies);

        sampleFormPage.fillSampleFormForUser(nameParts[0], nameParts[1], RandomUtils.email(), RandomUtils.password(),
                        "Other", "Sports", longestValue)
                .checkMessage();

    }

    @Story("Добавление нового клиента через Bank Manager")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка добавления покупателя")
    void checkAddingNewCustomerWithValidData() {
        loginPage.clickBtnBankManagerLogin();

        managerPage.clickBtnAddCustomer()
                .addCustomer(nameParts[0], nameParts[1], RandomUtils.postCode());
    }

    @Story("Создание счета для существующего клиента")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка создания учетной записи")
    void checkAccountCreationForExistingCustomer() {
        loginPage.clickBtnBankManagerLogin();

        managerPage.clickBtnAddCustomer()
                .addCustomer(nameParts[0], nameParts[1], RandomUtils.postCode());
        managerPage.openAccount(nameParts[0] + " " + nameParts[1], "Dollar");
    }

    @DataProvider(name = "depositData")
    public Object[][] depositData() {
        return new Object[][]{
                {100321, "Deposit Successful"},
                {0, null}
        };
    }

    @Story("Пополнение счета клиента на валидную сумму и на 0")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка пополнения счета", dataProvider = "depositData")
    void checkDepositAmount(int amount, String expectedMessage) {
        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Rupee");

        webSteps.goToPage(URL.LOGIN.getUrl());
        loginPage.clickBtnCustomerLogin();
        customerPage.login(nameParts[0] + " " + nameParts[1]);

        accountPage.checkSuccessLogin(nameParts[0] + " " + nameParts[1])
                .deposit(amount)
                .checkMessage(expectedMessage);
    }

    @Story("Снятие средств при достаточном балансе")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка успешного снятия средств")
    public void checkSuccessfulWithdrawalWhenBalanceIsSufficient() {

        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Pound");

        webSteps.goToPage(URL.LOGIN.getUrl());
        loginPage.clickBtnCustomerLogin();
        customerPage.login(nameParts[0] + " " + nameParts[1]);

        accountPage.checkSuccessLogin(nameParts[0] + " " + nameParts[1])
                .deposit(RandomUtils.getRandomNum(10, 999999));

        int balance = accountPage.getBalance();
        int amount = RandomUtils.getRandomNum(1, balance);

        accountPage.withdrawl(String.valueOf(amount))
                .checkMessage("Transaction successful");

        webSteps.refreshPage();
        accountPage.clickBtnTransactions();
        WaitHelper.waitForVisible(wait, transactionsPage.table);
        WaitHelper.waitForVisible(wait, transactionsPage.getRows().get(1));
        transactionsPage.checkTextInCell(transactionsPage.getTransactionCell(1, 1), String.valueOf(amount));
    }

    @Story("Попытка снять средства при недостаточном балансе")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка неуспешного снятия средств")
    public void checkFailedWithdrawalWhenBalanceIsInsufficient() {

        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Pound");

        webSteps.goToPage(URL.LOGIN.getUrl());
        loginPage.clickBtnCustomerLogin();
        customerPage.login(nameParts[0] + " " + nameParts[1]);

        accountPage.checkSuccessLogin(nameParts[0] + " " + nameParts[1])
                .deposit(RandomUtils.getRandomNum(10, 999999));

        int amount = 1000000;
        String textAmount = String.valueOf(amount);

        accountPage.withdrawl(textAmount)
                .checkMessage("Transaction Failed. You can not withdraw amount more than the balance.");

        webSteps.refreshPage();
        accountPage.clickBtnTransactions();
        WaitHelper.waitForVisible(wait, transactionsPage.table);
        transactionsPage.checkCellNotPresent(transactionsPage.getCellsByText(textAmount), textAmount);
    }

    @Story("Проверка обновления баланса после снятия средств")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка баланса")
    void checkBalanceUpdateAfterWithdrawal() {
        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Pound");
        webSteps.goToPage(URL.LOGIN.getUrl());
        loginPage.clickBtnCustomerLogin();
        customerPage.login(nameParts[0] + " " + nameParts[1]);
        accountPage.checkSuccessLogin(nameParts[0] + " " + nameParts[1])
                .deposit(RandomUtils.getRandomNum(10, 999999));

        int balance = accountPage.getBalance();
        int amount = RandomUtils.getRandomNum(1, balance);

        accountPage.withdrawl(String.valueOf(amount))
                .checkMessage("Transaction successful");
        balance = accountPage.getBalance();

        webSteps.refreshPage();
        accountPage.clickBtnTransactions();
        WaitHelper.waitForVisible(wait, transactionsPage.table);
        transactionsPage.checkBalance(balance);
    }

    @Story("Снятие всех оставшихся средств до нуля")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка снятия оставшихся средств")
    void checkWithdrawalOfRemainingFunds() {
        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Dollar");
        webSteps.goToPage(URL.LOGIN.getUrl());
        loginPage.clickBtnCustomerLogin();
        customerPage.login(nameParts[0] + " " + nameParts[1]);
        accountPage.checkSuccessLogin(nameParts[0] + " " + nameParts[1])
                .deposit(RandomUtils.getRandomNum(10, 999999));

        int balance = accountPage.getBalance();
        int amount = RandomUtils.getRandomNum(1, balance);

        accountPage.withdrawl(String.valueOf(amount))
                .checkMessage("Transaction successful");
        balance = accountPage.getBalance();

        accountPage.withdrawl(String.valueOf(balance))
                .checkMessage("Transaction successful")
                .checkBalance("0");
    }

    @Story("Очистка истории транзакций после операций")
    @Severity(SeverityLevel.MINOR)
    @Test(description = "Проверка очистки истории транзакций")
    void checkTransactionHistoryClearing() {
        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Dollar");
        webSteps.goToPage(URL.LOGIN.getUrl());
        loginPage.clickBtnCustomerLogin();
        customerPage.login(nameParts[0] + " " + nameParts[1]);
        accountPage.checkSuccessLogin(nameParts[0] + " " + nameParts[1])
                .deposit(RandomUtils.getRandomNum(10, 999999));

        int balance = accountPage.getBalance();
        int amount = RandomUtils.getRandomNum(1, balance);

        accountPage.withdrawl(String.valueOf(amount))
                .checkMessage("Transaction successful");
        webSteps.refreshPage();
        accountPage.clickBtnTransactions();

        WaitHelper.waitForVisible(wait, transactionsPage.table);

        transactionsPage.clickBtnReset()
                .checkTransactionsEmpty()
                .clickBtnBack();
        accountPage.checkBalance("0");
    }

    @Story("Удаление клиента из списка через Bank Manager")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка удаление покупателя")
    void checkCustomerDeletionFromList() {
        managerPage.createCustomerWithAccount(nameParts[0], nameParts[1], RandomUtils.postCode(), "Rupee")
                .clickBtnCustomer()
                .fillInputSearchCustomer(nameParts[0])
                .checkSearch(nameParts[0])
                .deleteCustomerByFirstName(nameParts[0])
                .cleanInputSearchCustomer()
                .checkNameNotPresent(nameParts[0]);
    }

}
