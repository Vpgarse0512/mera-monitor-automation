package org.pages;

import lombok.Getter;
import org.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.LinkedHashMap;
import java.util.List;

public class MM_SystemActivityScreen extends BaseTest {
    public MM_SystemActivityScreen() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1 justify-content-center']")
    private WebElement system_activity_title;
    @FindBy(xpath = "//th[@class='min-w-150px text-start']")
    private WebElement appURL_title;
    @FindBy(xpath = "//th[@class='min-w-300px text-start']")
    private WebElement activity_title;
    @FindBy(xpath = "//th[@class='min-w-125px text-center' and text()='Start Time']")
    private WebElement start_time_title;
    @FindBy(xpath = "//th[@class='min-w-125px text-center' and text()='Time Spent']")
    private WebElement time_spend_title;
    @FindBy(xpath = "//span[@class='select-custom-header']")
    private WebElement rows_per_pageText;
    @FindBy(xpath = "//a[contains(@class,'previousBttn')]")
    public WebElement previousButton;
    @FindBy(xpath = "//a[@class='nextBttn']")
    private WebElement nextButton;
    @FindBy(xpath = "//input[@class='form-control ss_date']")
    private WebElement datePicker;
    @FindBy(xpath = "//select[@class='form-control form-control-lg form-control-soli custom-select-class']")
    private WebElement rangeCount;
    @Getter
    @FindBy(xpath = "//select[@class='form-control form-control-lg form-control-soli custom-select-class']")
    private WebElement dropdownSelection;

    @FindBy(xpath = "//*[@id=\"kt_content_container\"]/div/div/div/div[2]/div/div[1]/table/tbody/tr[1]/td[4]/span")
    private WebElement systemFirstActivity;
    @FindBy(xpath = "//h2[@class='noRecordFound']")
    private WebElement noRecordFound;

    public boolean isNoRecordDisplay()
    {
        boolean value = false;
        try {
            value=noRecordFound.isDisplayed();
        }catch (Exception ex){

        }
        return value;
    }

    public WebElement getDropdownElement()
    {
        return dropdownSelection;
    }
    public void clickOnDropdown()
    {
        click(dropdownSelection);
    }

    public String getSystemActivityTitle() {
        return getText(system_activity_title);
    }

    public String getAppsURLText() {
        return getText(appURL_title);
    }

    public String getTitleText() {
        return getText(activity_title);
    }

    public String getStartTimeTitle() {
        return getText(start_time_title);
    }

    public String getSpendTime() {
        return getText(time_spend_title);
    }
    public String getPagePerRows()
    {
        return getText(rows_per_pageText);
    }
    public String getDate()
    {
        return getAttribute(datePicker,"value");
    }
    private void clickOnDatePicker() {
    click(datePicker);
    }
    private void clickSystemActivityTitle() {
        click(system_activity_title);
    }
public void clickOnNextButton()
{
    click(nextButton);
}
    public void clickOnPreviousButton()
    {
        click(previousButton);
    }
    public String getRangeCount()
    {
        return getText(rangeCount);
    }
    public String getFirstSystemActivityTime()
    {
        return getText(systemFirstActivity);
    }
    public LinkedHashMap<String, List<String>> getTableData() {
        WebElement table = driver.findElement(By.xpath("//table[@class='table align-middle gs-0 gy-4']"));
        LinkedHashMap<String, List<String>> allRowsData = new LinkedHashMap<>();
        List<WebElement> headerCells = table.findElements(By.tagName("th"));
        List<String> headerNames = extractCellText(headerCells);
        int size= headerNames.size();
        for (int i = 1; i < size; i++) {
            List<WebElement> columnCells = table.findElements(By.xpath(".//td[" + (i + 1) + "]"));
            List<String> columnData = extractCellText(columnCells);
            allRowsData.put(headerNames.get(i), columnData);
        }

        return allRowsData;
    }

    private static List<String> extractCellText(List<WebElement> cells) {
        List<String> cellData = new java.util.ArrayList<>();
        for (WebElement cell : cells) {
            cellData.add(cell.getText());
        }
        return cellData;
    }

    public void selectPageInitiationOnSystemActivityScreen(String [] str)
    {
        jsScrollByElement(dropdownSelection);

        for (String option:str) {
            sleepTime(1);
            jsScrollByElement(dropdownSelection);
            clickOnDropdown();
            Select dropdown = new Select(getDropdownElement());
            dropdown.selectByVisibleText(option);
            sleepTime(1);
            log.info(" Selected the number :"+option +" from dropdown.");
        }
    }
    public void selectOldDate(int day, String month) {
        System.out.println(day +" "+month);
        clickOnDatePicker();
        for (int i = 0; i <= 12; i++) {
            WebElement months = driver.findElement(By.xpath("//div[@class='react-datepicker__current-month']"));
            System.out.println(months.getText());
            System.out.println(months);
            if (months.getText().contains(month)) {
                driver.findElement(By.xpath("//div[text()='"+day+"']")).click();
                break;
            } else {
                driver.findElement(By.xpath("//button[@class='react-datepicker__navigation react-datepicker__navigation--previous']")).click();
            }

        }
        clickSystemActivityTitle();
    }




}
