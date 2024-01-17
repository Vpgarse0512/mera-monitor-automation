package org.pages;

import org.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MM_TimeClaimScreen extends BaseTest {

    public MM_TimeClaimScreen() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(),'Time Claim')]")
    private WebElement timeClaimTab;
    @FindBy(xpath = "//span[normalize-space()='Claim']")
    private WebElement claimTab;
    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1']")
    private WebElement timeClaimTitle;
    @FindBy(xpath = "//input[contains(@placeholder,'Select a Date')]")
    private WebElement datePicker;
    @FindBy(xpath = "//div[normalize-space()='Date']")
    private WebElement dateTitle;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[1]")
    private WebElement date;
    @FindBy(xpath = "//div[normalize-space()='First Activity']")
    private WebElement firstActivityTitle;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[2]")
    private WebElement firstActivity;
    @FindBy(xpath = "//div[normalize-space()='Last Activity']")
    private WebElement lastActivityTitle;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[3]")
    private WebElement lastActivity;
    @FindBy(xpath = "//div[normalize-space()='Total Time']")
    private WebElement totalTimeTitle;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[4]")
    private WebElement totalTime;
    @FindBy(xpath = "//div[normalize-space()='Productive Time']")
    private WebElement productiveTimeTitle;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[5]")
    private WebElement productiveTime;
    @FindBy(xpath = "//div[normalize-space()='Idle Time']")
    private WebElement idleTimeTitle;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[6]")
    private WebElement idleTime;
    @FindBy(xpath = "//th[normalize-space()='Start Time']")
    private WebElement startTimeTitle;
    @FindBy(xpath = "//th[normalize-space()='End Time']")
    private WebElement endTimeTitle;
    @FindBy(xpath = "//th[normalize-space()='Spend Time']")
    private WebElement spendTimeTitle;
    @FindBy(xpath = "//th[normalize-space()='Activity Status']")
    private WebElement activityStatusTitle;
    @FindBy(xpath = "//th[normalize-space()='Request Status']")
    private WebElement requestStatusTitle;
    @FindBy(xpath = "//th[normalize-space()='Reason']")
    private WebElement reasonTitle;
    @FindBy(xpath = "//th[@class='min-w-150px text-center']")
    private WebElement actionTitle;
    @FindBy(xpath = "//span[@class='text-dark  d-flex fs-6']")
    private List<WebElement> startTimes;
    @FindBy(xpath = "//span[@class='text-dark  d-flex  fs-6']")
    private List<WebElement> endTimes;
    @FindBy(xpath = "//span[@class='d-flex fs-6  justify-content-center']")
    private List<WebElement> spendTimes;
    @FindBy(xpath = "//span[@class='text-dark  d-flex fs-6']")
    private List<WebElement> startTime;

    public String getDate()
    {
        return getText(date);
    }
    public String getFirstActivity()
    {
        return getText(firstActivity);
    }
    public String getLastActivity()
    {
        return getText(lastActivity);
    }
    public String getTotalTime()
    {
        return getText(totalTime);
    }
    public String getProductiveTime()
    {
        return getText(productiveTime);
    }
    public String getIdleTime()
    {
        return getText(idleTime);
    }
    public void clickOnTimeClaimTab()
    {
        click(timeClaimTab);
    }
    public void clickOnClaimTab()
    {
        click(claimTab);
    }
    public String getTimeClaimTitle()
    {
        return getText(timeClaimTitle);
    }
    public void clickOnTimeClaimTitle()
    {
        click(timeClaimTitle);
    }
    public String getDateTitle()
    {
        return getText(dateTitle);
    }
    public boolean isDatePickerDisplayed()
    {
        return isElementDisplayed(datePicker);

    }
    public String getFirstActivityTitle()
    {
        return getText(firstActivityTitle);
    }
    public String getLastActivityTitle()
    {
        return getText(lastActivityTitle);
    }
    public String getTotalTimeTitle()
    {
        return getText(totalTimeTitle);
    }
    public String getProductiveTimeTitle()
    {
        return getText(productiveTimeTitle);
    }
    public String getIdleTimeTitle()
    {
        return getText(idleTimeTitle);
    }
    public String getStartTimeTitle()
    {
        return getText(startTimeTitle);
    }
    public String getEndTimeTitle()
    {
        return getText(endTimeTitle);
    }
    public String getSpendTimeTitle()
    {
        return getText(spendTimeTitle);
    }
    public String getActivityStatusTitle()
    {
        return getText(activityStatusTitle);
    }
    public String getRequestStatusTitle()
    {
        return getText(requestStatusTitle);
    }
    public String getReasonTitle()
    {
        return getText(reasonTitle);
    }
    public String getActionTitle()
    {
        return getText(actionTitle);
    }
    public void selectOldDate(int day, String month) {
        click(datePicker);
        for (int i = 0; i <= 12; i++) {
            WebElement months = driver.findElement(By.xpath("//div[@class='react-datepicker__current-month']"));
            if (months.getText().contains(month)) {
                driver.findElement(By.xpath("//div[text()='"+day+"']")).click();
                break;
            } else {
                driver.findElement(By.xpath("//button[@class='react-datepicker__navigation react-datepicker__navigation--previous']")).click();
            }

        }
        clickOnTimeClaimTitle();
    }
}