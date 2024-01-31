package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import org.helpers.endPoints.userEndPointAPIs.ActivitySummeryReportEndPoint;
import org.pages.MM_ActivitySummeryScreen;
import org.pages.MM_AttendanceScreen;
import org.testng.asserts.SoftAssert;
import org.timeUtil.TimeDateClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ActivitySummerySteps {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ActivitySummerySteps.class.getName());
    int day;
    String month;

    @And("User click on the activity summery tab.")
    public void userClickOnTheActivitySummeryTab() {
        MM_ActivitySummeryScreen activity = new MM_ActivitySummeryScreen();
        activity.clickOnActivitySummeryTab();
    }

    @Then("Verify the user can see component in the activity summery.")
    public void verifyTheUserCanSeeComponentInTheActivitySummery() {
        MM_ActivitySummeryScreen activity = new MM_ActivitySummeryScreen();
        day = Integer.parseInt(activity.getDatePickerDate().split("/")[0]);
        month = TimeDateClass.getMonthName(Integer.parseInt(activity.getDatePickerDate().split("/")[1]));
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(activity.getActivitySummeryTittle(), "Activity Summary");
        soft.assertEquals(activity.getDateTitle(), "Date");
        soft.assertEquals(activity.getFirstActivityTitle(), "First Activity");
        soft.assertEquals(activity.getLastActivityTitle(), "Last Activity");
        soft.assertEquals(activity.getTotalTimeTitle(), "Total Time");
        soft.assertEquals(activity.getProductiveTimeTitle(), "Productive Time");
        soft.assertEquals(activity.getIdleTimeTitle(), "Idle Time");
        soft.assertAll();
    }

    @Then("Verify the user activity summery data mapping with api's.")
    public void verifyTheUserActivitySummeryDataMappingWithApiS() {
        HashMap<String, Object> api = new ActivitySummeryReportEndPoint().getActivitySummeryResponse(day, month);
        MM_ActivitySummeryScreen summery = new MM_ActivitySummeryScreen();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(summery.getFirstActivity(), api.get("firstActivity"));
        soft.assertEquals(summery.getLastActivity(), api.get("lastActivity"));
        soft.assertEquals(summery.getTotalTime(), api.get("totalTime"));
        soft.assertEquals(summery.getProductiveTime(), api.get("totalProductiveTime"));
        soft.assertEquals(summery.getIdleTime(), api.get("totalIdleTime"));
        soft.assertAll();
    }

    @Then("Verify the user table activity summery mapping with api's.")
    public void verifyTheUserTableActivitySummeryMappingWithApiS() {
        JsonPath activity = new ActivitySummeryReportEndPoint().getActivitySummeryDetails(day, month);
        int size = activity.getList("details").size();
        System.out.println(size);
        if (size!=0) {
            LinkedHashMap<String, ArrayList<String>> api = new ActivitySummeryReportEndPoint().getTableActivitySummeryResponse(day, month);
            MM_ActivitySummeryScreen summery = new MM_ActivitySummeryScreen();
            System.out.println(summery.getTableData());
            SoftAssert soft = new SoftAssert();
            //String list[] = {"Start Time", "End Time", "Spend Time", "Activity Status"};
            //String list[] = {"startTime", "endTime", "spentTime", "userActivityStatus"};
            for (int i = 0; i <= size-1; i++) {
                //if (key.equalsIgnoreCase("Start Time")) {
                soft.assertEquals(summery.getTableData().get("Start Time").get(i), api.get("startTime").get(i));
                soft.assertEquals(summery.getTableData().get("End Time").get(i), api.get("endTime").get(i));
                soft.assertEquals(summery.getTableData().get("Spend Time").get(i), api.get("spentTime").get(i));
                soft.assertEquals(summery.getTableData().get("Activity Status").get(i), api.get("userActivityStatus").get(i));
            }
            soft.assertAll();
        }else if (size==0){
            System.out.println("Day Not started yet or it's Holiday !");
        }

    }
}