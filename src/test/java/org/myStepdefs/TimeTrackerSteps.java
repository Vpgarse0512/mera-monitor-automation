package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import org.TestClasses.WebUserTestingClass;
import org.helpers.endPoints.NewTimeTrackerEndPoint;
import org.helpers.endPoints.TimeTrackerEndPoints;
import org.pages.MM_TimeClaimStatusScreen;
import org.pages.MM_TimeTrackerScreen;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;
import org.timeUtil.TimeDateClass;
import org.utilities.StringUtil;

import java.util.LinkedHashMap;

public class TimeTrackerSteps {

    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TimeTrackerSteps.class.getName());

    @Then("Verify user can see the data in time for a particular day.")
    public void verifyUserCanSeeTheDataInTimeForAParticularDay() {

    }

    @Then("Verify user is able to see the data for a date range.")
    public void verifyUserIsAbleToSeeTheDataForADateRange() {
        MM_TimeTrackerScreen tracker=new MM_TimeTrackerScreen();
        Assert.assertEquals(tracker.getUserName(),"");
        Assert.assertEquals(tracker.getDate(),"");
        Assert.assertEquals(tracker.getDepartment(),"");
    }

    @Then("Verify user's Active time, Idle Time and Total Time Data on time tracker page should be correct.")
    public void verifyUserSActiveTimeIdleTimeAndTotalTimeDataOnTimeTrackerPageShouldBeCorrect() {
       /* expected [01/03/2024] but found [03/01/2024],
        expected [00:00:00] but found [-],
        expected [1/3/2024 4:07:00 PM] but found [04:07:00 PM],
        expected [1/3/2024 7:04:31 PM] but found [07:04:31 PM]*/
        SoftAssert soft=new SoftAssert();
        MM_TimeTrackerScreen timeTracker = new MM_TimeTrackerScreen();
        Assert.assertEquals(timeTracker.getTimeTrackerTittle(), "Time Tracker");
        int day=Integer.parseInt(System.getProperty("day"));
        LinkedHashMap<String, Object> apis = new NewTimeTrackerEndPoint().getTimeTrackerMapData(day);
        soft.assertEquals(timeTracker.getUserName(), apis.get("name"));
        soft.assertEquals(timeTracker.getDate(), TimeDateClass.convertDateFormat((String) apis.get("date"),"M/d/yyyy hh:mm:ss a","dd/MM/yyyy"));
        soft.assertEquals(timeTracker.getDepartment(), apis.get("dept"));
        soft.assertEquals(timeTracker.getActiveTime(), TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble((String) apis.get("activeTime"))));
        soft.assertEquals(timeTracker.getIdleTime(), TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble((String)apis.get("idleTime"))),"Values do not match!");
        soft.assertEquals(timeTracker.getTotalTime(), TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble((String)apis.get("totalTime"))));
        soft.assertEquals(timeTracker.getFirstActivity(), TimeDateClass.convertDateFormat((String) apis.get("firstActivity"),"M/d/yyyy h:mm:ss a","hh:mm:ss a"));// first activity
        soft.assertEquals(timeTracker.getLastActivity(), TimeDateClass.convertDateFormat((String) apis.get("lastActivity"),"M/d/yyyy h:mm:ss a","hh:mm:ss a"));// last activity
        soft.assertAll();
        logger.info("verified the user active time , idle time and total time data verified successfully!");
    }

    @Then("Verify user can see the components in the time tracker screen.")
    public void verifyUserCanSeeTheComponentsInTheTimeTrackerScreen() {
        MM_TimeTrackerScreen tracker=new MM_TimeTrackerScreen();
        Assert.assertEquals(tracker.getNameTitle(),"Name");
        Assert.assertEquals(tracker.getDateTitle(),"Date");
        Assert.assertEquals(tracker.getDepartmentTitle(),"Department");
        Assert.assertEquals(tracker.getActivityTitle(),"Active Time");
        Assert.assertEquals(tracker.getIdleTimeTitle(),"Idle Time");
        Assert.assertEquals(tracker.getAwayTimeTitle(),"Away Time");
        Assert.assertEquals(tracker.getTotalTimeTitle(),"Total Time");
        Assert.assertEquals(tracker.getFirstActivityTitle(),"First Activity");
        Assert.assertEquals(tracker.getLastActivityTitle(),"Last Activity");
        logger.info("Time Tracker component verified successfully !");
    }
    @And("User select the particular day and month using calender on claim status.")
    public void userSelectTheParticularDayAndMonthUsingCalenderOnClaimStatus() {
        int day = Integer.parseInt(System.getProperty("day"));
        String month = System.getProperty("month");
        MM_TimeTrackerScreen claim=new MM_TimeTrackerScreen();
        claim.selectOldDate(day,month);
        logger.info("date updated successfully !");
    }
}
