package org.pages;

import org.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MM_ActivitySummeryScreen extends BaseTest {
    public MM_ActivitySummeryScreen() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[@class='menu-title' and text()='Activity Summary']")
    private WebElement activitySummeryTab;

    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1']")
    private WebElement activeSummery_title;

    @FindBy(xpath = "//input[@class='form-control ss_date']")
    private WebElement datePicker;

    @FindBy(xpath = "(//div[@class='fs-6 fw-bolder'])[1]")
    private WebElement date_title;

    @FindBy(xpath = "(//div[@class='fs-6 fw-bolder'])[2]")
    private WebElement firstActivity_title;

    @FindBy(xpath = "(//div[@class='fs-6 fw-bolder'])[3]")
    private WebElement lastActivity_title;
    @FindBy(xpath = "(//div[@class='fs-6 fw-bolder'])[4]")
    private WebElement totalTime_title;
    @FindBy(xpath = "(//div[@class='fs-6 fw-bolder'])[5]")
    private WebElement productiveTime_title;
    @FindBy(xpath = "(//div[@class='fs-6 fw-bolder'])[6]")
    private WebElement idleTime_title;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[1]")
    private WebElement date;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[2]")
    private WebElement firstActivity;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[3]")
    private WebElement lastActivity;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[4]")
    private WebElement totalTime;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[5]")
    private WebElement productiveTime;
    @FindBy(xpath = "(//div[@class='fw-semibold text-white'])[6]")
    private WebElement idleTime;

    @FindBy(xpath = "//table[@class='table align-middle gs-0 gy-4']")
    private WebElement table;
    @FindBy(xpath = "//tr")
    private List<WebElement> rows;
    @FindBy(xpath = "//th")
    private List<WebElement> headerCell;
    @FindBy(xpath = "//td")
    private List<WebElement> bodyCells;

    public String getDatePickerDate()
    {
        return getAttribute(datePicker,"value");
    }
    public String getActivitySummeryTittle()
    {
        return getText(activeSummery_title);
    }
    public void clickOnActivitySummeryTab() {
        click(activitySummeryTab);
    }

    public String getDateTitle() {
        return getText(date_title);
    }

    public String getFirstActivityTitle() {
        return getText(firstActivity_title);
    }

    public String getLastActivityTitle() {
        return getText(lastActivity_title);
    }

    public String getTotalTimeTitle() {
        return getText(totalTime_title);
    }

    public String getProductiveTimeTitle() {
        return getText(productiveTime_title);
    }

    public String getIdleTimeTitle() {
        return getText(idleTime_title);
    }

    public String getDate() {
        return getText(date);
    }

    public String getFirstActivity() {
        return getText(firstActivity);
    }

    public String getLastActivity() {
        return getText(lastActivity);
    }

    public String getTotalTime() {
        return getText(totalTime);
    }

    public String getProductiveTime() {
        return getText(productiveTime);
    }

    public String getIdleTime() {
        return getText(idleTime);
    }

    public void getTableElement() {
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (int i = 1; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            List<WebElement> columns = row.findElements(By.tagName("td"));
            for (int j = 1; j < columns.size(); j++) {
                WebElement colum = columns.get(j);
                System.out.print(colum.getText() + "\t");
            }
        }
        System.out.println();
    }

    public LinkedHashMap<String, List<String>> getTableData() {
        LinkedHashMap<String, List<String>> allRowsData = new LinkedHashMap<>();
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
