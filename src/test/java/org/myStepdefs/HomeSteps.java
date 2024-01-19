package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.base.BasePage;
import org.helpers.endPoints.FiveTilesDataEndPoint;
import org.pages.MM_HomeScreen;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;
import org.timeUtil.TimeDateClass;

public class HomeSteps {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HomeSteps.class.getName());
    String currentDay;

    @Then("Verify User should be able to open Dashboard page after login.")
    public void verifyUserShouldBeAbleToOpenDashboardPageAfterLogin() {
        MM_HomeScreen home = new MM_HomeScreen();
        SoftAssert soft=new SoftAssert();
        soft.assertTrue(home.isMeraMonitorImageDisplay());
        soft.assertTrue(home.isDashboardTabDisplayed());
        soft.assertEquals(home.getDashboardTabText(), "Dashboard");
        soft.assertEquals(home.getTimeTitle(), "Time");
        soft.assertAll();
        logger.info("User logged into the application Successfully ! ");
    }

    @Then("Verify user should be seen all options on left side.")
    public void verifyUserShouldBeSeenAllOptionsOnLeftSide() {
        MM_HomeScreen home=new MM_HomeScreen();
        SoftAssert soft=new SoftAssert();
        soft.assertEquals(home.getDashboardTabText(),"Dashboard");
        soft.assertEquals(home.getReportTabText(),"Report");
        soft.assertEquals(home.getScreenshotTabText(),"Screenshot");
        soft.assertEquals(home.getTimeClaimTabText(),"Time Claim");
        soft.assertEquals(home.getHolidayTabText(),"Holiday");
        soft.assertAll();
        logger.info("left side panel all option verified successfully !");
    }

    @Then("Verify all {int} tiles are showing on the Dashboard page.")
    public void verifyAllTilesAreShowingOnTheDashboardPage(int arg0) {
        MM_HomeScreen home = new MM_HomeScreen();
        SoftAssert soft=new SoftAssert();
        System.out.println(arg0);
        home.sleepTime(3);
        soft.assertEquals(home.getActiveTittle(), "Active Time");
        soft.assertEquals(home.getIdleTittle(), "Idle Time");
        soft.assertEquals(home.getTotalHoursTittle(), "Total Hours");
        soft.assertEquals(home.getFirstActivityTittle(), "First Activity");
        soft.assertEquals(home.getLastActivityTittle(), "Last Activity");
        soft.assertAll();
        logger.info("successfully validated all tiles on dashboard screen !");
    }

    @Then("Verify user should able to see all data's of current day on all {int} tiles.")
    public void verifyUserShouldAbleToSeeAllDataSOfCurrentDayOnAllTiles(int arg0) {
        MM_HomeScreen home=new MM_HomeScreen();
        FiveTilesDataEndPoint five=new FiveTilesDataEndPoint();
        SoftAssert soft=new SoftAssert();
        soft.assertEquals(home.getFirstActivity(),five.getFiveTilesDataMap().get("firstActivity"));
        soft.assertEquals(home.getLastActivity(),five.getFiveTilesDataMap().get("lastActivity"));
        soft.assertEquals(home.getActiveTimePercent(),five.getFiveTilesDataMap().get("activePercent"));
        soft.assertEquals(home.getIdleTimePercent(),five.getFiveTilesDataMap().get("idlePercent"));
        soft.assertAll();
        logger.info("successfully validated five tiles data !");
    }

    @Then("Verify Productive vs Idle section present on dashboard.")
    public void verifyProductiveVsIdleSectionPresentOnDashboard() {
        MM_HomeScreen home = new MM_HomeScreen();
        Assert.assertEquals(home.getProductivityVsIdleTabText(), "Productivity vs Idle");
        logger.info("Productive vs Idle section successfully !");
    }

    @Then("Verify user should be able to see the web and app time for current week for Website, Application.")
    public void verifyUserShouldBeAbleToSeeTheWebAndAppTimeForCurrentWeekForWebsiteApplication() {
        MM_HomeScreen home = new MM_HomeScreen();
        SoftAssert soft=new SoftAssert();
        home.jsScrollByElement(home.webAndAppsTitle);
        soft.assertEquals(home.getWebAndAppTabText(), "Web & Apps");
        soft.assertEquals(home.getApplicationTitle(), "Application");
        soft.assertEquals(home.getWebsiteTitle(), "Website");
        soft.assertAll();
        logger.info("web and apps section title verified successfully !");
    }

    @And("User click on the report tab.")
    public void userClickOnTheReportTab() {
        MM_HomeScreen home = new MM_HomeScreen();
        home.click_OnReportTab();
        logger.info("clicked on report tab successfully !");
    }

    @And("User click on time tracker option.")
    public void userClickOnTimeTrackerOption() {
        MM_HomeScreen home = new MM_HomeScreen();
        home.click_OnTimeTracker();
        logger.info("clicked on time tracker successfully !");
    }

    @Then("Verify Hours for current day on Time section.")
    public void verifyHoursForCurrentDayOnTimeSection() {
        MM_HomeScreen home=new MM_HomeScreen();
        FiveTilesDataEndPoint five=new FiveTilesDataEndPoint();
        SoftAssert soft=new SoftAssert();
        soft.assertEquals(home.getActiveTime(),five.getFiveTilesDataMap().get("activeTime"));
        soft.assertEquals(home.getIdleTime(),five.getFiveTilesDataMap().get("idleTime"));
        soft.assertEquals(home.getTotalTime(),five.getFiveTilesDataMap().get("totalHours"));
        soft.assertAll();
    }

    @Then("Verify user should be able to see the past attendance from Attendance calendar.")
    public void verifyUserShouldBeAbleToSeeThePastAttendanceFromAttendanceCalendar() {
        MM_HomeScreen home=new MM_HomeScreen();
        SoftAssert soft=new SoftAssert();
        home.jsScrollByElement(home.attendanceTitle);
        soft.assertEquals(home.getAttendanceTitle(),"Attendance");
        soft.assertAll();
    }

    @Then("Verify user should able to see current date on the screen.")
    public void verifyUserShouldAbleToSeeCurrentDateOnTheScreen() {
        MM_HomeScreen home=new MM_HomeScreen();
        currentDay = TimeDateClass.getToDate().replaceAll("-","/");
        logger.info("Current day :"+ currentDay);
        SoftAssert soft=new SoftAssert();
        soft.assertTrue(home.getWeaklyDateOnProductiveVsIdleSection().contains(currentDay));
        soft.assertAll();
    }
}
