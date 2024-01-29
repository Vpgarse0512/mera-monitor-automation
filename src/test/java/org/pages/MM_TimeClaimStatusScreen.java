package org.pages;

import org.base.BaseTest;
import org.myStepdefs.ScreenshotSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MM_TimeClaimStatusScreen extends BaseTest {
    public MM_TimeClaimStatusScreen() {
        PageFactory.initElements(driver, this);
    }
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MM_TimeClaimStatusScreen.class.getName());

    @FindBy(xpath = "//span[normalize-space()='Status']")
    private WebElement statusTab;
    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1']")
    private WebElement timeClaimStatusTitle;
    @FindBy(xpath = "//input[@placeholder='Select Date Range']")
    private WebElement dateRangePicker;
    @FindBy(xpath = "//th[normalize-space()='Name']")
    private WebElement nameTitle;
    @FindBy(xpath = "//th[normalize-space()='Date']")
    private WebElement datesTitle;
    @FindBy(xpath = "//th[normalize-space()='Start Time']")
    private WebElement startTimesTitle;
    @FindBy(xpath = "//th[normalize-space()='End Time']")
    private WebElement endTimesTitle;
    @FindBy(xpath = "//th[normalize-space()='Reason']")
    private WebElement reasonTitle;
    @FindBy(xpath = "//th[normalize-space()='Approved / Rejected By']")
    private WebElement approveTitle;
    @FindBy(xpath = "(//th[contains(@class,'text-center')])[3]")
    private WebElement statusTitle;


    @FindBy(xpath = "(//span[@class='text-dark  d-block fs-6 '])[1]")
    private WebElement userName;
    @FindBy(xpath = "(//span[@class='text-dark  d-block fs-6 '])[2]")
    private WebElement date;
    @FindBy(xpath = "//span[@class='text-dark  d-block fs-6 text-center']")
    private WebElement startTime;
    @FindBy(xpath = "(//span[@class='text-dark d-block fs-6 text-center'])[1]")
    private WebElement endTime;
    @FindBy(xpath = "(//span[@class='text-dark d-block fs-6'])[1]")
    private WebElement reason;
    @FindBy(xpath = "(//span[@class='text-dark d-block fs-6'])[2]")
    private WebElement approvedBy;
    @FindBy(xpath = "//span[@class=' badge fw-bold me-auto px-4 py-3 badge-light-success ']")
    private WebElement status;
    @FindBy(xpath = "//h2[@class='noRecordFound user-select-none']")
    private WebElement noRecordFound;

    public Object foundNoRecordText()
    {
        String str = "dataPresent";
        try {

            if (noRecordFound.isDisplayed())
                str = null;
        }catch (Exception ex){
            logger.info(ex.toString());
        }
        return str;
    }
    public String getUserName()
    {
        return getText(userName);
    }
    public String getClaimDate()
    {
        return getText(date);
    }
    public String getStartTime()
    {
        return getText(startTime);
    }
    public String getEndTime()
    {
        return getText(endTime);
    }
    public String getReason()
    {
        return getText(reason);
    }
    public String getApprovedBy()
    {
        return getText(approvedBy);
    }
    public String getStatus()
    {
        return getText(status);
    }
    public void clickOnStatusTab()
    {
        click(statusTab);
    }
    public String getTimeClaimStatusTitle()
    {
        return getText(timeClaimStatusTitle);
    }
    public void clickOnDateRangePicker()
    {
        click(dateRangePicker);
    }
    public boolean isDateRangePicker()
    {

        return isElementDisplayed(dateRangePicker);
    }
    public String getNameTitle()
    {
        return getText(nameTitle);
    }
    public String getDatesTitle()
    {
        return getText(datesTitle);
    }
    public String getStartTimesTitle()
    {
        return getText(startTimesTitle);
    }
    public String getEndTimesTitle()
    {
        return getText(endTimesTitle);
    }
    public String getReasonTitle()
    {
        return getText(reasonTitle);
    }
    public String getApproveTitle()
    {
        return getText(approveTitle);
    }
    public String getStatusTitle()
    {
        return getText(statusTitle);
    }
    public void clickOnTimeClaimStatusTitle()
    {
        click(timeClaimStatusTitle);
    }

    public void selectOldDate(int day, String month) {
       clickOnDateRangePicker();
        for (int i = 0; i <= 12; i++) {
            WebElement months = driver.findElement(By.xpath("//div[@class='react-datepicker__current-month']"));
            if (months.getText().toLowerCase().contains(month.toLowerCase())) {
                driver.findElement(By.xpath("//div[text()='"+day+"']")).click();
                break;
            } else {
                driver.findElement(By.xpath("//button[@class='react-datepicker__navigation react-datepicker__navigation--previous']")).click();
            }

        }
        clickOnTimeClaimStatusTitle();
    }
}

