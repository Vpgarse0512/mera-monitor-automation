package org.pages;

import org.base.BaseTest;
import org.myStepdefs.ScreenshotSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class MM_ScreenshotScreen extends BaseTest {

    public MM_ScreenshotScreen()
    {
        PageFactory.initElements(driver,this);
    }
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ScreenshotSteps.class.getName());
    @FindBy(xpath = "//span[normalize-space()='Screenshot']")
    private WebElement screenShot_tab;
    @FindBy(xpath = "//span[@class='card-label fw-bolder fs-3 mb-1']")
    private WebElement screenShotTitle;
    @FindBy(xpath = "//input[@placeholder='Select a Date']")
    private WebElement datePicker;
    @FindBy(xpath = "//span[@class='image-gallery-index-total']")
    private WebElement maxScreenshotCount;
    @FindBy(xpath = "//span[@class='image-gallery-index-current']")
    private WebElement minimumScreenshotCount;
    @FindBy(xpath = "//button[@aria-label='Previous Slide']")
    private WebElement previousSlid;
    @FindBy(xpath = "//button[@class='image-gallery-icon image-gallery-right-nav']")
    private WebElement nextSlid;
    @FindBy(xpath = "//span[@class='image-gallery-description']")
    private List<WebElement> imageTimes;
    @FindBy(xpath = "//button[@aria-label='Play or Pause Slideshow']//*[name()='svg']")
    public WebElement playButton;

    public boolean isPlayButtonDisplayed()
    {
        return isElementDisplayed(playButton);
    }
    public void clickOnScreenshotTab()
    {
        click(screenShot_tab);
    }
    public String getScreenshotTitle()
    {
        return getText(screenShotTitle);
    }
    public boolean isDatePickerDisplay()
    {
        return isElementDisplayed(datePicker);
    }
    public boolean isNextSlidButtonDisplayed()
    {
        return isElementDisplayed(nextSlid);
    }
    public boolean isPreviousButtonDisplayed()
    {
        return isElementDisplayed(previousSlid);
    }
    public int getScreenshotMaxCount()
    {
        return Integer.parseInt(getText(maxScreenshotCount));
    }
    public int getScreenshotMinCount()
    {
        return Integer.parseInt(getText(minimumScreenshotCount));
    }
    public void clickOnPrevious()
    {
        click(previousSlid);
    }
    public void clickOnNext()
    {
        click(nextSlid);}
    public void clickOnDatePicker()
    {
        click(datePicker);
    }
    public void clickOnScreenshotTitle()
    {
        click(screenShotTitle);
    }
    public void verifyScreenshotsOnScreenshotScreen()
    {
        logger.info("Screenshot taken for the user is 'Count :'"+getScreenshotMaxCount());
        sleepTime(4);
        int i=1;
        while (i<=getScreenshotMaxCount())
        {
            String time=driver.findElement(By.xpath("(//span[@class='image-gallery-description'])["+i+"]")).getText();
            System.out.println(time);
            //Assert.assertEquals(time,);
            sleepTime(1);
            clickOnNext();

            i++;
        }
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
        clickOnScreenshotTitle();
    }

}
