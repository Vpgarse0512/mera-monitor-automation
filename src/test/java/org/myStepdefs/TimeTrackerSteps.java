package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.helpers.endPoints.userEndPointAPIs.NewTimeTrackerEndPoint;
import org.pages.MM_TimeTrackerScreen;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.timeUtil.TimeDateClass;

import java.util.*;

public class TimeTrackerSteps {

    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TimeTrackerSteps.class.getName());
    String date;

    @Then("Verify user can see the data in time for a particular day.")
    public void verifyUserCanSeeTheDataInTimeForAParticularDay() {

    }

    @Then("Verify user is able to see the data for a date range.")
    public void verifyUserIsAbleToSeeTheDataForADateRange() {
        MM_TimeTrackerScreen tracker = new MM_TimeTrackerScreen();
        String month = System.getProperty("month");
        System.out.println(date);
        tracker.selectOldDate(Integer.parseInt(date), month);
        tracker.sleepTime(7);
    }

    @Then("Verify user's Active time, Idle Time and Total Time Data on time tracker page should be correct.")
    public void verifyUserSActiveTimeIdleTimeAndTotalTimeDataOnTimeTrackerPageShouldBeCorrect() {
       /* expected [01/03/2024] but found [03/01/2024],
        expected [00:00:00] but found [-],
        expected [1/3/2024 4:07:00 PM] but found [04:07:00 PM],
        expected [1/3/2024 7:04:31 PM] but found [07:04:31 PM]*/
        SoftAssert soft = new SoftAssert();
        MM_TimeTrackerScreen timeTracker = new MM_TimeTrackerScreen();
        Assert.assertEquals(timeTracker.getTimeTrackerTittle(), "Time Tracker");
        int day = Integer.parseInt(System.getProperty("day"));
        LinkedHashMap<String, Object> apis = new NewTimeTrackerEndPoint().getTimeTrackerMapData(day);
        soft.assertEquals(timeTracker.getUserName(), apis.get("name"));
        soft.assertEquals(timeTracker.getDate(), TimeDateClass.convertDateFormat(apis.get("date").toString().split(" ")[0], "M/dd/yyyy", "dd/MM/yyyy"));
        soft.assertEquals(timeTracker.getDepartment(), apis.get("dept"));
        soft.assertEquals(timeTracker.getActiveTime(), apis.get("activeTime"));
        soft.assertEquals(timeTracker.getIdleTime(), apis.get("idleTime"), "Values do not match!");
        soft.assertEquals(timeTracker.getTotalTime(), apis.get("totalTime"));
        soft.assertEquals(timeTracker.getFirstActivity(), apis.get("firstActivity"));// first activity
        soft.assertEquals(timeTracker.getLastActivity(), apis.get("lastActivity"));// last activity
        soft.assertAll();
        logger.info("verified the user active time , idle time and total time data verified successfully!");
    }

    @Then("Verify user can see the components in the time tracker screen.")
    public void verifyUserCanSeeTheComponentsInTheTimeTrackerScreen() {
        MM_TimeTrackerScreen tracker = new MM_TimeTrackerScreen();
        Assert.assertEquals(tracker.getNameTitle(), "Name");
        Assert.assertEquals(tracker.getDateTitle(), "Date");
        Assert.assertEquals(tracker.getDepartmentTitle(), "Department");
        Assert.assertEquals(tracker.getActivityTitle(), "Active Time");
        Assert.assertEquals(tracker.getIdleTimeTitle(), "Idle Time");
        Assert.assertEquals(tracker.getAwayTimeTitle(), "Away Time");
        Assert.assertEquals(tracker.getTotalTimeTitle(), "Total Time");
        Assert.assertEquals(tracker.getFirstActivityTitle(), "First Activity");
        Assert.assertEquals(tracker.getLastActivityTitle(), "Last Activity");
        logger.info("Time Tracker component verified successfully !");
    }

    @And("User select the particular day and month using calender on claim status.")
    public void userSelectTheParticularDayAndMonthUsingCalenderOnClaimStatus() {
        int day = Integer.parseInt(System.getProperty("day"));
        String month = System.getProperty("month");
        MM_TimeTrackerScreen claim = new MM_TimeTrackerScreen();
        claim.selectOldDate(day, month);
        logger.info("date updated successfully !");
    }

    @And("User can see the todays date on tracker screen.")
    public void userCanSeeTheTodaysDateOnTrackerScreen() {
        MM_TimeTrackerScreen claim = new MM_TimeTrackerScreen();
        date = claim.getDate().split("/")[0];
        logger.info("todays date : " + date);
    }

    @Then("Verify range of data should be verify with api's.")
    public void verifyRangeOfDataShouldBeVerifyWithApiS() {
        SoftAssert soft=new SoftAssert();
        NewTimeTrackerEndPoint tracker = new NewTimeTrackerEndPoint();
        MM_TimeTrackerScreen time=new MM_TimeTrackerScreen();
        int toDay = Integer.parseInt(System.getProperty("day"));
        int size=tracker.getTimeTrackerRangData(toDay, Integer.parseInt(date)).getList("").size();
        //System.out.println(size);
        String [] keys= {"userName","date","totalTime","totalActiveTime","totalIdleTime","awayTime","firstActivity","lastActivity","department","timeZone"};

        Map<String, List<String>> apiData = tracker.range(size,keys,toDay,date);
        for (int i=0;i<10;i++)
        {

            soft.assertEquals(time.getTableData().get("Name").get(i),apiData.get("userName").get(i));
            soft.assertEquals(time.getTableData().get("Date").get(i),TimeDateClass.convertDateFormat(apiData.get("date").get(i),"M/dd/yyyy hh:mm:ss a", "dd/MM/yyyy"));
            soft.assertEquals(time.getTableData().get("Total Time").get(i),TimeDateClass.convertSecondsToHHMMSSFormat(apiData.get("totalTime").get(i)));
           // System.out.println(time.getTableData().get("Total Time").get(i)+"   "+TimeDateClass.convertSecondsToHHMMSSFormat(apiData.get("totalTime").get(i)));
            soft.assertEquals(time.getTableData().get("Active Time").get(i),TimeDateClass.convertSecondsToHHMMSSFormat(apiData.get("totalActiveTime").get(i)));
            soft.assertEquals(time.getTableData().get("Idle Time").get(i),TimeDateClass.convertSecondsToHHMMSSFormat(apiData.get("totalIdleTime").get(i)));
            soft.assertEquals(time.getTableData().get("Away Time").get(i),TimeDateClass.convertSecondsToHHMMSSFormat(apiData.get("awayTime").get(i)));
            //String[] firstActivity = apiData.get("firstActivity").get(i).split(" ");
            //soft.assertEquals(time.getTableData().get("First Activity").get(i),TimeDateClass.convertDateFormat(apiData.get("firstActivity").get(i),"MM/dd/yyyy h:mm:ss a","hh:mm:ss a"));
            //String[] lastActivity = apiData.get("lastActivity").get(i).split(" ");
           // soft.assertEquals(time.getTableData().get("Last Activity").get(i),TimeDateClass.convertDateFormat(apiData.get("lastActivity").get(i),"MM/dd/yyyy h:mm:ss a","hh:mm:ss a"));
            soft.assertEquals(time.getTableData().get("Department").get(i),apiData.get("department").get(i));
            soft.assertEquals(time.getTableData().get("Time Zone").get(i),apiData.get("timeZone").get(i));
            //System.out.println(time.getTableData().get("Name").get(i)+" "+apiData.get("userName").get(i));
            soft.assertAll();
        }
    }
}
