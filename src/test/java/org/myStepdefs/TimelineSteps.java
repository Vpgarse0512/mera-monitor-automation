package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.pages.MM_TimelinesScreen;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

public class TimelineSteps {
    private static Logger logger = Logger.getLogger(ProductiveVsIdleSteps.class.getName().getClass());
    @And("User click on the timeline tab.")
    public void userClickOnTheTimelineTab() {
        MM_TimelinesScreen time=new MM_TimelinesScreen();
        time.clickOnTimelineTab();
        logger.info("click on timeline tab successfully !");
    }

    @Then("Verify all the components on the timeline screen.")
    public void verifyAllTheComponentsOnTheTimelineScreen() {
        SoftAssert soft=new SoftAssert();
        MM_TimelinesScreen time=new MM_TimelinesScreen();
        soft.assertEquals(time.getTimelinesTitle(),"Timeline");
        soft.assertEquals(time.getAppNameTitle(),"AppName");
        soft.assertEquals(time.getURLTitle(),"Url");
        soft.assertEquals(time.getTimeElapsedTitle(),"Time Elapsed");
        logger.info("all components are validated successfully !");

    }

    @Then("Verify the user details on timeline screen from api's.")
    public void verifyTheUserDetailsOnTimelineScreenFromApiS() {
    }
}
