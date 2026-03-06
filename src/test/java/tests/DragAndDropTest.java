package tests;

import base.WebSteps;
import enums.URL;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DroppablePage;

@Epic("UI тесты")
@Feature("Drag n Drop (IFrame)")
@Test(description = "Проверка переноса элемента")
public class DragAndDropTest extends BaseTest {
    WebSteps webSteps;
    DroppablePage droppablePage;

    @BeforeMethod
    void setUp() {
        webSteps = new WebSteps(driver, wait);
        droppablePage = new DroppablePage(driver, wait);

        webSteps.goToPage(URL.DROPPABLE.getUrl());
    }

    @Story("Проверка переноса элемента")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка переноса элемента")
    void checkDragAndDrop() {
        webSteps.switchIframe(droppablePage.iframe);
        String initialText = webSteps.getText(droppablePage.droppable);

        droppablePage.moveElement()
                .checkChangeText(initialText);
    }
}
