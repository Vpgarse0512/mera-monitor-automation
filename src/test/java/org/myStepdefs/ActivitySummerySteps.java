package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import org.helpers.endPoints.userEndPointAPIs.ActivitySummeryReportEndPoint;
import org.helpers.jsonReader.JsonHelper;
import org.pages.MM_ActivitySummeryScreen;
import org.pages.MM_AttendanceScreen;
import org.testng.asserts.SoftAssert;
import org.timeUtil.TimeDateClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ActivitySummerySteps {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ActivitySummerySteps.class.getName());
    int day;
    String month;
    String email = JsonHelper.getValue("email1").toString();
    String password = JsonHelper.getValue("password1").toString();
    String months = JsonHelper.getValue("month").toString();
    int days = Integer.parseInt(JsonHelper.getValue("day").toString());
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
        String email = JsonHelper.getValue("email1").toString();
        String password = JsonHelper.getValue("password1").toString();
        HashMap<String, Object> api = new ActivitySummeryReportEndPoint().getActivitySummeryResponse(day, month,email,password);
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
        String email = JsonHelper.getValue("email1").toString();
        String password = JsonHelper.getValue("password1").toString();
        JsonPath activity = new ActivitySummeryReportEndPoint().getActivitySummeryDetails(day, month,email,password);
        int size = activity.getList("details").size();
        System.out.println(size);
        if (size!=0) {
            LinkedHashMap<String, ArrayList<String>> api = new ActivitySummeryReportEndPoint().getTableActivitySummeryResponse(day, month,email,password);
            LinkedHashMap<String, List<String>> summery = new MM_ActivitySummeryScreen().getTableData();
            System.out.println(summery);
            int uiSize = summery.get("Start Time").size();
            System.out.println(uiSize);
            SoftAssert soft = new SoftAssert();
            //String list[] = {"Start Time", "End Time", "Spend Time", "Activity Status"};
            //String list[] = {"startTime", "endTime", "spentTime", "userActivityStatus"};
            for (int i = 0; i <= uiSize-1; i++) {
                //if (key.equalsIgnoreCase("Start Time")) {
                soft.assertEquals(summery.get("Start Time").get(i), TimeDateClass.convertHMMSSToHHMMSS(api.get("startTime").get(i).split(" ")[1]));
                soft.assertEquals(summery.get("End Time").get(i), api.get("endTime").get(i).split(" ")[1]);
                soft.assertEquals(summery.get("Spend Time").get(i), TimeDateClass.convertSecondsToHHMMSSFormat(api.get("spentTime").get(i)));
                soft.assertEquals(summery.get("Activity Status").get(i), api.get("userActivityStatus").get(i));
            }
            soft.assertAll();
        }else if (size==0){
            System.out.println("Day Not started yet or it's Holiday !");
        }

    }
}