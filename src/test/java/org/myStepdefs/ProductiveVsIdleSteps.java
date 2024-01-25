package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.base.BasePage;
import org.helpers.endPoints.NewTimeTrackerEndPoint;
import org.helpers.endPoints.ProductivityVsIdleEndPoints;
import org.pages.MM_ProductivityIdleScreen;
import org.pages.MM_TimeClaimStatusScreen;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;
import org.timeUtil.TimeDateClass;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ProductiveVsIdleSteps {
    private static Logger logger = Logger.getLogger(ProductiveVsIdleSteps.class.getName().getClass());
    int cDate;

    @And("User click on the productivity vs idle tab.")
    public void userClickOnTheProductivityVsIdleTab() {
        MM_ProductivityIdleScreen productive = new MM_ProductivityIdleScreen();
        productive.clickOnProductiveVsIdleTab();
        logger.info("clicked on productive vs idle tab successfully !");
    }

    @Then("Verify the user can see component in the productivity vs idle screen.")
    public void verifyTheUserCanSeeComponentInTheProductivityVsIdleScreen() {
        MM_ProductivityIdleScreen productive = new MM_ProductivityIdleScreen();
        cDate = Integer.parseInt(productive.getDate().split("/")[0]);
        String month = System.getProperty("month");
        try {
            if (productive.isNoRecordFoundDisplay()) {
                for (int i = 0; i < 5; i++) {
                    if (productive.isNoRecordFoundDisplay()) {
                        productive.selectOldDate(cDate, month);
                        break;
                    }
                    cDate = cDate - 1;
                }
            }
        } catch (Exception ex) {
            System.out.println("Record showing on the productive vs idle screen !");
        }
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(productive.getProductiveVsIdleTitle(), "Productive vs Idle");
        soft.assertEquals(productive.getDateTitle(), "Date");
        soft.assertEquals(productive.getProductiveTimeTitle(), "Productive Time");
        soft.assertEquals(productive.getProductivityTitle(), "Productivity");
        soft.assertEquals(productive.getUnproductiveTimeTitle(), "Unproductive Time");
        soft.assertEquals(productive.getIdleTimeTitle(), "Idle Time");
        soft.assertEquals(productive.getAwayTimeTitle(), "Away Time");
        soft.assertEquals(productive.getTotalTimeTitle(), "Total Time");
        soft.assertEquals(productive.isDatePickerTitle(), true);
        soft.assertAll();
        logger.info("productivity vs idle components verified successfully !");
    }

    @Then("Verify the user productivity vs idle data mapping with api's.")
    public void verifyTheUserProductivityVsIdleDataMappingWithApiS() {
        SoftAssert soft = new SoftAssert();
        String day = TimeDateClass.getToDate().split("-")[0];
        String month = System.getProperty("month");
        HashMap<String, Object> api = new ProductivityVsIdleEndPoints().getProductivityVsIdleDetailsMap(Integer.parseInt(day), month);
        MM_ProductivityIdleScreen productive = new MM_ProductivityIdleScreen();
        //soft.assertEquals(productive.getDate(),api.get(""));
        soft.assertEquals(productive.getProductiveTime(), api.get("productiveTime"));
        soft.assertEquals(productive.getUnProductiveTime(), api.get("unproductiveTime"));
        soft.assertEquals(productive.getIdleTime(), api.get("idleTime"));
        soft.assertEquals(productive.getAwayTime(), api.get("awayTime"));
        soft.assertEquals(productive.getTotalTime(), api.get("totalTime"));
        soft.assertAll();
    }


    @Then("Verify the user can check the productivity vs idle report for past date.")
    public void verifyTheUserCanCheckTheProductivityVsIdleReportForPastDate() {
        MM_ProductivityIdleScreen productive = new MM_ProductivityIdleScreen();
        SoftAssert soft = new SoftAssert();
        HashMap<String, Object> productivity = new ProductivityVsIdleEndPoints().getProductivityVsIdleDetailsMap(3, "January");
        soft.assertEquals(productive.getDate(), productivity.get("date"));
        soft.assertEquals(productive.getProductiveTime(), productivity.get("productiveTime"));
        soft.assertEquals(productive.getUnProductiveTime(), productivity.get("unproductiveTime"));
        soft.assertEquals(productive.getIdleTime(), productivity.get("idleTime"));
        soft.assertEquals(productive.getAwayTime(), productivity.get("awayTime"));
        soft.assertEquals(productive.getTotalTime(), productivity.get("totalTime"));
        soft.assertAll();
    }

    @And("User click on the productive green graph.")
    public void userClickOnTheProductiveGreenGraph() {
        MM_ProductivityIdleScreen productive = new MM_ProductivityIdleScreen();
        productive.clickOnGreenProductiveGraph();
    }

    @Then("Verify the popup with productive time column open.")
    public void verifyThePopupWithProductiveTimeColumnOpen() {
        MM_ProductivityIdleScreen productive = new MM_ProductivityIdleScreen();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(productive.isProductiveSelected(), true);
        soft.assertEquals(productive.getUnproductivePopupTitle(), "Unproductive");
        soft.assertEquals(productive.getIdlePopupTitle(), "Idle");
        soft.assertEquals(productive.getKeyboardMouseStockTitle(), "Keyboard & Mouse Stroke");
        soft.assertAll();
    }

    @Then("Verify the productive column entries.")
    public void verifyTheProductiveColumnEntries() {
    }

    @Then("User click on cross icon from popup screen.")
    public void userClickOnCrossIconFromPopupScreen() {
        MM_ProductivityIdleScreen productive = new MM_ProductivityIdleScreen();
        productive.clickOnCrossIcon();
    }

    @And("User click on the unproductive red graph.")
    public void userClickOnTheUnproductiveRedGraph() {
        MM_ProductivityIdleScreen productive = new MM_ProductivityIdleScreen();
        productive.clickOnRedUnProductiveGraph();

    }

    @Then("Verify the popup with unproductive time column open.")
    public void verifyThePopupWithUnproductiveTimeColumnOpen() {
        MM_ProductivityIdleScreen productive = new MM_ProductivityIdleScreen();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(productive.isUnProductiveSelected(), true);
        soft.assertEquals(productive.getProductivePopupTitle(), "Productive");
        soft.assertEquals(productive.getIdlePopupTitle(), "Idle");
        soft.assertEquals(productive.getKeyboardMouseStockTitle(), "Keyboard & Mouse Stroke");
        soft.assertAll();
    }

    @Then("Verify the unproductive column entries.")
    public void verifyTheUnproductiveColumnEntries() {
    }

    @And("User click on the web and apps tab.")
    public void userClickOnTheWebAndAppsTab() {
    }

    @And("User select the particular day and month using calender on claim status.")
    public void userSelectTheParticularDayAndMonthUsingCalenderOnClaimStatus() {
        int day = Integer.parseInt(System.getProperty("day"));
        String month = System.getProperty("day");
        MM_ProductivityIdleScreen claim = new MM_ProductivityIdleScreen();
        claim.selectOldDate(day, month);
        logger.info("date updated successfully !");
    }

    @Then("Verify Idle away and total Time on this page is same as Idle Time on Time tracker Page !")
    public void verifyIdleAwayAndTotalTimeOnThisPageIsSameAsIdleTimeOnTimeTrackerPage() {
        LinkedHashMap<String, Object> api = new NewTimeTrackerEndPoint().getTimeTrackerMapData(cDate);
        MM_ProductivityIdleScreen productive=new MM_ProductivityIdleScreen();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(productive.getTotalTime(), api.get("totalTime"));
        soft.assertEquals(productive.getIdleTime(), api.get("idleTime"));
        soft.assertEquals(productive.getAwayTime(), api.get("awayTime"));
        soft.assertAll();
    }
}
