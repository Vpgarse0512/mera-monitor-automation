package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import org.helpers.endPoints.ClaimTimeForUserStatusEndPoints;
import org.helpers.endPoints.UserTimeToClaimEndPoint;
import org.pages.MM_HolidayScreen;
import org.pages.MM_TimeClaimScreen;
import org.pages.MM_TimeClaimStatusScreen;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;
import org.timeUtil.TimeDateClass;
import org.utilities.StringUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class TimeClaimSteps {
    private static Logger logger = Logger.getLogger(TimeClaimSteps.class.getName().getClass());

    @And("User click on time claim tab.")
    public void userClickOnTimeClaimTab() {
        MM_TimeClaimScreen timeClaim = new MM_TimeClaimScreen();
        timeClaim.clickOnTimeClaimTab();
        logger.info("user clicked on time claim tab !");
    }

    @And("User click on claim option sub tab.")
    public void userClickOnClaimOptionSubTab() {
        MM_TimeClaimScreen timeClaim = new MM_TimeClaimScreen();
        timeClaim.clickOnClaimTab();
        logger.info("user clicked on claim tab !");
    }

    @Then("Verify all the components on the time claim screen.")
    public void verifyAllTheComponentsOnTheTimeClaimScreen() {
        MM_TimeClaimScreen timeClaim = new MM_TimeClaimScreen();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(timeClaim.getTimeClaimTitle(), "Time Claim");
        soft.assertTrue(timeClaim.isDatePickerDisplayed());
        soft.assertEquals(timeClaim.getDateTitle(), "Date");
        soft.assertEquals(timeClaim.getFirstActivityTitle(), "First Activity");
        soft.assertEquals(timeClaim.getLastActivityTitle(), "Last Activity");
        soft.assertEquals(timeClaim.getTotalTimeTitle(), "Total Time");
        soft.assertEquals(timeClaim.getProductiveTimeTitle(), "Productive Time");
        soft.assertEquals(timeClaim.getIdleTimeTitle(), "Idle Time");
        soft.assertEquals(timeClaim.getStartTimeTitle(), "Start Time");
        soft.assertEquals(timeClaim.getEndTimeTitle(), "End Time");
        soft.assertEquals(timeClaim.getSpendTimeTitle(), "Spend Time");
        soft.assertEquals(timeClaim.getActivityStatusTitle(), "Activity Status");
        soft.assertEquals(timeClaim.getRequestStatusTitle(), "Request Status");
        soft.assertEquals(timeClaim.getReasonTitle(), "Reason");
        soft.assertEquals(timeClaim.getActionTitle(), "Action");
        logger.info("all components time claim screen verified successfully!");
    }

    @Then("Verify the time claim activity times with api's.")
    public void verifyTheTimeClaimActivityTimesWithApiS(int day,String month) {
        // API validation pending 
        MM_TimeClaimScreen timeClaim = new MM_TimeClaimScreen();
        HashMap<String, Object> claimApi = new UserTimeToClaimEndPoint().getTimeClaimMap(day);
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(timeClaim.getDate(), claimApi.get("date"));
        String[] firstActivity = claimApi.get("firstActivity").toString().split(" ");
        soft.assertTrue(timeClaim.getFirstActivity().contains(firstActivity[1]+" "+firstActivity[2]));
        String[] lastActivity = claimApi.get("lastActivity").toString().split(" ");
        soft.assertTrue((timeClaim.getLastActivity().contains(lastActivity[1]+" "+lastActivity[2])));
        soft.assertEquals(timeClaim.getTotalTime(),TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble((String) claimApi.get("totalTime")),"%02d:%02d"));
        soft.assertEquals(timeClaim.getProductiveTime(),TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble((String) claimApi.get("productiveTime")),"%02d:%02d"));
        soft.assertEquals(timeClaim.getIdleTime(),TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble((String) claimApi.get("idleTime")),"%02d:%02d"));
        soft.assertAll();
        logger.info("claim activities are verified successfully !");
    }

    @Then("Verify the time claims record with api's.")
    public void verifyTheTimeClaimsRecordWithApiS(int day,String month) {

        /*SoftAssert soft=new SoftAssert();
        HashMap<String, Object> timeClaim = new UserTimeToClaimEndPoint().getTimeClaimMap(day);
        MM_TimeClaimScreen steps=new MM_TimeClaimScreen();
        soft.assertEquals(steps.getDate(),timeClaim.get("date"));
        String[] firstActivity = timeClaim.get("firstActivity").toString().split(" ");
        soft.assertTrue(steps.getFirstActivity().contains(firstActivity[1]+" "+firstActivity[2]));
        String[] lastActivity = timeClaim.get("lastActivity").toString().split(" ");
        soft.assertTrue((steps.getLastActivity().contains(lastActivity[1]+" "+lastActivity[2])));
        soft.assertEquals(steps.getTotalTime(),TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble((String) timeClaim.get("totalTime")),"%02d:%02d"));
        soft.assertEquals(steps.getProductiveTime(),TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble((String) timeClaim.get("productiveTime")),"%02d:%02d"));
        soft.assertEquals(steps.getIdleTime(),TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble((String) timeClaim.get("idleTime")),"%02d:%02d"));
        soft.assertAll();
        logger.info("verified all claims data with verified successfully !");*/

    }

    @And("User click on status option sub tab.")
    public void userClickOnStatusOptionSubTab() {
        MM_TimeClaimStatusScreen timeStatus = new MM_TimeClaimStatusScreen();
        timeStatus.clickOnStatusTab();
        logger.info("clicked on status tab successfully !");
    }

    @Then("Verify all the components on the time claim status screen.")
    public void verifyAllTheComponentsOnTheTimeClaimStatusScreen() {
        MM_TimeClaimStatusScreen timeStatus = new MM_TimeClaimStatusScreen();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(timeStatus.getTimeClaimStatusTitle(), "Time Claim Status");
        soft.assertEquals(timeStatus.getNameTitle(), "Name");
        soft.assertEquals(timeStatus.getDatesTitle(), "Date");
        soft.assertEquals(timeStatus.getStartTimesTitle(), "Start Time");
        soft.assertEquals(timeStatus.getEndTimesTitle(), "End Time");
        soft.assertEquals(timeStatus.getReasonTitle(), "Reason");
        soft.assertEquals(timeStatus.getApproveTitle(), "Approved / Rejected By");
        soft.assertEquals(timeStatus.getStatusTitle(), "Status");
        soft.assertAll();
        logger.info("all components are verified from claim status screen !");
    }

    @Then("Verify the time claims status record and status with api's.")
    public void verifyTheTimeClaimsStatusRecordAndStatusWithApiS() {
        int day=Integer.parseInt(System.getProperty("day"));
        String month=System.getProperty("month");
        LinkedHashMap claim = new ClaimTimeForUserStatusEndPoints().getClaimData(day,month);
        MM_TimeClaimStatusScreen status = new MM_TimeClaimStatusScreen();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(status.getUserName(), claim.get("userName"));
        soft.assertEquals(status.getClaimDate(), TimeDateClass.convertDateFormat(claim.get("recordDate").toString(),"yyyy-MM-dd", "dd/MM/yyyy"));
        soft.assertEquals(status.getStartTime(), StringUtil.extractTime(claim.get("fromTime").toString()));
        soft.assertEquals(status.getEndTime(), StringUtil.extractTime(claim.get("toTime").toString()));
        soft.assertEquals(status.getReason(), claim.get("reason"));
        soft.assertEquals(status.getApprovedBy(), claim.get("responseBy"));
        soft.assertEquals(status.getStatus(), claim.get("claimStatus"));
        soft.assertAll();
        logger.info("all the claim status record verified successfully !");
    }



    @And("User select the particular day and month using calender on claim status.")
    public void userSelectTheParticularDayAndMonthUsingCalenderOnClaimStatus(int day,String month) {
        MM_TimeClaimStatusScreen claim=new MM_TimeClaimStatusScreen();
        claim.selectOldDate(day,month);
        logger.info("date updated successfully !");
    }

    @And("User select the particular day and month using calender on the time claim screen.")
    public void userSelectTheParticularDayAndMonthUsingCalenderOnTheTimeClaimScreen(int day,String month) {
        MM_TimeClaimScreen claim=new MM_TimeClaimScreen();
        claim.selectOldDate(day,month);
    }
}
