package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.helpers.endPoints.userEndPointAPIs.NewTimeTrackerEndPoint;
import org.helpers.endPoints.userEndPointAPIs.SystemActivityEndPoints;
import org.junit.Assert;
import org.pages.MM_HomeScreen;
import org.pages.MM_SystemActivityScreen;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;
import org.timeUtil.TimeDateClass;

import java.util.LinkedHashMap;
import java.util.List;

public class SystemActivitySteps {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SystemActivitySteps.class.getName());

    String day;
    String firstActivity;
    int pastDay;

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
        String[] num={"10","25","50","75","100","All"};
        system.selectPageInitiationOnSystemActivityScreen(num);
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
            /*System.out.println(systemActivityData);
            System.out.println(allData);*/
            for (int i = 0; i < size; i++) {
                soft.assertEquals(allData.get("Apps / URL's").get(i), systemActivityData.get("processName").get(i));
                soft.assertEquals(allData.get("Title").get(i), systemActivityData.get("titleName").get(i));
                soft.assertEquals(allData.get("Start Time").get(i), TimeDateClass.convertDateFormat(systemActivityData.get("startTime").get(i), "M/dd/yyyy hh:mm:ss a", "hh:mm:ss"));
                soft.assertTrue(allData.get("Time Spent").get(i).contains(TimeDateClass.convertSecondsToHHMMSS(systemActivityData.get("processTotalTimeSeconds").get(i))));
            }
            soft.assertAll();
        /*expected [] but found [msedgewebview2.exe],
                expected [1/18/2024 11:07:48 AM] but found [11:07:48],
        expected [10] but found [00:00:10]*/
        } catch (Exception ex) {
            System.out.println(ex + " " + "Todays data not found either user on leave or still not started work");
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
        SoftAssert soft = new SoftAssert();
        NewTimeTrackerEndPoint firstActivity = new NewTimeTrackerEndPoint();
        MM_SystemActivityScreen systemActivity = new MM_SystemActivityScreen();
        soft.assertEquals(systemActivity.getFirstSystemActivityTime(), firstActivity.getTimeTrackerMapData(Integer.parseInt(day)).get("firstActivity").toString().split(" ")[0]);
        soft.assertAll();

    }

    @Then("Verify that Time spent on any activity should be the difference between next Activity start time and that particular Activity start time.")
    public void verifyThatTimeSpentOnAnyActivityShouldBeTheDifferenceBetweenNextActivityStartTimeAndThatParticularActivityStartTime() {
        try {
            SystemActivityEndPoints system = new SystemActivityEndPoints();
            MM_SystemActivityScreen steps = new MM_SystemActivityScreen();
            SoftAssert soft = new SoftAssert();
            int size = 10;
            String firstActivityTime = steps.getFirstSystemActivityTime();
            String[] str = {"processName", "titleName", "processTotalTimeSeconds", "startTime", "endTime", "url"};
            LinkedHashMap<String, List<String>> processTime = system.systemActivityRangeOfData(Integer.parseInt(day), size, str);
            LinkedHashMap<String, List<String>> startTime = steps.getTableData();
            for (int i = 0; i <= size - 1; i++) {
                String seconds = processTime.get("processTotalTimeSeconds").get(i);
                String startActivityTime = startTime.get("Start Time").get(i + 1);
                System.out.println(seconds + "  " + startActivityTime);
                String addition = TimeDateClass.addTime(firstActivityTime, seconds);
                soft.assertEquals(startActivityTime, addition);
                System.out.println(startActivityTime + "  " + addition);
                firstActivityTime = addition;
                System.out.println(firstActivityTime);
            }
            soft.assertAll();
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @Then("Verify user can able to change the date to see activities of that day.")
    public void verifyUserCanAbleToChangeTheDateToSeeActivitiesOfThatDay() {
        pastDay = Integer.parseInt(day) - 1;
        MM_SystemActivityScreen pastDate = new MM_SystemActivityScreen();
        pastDate.selectOldDate(pastDay, "January");

    }

    @Then("Verify activities of the past day with api's.")
    public void verifyActivitiesOfThePastDayWithApiS() {

        try {
            SoftAssert soft = new SoftAssert();
            String[] str = {"processName", "titleName", "processTotalTimeSeconds", "startTime", "endTime", "url"};
            MM_SystemActivityScreen activity = new MM_SystemActivityScreen();
            activity.scrollToElement(activity.getDropdownElement());
            SystemActivityEndPoints system = new SystemActivityEndPoints();
            // int size = Integer.parseInt(activity.getRangeCount());
            int size = 10;
            LinkedHashMap<String, List<String>> systemActivityData = system.systemActivityRangeOfData(pastDay, size, str);
            LinkedHashMap<String, List<String>> allData = activity.getTableData();
            //System.out.println(systemActivityData);
            //System.out.println(allData);
            for (int i = 0; i < size; i++) {
                soft.assertEquals(allData.get("Apps / URL's").get(i), systemActivityData.get("processName").get(i));
                soft.assertEquals(allData.get("Title").get(i), systemActivityData.get("titleName").get(i));
                soft.assertEquals(allData.get("Start Time").get(i), TimeDateClass.convertDateFormat(systemActivityData.get("startTime").get(i), "M/dd/yyyy hh:mm:ss a", "hh:mm:ss"));
                soft.assertTrue(allData.get("Time Spent").get(i).contains(TimeDateClass.convertSecondsToHHMMSS(systemActivityData.get("processTotalTimeSeconds").get(i))));
            }
            soft.assertAll();
        /*expected [] but found [msedgewebview2.exe],
                expected [1/18/2024 11:07:48 AM] but found [11:07:48],
        expected [10] but found [00:00:10]*/
        } catch (Exception ex) {
            System.out.println(ex + " " + "Todays data not found either user on leave or still not started work");
        }
    }

    @Then("Verify user able to change the next and previous button successfully")
    public void verifyUserAbleToChangeTheNextAndPreviousButtonSuccessfully() {
        MM_SystemActivityScreen system = new MM_SystemActivityScreen();
        try {
            if (system.isNoRecordDisplay())
                userSelectTheParticularDayAndMonthUsingCalender();
        }catch (Exception ex){}

        system.sleepTime(1);
        system.clickOnNextButton();
        system.sleepTime(1);
        system.scrollToElement(system.previousButton);
        system.clickOnPreviousButton();
    }
    @And("User select the particular day and month using calender.")
    public void userSelectTheParticularDayAndMonthUsingCalender() {
        int day = Integer.parseInt(System.getProperty("day"));
        String month = System.getProperty("month");
        MM_SystemActivityScreen system = new MM_SystemActivityScreen();
        system.selectOldDate(day, month);
        logger.info("user successfully selected mentioned date and month !");
    }

}
