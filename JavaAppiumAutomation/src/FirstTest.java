import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {
    private AppiumDriver driver;
    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","c:\\learning\\mobiletest\\JavaAppiumAutomation\\apks\\org.wikipedia.apk"); // full path to apk
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);

    }
    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void rightApp()
    {
        waitForElementByXpathAndClick(
          "//*[contains(@text,'Search Wikipedia')]",
          "can't find search input",
          5
        );
        waitForElementByXpathAndSendKeys(
                "//*[contains(@text,'Search…')]",
                "Java",
                "can't find search input",
                5
        );
        waitForElementPresentByXpath(
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]",
                "required search result is not present on page",
                15
        );
        System.out.println("application - ok, searching - ok, result - ok");
    }

    @Test
    public void searchCancelTest()
    {

        waitForElementByIdAndClick(
                "org.wikipedia:id/search_container",
                "can't find search input",
                5
        );
        waitForElementByXpathAndSendKeys(
                "//*[contains(@text,'Search…')]",
                "Java",
                "can't find search input",
                5
        );
        waitForElementPresentByXpath(
                "//*[@class='android.widget.FrameLayout'][2]",
                "there are less than two search results on the page",
                15
        );
        waitForElementByIdAndDblClick(
                "org.wikipedia:id/search_close_btn",
                "can't find search close button",
                5
        );

        waitForElementNotPresent(
                "org.wikipedia:id/search_close_btn",
                "close button still present on the page",
                10
        );
    }
    @Test
    public void wordExist()
    {
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text,'Search Wikipedia')]");
        element_to_init_search.click();
        WebElement element_to_init_search_line = waitForElementPresentByXpath(
                "//*[contains(@text,'Search…')]",
                "can't find search input"
        );

        Assert.assertTrue(element_to_init_search_line.isDisplayed());
    }

    private WebElement waitForElementPresentByXpath(String xpath, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message+"\n");
        By by= By.xpath(xpath);
        return  wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresentByXpath(String xpath, String error_message)
    {
        return  waitForElementPresentByXpath(xpath, error_message, 5);
    }

    private WebElement waitForElementByXpathAndClick(String xpath, String error_message, long timeoutInSeconds)
    {
        WebElement element =  waitForElementPresentByXpath(xpath, error_message, timeoutInSeconds);
        element.click();
        return element;
    }
    private WebElement waitForElementByXpathAndSendKeys(String xpath, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element =  waitForElementPresentByXpath(xpath, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementPresentById(String id, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message+"\n");
        By by= By.id(id);
        return  wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
    private WebElement waitForElementByIdAndClick(String id, String error_message, long timeoutInSeconds)
    {
        WebElement element =  waitForElementPresentById(id, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementByIdAndDblClick(String id, String error_message, long timeoutInSeconds)
    {
       //Actions action = new Actions(driver);
        WebElement element =  waitForElementPresentById(id, error_message, timeoutInSeconds);
       // action.doubleClick(element).perform();

        element.click();
        element.click();
        return element;
    }
    private boolean waitForElementNotPresent(String id, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message+"\n");
        By by= By.id(id);
        return  wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
}
