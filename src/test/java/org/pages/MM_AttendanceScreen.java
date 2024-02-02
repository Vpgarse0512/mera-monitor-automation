package org.pages;

import org.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MM_AttendanceScreen extends BaseTest {
    public MM_AttendanceScreen() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[@class='menu-title' and text()='Attendance']")
    private WebElement attendanceTab;
    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1']")
    private WebElement attendance_title;
    @FindBy(xpath = "//button[@class='btn btn-primary' and text()='Download']")
    private WebElement download_button;
    @FindBy(xpath = "//th[@class=' min-w-150px']")
    private WebElement name_title;
    @FindBy(xpath = "//th[@class=' min-w-120px']")
    private WebElement date_title;
    @FindBy(xpath = "//th[@class=' min-w-150px text-center']")
    private WebElement logged_hours_title;
    @FindBy(xpath = "//th[@class=' min-w-140px text-center']")
    private WebElement total_hours_Expected_title;
    @FindBy(xpath = "//th[contains(text(),'Holidays')]")
    private WebElement no_of_holidays;
    @FindBy(xpath = "//th[contains(text(),'No of Leaves')]")
    private WebElement no_of_leaves;
    @FindBy(xpath = "//h2[@id='fc-dom-1']")
    private WebElement month_year_title;
    @FindBy(xpath = "//td[@class='fc-day fc-day-fri fc-day-today fc-daygrid-day']")
    private WebElement current_day;
    @FindBy(xpath = "//div[@class='react-datepicker__input-container']")
    private WebElement date_picker;

    @FindBy(xpath = "(//span[@class='text-dark d-block fs-6'])[1]")
    private WebElement userName;
    @FindBy(xpath = "(//span[@class='text-dark d-block fs-6'])[2]")
    private WebElement date;
    @FindBy(xpath = "(//span[@class='text-dark d-block fs-6 text-center'])[1]")
    private WebElement loggedHours;
    @FindBy(xpath = "(//span[@class='text-dark d-block fs-6 text-center'])[2]")
    private WebElement totalHours;
    @FindBy(xpath = "(//span[@class='text-dark d-block fs-6 text-center'])[3]")
    private WebElement noOfHolidays;
    @FindBy(xpath = "(//span[@class='text-dark d-block fs-6 text-center'])[4]")
    private WebElement noOfLeaves;

    @FindBy(xpath = "//input[@class='form-control custom-Height']")
    private WebElement datePicker;
    @FindBy(xpath = "(//div[@class='fc-event-title'])[5]")
    private WebElement inTime;
    @FindBy(xpath = "(//div[@class='fc-event-title'])[6]")
    private WebElement outTime;
    @FindBy(xpath = "(//div[@class='fc-popover-body ']//div[@class='fc-event-title'])[1]")
    private WebElement totalTime;
    @FindBy(xpath = "(//div[@class='fc-event-title'])[4]")
    private WebElement presentyTitle;
    @FindBy(xpath = "//a[@class='fc-daygrid-more-link fc-more-link']")
    private WebElement moreInfo;
    public void clickOnMoreLink()
    {
        click(moreInfo);
    }
    public String getInTime()
    {
        return getText(inTime);
    }
    public String getOutTime()
    {
        return getText(outTime);
    }
    public String getTotalTime()
    {
        return getText(totalTime);
    }
    public String getPresenty()
    {
        return getText(presentyTitle);
    }
    public void clickOnDatePicker() {
        click(datePicker);
    }

    public String getName() {
        return getText(userName);
    }

    public String getDate() {
        return getText(date);
    }
    public  int getTodayDate(){
        return Integer.parseInt(getText(date).trim().split("/")[0]);
    }

    public String getLoggedHours() {
        return getText(loggedHours);
    }

    public String getTotalHours() {
        return getText(totalHours);
    }

    public String getNoOfHolidays() {
        return getText(noOfHolidays);
    }

    public String getNoOfLeaves() {
        return getText(noOfLeaves);
    }

    public void clickOnAttendanceTab() {
        click(attendanceTab);
    }

    public String getAttendanceTitle() {
        return getText(attendance_title);
    }

    public boolean isAttendanceTitleDisplay() {
        return isElementDisplayed(attendance_title);
    }

    public void getDownloadButtonText() {
        getText(download_button);
    }

    public boolean isDownloadButtonDisplay() {
        return isElementDisplayed(download_button);
    }

    public String getNameTitle() {
        return getText(name_title);
    }

    public boolean isNameTitleDisplay() {
        return isElementDisplayed(name_title);
    }

    public String getDateTitle() {
        return getText(date_title);
    }

    public boolean isDateTitleDisplay() {
        return isElementDisplayed(date_title);
    }

    public String getLoggedHoursTitle() {
        return getText(logged_hours_title);
    }

    public boolean isLoggedHoursTitleDisplay() {
        return isElementDisplayed(logged_hours_title);
    }

    public String getTotalHoursExpectedTitle() {
        return getText(total_hours_Expected_title);
    }

    public boolean isTotalHoursExpectedTitleDisplay() {
        return isElementDisplayed(total_hours_Expected_title);
    }

    public String getNoHolidaysTitle() {
        return getText(no_of_holidays);
    }

    public boolean isNoHolidaysTitleDisplay() {
        return isElementDisplayed(no_of_holidays);
    }

    public String getNoLeavesTitle() {
        return getText(no_of_leaves);
    }

    public boolean isNoLeavesTitleDisplay() {
        return isElementDisplayed(no_of_leaves);
    }

    public String getMonthAndYearTitle() {
        return getText(month_year_title);
    }

    public boolean isMonthAndYearTitleDisplay() {
        return isElementDisplayed(month_year_title);
    }

    public String getCurrentDayTitle() {
        return getText(current_day);
    }

    public boolean isCurrentDayTitleDisplay() {
        return isElementDisplayed(current_day);
    }

    public boolean isDatePickerDisplay() {
        return isElementDisplayed(date_picker);
    }

    public void clickOnNameTitle()
    {
        click(name_title);
    }
    public void selectOldDate(int day, String month) {
        clickOnDatePicker();
        for (int i = 0; i <= 12; i++) {
            WebElement months = driver.findElement(By.xpath("//div[@class='react-datepicker__current-month']"));
            if (months.getText().contains(month)) {
                driver.findElement(By.xpath("//div[text()='"+day+"']")).click();
                break;
            } else {
                driver.findElement(By.xpath("//button[@class='react-datepicker__navigation react-datepicker__navigation--previous']")).click();
            }

        }
        clickOnNameTitle();
    }
}
