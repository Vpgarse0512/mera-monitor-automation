package org.pages;

import org.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MM_HomeScreen extends BaseTest {
    public MM_HomeScreen() {
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//img[@class='aside-logo w-150px py-2']")
    public WebElement mera_monitor_image;
    @FindBy(xpath = "//span[text()='Dashboard']")
    public WebElement dashBoard_tab;
    @FindBy(xpath = "//span[text()='Report']")
    public WebElement report_tab;
    @FindBy(xpath = "//span[text()='Screenshot']")
    public WebElement screenshot_tab;
    @FindBy(xpath = "//span[text()='Time Claim']")
    public WebElement time_claim_tab;
    @FindBy(xpath = "//span[@class='menu-title' and .='Holiday']")
    public WebElement holiday_tab;
    @FindBy(xpath = "//span[@class='menu-title' and .='Time Tracker']")
    public WebElement time_tracker_tab;
    @FindBy(xpath = "//span[@class='menu-title' and .='System Activity']")
    public WebElement system_activity_tab;
    @FindBy(xpath = "//span[@class='menu-title' and .='Productivity vs Idle']")
    public WebElement productivity_idle_tab;
    @FindBy(xpath = "//span[@class='menu-title' and .='Web&Apps']")
    public WebElement web_app_tab;
    @FindBy(xpath = "//span[@class='menu-title' and .='Attendance']")
    public WebElement attendance_tab;
    @FindBy(xpath = "//span[@class='menu-title' and .='Timeline']")
    public WebElement timeline_tab;
    @FindBy(xpath = "//span[@class='menu-title' and .='Claim']")
    public WebElement claim_tab;
    @FindBy(xpath = "//span[@class='menu-title' and .='Status']")
    public WebElement status_tab;
    @FindBy(xpath = "(//div[@class='text-inverse-warning fw-bolder'])[1]")
    public WebElement active_time;
    @FindBy(xpath = "(//div[@class='card-body']//div[@class='text-inverse-warning fw-bolder fs-5 mb-2'])[1]")
    public WebElement active_time_tittle;
    @FindBy(xpath = "(//div[@class='card-body']//div[@class='text-inverse-warning fw-bolder fs-5 mb-2'])[2]")
    public WebElement idle_time_Tittle;
    @FindBy(xpath = "(//div[@class='text-inverse-warning fw-bolder'])[2]")
    public WebElement idle_time;
    @FindBy(xpath = "(//div[@class='card-body']//div[@class='text-inverse-warning fw-bolder fs-5 mb-2'])[3]")
    public WebElement total_time_tittle;
    @FindBy(xpath = "(//div[@class='text-inverse-warning fw-bolder mb-2 mt-5'])[1]")
    public WebElement total_time;
    @FindBy(xpath = "(//div[@class='card-body']//div[@class='text-inverse-warning fw-bolder fs-5 mb-2'])[4]")
    public WebElement first_activity_tittle;
    @FindBy(xpath = "(//div[@class='text-inverse-warning fw-bolder mb-2 mt-5'])[2]")
    public WebElement first_activity;
    @FindBy(xpath = "(//div[@class='card-body']//div[@class='text-inverse-warning fw-bolder fs-5 mb-2'])[5]")
    public WebElement last_activity_tittle;
    @FindBy(xpath = "(//div[@class='text-inverse-warning fw-bolder mb-2 mt-5'])[3]")
    public WebElement last_activity;
    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1' and .='Productivity vs Idle']")
    public WebElement productive_vs_idleTittle;
    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1' and .='Web & Apps']")
    public WebElement webAndAppsTitle;
    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1' and .='Attendance']")
    public WebElement attendanceTitle;
    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1' and .='Time']")
    public WebElement timeTitle;
    @FindBy(xpath = "//a[@class='btn btn-sm btn-color-muted ' and text()='Website']")
    public WebElement webSite_title;
    @FindBy(xpath = "//a[@class='btn btn-sm btn-color-muted ' and text()='Application']")
    public WebElement application_title;
    @FindBy(xpath = "(//span[@class='text-inverse-warning fw-bolder'])[1]")
    public WebElement activeTimePercent;
    @FindBy(xpath = "(//span[@class='text-inverse-warning fw-bolder'])[2]")
    public WebElement idleTimePercent;

    @FindBy(xpath = "(//span[@class='text-muted fw-bold fs-7'])[1]")
    public WebElement weaklyDate;
    @FindBy(xpath = "//h2[@class='noRecordFound']")
    public WebElement noRecordFound;
    @FindBy(xpath = "(//div[@class='d-flex justify-content-start flex-column']//span[@class='text-muted fw-bold text-muted d-block fs-7'])[1]")
    public WebElement todayDate;
    public int getTodayDate()
    {
        return Integer.parseInt(todayDate.getText().trim().split(" ")[2]);
    }
    public String getWeaklyDateOnProductiveVsIdleSection()
    {
        return getText(weaklyDate);
    }
    public String getActiveTimePercent()
    {
        return getText(activeTimePercent);
    }
    public String getIdleTimePercent()
    {
        return getText(idleTimePercent);
    }
    public boolean isMeraMonitorImageDisplay()
    {
        return isElementDisplayed(mera_monitor_image);
    }
    public void click_OnDashboardTab() {
        click(dashBoard_tab);
    }

    public boolean isDashboardTabDisplayed() {
        return isElementDisplayed(dashBoard_tab);
    }

    public String getDashboardTabText() {
        return getText(dashBoard_tab);
    }

    public void click_OnReportTab() {
        click(report_tab);
    }

    public boolean isReportTabDisplayed() {
        return isElementDisplayed(report_tab);
    }

    public String getReportTabText() {
        return getText(report_tab);
    }

    public void click_OnTimeTracker() {
        click(time_tracker_tab);
    }

    public boolean isTimeTrackerTabDisplayed() {
        return isElementDisplayed(time_tracker_tab);
    }

    public String getTimeTrackerTabText() {
        return getText(time_tracker_tab);
    }

    public void click_OnSystemActivity() {
        click(system_activity_tab);
    }

    public boolean isSystemActivityTabDisplayed() {
        return isElementDisplayed(system_activity_tab);
    }

    public String getSystemActivityTabText() {
        return getText(system_activity_tab);
    }

    public void click_OnProductivityVsIdle_Tab() {
        click(productivity_idle_tab);
    }

    public boolean isProductivityVsIdleTabDisplayed() {
        return isElementDisplayed(productivity_idle_tab);
    }

    public String getProductivityVsIdleTabText() {
        return getText(productive_vs_idleTittle);
    }

    public void click_OnWebAndApps() {
        click(web_app_tab);
    }

    public boolean isWebAndAppTabDisplayed() {
        return isElementDisplayed(web_app_tab);
    }

    public String getWebAndAppTabText() {
        return getText(webAndAppsTitle);
    }

    public void click_OnAttendanceTab() {
        click(attendance_tab);
    }

    public boolean isAttendanceTabDisplayed() {
        return isElementDisplayed(attendance_tab);
    }

    public String getAttendanceTabText() {
        return getText(attendanceTitle);
    }

    public void click_OnTimeline() {
        click(timeline_tab);
    }

    public boolean isTimelineTabDisplayed() {
        return isElementDisplayed(timeline_tab);
    }

    public String getTimeLineTabText() {
        return getText(timeline_tab);
    }

    public void click_onScreenShot_Tab() {
        click(screenshot_tab);
    }

    public boolean isScreenShotTabDisplayed() {
        return isElementDisplayed(screenshot_tab);
    }

    public String getScreenshotTabText() {
        return getText(screenshot_tab);
    }

    public void click_onTimeClaim() {
        click(time_claim_tab);
    }

    public boolean isTimeClaimTabDisplayed() {
        return isElementDisplayed(time_claim_tab);
    }

    public String getTimeClaimTabText() {
        return getText(time_claim_tab);
    }

    public void click_OnClaim_Tab() {
        click(claim_tab);
    }

    public boolean isClaimTabDisplayed() {
        return isElementDisplayed(claim_tab);
    }

    public String getClaimTabText() {
        return getText(claim_tab);
    }

    public void click_OnStatus() {
        click(status_tab);
    }

    public boolean isStatusTabDisplayed() {
        return isElementDisplayed(status_tab);
    }

    public String getStatusTabText() {
        return getText(status_tab);
    }

    public void click_OnHoliday() {
        click(holiday_tab);
    }

    public boolean isHolidayTabDisplayed() {
        return isElementDisplayed(holiday_tab);
    }

    public String getHolidayTabText() {
        return getText(holiday_tab);
    }

    public String getActiveTime() {
        return getText(active_time);
    }

    public String getIdleTime() {
        return getText(idle_time);
    }

    public String getTotalTime() {
        return getText(total_time);
    }

    public String getFirstActivity() {
        return getText(first_activity);
    }

    public String getLastActivity() {
        return getText(last_activity);
    }

    public String getActiveTittle() {
        return getText(active_time_tittle);
    }

    public String getIdleTittle() {
        return getText(idle_time_Tittle);
    }

    public String getTotalHoursTittle() {
        return getText(total_time_tittle);
    }

    public String getFirstActivityTittle() {
        return getText(first_activity_tittle);
    }

    public String getLastActivityTittle() {
        return getText(last_activity_tittle);
    }

    public String getProductiveVsIdleTitle() {
        return getText(productive_vs_idleTittle);
    }

    public String getWebAppsTitle() {
        return getText(webAndAppsTitle);
    }
    public String getAttendanceTitle()
    {
        return getText(attendanceTitle);
    }
    public String getTimeTitle()
    {
        return getText(timeTitle);
    }
    public String getWebsiteTitle()
    {
        return getText(webSite_title);
    }
    public String getApplicationTitle()
    {
        return getText(application_title);
    }
}
