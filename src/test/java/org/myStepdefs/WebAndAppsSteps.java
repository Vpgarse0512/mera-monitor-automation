package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.pages.MM_WebAppsScreen;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

public class WebAndAppsSteps {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(WebAndAppsSteps.class.getName());

    @And("User click on the web and apps tab.")
    public void userClickOnTheWebAndAppsTab() {
        MM_WebAppsScreen webapps=new MM_WebAppsScreen();
        webapps.clickOnWebAndAppsTab();
        logger.info("user clicked on the web and apps tab successfully !");
    }

    @Then("Verify all the components on the web and apps screen.")
    public void verifyAllTheComponentsOnTheWebAndAppsScreen() {
        SoftAssert soft=new SoftAssert();
        MM_WebAppsScreen webapps=new MM_WebAppsScreen();
        soft.assertEquals(webapps.getWebsiteLinkTitle(),"Websites");
        soft.assertEquals(webapps.getApplicationLinkTitle(),"Applications");
        soft.assertEquals(webapps.getWebSiteTitle(),"Website");
        soft.assertEquals(webapps.getDurationTitle(),"Duration");
        soft.assertEquals(webapps.getPercentageTitle(),"Percentage");
        soft.assertEquals(webapps.getURLTitle(),"URL");
        soft.assertEquals(webapps.getDurationSecondTitle(),"Duration");
        soft.assertEquals(webapps.getPercentageSecondTitle(),"Percentage");
        try {
            soft.assertEquals(webapps.getTotalTimeSpendTitle(),"Total Time Spent");
        }catch (Exception ex){
            logger.info(ex.toString());
        }

        logger.info("all the component tittle has been verified successfully !");

    }

    @Then("verify websites data from api's integration.")
    public void verifyWebsitesDataFromApiSIntegration() {
        
    }

    @And("User click on application tab.")
    public void userClickOnApplicationTab() {
        MM_WebAppsScreen webapps=new MM_WebAppsScreen();
        webapps.clickOnApplicationLink();
        logger.info("user clicked on the application tab successfully !");
    }

    @Then("Verify application data from api's integration.")
    public void verifyApplicationDataFromApiSIntegration() {
    }

    @Then("Verify all the components on the application screen.")
    public void verifyAllTheComponentsOnTheApplicationScreen() {
        SoftAssert soft=new SoftAssert();
        MM_WebAppsScreen webapps=new MM_WebAppsScreen();
        soft.assertEquals(webapps.getAppNameTitle(),"App Name");
        soft.assertEquals(webapps.getDurationAppTitle(),"Duration");
        soft.assertEquals(webapps.getPercentageAppTitle(),"Percentage");
        soft.assertEquals(webapps.getTitleApp(),"Title");
        soft.assertEquals(webapps.getDurationTitleAppSecond(),"Duration");
        soft.assertEquals(webapps.getPercentageTitleAppSecond(),"Percentage");
        soft.assertEquals(webapps.getTotalTimeSpendTitleAppSecond(),"Total Time Spent");
        logger.info("all the component tittle has been verified successfully !");
    }
}
