package org.pages;

import org.base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MM_TimelinesScreen extends BaseTest {
    public MM_TimelinesScreen() {
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//span[@class='menu-title'][normalize-space()='Timeline']")
    private WebElement timelinesTab;
    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1']")
    private WebElement timelines_title;
    @FindBy(xpath = "//a[@class='nav-link btn btn-sm  btn-active btn-active-light-primary active fw-bolder px-4 me-1']")
    private WebElement list_button;
    @FindBy(xpath = "//a[@class='nav-link btn btn-sm  btn-active btn-active-light-primary fw-bolder px-4']")
    private WebElement graph_button;
    @FindBy(xpath = "//th[@class=' min-w-125px']")
    private WebElement appName_title;
    @FindBy(xpath = "//th[@class=' min-w-150px']")
    private WebElement url_title;
    @FindBy(xpath = "//th[@class=' min-w-125px text-center']")
    private WebElement timeElapsed_title;
    @FindBy(xpath = "//a[@class='rounded p-3 hover-scale hoverColm mb-2 ']")
    private List<WebElement> time_slots;

    public void clickOnTimelineTab()
    {
        click(timelinesTab);
    }
    public List<WebElement> getTimeSlots()
    {
        return time_slots;
    }
    public String getTimelinesTitle()
    {
        return getText(timelines_title);
    }

    public String getListButtonTitle()
    {
        return getText(list_button);
    }
    public void clickOnListButton()
    {
        click(list_button);
    }
    public String geGraphButtonTitle()
    {
        return getText(graph_button);
    }
    public void clickOnGraphButton()
    {
        click(graph_button);
    }
    public String getAppNameTitle()
    {
        return getText(appName_title);
    }
    public String getURLTitle()
    {
        return getText(url_title);
    }
    public String getTimeElapsedTitle()
    {
        return getText(timeElapsed_title);
    }
}
