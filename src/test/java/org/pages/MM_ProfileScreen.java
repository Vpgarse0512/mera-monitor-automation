package org.pages;

import org.base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MM_ProfileScreen extends BaseTest {
    public MM_ProfileScreen() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='fw-bolder d-flex align-items-center fs-5']")
    private WebElement profile_name;
    @FindBy(xpath = "//a[@class='fw-bold text-muted text-hover-primary fs-7']")
    private WebElement email_profile;
    @FindBy(xpath = "//a[@class='menu-link px-5' and .='Change Password']")
    private WebElement change_Password;
    @FindBy(xpath = "//a[@class='menu-link px-5' and .='Sign Out']")
    private WebElement sign_out;
    @FindBy(xpath = "//div[@class='cursor-pointer symbol symbol-30px symbol-md-40px']")
    private WebElement profile;

    public void click_OnProfile()
    {
        click(profile);
    }
    public String GetProfileName()
    {
        return getText(profile_name);
    }
    public String getProfileEmail()
    {
        return getText(email_profile);
    }
    public void click_OnChangePassword()
    {
        click(change_Password);
    }
    public String getChangePasswordText()
    {
        return getText(change_Password);
    }
    public void click_OnSignOut()
    {
        click(sign_out);
    }
    public String getSignOutText()
    {
        return getText(sign_out);
    }

}
