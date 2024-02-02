package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.base.BasePage;
import org.base.BaseTest;
import org.helpers.jsonReader.JsonHelper;
import org.pages.MM_HomeScreen;
import org.pages.MM_ScreenshotScreen;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;
import org.timeUtil.TimeDateClass;

import java.util.function.Supplier;

public class ScreenshotSteps {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ScreenshotSteps.class.getName());
    String email = JsonHelper.getValue("email1").toString();
    String password = JsonHelper.getValue("password1").toString();
    String month = JsonHelper.getValue("month").toString();
    int day = Integer.parseInt(JsonHelper.getValue("day").toString());
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
        } catch (Exception e) {
            logger.info(e.toString());
        }
        logger.info("all the components are verified successfully on the screenshot screen !");
    }

    @Then("Verify the screen shots and screenshot time with api's.")
    public void verifyTheScreenShotsAndScreenshotTimeWithApiS(int date,int day,String month,String email,String password) {
        MM_ScreenshotScreen screenshot = new MM_ScreenshotScreen();

        if (screenshot.foundNoScreenShotText() == null)
            logger.info("screen shot not captured !");
        else
            screenshot.verifyScreenshotsOnScreenshotScreen(TimeDateClass.getCustomDate(date,month),day,month,email,password);
    }

    @And("User select the particular day and month using calender.")
    public void userSelectTheParticularDayAndMonthUsingCalender() {
        int day = Integer.parseInt(System.getProperty("day"));
        String month = System.getProperty("month");
        MM_ScreenshotScreen screenshot = new MM_ScreenshotScreen();
        screenshot.selectOldDate(day, month);
        logger.info("user successfully selected mentioned date and month !");
    }

    @Then("Verify user able to maximize the screenshot image.")
    public void verifyUserAbleToMaximizeTheScreenshotImage() {
        MM_ScreenshotScreen screenshot=new MM_ScreenshotScreen();
        screenshot.scrollToElement(screenshot.slideShowButton);
        screenshot.clickOnMaximizeScreenButton();
        screenshot.sleepTime(2);

    }

    @Then("Verify user should be able to minimize the screenshot images.")
    public void verifyUserShouldBeAbleToMinimizeTheScreenshotImages() {
        MM_ScreenshotScreen screenshot=new MM_ScreenshotScreen();
        screenshot.clickOnMaximizeScreenButton();
    }

    @Then("Verify user can start and stop the slideshow of the screenshot.")
    public void verifyUserCanStartAndStopTheSlideshowOfTheScreenshot() {
        MM_ScreenshotScreen screenshot=new MM_ScreenshotScreen();
        screenshot.clickOnSlideShowButton();
        screenshot.sleepTime(3);
        screenshot.clickOnSlideShowButton();
    }

    @Then("Verify user should able to change the date.")
    public void verifyUserShouldAbleToChangeTheDate() {
        MM_ScreenshotScreen screenshot=new MM_ScreenshotScreen();
        screenshot.sleepTime(2);
        //screenshot.scrollToElement(screenshot.logo);
        screenshot.jsScrollToTop();
        screenshot.sleepTime(2);
        String day = System.getProperty("day");
        String month = System.getProperty("month");
        screenshot.selectOldDate(Integer.parseInt(day),month);
        screenshot.sleepTime(2);
    }
}
