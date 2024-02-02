package org.myStepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.base.BasePage;
import org.helpers.endPoints.userEndPointAPIs.LoginEndPoints;
import org.helpers.jsonReader.JsonHelper;
import org.json.simple.parser.ParseException;
import org.pages.MM_HomeScreen;
import org.pages.MM_LoginScreen;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import org.propertyHelper.PropertiesUtils;
import org.propertyHelper.PropertyFileEnum;

import java.io.IOException;
import java.util.LinkedHashMap;

public class LoginSteps {

    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginSteps.class.getName());
    @When("User fill the invalid {string}email details on email field.")
    public void userFillTheInvalidEmailDetailsOnEmailField(String invalidEmail) throws IOException, ParseException {
        String email = JsonHelper.getValue(invalidEmail).toString();
        MM_LoginScreen login = new MM_LoginScreen();
        login.sendKeys_onEmail(email);
        logger.info("Invalid email id entered successfully : "+ email);
    }

    @When("User fill the invalid {string}password details on password field.")
    public void userFillTheInvalidPasswordDetailsOnPasswordField(String invalidPassword) throws IOException, ParseException {
        String pass = JsonHelper.getValue(invalidPassword).toString();
        MM_LoginScreen login = new MM_LoginScreen();
        login.sendKeys_onPassword(pass);
        logger.info("Invalid password entered successfully : "+pass);
    }

    @And("User click on submit button.")
    public void userClickOnSubmitButton() {
        MM_LoginScreen login = new MM_LoginScreen();
        login.click_onContinueButton();
        logger.info("clicked on submit button successfully !");
    }

    @Then("The error validation messages on email field display successfully.")
    public void theErrorValidationMessagesOnEmailDisplaySuccessfully(int day,String month,String email,String password) throws IOException, ParseException {
        MM_LoginScreen login = new MM_LoginScreen();
        LoginEndPoints apiLogin=new LoginEndPoints();
        LinkedHashMap<String, Object> api = apiLogin.getTheDetailsFromLoginAPI(day,month,email,password);
        Assert.assertTrue(login.isErrorMessageDisplayed());
        Assert.assertEquals(login.getErrorTextMessage(),api.get("message"));
        System.out.println(api.get("message"));
        logger.info("error validation message validated successfully !");
    }

    @Given("User should be on login screen.")
    public void userShouldBeOnLoginScreen() {
        MM_LoginScreen login = new MM_LoginScreen();
        login.isEmailField_displayed();
        login.isPasswordField_displayed();
        login.isContinueField_displayed();
        logger.info("mera monitor login screen verified successfully !");
    }

    @When("User fill the valid {string}email details on email field.")
    public void userFillTheValidEmailDetailsOnEmailField(String validEmail) {
        String email = PropertiesUtils.getProperty(PropertyFileEnum.CREAD,validEmail );
        MM_LoginScreen login = new MM_LoginScreen();
        login.clearEmailField();
        login.sendKeys_onEmail(email);
        logger.info("valid email id entered successfully : "+ email);
    }

    @When("User fill the valid {string}password details on password field.")
    public void userFillTheValidPasswordDetailsOnPasswordField(String password) {
        String pass = PropertiesUtils.getProperty(PropertyFileEnum.CREAD, password);
        MM_LoginScreen login = new MM_LoginScreen();
        login.clearPasswordField();
        login.sendKeys_onPassword(pass);
        logger.info("valid password entered successfully : "+pass );
    }


    @And("User clear the text from email id and password field.")
    public void userClearTheTextFromEmailIdAndPasswordField() {
        MM_LoginScreen login=new MM_LoginScreen();
        login.clearEmailField();
        login.sleepTime(1);
        login.clearPasswordField();
        logger.info("cleared email and text field from web application.");
    }
}
