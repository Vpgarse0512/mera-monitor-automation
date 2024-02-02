package org.myStepdefs;

import io.restassured.path.json.JsonPath;
import org.helpers.endPoints.userEndPointAPIs.TimeTrackerEndPoints;
import org.helpers.jsonReader.JsonHelper;
import org.json.simple.parser.ParseException;
import org.pages.MM_HomeScreen;
import org.pages.MM_TimeTrackerScreen;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import org.timeUtil.TimeDateClass;
import org.utilities.StringUtil;

import java.io.IOException;

public class ReportTabSteps {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReportTabSteps.class.getName());
    String email = JsonHelper.getValue("email1").toString();
    String password = JsonHelper.getValue("password1").toString();
    String month = JsonHelper.getValue("month").toString();
    int day = Integer.parseInt(JsonHelper.getValue("day").toString());
    @Test
    public void validate_user_time_tracker_details() throws InterruptedException, IOException, ParseException {
        LoginSteps loginTest = new LoginSteps();
        //loginTest.login_with_valid_user_Test();
        MM_HomeScreen report = new MM_HomeScreen();
        Assert.assertEquals(report.getReportTabText(), "Report");
        report.click_OnReportTab();
        logger.info("clicked on Report tab successfully !");
        Assert.assertEquals(report.getTimeTrackerTabText(), "Time Tracker");
        report.click_OnTimeTracker();
        MM_TimeTrackerScreen timeTracker = new MM_TimeTrackerScreen();
        Assert.assertEquals(timeTracker.getTimeTrackerTittle(), "Time Tracker");
        TimeTrackerEndPoints tracker = new TimeTrackerEndPoints();
        JsonPath trackerDetails = tracker.getTimeTrackerDetails(3,"January","akumar@aapnainfotech.com","Test@123");
        Assert.assertEquals(timeTracker.getUserName(), trackerDetails.getString("[1].employeeName"));
        Assert.assertEquals(timeTracker.getDate(), trackerDetails.getString("[1].reportDate").replace("-", "/"));
        Assert.assertEquals(timeTracker.getDepartment(), trackerDetails.getString("[1].department"));
        Assert.assertEquals(timeTracker.getActiveTime(), TimeDateClass.convertSecondsToHHMMSSFormat(trackerDetails.getString("[1].activeTime")));
        Assert.assertEquals(timeTracker.getIdleTime(), TimeDateClass.convertSecondsToHHMMSSFormat(trackerDetails.getString("[1].idleTime")));
        Assert.assertEquals(timeTracker.getAwayTime(), TimeDateClass.convertSecondsToHHMMSSFormat(trackerDetails.getString("[1].awayTime")));
        Assert.assertEquals(timeTracker.getTotalTime(), TimeDateClass.convertSecondsToHHMMSSFormat(trackerDetails.getString("[1].totalTime")));
        Assert.assertEquals(timeTracker.getFirstActivity(), StringUtil.removeStringFromSpecificIndex(trackerDetails.getString("[1].inTime"),0,11));// first activity
        Assert.assertEquals(StringUtil.removeStringFromSpecificIndex(timeTracker.getLastActivity(),0,1),
                StringUtil.removeStringFromSpecificIndex(trackerDetails.getString("[1].outTime"),0,11));// last activity
        logger.info("Time Tracker activity verified successfully !");
    }
}