package org.base;

import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.io.*;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseTest {
    public static final Logger log = LogManager.getLogger(BaseTest.class);
    public WebDriver driver;
    public static final int WAIT = 45;
    Properties configProp = new Properties();
    protected FileInputStream configFis;
    protected File file = new File("");

    public BaseTest() {

        this.driver = BasePage.getWebDriver();
    }

    /**
     * method to mouse hover on element
     *
     * @param element to mouse hover
     */
    public void mouseHover(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();

    }

    /**
     * method to select by visible text
     *
     * @param element to select
     */
    public void selectByVisibleText(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    /**
     * method to select checkbox
     *
     * @param element to be checked
     */
    public void selectCheckBox(WebElement element) {
        if (!element.isSelected()) {
            element.click();
        } else {
            Assert.assertFalse(true);
        }
    }

    /**
     * method to open specified url
     *
     * @param url to open
     */
    //Step to navigate to specified URL
    public void get(String url) {
        driver.get(url);
    }

    /**
     * method to navigate to specified page
     *
     * @param url navigation url
     */
    public void navigate(String url) {
        driver.navigate().to(url);
    }

    /**
     * method to click on an element with action class
     *
     * @param element to be clicked
     */
    public void clickOnElementUsingActions(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click().perform();
    }

    /**
     * method to click on an element using javascript
     *
     * @param element to be clicked
     */
    public void clickOnElementUsingJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * method to click on specific element
     *
     * @param element to click
     */
    public void click(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }

    /**
     * method to click on specific element
     *
     * @param element to click
     */
    public void clickWithoutWait(WebElement element) {
        sleepTime(1);
        element.click();
    }

    /**
     * method to click on check box
     *
     * @param element to check
     */
    public void check(WebElement element) {
        sleepTime(1);
        element.click();
    }

    /**
     * method to get text of element
     *
     * @param element to get text
     */
    public String getText(WebElement element) {
        waitForVisibility(element);
        String label = element.getText().trim();
        return label;
    }

    /**
     * method to check is element display
     *
     * @param element to get text
     */
    public boolean isElementDisplayed(WebElement element) {
        boolean status = waitForVisibility(element);
        return status;
    }

    public boolean isElementSelected(WebElement element) {
        waitForInvisibility(element);
        return element.isSelected();
    }

    /**
     * method to send keys on element
     *
     * @param element to send text
     */
    public void sendKeys(WebElement element, String keys) {
        waitForVisibility(element);
        element.sendKeys(keys);
    }

    /**
     * method to get int part from a string
     *
     * @param getInt string passed
     * @return
     */
    public int getIntValue(String getInt) {
        Pattern intsOnly = Pattern.compile("\\d+");
        Matcher makeMatch = intsOnly.matcher(getInt);
        makeMatch.find();
        String inputInt = makeMatch.group();
        return Integer.parseInt(inputInt);
    }

    /**
     * method to get title of current webpage
     *
     * @return String name of a webpage
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * method to wait until page is loaded completely
     *
     * @param PageName String name of current webpage
     */
    public void waitForPageToLoad(String PageName) {
        String pageLoadStatus;
        JavascriptExecutor js;
        do {
            js = (JavascriptExecutor) driver;
            pageLoadStatus = (String) js.executeScript("return document.readyState");
//            Log.info(".");
        } while (!pageLoadStatus.equals("complete"));
//        Log.info(PageName + " page loaded successfully");
    }

    /**
     * method verify whether element is present on screen
     *
     * @param targetElement element to be present
     * @return true if element is present else throws exception
     * @throws InterruptedException Thrown when a thread is waiting, sleeping,
     *                              or otherwise occupied, and the thread is interrupted, either e
     *                              or during the activity.
     */
    public Boolean isElementPresent(By targetElement) throws InterruptedException {
        Boolean isPresent = driver.findElements(targetElement).size() > 0;
        return isPresent;
    }

    /**
     * method verify whether element is not present on screen
     *
     * @param targetElement element not to be present
     * @return true if element is not present else throws exception
     * @throws InterruptedException Thrown when a thread is waiting, sleeping,
     *                              or otherwise occupied, and the thread is interrupted, either e
     *                              or during the activity.
     */
    public Boolean isElementNotPresent(By targetElement) throws InterruptedException {
        Boolean isPresent = (driver.findElements(targetElement).size() == 0);
        return isPresent;
    }

    /**
     * method to wait for an element to be visible
     *
     * @param targetElement element to be visible
     * @return true if element is visible else throws TimeoutException
     */
    public boolean waitForVisibility(WebElement targetElement) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT));
            wait.until(ExpectedConditions.visibilityOf(targetElement));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element is not visible: " + targetElement);
            System.out.println(e.getMessage());
            throw new TimeoutException();
        }
    }

    public boolean waitForAllElementsVisibility(List<WebElement> webElements) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT));
            wait.until(ExpectedConditions.visibilityOfAllElements(webElements));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element is not visible: " + webElements);
            System.out.println(e.getMessage());
            throw new TimeoutException();
        }
    }

    /**
     * Method to wait for an element to be clickable
     *
     * @param targetElement element to be clickable
     * @param targetElement element to be invisible
     * @param targetElement element to be invisible
     * @return true if element is clickable else throws TimeoutException
     * <p>
     * method to wait for an element until it is invisible
     * @return true if element gets invisible else throws TimeoutException
     * <p>
     * method to find an element
     * @return WebElement if found else throws NoSuchElementException
     * <p>
     * method to wait for an element until it is invisible
     * @return true if element gets invisible else throws TimeoutException
     * <p>
     * method to find an element
     * @return WebElement if found else throws NoSuchElementException
     */
    public boolean waitForElementToBeClickable(WebElement targetElement) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT));
            wait.until(ExpectedConditions.elementToBeClickable(targetElement));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element is not clickable: " + targetElement);
            System.out.println(e.getMessage());
            throw new TimeoutException();
        }
    }

    /**
     * Method to wait for an element until it is invisible
     *
     * @param targetElement element to be invisible
     * @return true if element gets invisible else throws TimeoutException
     */
    public boolean waitForInvisibility(WebElement targetElement) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT));
            wait.until(ExpectedConditions.invisibilityOf(targetElement));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element is still visible: " + targetElement);
            System.out.println(e.getMessage());
            throw new TimeoutException();
        }
    }

    /**
     * Method to find an element
     *
     * @param locator element to be found
     * @return WebElement if found else throws NoSuchElementException
     */
    public WebElement findElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element;
        } catch (NoSuchElementException e) {
//            Log.logError(this.getClass().getName(), "findElement", "Element not found " + locator);
            throw new NoSuchElementException(e.getMessage());
        }
    }

    /**
     * Method to find all the elements of specific locator
     *
     * @param locator element to be found
     * @return return the list of elements if found else throws NoSuchElementException
     */
    public List<WebElement> findElements(By locator) {
        try {
            List<WebElement> element = driver.findElements(locator);
            return element;
        } catch (NoSuchElementException e) {
//            Log.logError(this.getClass().getName(), "findElements", "element not found" + locator);
            throw new NoSuchElementException(e.getMessage());
        }
    }

    /**
     * Method to match value with list elements and click on it
     *
     * @param fetchedListElements List of fetched value
     * @param valueToBeMatched    value to be matched with list elements
     */
    public void clickOnMatchingValue(List<WebElement> fetchedListElements, String valueToBeMatched) {
        for (WebElement element : fetchedListElements) {
            if (element.getText().equalsIgnoreCase(valueToBeMatched)) {
                element.click();
                return;
            }
        }
    }

    /**
     * method to check value contained in list elements and click on it
     *
     * @param fetchedListElements List of fetched value
     * @param valueToBeContained  value to be contained in list elements
     */
    public void clickOnContainingValue(List<WebElement> fetchedListElements, String valueToBeContained) {
        for (WebElement element : fetchedListElements) {
            if (element.getText().toLowerCase().contains(valueToBeContained.toLowerCase())) {
                element.click();
                //System.out.println("Trying to select: "+valueToBeContained );
                return;
            }
            //		System.out.println(element.getText() );
        }
    }

    /**
     * method to accept alert,
     * exception is thrown if no alert is present
     */
    public void acceptAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
            throw new NoAlertPresentException();
        }
    }

    /**
     * method to get message test of alert
     *
     * @return message text which is displayed
     */
    public String getAlertText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            return alertText;
        } catch (NoAlertPresentException e) {
            throw new NoAlertPresentException();
        }
    }

    /**
     * method to verify if alert is present
     *
     * @return returns true if alert is present else false
     */
    public boolean isAlertPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT));
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            throw new NoAlertPresentException();
        }
    }

    /**
     * method to select a value from dropdown with index
     *
     * @param selectLocator          element with select tag
     * @param valueToBeSelectedIndex index to be selected
     */
    public void selectValueFromDropdownViaIndex(By selectLocator, int valueToBeSelectedIndex) {
        Select selectFromDropdown = new Select(findElement(selectLocator));
        selectFromDropdown.selectByIndex(valueToBeSelectedIndex);
    }

    /**
     * @param -WebElement
     * @return - non
     * @Method - clear
     * @Description - clear text
     */
    public void clear(WebElement e) {
        waitForVisibility(e);
        e.clear();
    }

    /**
     * method to clear filed using keyboard keys
     *
     * @param e field element to clear
     */

    public void clearFieldText(WebElement e) {
        sleepTime(1);
        e.clear();
    }

    /**
     * method is to refresh the opened web page
     */
    public void refreshBrowser() {
        driver.navigate().refresh();
    }

    /**
     * @param -WebElement,String message
     * @return - non
     * @Method - click
     * @Description - click on the webelement and print msg on console
     */
    public void click(WebElement elm, String msg) {
        try {
            waitForVisibility(elm);
            log.info(msg);
            elm.click();

        } catch (StaleElementReferenceException e) {
            waitForVisibility(elm);
            log.info(msg);
            elm.click();

        }

    }

    /**
     * @param -WebElement,String Text & String message
     * @return - non
     * @Method - SendKeys
     * @Description - send text and print log msg on console
     */
    public void sendKeys(WebElement e, String txt, String msg) {
        waitForVisibility(e);
        log.info(msg);
        e.sendKeys(txt);
    }


    /**
     * @param -WebElement, String Attribute name
     * @return - non
     * @Method - getAttribute
     * @Description - get attribute of element
     */
    public String getAttribute(WebElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }


    /**
     * @param -WebElement,boolean value & String  message
     * @return - non
     * @Method - softAssert
     * @Description - boolean assert verify
     */
    public void softAssert(WebElement e, Boolean value, String msg) {
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(e.isDisplayed(), value.booleanValue(), msg);

    }

    /**
     * @param -WebElement
     * @return - non
     * @Method - scrollToElement
     * @Description - Scroll till the element view
     */
    public Status scrollToElement(WebElement element) {

        try {

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            return Status.PASS;
        } catch (Exception e) {

            log.info(e + "Fail");
            return Status.FAIL;
        }
    }

    /**
     * @param -int seconds
     * @return - non
     * @Method - sleepTime
     * @Description - wait statically for seconds
     */
    public void sleepTime(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param -web element
     * @return - non
     * @Method - jsClick
     * @Description - click on web element using java script executor
     */
    public void jsClick(WebElement elem) {
        waitForVisibility(elem);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        System.out.println("** going to click ");
        js.executeScript("arguments[0].click()", elem);
        System.out.println("**clicked");

    }

    /**
     * @param -web element
     * @return - non
     * @Method - jsScrollByElemen
     * @Description - Scroll element till the web element view
     */
    public void jsScrollByElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * @param - non
     * @return - non
     * @Method - implicitlyWait
     * @Description - implicit wait for page load or element
     */
    public void implicitlyWait() {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }


}
