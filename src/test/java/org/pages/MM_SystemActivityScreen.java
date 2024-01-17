package org.pages;

import lombok.Getter;
import org.base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
    @FindBy(xpath = "//a[@class='previousBttn ']")
    private WebElement previousButton;
    @FindBy(xpath = "//a[@class='nextBttn']")
    private WebElement nextButton;
    @Getter
    @FindBy(xpath = "//select[@class='form-control form-control-lg form-control-soli custom-select-class']")
    private WebElement dropdownSelection;
    public WebElement getDropdownElement()
    {
        return dropdownSelection;
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
}
