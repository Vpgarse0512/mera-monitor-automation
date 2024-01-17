package org.pages;

import org.base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MM_LoginScreen extends BaseTest {
    public MM_LoginScreen() {
        PageFactory.initElements(driver, this);
    }
    @FindBy(name = "email")
    public WebElement email_field;
    @FindBy(name = "password")
    public WebElement password_field;
    @FindBy(id = "kt_sign_in_submit")
    public WebElement continue_button;
    @FindBy(xpath = "//a[contains(.,'Forgot Password')]")
    public WebElement forgot_password_link;
    @FindBy(xpath="//div[@class='alert-text font-weight-bold']")
    public WebElement errorMessage;

    public void sendKeys_onEmail(String email) {
        sendKeys(email_field, email);
    }
    public void clearEmailField()
    {
        clearFieldText(email_field);
    }
    public boolean isEmailField_displayed()
    {
        return isElementDisplayed(email_field);
    }

    public void sendKeys_onPassword(String password) {
        sendKeys(password_field, password);
    }
    public void clearPasswordField()
    {
        clearFieldText(password_field);
    }
    public boolean isPasswordField_displayed()
    {
        return isElementDisplayed(password_field);
    }
    public void click_onContinueButton()
    {
        click(continue_button);
    }
    public boolean isContinueField_displayed()
    {
        return isElementDisplayed(continue_button);
    }
    public void click_onForgotLink()
    {
        click(forgot_password_link);
    }
    public boolean isForgotPasswordLink_displayed()
    {
        return isElementDisplayed(forgot_password_link);
    }
    public boolean isErrorMessageDisplayed()
    {
        return isElementDisplayed(errorMessage);
    }
    public String getErrorTextMessage()
    {
        return getText(errorMessage);
    }
}
