package org.pages;

import org.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MM_ProductivityIdleScreen extends BaseTest {
    public MM_ProductivityIdleScreen() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[@class='menu-title' and text()='Productivity vs Idle']")
    private WebElement productivity_Vs_IdleTab;
    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1']")
    private WebElement productivity_Vs_IdleTitle;
    @FindBy(xpath = "//th[@class=' min-w-120px fs-6 text-center']")
    private WebElement date_title;
    @FindBy(xpath = "//th[@class='min-w-100px fs-6 text-center' and text()='Productive Time']")
    private WebElement productiveTime_title;
    @FindBy(xpath = "//th[@class='min-w-100px fs-6 text-center' and text()='Unproductive Time']")
    private WebElement unproductiveTime_title;
    @FindBy(xpath = "//th[@class='min-w-100px fs-6 text-center' and text()='Idle Time']")
    private WebElement idleTime_title;
    @FindBy(xpath = "//th[@class='min-w-100px fs-6 text-center' and text()='Away Time']")
    private WebElement awayTime_title;
    @FindBy(xpath = "//th[@class='min-w-100px fs-6 text-center' and text()='Total Time']")
    private WebElement totalTime_title;
    @FindBy(xpath = "//th[@class='min-w-150px fs-6 text-center' and text()='Productivity']")
    private WebElement productivity_title;
    @FindBy(xpath = "//input[@class='form-control w-17']")
    private WebElement date_picker;
    @FindBy(xpath = "//input[@class='form-control w-17']")
    private WebElement date;
    @FindBy(xpath = "//span[@class='text-success  d-flex mb-1 mx-6 fs-6 ']")
    private WebElement productiveTime;
    @FindBy(xpath = "//span[@class=' d-flex mb-1 fs-6 mx-7']")
    private WebElement UnProductiveTime;
    @FindBy(xpath = "//span[@class='d-flex mb-1 fs-6 mx-6 justify-content-center']")
    private WebElement idleTime;
    @FindBy(xpath = "(//span[@class=' d-flex mb-1 fs-6 mx-6 justify-content-center'])[1]")
    private WebElement awayTime;
    @FindBy(xpath = "(//span[@class=' d-flex mb-1 fs-6 mx-6 justify-content-center'])[2]")
    private WebElement totalTime;
    @FindBy(xpath = "//*[@id=\"SvgjsG1511\"]")
    private WebElement productiveGreenGraph;
    @FindBy(xpath = "//*[@id=\"SvgjsPath2707\"]")
    private WebElement UnProductiveRedGraph;
    @FindBy(xpath = "//*[@id=\"SvgjsPath2716\"]")
    private WebElement idleYellowGraph;

    // popup screen

    @FindBy(xpath = "//*[@id=\"custom-apexChart\"]/table/tbody/tr/td[8]/span/div/div[2]/div[3]/div/div/div/div[1]/i")
    private WebElement cross;
    @FindBy(xpath = "//a[@id='kt_billing_6months_tab']")
    private WebElement productivePopupTitle;
    @FindBy(xpath = "//a[@id='kt_billing_1year_tab']")
    private WebElement unProductivePopupTitle;
    @FindBy(xpath = "(//a[@id='kt_billing_alltime_tab'])[1]")
    private WebElement idlePopupTitle;
    @FindBy(xpath = "(//a[@id='kt_billing_alltime_tab'])[2]")
    private WebElement keyboardMouseStockPopupTitle;
    @FindBy(xpath = "//h2[@class='noRecordFound']")
    private WebElement noRecordsFound;
    public void clickOnProductiveVsIdleTab() {
        clickWithoutWait(productivity_Vs_IdleTab);
    }

    public String getProductiveVsIdleTitle() {
        return getText(productivity_Vs_IdleTitle);
    }

    public String getDateTitle() {
        return getText(date_title);
    }

    public String getProductiveTimeTitle() {
        return getText(productiveTime_title);
    }

    public String getUnproductiveTimeTitle() {
        return getText(unproductiveTime_title);
    }

    public String getIdleTimeTitle() {
        return getText(idleTime_title);
    }

    public String getAwayTimeTitle() {
        return getText(awayTime_title);
    }

    public String getTotalTimeTitle() {
        return getText(totalTime_title);
    }

    public String getProductivityTitle() {
        return getText(productivity_title);
    }

    public boolean isDatePickerTitle() {
        return isElementDisplayed(date_picker);
    }

    public String getDate() {
        return getAttribute(date,"value");
    }

    public String getProductiveTime() {
        return getText(productiveTime);
    }

    public String getUnProductiveTime() {
        return getText(UnProductiveTime);
    }

    public String getIdleTime() {
        return getText(idleTime);
    }

    public String getAwayTime() {
        return getText(awayTime);
    }

    public String getTotalTime() {
        return getText(totalTime);
    }

    public void clickOnGreenProductiveGraph() {
       // mouseHover(productiveGreenGraph);
        sleepTime(1);
        click(productiveGreenGraph);
    }

    public void clickOnRedUnProductiveGraph() {
        click(UnProductiveRedGraph);
    }
    public void clickOnYellowIdleGraph()
    {
        click(idleYellowGraph);
    }
    public void clickOnCrossIcon()
    {
        click(cross);
    }
    public String getProductivePopupTitle()
    {
        return getText(productivePopupTitle);
    }
    public String getUnproductivePopupTitle()
    {
        return getText(unProductivePopupTitle);
    }
    public String getIdlePopupTitle()
    {
        return getText(idlePopupTitle);
    }
    public String getKeyboardMouseStockTitle()
    {
        return getText(keyboardMouseStockPopupTitle);
    }
    public boolean isProductiveSelected()
    {
        return isElementSelected(productivePopupTitle);
    }
    public boolean isUnProductiveSelected()
    {
        return isElementSelected(unProductivePopupTitle);
    }
    public void clickOnProductivityIdleTitle()
    {
        click(productivity_Vs_IdleTitle);
    }
    public void clickOnDatePicker()
    {
        click(date_picker);
    }

    public boolean isNoRecordFoundDisplay()
    {
     return isElementDisplayed(noRecordsFound);
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
        clickOnProductivityIdleTitle();
    }
}
