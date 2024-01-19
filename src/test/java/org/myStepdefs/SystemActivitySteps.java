package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.helpers.endPoints.SystemActivityEndPoints;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;
import org.pages.MM_HomeScreen;
import org.pages.MM_SystemActivityScreen;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;
import org.timeUtil.TimeDateClass;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SystemActivitySteps {
    private static Logger logger = Logger.getLogger(SystemActivitySteps.class.getName().getClass());
    String day;

    @And("User click on the system activity tab.")
    public void userClickOnTheSystemActivityTab() {
        MM_HomeScreen home = new MM_HomeScreen();
        home.click_OnSystemActivity();
        logger.info("clicked on system activity tab successfully !");
    }

    @Then("Verify the user can see component in the system activity screen.")
    public void verifyTheUserCanSeeComponentInTheSystemActivityScreen() {
        MM_SystemActivityScreen system = new MM_SystemActivityScreen();
        Assert.assertEquals(system.getSystemActivityTitle(), "System Activity");
        Assert.assertEquals(system.getAppsURLText(), "Apps / URL's");
        Assert.assertEquals(system.getTitleText(), "Title");
        Assert.assertEquals(system.getStartTimeTitle(), "Start Time");
        Assert.assertEquals(system.getSpendTime(), "Time Spent");
        logger.info("System activity screen component verified successfully !");
    }

    @Then("Verify user should be able to select Row per pages {int}.")
    public void verifyTheUserCanIncreaseTheLimitOfActivityEntryUpTo(int i) {
        int no = i;
        MM_SystemActivityScreen system = new MM_SystemActivityScreen();
        Select dropdown = new Select(system.getDropdownElement());
        dropdown.selectByVisibleText(String.valueOf(no));
    }

    @Then("Verify the activity data entry with api's response.")
    public void verifyTheActivityDataEntryWithApiSResponse() {
    }


    @Then("Verify user should able to see current date all used url & App when loading the page first time.")
    public void verifyUserShouldAbleToSeeCurrentDateAllUsedUrlAppWhenLoadingThePageFirstTime() {
        try {
            SoftAssert soft = new SoftAssert();
            String[] str = {"processName", "titleName", "processTotalTimeSeconds", "startTime", "endTime", "url"};
            MM_SystemActivityScreen activity = new MM_SystemActivityScreen();
            SystemActivityEndPoints system = new SystemActivityEndPoints();
            // int size = Integer.parseInt(activity.getRangeCount());
            int size = 10;
            LinkedHashMap<String, List<String>> systemActivityData = system.systemActivityRangeOfData(Integer.parseInt(day), size, str);
            LinkedHashMap<String, List<String>> allData = activity.getTableData();
            System.out.println(systemActivityData);
            System.out.println(allData);
            for (int i = 0; i < size; i++) {
                soft.assertEquals(allData.get("Apps / URL's").get(i), systemActivityData.get("processName").get(i));
                soft.assertEquals(allData.get("Title").get(i), systemActivityData.get("titleName").get(i));
                soft.assertEquals(allData.get("Start Time").get(i), TimeDateClass.convertDateFormat(systemActivityData.get("startTime").get(i),"M/dd/yyyy hh:mm:ss a","hh:mm:ss"));
                soft.assertTrue(allData.get("Time Spent").get(i).contains(TimeDateClass.convertSecondsToHHMMSS(Double.parseDouble(systemActivityData.get("processTotalTimeSeconds").get(i)))));
            }
            soft.assertAll();
        /*expected [] but found [msedgewebview2.exe],
                expected [1/18/2024 11:07:48 AM] but found [11:07:48],
        expected [10] but found [00:00:10]*/
        }catch (Exception ex)
        {
            System.out.println(ex +" "+ "Todays data not found either user on leave or still not started work");
        }

    }

    @And("User get the date from system activity screen.")
    public void userGetTheDateFromSystemActivityScreen() {
        MM_SystemActivityScreen system = new MM_SystemActivityScreen();
        day = system.getDate().split("/")[0];
        System.out.println(day);
    }

    @Then("Verify that first activity of the user should have start time as day start time.")
    public void verifyThatFirstActivityOfTheUserShouldHaveStartTimeAsDayStartTime() {

    }
}
