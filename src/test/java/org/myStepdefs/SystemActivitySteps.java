package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;
import org.pages.MM_HomeScreen;
import org.pages.MM_SystemActivityScreen;
import org.testng.log4testng.Logger;

public class SystemActivitySteps {
    private static Logger logger = Logger.getLogger(SystemActivitySteps.class.getName().getClass());
    @And("User click on the system activity tab.")
    public void userClickOnTheSystemActivityTab() {
        MM_HomeScreen home=new MM_HomeScreen();
        home.click_OnSystemActivity();
        logger.info("clicked on system activity tab successfully !");
    }

    @Then("Verify the user can see component in the system activity screen.")
    public void verifyTheUserCanSeeComponentInTheSystemActivityScreen() {
        MM_SystemActivityScreen system=new MM_SystemActivityScreen();
        Assert.assertEquals(system.getSystemActivityTitle(),"System Activity");
        Assert.assertEquals(system.getAppsURLText(),"Apps / URL's");
        Assert.assertEquals(system.getTitleText(),"Title");
        Assert.assertEquals(system.getStartTimeTitle(),"Start Time");
        Assert.assertEquals(system.getSpendTime(),"Time Spent");
        logger.info("System activity screen component verified successfully !");
    }

    @Then("Verify the user can increase the limit of activity entry up to {int} .")
    public void verifyTheUserCanIncreaseTheLimitOfActivityEntryUpTo(int arg0) {
        MM_SystemActivityScreen system=new MM_SystemActivityScreen();
        Select dropdown=new Select(system.getDropdownElement());
        dropdown.selectByVisibleText(String.valueOf(arg0));
    }

    @Then("Verify the activity data entry with api's response.")
    public void verifyTheActivityDataEntryWithApiSResponse() {
    }
}
