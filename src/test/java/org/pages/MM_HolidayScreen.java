package org.pages;

import org.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MM_HolidayScreen extends BaseTest {
    public MM_HolidayScreen() {
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//span[@class='menu-title' and .='Holiday']")
    private WebElement holidayTab;
    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1']")
    private WebElement holiday_title;
    @FindBy(xpath = "//th[@class='min-w-150px text-start ']")
    private WebElement name_title;
    @FindBy(xpath = "//th[@class='min-w-150px ']")
    private WebElement day_title;
    @FindBy(xpath = "//th[@class='min-w-150px text-start']")
    private WebElement date_title;

    public void clickOnHolidayTab()
    {
        click(holidayTab);
    }
    public String getHolidayTitle()
    {
        return getText(holiday_title);
    }
    public String getNameTitle()
    {
        return getText(name_title);
    }
    public String getDayTitle()
    {
        return getText(day_title);
    }
    public String getDateTitle()
    {
        return getText(date_title);
    }


    public Map<String, List<String>> getTableData() {
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
