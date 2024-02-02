package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.helpers.endPoints.userEndPointAPIs.AttendaceByUserEndPoint;
import org.helpers.endPoints.userEndPointAPIs.AttendanceEndpoints;
import org.helpers.jsonReader.JsonHelper;
import org.pages.MM_AttendanceScreen;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import java.util.HashMap;
import java.util.Map;

public class AttendanceSteps {

    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AttendanceSteps.class.getName());
    String email = JsonHelper.getValue("email1").toString();
    String password = JsonHelper.getValue("password1").toString();
    String month = JsonHelper.getValue("month").toString();
    int day = Integer.parseInt(JsonHelper.getValue("day").toString());
    int todayDate;
    @And("User click on the attendance tab.")
    public void userClickOnTheAttendanceTab() {
        MM_AttendanceScreen attendance = new MM_AttendanceScreen();
        attendance.clickOnAttendanceTab();
        logger.info("clicked on attendance tab successfully!");
    }

    @Then("Verify all the components on the attendance screen.")
    public void verifyAllTheComponentsOnTheAttendanceScreen() {
        MM_AttendanceScreen attendance = new MM_AttendanceScreen();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(attendance.getAttendanceTitle(), "Attendance");
        soft.assertEquals(attendance.getNameTitle(), "Name");
        soft.assertEquals(attendance.getLoggedHoursTitle(), "Logged Hours");
        soft.assertEquals(attendance.getDateTitle(), "Date");
        soft.assertEquals(attendance.getTotalHoursExpectedTitle(), "Total Hours Expected");
        soft.assertEquals(attendance.getNoHolidaysTitle(), "No of Holidays");
        soft.assertEquals(attendance.getNoLeavesTitle(), "No of Leaves");
        logger.info("all components verified successfully!");
    }

    @Then("Verify the user details on attendance screen from api's.")
    public void verifyTheUserDetailsOnAttendanceScreenFromApiS(int day,String month,String email,String password) {
        MM_AttendanceScreen attendance = new MM_AttendanceScreen();
        attendance.selectOldDate(day,month);
        AttendanceEndpoints api = new AttendanceEndpoints();
        HashMap<String, Object> response = api.getUserAttendanceDetailsInMap(day,month,email,password);
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(attendance.getName(), response.get("name"));
        soft.assertEquals(attendance.getLoggedHours(),response.get("loggedHours"));
        soft.assertEquals(attendance.getNoOfLeaves(),response.get("leaves") );
        soft.assertTrue(attendance.getTotalHours().contains((CharSequence) response.get("expectedHour")));
        soft.assertEquals(attendance.getNoOfHolidays(),response.get("holiday") );
        soft.assertAll();
        logger.info("User attendance data verified successfully!");
    }

    @Then("Verify current date in out total time from api's.")
    public void verifyCurrentDateInOutTotalTimeFromApiS(int day,String month,String email,String password) {
        MM_AttendanceScreen attendance = new MM_AttendanceScreen();
        attendance.clickOnMoreLink();
        SoftAssert soft=new SoftAssert();
        AttendaceByUserEndPoint api=new AttendaceByUserEndPoint();
        Map<String, Object> time = api.getInOutTotalTime(day,month,email,password);
       // soft.assertEquals(attendance.getPresenty(),time.get("Presenty"));
        soft.assertEquals(attendance.getInTime(),time.get("In"));
        soft.assertEquals(attendance.getOutTime(),time.get("Out"));
        soft.assertEquals(attendance.getTotalTime(),time.get("Total"));
        soft.assertAll();
        logger.info("old date 3/jan data verified successfully!");
    }
}
