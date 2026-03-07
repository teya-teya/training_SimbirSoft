package tests;

import base.WebChecks;
import base.WebSteps;
import enums.URL;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.way2auto_jquery.AlertPage;
import pages.way2auto_jquery.DroppablePage;
import pages.way2auto_jquery.FramesAndWindowsPage;
import pages.way2auto_jquery.FramesWindowsPage;
import utils.RandomUtils;

@Epic("UI тесты")
@Feature("way2auto jquery")
public class Way2AutoJqueryTest extends BaseTest {
    WebSteps webSteps;
    WebChecks webChecks;
    DroppablePage droppablePage;
    FramesAndWindowsPage framesAndWindowsPage;
    FramesWindowsPage framesWindowsPage;
    AlertPage alertPage;

    @BeforeMethod
    void setUp() {
        webSteps = new WebSteps(driver, wait);
        webChecks = new WebChecks(driver, wait);
        droppablePage = new DroppablePage(driver, wait);
        framesAndWindowsPage = new FramesAndWindowsPage(driver, wait);
        framesWindowsPage = new FramesWindowsPage(driver, wait);
        alertPage = new AlertPage(driver, wait);
    }

    @Story("Проверка переноса элемента")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка переноса элемента")
    void checkDragAndDrop() {
        webSteps.goToPage(URL.DROPPABLE.getUrl())
                .switchIframe(droppablePage.iframe);
        String initialText = webSteps.getText(droppablePage.droppable);

        droppablePage.moveElement()
                .checkChangeText(initialText);
    }

    @Story("Проверка вкладок")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка перехода на другие вкладки браузера")
    void checkTabsBrowser() {
        webSteps.goToPage(URL.FRAMES_AND_WINDOWS.getUrl())
                .switchIframe(framesAndWindowsPage.iframe);

        framesAndWindowsPage.clickLinkNewBrowserTab();

        webSteps.switchToTab(2);

        framesWindowsPage.clickLinkNewBrowserTab();

        webChecks.checkCountTabsBrowser(3);
    }

    @Story("Проверка браузерного уведомления")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка вставки текста в браузерное уведомление")
    void checkAlert() {
        webSteps.goToPage(URL.ALERT.getUrl());
        String name = RandomUtils.fullName();
        alertPage.clickTabInputAlert()
                .clickButtonDemonstrate()
                .fillAndClickOkAlert(name)
                .checkTextInBlock(name);
    }
}
