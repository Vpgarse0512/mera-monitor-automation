package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import org.helpers.endPoints.userEndPointAPIs.HolidayEndpoints;
import org.helpers.jsonReader.JsonHelper;
import org.pages.MM_HolidayScreen;
import org.testng.asserts.SoftAssert;
import org.timeUtil.TimeDateClass;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HolidaySteps {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HolidaySteps.class.getName());
    String email = JsonHelper.getValue("email1").toString();
    String password = JsonHelper.getValue("password1").toString();
    String month = JsonHelper.getValue("month").toString();
    int day = Integer.parseInt(JsonHelper.getValue("day").toString());
    @And("User click on holiday tab.")
    public void userClickOnTimeHolidayTab() {
        MM_HolidayScreen holiday = new MM_HolidayScreen();
        holiday.clickOnHolidayTab();
        logger.info("user clicked on holiday tab successfully !");
    }

    @Then("Verify all UI components on the holiday screen.")
    public void verifyAllUIComponentsOnTheHolidayScreen() {
        MM_HolidayScreen holiday = new MM_HolidayScreen();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(holiday.getHolidayTitle(), "Holiday");
        soft.assertEquals(holiday.getNameTitle(), "Name");
        soft.assertEquals(holiday.getDayTitle(), "Day");
        soft.assertEquals(holiday.getDateTitle(), "Date");
        soft.assertAll();
    }

    @Then("Verify the holiday data with holidays api's.")
    public void verifyTheHolidayDataWithHolidaysApiS(int day,String month,String email,String password) {
        MM_HolidayScreen holiday = new MM_HolidayScreen();
        Map<String, List<String>> uiData = holiday.getTableData();
        HolidayEndpoints api = new HolidayEndpoints();
        JsonPath response = api.getHolidayList(day, month, email, password);
        int size=response.getList("").size();
        LinkedHashMap<String, List<String>> linkList = new HolidayEndpoints().getHolidayData(day, month, email, password);
        SoftAssert soft = new SoftAssert();
        //String[] list = {"Name", "Date"};
        try {
            //for (int i = 0; i < size - 1; i++) {
            for (int i = 0; i < 9; i++) {
                if (i == 9) {
                    holiday.clickOnNextButton();
                    //System.out.println("clicked on next button !");
                    Thread.sleep(1000);
                }
                soft.assertEquals(uiData.get("Name").get(i), linkList.get("holidayName").get(i));
                soft.assertEquals(uiData.get("Date").get(i), TimeDateClass.convertDateFormat(linkList.get("holidayDate").get(i)));
                //System.out.println(uiData.get("Date").get(i) + "   " + TimeDateClass.convertDateFormat(linkList.get("holidayDate").get(i)));
                if (i == size / 2) {
                    holiday.scrollToElement(holiday.next_Button);
                    //System.out.println("Scrolled down till end !");
                    Thread.sleep(1000);
                }

            }
        } catch (Exception ex) {
            soft.assertEquals(holiday.getNoHolidayFound(), "No Holidays Found");
            System.out.println("No Holidays Found");

        }
        soft.assertAll();
    }
}
