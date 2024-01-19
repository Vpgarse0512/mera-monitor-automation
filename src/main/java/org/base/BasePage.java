package org.base;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.propertyHelper.PropertiesUtils;
import org.propertyHelper.PropertyFileEnum;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
@Listeners
public class BasePage extends BaseTest {

    private static Logger log = Logger.getLogger(BasePage.class.getName());
    public WebDriver driver;

    /**
     * ThreadLocal variable which contains the web driver instance which is
     * used to perform browser interactions with.
     */
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    @BeforeSuite
    public void dataReady(){
        String day = PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "day");
        String month = PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "month");
        String accessToken = PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        String url = PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "url");
        String browser = PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "browser");
        System.out.println("Day added in global property file : "+day+" and Month :"+month);
        System.setProperty("day",day);
        System.setProperty("month",month);
        System.setProperty("token",accessToken);
        System.setProperty("url",url);
        System.setProperty("browser",browser);
        log.info("all the data ready to used in system property !");
    }

    @BeforeClass
    public void setUp() {
        // browser name value passed from command line
        String browserName = System.getProperty("browser");
        if (browserName == null) {
            browserName = "chrome";
        }
        // if browser name passed as firefox
        if (browserName.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            webDriver.set(new FirefoxDriver());
            driver.navigate().to(PropertiesUtils.getProperty(PropertyFileEnum.GLOB,"url"));
        }
        // if browser name passed as chrome
        else if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            webDriver.set(driver);
            driver.navigate().to(System.getProperty("url"));
        } else {
            System.out.println(browserName + " Browser Not Supported");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
    }

    @AfterClass
    public void teardown()  {
        // Here will compare if test is failing then only it will enter into if condition
        log.info("Shutting down driver" + "\n" + "----------------------------------------------");
        System.out.println("\n");
        // quitting the web driver
        driver.quit();
        /*if (result.isSuccess()) {
            try {

                log.info("Shutting down driver" + "\n" + "----------------------------------------------");
                System.out.println("\n");
                // quitting the web driver
                getWebDriver().quit();

            } catch (Exception e) {
                // Create reference of TakesScreenshot
                TakesScreenshot ts = (TakesScreenshot) getWebDriver();
                // Call method to capture screenshot
                File source = ts.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(source, new File(".//screenshots//" + "FailScreenshot" +
                        new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss")
                                .format(new GregorianCalendar().getTime())
                        + ".png"));
                log.info("Scenario failed and screenshot saved in outputFiles folder");
            }
        }*/

    }

    /**
     * @return the webdriver for the current thread
     */
    public static WebDriver getWebDriver() {
        //System.out.println("WebDriver: " + webDriver.get());
        return webDriver.get();
    }
}
