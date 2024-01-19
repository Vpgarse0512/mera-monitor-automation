package org.pages;

import org.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MM_TimeTrackerScreen extends BaseTest {
    public MM_TimeTrackerScreen() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1']")
    private WebElement time_tracker_title;

    @FindBy(xpath = "//tr")
    private List<WebElement> rows;
    @FindBy(xpath = "//td")
    private List<WebElement> columns;
    @FindBy(xpath = "//div[@class='react-datepicker__input-container']")
    private WebElement date_picker;
    @FindBy(xpath = "//button[@aria-label='Previous Month']")
    private WebElement previous_month;
    @FindBy(xpath = "//button[@aria-label='Next Month']")
    private WebElement next_month;
    @FindBy(xpath = "(//span[@class='text-dark  d-flex mb-1 fs-6'])[1]")
    private WebElement name;
    @FindBy(xpath = "(//span[@class='text-dark  d-flex mb-1 fs-6'])[2]")
    private WebElement date;
    @FindBy(xpath = "//div[@class='react-datepicker__week']")
    private WebElement week_picker;
    @FindBy(xpath = "(//span[@class='d-flex mb-1 fs-6 justify-content-center'])[1]")
    private WebElement department;
    @FindBy(xpath = "(//span[@class='d-flex mb-1 fs-6 justify-content-center'])[3]")
    private WebElement away_time;
    @FindBy(xpath = "(//span[@class='d-flex mb-1 fs-6 justify-content-center'])[2]")
    private WebElement idle_time;
    @FindBy(xpath = "//span[@class='text-success  d-flex mb-1 fs-6 justify-content-center']")
    private WebElement active_time;
    @FindBy(xpath = "(//span[@class=' d-flex mb-1 fs-6 justify-content-center'])[1]")
    private WebElement total_time;
    @FindBy(xpath = "(//span[@class=' d-flex mb-1 fs-6 justify-content-center'])[2]")
    private WebElement first_activity;
    @FindBy(xpath = "(//span[@class=' d-flex mb-1 fs-6 justify-content-center'])[3]")
    private WebElement last_Activity;
    @FindBy(xpath = "//th[text()='Name']")
    private WebElement name_title;
    @FindBy(xpath = "//th[text()=' Date ']")
    private WebElement date_title;
    @FindBy(xpath = "//th[text()='Department ']")
    private WebElement department_title;
    @FindBy(xpath = "//th[text()='Active Time']")
    private WebElement activeTime_title;
    @FindBy(xpath = "//th[text()='Idle Time']")
    private WebElement idleTime_title;
    @FindBy(xpath = "//th[text()='Away Time']")
    private WebElement awayTime_title;
    @FindBy(xpath = "//th[text()='Total Time']")
    private WebElement totalTime_title;
    @FindBy(xpath = "//th[text()='First Activity']")
    private WebElement firstActivity_title;
    @FindBy(xpath = "//th[text()='Last Activity']")
    private WebElement lastActivity_title;
    @FindBy(xpath = "//div[@class='react-datepicker__input-container']")
    private WebElement datePicker;
    public void clickOnName()
    {
        click(name_title);
    }
    private void clickOnDatePicker() {
        click(datePicker);
    }
    public String getTimeTrackerTittle() {
        return getText(time_tracker_title);
    }

    public String getUserName() {
        return getText(name);
    }

    public String getDate() {
        return getText(date);
    }

    public String getDepartment() {
        return getText(department);
    }

    public String getAwayTime() {
        return getText(away_time);

    }

    public String getIdleTime() {
        return getText(idle_time);

    }

    public String getActiveTime() {
        return getText(active_time);
    }

    public String getTotalTime() {
        return getText(total_time);
    }

    public String getFirstActivity() {
        return getText(first_activity);
    }

    public String getLastActivity() {
        return getText(last_Activity);
    }

    public String getNameTitle() {
        return getText(name_title);
    }

    public String getDateTitle() {
        return getText(date_title);
    }
    public String getDepartmentTitle()
    {
        return getText(department_title);
    }
    public String getActivityTitle()
    {
        return getText(activeTime_title);
    }
    public String getIdleTimeTitle()
    {
        return getText(idleTime_title);
    }
    public String getAwayTimeTitle()
    {
        return getText(awayTime_title);
    }
    public String getTotalTimeTitle()
    {
        return getText(totalTime_title);
    }
    public String getFirstActivityTitle()
    {
        return getText(firstActivity_title);
    }
    public String getLastActivityTitle()
    {
        return getText(lastActivity_title);
    }


    public void selectOldDate(int day, String month) {
        System.out.println(day +" "+month);
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
        clickOnName();
    }
    public void getTableElement()
    {
        WebElement table = driver.findElement(By.xpath("//table[@class='table align-middle gs-0 gy-4']"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (int i=1;i<rows.size();i++)
        {
         WebElement row=rows.get(i);
            List<WebElement> columns = row.findElements(By.tagName("td"));
            for (int j=1;j<columns.size();j++)
            {
                WebElement colum = columns.get(j);
                System.out.print(colum.getText()+"\t");
            }
        }
        System.out.println();
    }

        public  Map<String, List<String>> getTableData() {
            WebElement table = driver.findElement(By.xpath("//table[@class='table align-middle gs-0 gy-4']"));
            Map<String, List<String>> allRowsData = new HashMap<>();
            List<WebElement> headerCells = table.findElements(By.tagName("th"));
            List<String> headerNames = extractCellText(headerCells);

            for (int i = 1; i < headerNames.size(); i++) {
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


}
