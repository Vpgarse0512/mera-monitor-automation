package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.helpers.endPoints.HolidayEndpoints;
import org.pages.MM_HolidayScreen;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HolidaySteps {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HolidaySteps.class.getName());

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
    public void verifyTheHolidayDataWithHolidaysApiS() {
        MM_HolidayScreen holiday = new MM_HolidayScreen();
        Map<String, List<String>> uiData = holiday.getTableData();
        HolidayEndpoints api = new HolidayEndpoints();
        int size = api.getHolidayList().getList("").size();
        LinkedHashMap<String, List<String>> linkList = new HolidayEndpoints().getHolidayData();
        //System.out.println(holiday.getTableData().get("Name").get(2));
        SoftAssert soft = new SoftAssert();
        String[] list = {"Name", "Date"};
        for (String key : list) {
            for (int i = 0; i < size - 1; i++) {
                soft.assertEquals(uiData.get(key).get(i), linkList.get("holidayName").get(i));
                System.out.println(uiData.get(key).get(i) + " " + linkList.get("holidayName").get(i));
            }
        }
        soft.assertAll();
    }
}
