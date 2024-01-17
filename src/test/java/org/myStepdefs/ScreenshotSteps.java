package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.base.BasePage;
import org.base.BaseTest;
import org.pages.MM_HomeScreen;
import org.pages.MM_ScreenshotScreen;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import java.util.function.Supplier;

public class ScreenshotSteps {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ScreenshotSteps.class.getName());
    @And("User click on Screenshot tab.")
    public void userClickOnScreenshotTab() {
        MM_HomeScreen home = new MM_HomeScreen();
        home.click_onScreenShot_Tab();
        logger.info("clicked on the screenshot tab successfully !");
    }

    @Then("Verify all the components on the screenshot screen.")
    public void verifyAllTheComponentsOnTheScreenshotScreen() {
        MM_ScreenshotScreen screenshot = new MM_ScreenshotScreen();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(screenshot.getScreenshotTitle(), "");
        soft.assertTrue(screenshot.isDatePickerDisplay());
        try {
            if (screenshot.isNextSlidButtonDisplayed()) {
                soft.assertTrue(screenshot.isNextSlidButtonDisplayed());
                soft.assertTrue(screenshot.isPreviousButtonDisplayed());
                BaseTest base = new BaseTest();
                base.scrollToElement(screenshot.playButton);
                soft.assertTrue(screenshot.isPlayButtonDisplayed());
            } else System.out.println("screenshot not captured for the current day.");
        } catch (Exception ex) {
            logger.info((Supplier<String>) ex);
        }
        logger.info("all the components are verified successfully on the screenshot screen !");
    }

    @Then("Verify the screen shots and screenshot time with api's.")
    public void verifyTheScreenShotsAndScreenshotTimeWithApiS() {
        MM_ScreenshotScreen screenshot=new MM_ScreenshotScreen();
        screenshot.verifyScreenshotsOnScreenshotScreen();
    }

    @And("User select the particular day and month using calender.")
    public void userSelectTheParticularDayAndMonthUsingCalender() {
        int day = Integer.parseInt(System.getProperty("day"));
        String month = System.getProperty("day");
        MM_ScreenshotScreen screenshot=new MM_ScreenshotScreen();
        screenshot.selectOldDate(day,month);
        logger.info("user successfully selected mentioned date and month !");
    }
}
