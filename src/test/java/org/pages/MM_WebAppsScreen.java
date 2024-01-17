package org.pages;

import org.base.BasePage;
import org.base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MM_WebAppsScreen extends BaseTest {
    public MM_WebAppsScreen() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[@class='menu-title' and text()='Web&Apps']")
    private WebElement webAppsTab;
    @FindBy(xpath = "//a[@class='nav-link me-6 text-dark active-wrapper']")
    private WebElement webSite_link;
    @FindBy(xpath = "//a[@class='nav-link me-6 text-active-primary fw-bolder ']")
    private WebElement application_link;
    @FindBy(xpath = "//tr[@class='fw-bolder text-bold web-vistied']/th[.='Website']")
    private WebElement website_title;
    @FindBy(xpath = "//tr[@class='fw-bolder text-bold web-vistied']/th[.='Duration']")
    private WebElement duration_title;
    @FindBy(xpath = "//tr[@class='fw-bolder text-bold web-vistied']/th[.='Percentage']")
    private WebElement percentage_title;
    @FindBy(xpath = "//tr[@class='fw-bolder text-bold web-vistied ']/th[.='URL']")
    private WebElement URL_title;
    @FindBy(xpath = "//tr[@class='fw-bolder text-bold web-vistied ']/th[.='Duration']")
    private WebElement durationSecond_title;
    @FindBy(xpath = "//tr[@class='fw-bolder text-bold web-vistied ']/th[.='Percentage']")
    private WebElement percentageSecond_title;
    @FindBy(xpath = "//span[@class=' fw-bolder text-bold fs-4']")
    private WebElement total_time_spend_title;
    @FindBy(xpath = "//span[@class='min-w-100px fw-bolder text-bold fs-4']")
    private WebElement totalTime;
    @FindBy(xpath = "//div[@class='mt-4 text-bold fs-4 fw-bolder']")
    private WebElement site_url;
    @FindBy(xpath = "//tr[@style='cursor: pointer; font-size: 1.1rem;']//td")
    private List<WebElement> cellsOfWebsites;
    @FindBy(xpath = "//*[@id=\"kt_content_container\"]/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr/td")
    private List<WebElement> cellTwoWebsites;

    @FindBy(xpath = "//th[text()='App Name']")
    private WebElement appName_application;
    @FindBy(xpath = "(//th[text()='Duration'])[1]")
    private WebElement durationTitleApp_application;
    @FindBy(xpath = "(//th[text()='Percentage'])[1]")
    private WebElement percentageTitleApp_application;
    @FindBy(xpath = "//span[text()='Total Time Spent']")
    private WebElement totalTimeSpendTitleApp_application;
    @FindBy(xpath = "//th[text()='Title']")
    private WebElement titleApp_application;
    @FindBy(xpath = "(//th[text()='Duration'])[2]")
    private WebElement durationTitleSecondApp_application;
    @FindBy(xpath = "(//th[text()='Percentage'])[2]")
    private WebElement percentageSecondApp_application;

    public void clickOnWebAndAppsTab() {
        click(webAppsTab);
    }

    public String getWebsiteLinkTitle() {
        return getText(webSite_link);
    }

    public void clickOnWebSiteLink() {
        click(webSite_link);
    }

    public String getApplicationLinkTitle() {
        return getText(application_link);
    }

    public void clickOnApplicationLink() {
        click(application_link);
    }

    public String getWebSiteTitle() {
        return getText(website_title);
    }

    public String getDurationTitle() {
        return getText(duration_title);
    }

    public String getPercentageTitle() {
        return getText(percentage_title);
    }

    public String getURLTitle() {
        return getText(URL_title);
    }

    public String getDurationSecondTitle() {
        return getText(durationSecond_title);
    }

    public String getPercentageSecondTitle() {
        return getText(percentageSecond_title);
    }

    public String getTotalTimeSpendTitle() {
        return getText(total_time_spend_title);
    }

    public String getTotalTime() {
        return getText(totalTime);
    }

    public String getSiteUrlTitle() {
        return getText(site_url);
    }

    public String getAppNameTitle() {
        return getText(appName_application);
    }

    public String getDurationAppTitle() {
        return getText(durationTitleApp_application);
    }

    public String getPercentageAppTitle() {
        return getText(percentageTitleApp_application);
    }

    public String getTotalTimeSpendTitleAppSecond() {
        return getText(totalTimeSpendTitleApp_application);
    }

    public String getTitleApp() {
        return getText(titleApp_application);
    }

    public String getDurationTitleAppSecond() {
        return getText(durationTitleSecondApp_application);
    }

    public String getPercentageTitleAppSecond() {
        return getText(percentageSecondApp_application);
    }
}
