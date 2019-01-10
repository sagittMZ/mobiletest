import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class FirstTest {
    private AppiumDriver driver;
    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","6.0");
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
        waitForElementAndClick(
          By.xpath("//*[contains(@text,'Search Wikipedia')]"),
          "can't find search input",
          5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "can't find search input",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "required search result is not present on page",
                15
        );
        System.out.println("application - ok, searching - ok, result - ok");
    }

    @Test
    public void searchCancelTest()
    {

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find search input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "can't find search input",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@class='android.widget.FrameLayout'][2]"),
                "there are less than two search results on the page",
                15
        );

        waitForElementAndClear(
          By.id("org.wikipedia:id/search_src_text") ,
          "clear unsuccessful",
          5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "can't find search close button",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "close button still present on the page",
                10
        );
    }
    @Test
    public void testCompareArticleTitle()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "can't find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "can't find right article",
                5
        );
        WebElement article_title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );
        String article_title = article_title_element.getAttribute("text");
        Assert.assertEquals(
                "unexpected title",
                "Java (programming language)",
                article_title
        );
    }
    @Test
    public void testSwipeArticle()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "can't find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "can't find search input",
                5
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );

        swipeUp(2000);
        swipeUp(2000);
    }

    @Test
    public void  saveCoupleArticleToMyListAndDeleteOne()
    {
//        String article_name1 ="Object-oriented programming language";
        String article_name1 ="Java (programming language)";
        String search_word="Java";
        String name_of_folder ="Learning programming";
        startSearchingArticleAndGoTo(article_name1, search_word);
        firstSavingArticle(name_of_folder);
        startSearchingArticleAndGoTo("Java version history", search_word);
        anotherSavingArticles(name_of_folder);
        goToMyList();
        selectTheFolder(name_of_folder);
        deleteArticleByLeftSwipe("Java version history");
        waitForElementPresent(
                By.xpath("//*[@text='"+article_name1+"']"),
                "can't find saved article",
                15
        );

    }
    @Test
    public void assertElementPresent()
    {
        String article_name ="Khinkali";
        String search_phrase="Khinkali";
        articleSearchAndGoToWithoutWaitTitle(article_name, search_phrase);
    }

    @Test
    public void saveFirstArticleToMyList()
    {
        //start search
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "can't find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "can't find right article",
                5
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "can't find 'more options button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "can't find option to adding article to reading list",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "can't find 'GOT IT' tip overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "cant find input to set name of articles folder",
                5
        );

        String name_of_folder ="Learning programming";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "cant put text to set name of articles folder",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "can't press to 'OK' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "can't find the 'X' button, cant close article",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "can't find the navigation button to 'My list' option",
                5
        );

        waitForElementAndClick(
              //  By.xpath("//org.wikipedia:id/item_title[@text='Learning programming']"),
                By.xpath("//*[@text='"+name_of_folder+"']"), // добавляем переменную используя конкатенацию, чтоб не слился xpath "+ %var_name% +"
                "can't find the 'Learning programming' folder",
                15
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "can't find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "can't delete saved article",
                5
        );

    }

    @Test
    public void testSwipeToArticleElement()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "can't find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "can't find search input",
                5
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "cant find the end of the article",
                25
        );

    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );

        //org.wikipedia:id/search_results_list - локатор общего контейнера с результатами поиска
        //org.wikipedia:id/page_list_item_container - локатор элемента в общем контейнере
        String search_phrase= "Linkin Park Discography";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_phrase,
                "can't find search input",
                5
        );
        // в данном случае /* спуск к дочернему элементу
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "can't find anything by the request "+ search_phrase,
                15
        );
        int amountOfSearchResults= getAmountOfElement(
                By.xpath(search_result_locator)
        );
        System.out.println(amountOfSearchResults);
        Assert.assertTrue(
                "there are few search results",
                amountOfSearchResults >0
        );
    }

    @Test
    public void wordExist()
    {
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text,'Search Wikipedia')]");
        element_to_init_search.click();
        WebElement element_to_init_search_line = waitForElementPresent(
                By.xpath("//*[contains(@text,'Search…')]"),
                "can't find search input"
        );

        Assert.assertTrue(element_to_init_search_line.isDisplayed());
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message+"\n");
       // By by= By.xpath(xpath);
        return  wait.until(presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message)
    {
        return  waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element =  waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element =  waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }


    private void startSearchingArticleAndGoTo(String article_name, String search_word)
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        //search_word="Java"
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_word,
                "can't find search input",
                5
        );
        //article_name ="Object-oriented programming language";
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'"+article_name+"')]"),
                "can't find right article",
                5
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );


    }

    private void articleSearchAndGoToWithoutWaitTitle(String article_name, String search_word)
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        //search_word="Java"
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_word,
                "can't find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'"+article_name+"')]"),
                "can't find right article",
                5
        );

        Assert.assertNotNull("cant find title of article",presenceOfElementLocated(By.id("org.wikipedia:id/view_page_title_text")));
    }

    private void firstSavingArticle(String name_of_folder)
    {
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "can't find 'more options button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "can't find option to adding article to reading list",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "can't find 'GOT IT' tip overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "cant find input to set name of articles folder",
                5
        );

        //String name_of_folder ="Learning programming";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "cant put text to set name of articles folder",
                5
        );


        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "can't press to 'OK' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "can't find the 'X' button, cant close article",
                15
        );


    }

    private void anotherSavingArticles(String name_of_folder)
    {
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "can't find 'more options button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "can't find option to adding article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='"+name_of_folder+"']"),
                "can't find earlier saved folder: "+name_of_folder+".",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "can't find the 'X' button, cant close article",
                15
        );

    }

    private void goToMyList()
    {
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "can't find the navigation button to 'My list' option",
                10
        );
    }

    private void selectTheFolder(String name_of_folder)
    {
        waitForElementAndClick(
                //  By.xpath("//org.wikipedia:id/item_title[@text='Learning programming']"),
                By.xpath("//*[@text='"+name_of_folder+"']"), // добавляем переменную используя конкатенацию, чтоб не слился xpath "+ %var_name% +"
                "can't find the 'Learning programming' folder",
                15
        );
    }

    private void deleteArticleByLeftSwipe(String article_name)
    {
        //String article_name = "Java (programming language";
        swipeElementToLeft(
                By.xpath("//*[@text='"+article_name+"']"),
                "can't find saved article"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='"+article_name+"']"),
                "can't delete saved article",
                25
        );
    }

    private WebElement waitForElementAndDblClick(By by, String error_message, long timeoutInSeconds)
    {
       //Actions action = new Actions(driver);
        WebElement element =  waitForElementPresent(by, error_message, timeoutInSeconds);
       // action.doubleClick(element).perform();

        element.click();
        element.click();
        return element;
    }
    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message+"\n");

        return  wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size=driver.manage().window().getSize();
        int x=size.width/2;
        int  y_start = (int)(size.height*0.8);
        int  y_end = (int)(size.height*0.2);
        action
                .press(x,y_start)
                .waitAction(timeOfSwipe)
                .moveTo(x, y_end)
                .release()
                .perform();
    }

    protected  void swipeUpQuick()
    {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes)
    {
       // driver.findElements(by); //search all elements
       // driver.findElements(by).size(); //return number of elements
        int already_swiped=0;
        while (driver.findElements(by).size() ==0){
            if(already_swiped>max_swipes){
                waitForElementPresent(by, "cant find element during swiping up \n" + error_message,0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }

    }

    protected void swipeElementToLeft(By by, String error_message)
    {
        WebElement element =  waitForElementPresent(
                by,
                error_message,
                25);
        int x_left = element.getLocation().getX(); //находим положение самой левой точки элемента по оси Х
        int x_right = x_left + element.getSize().getWidth(); //к найденой точке добавляем размер элемента по ширине
        int y_upper = element.getLocation().getY();
        int y_lower = y_upper + element.getSize().getHeight();
        int y_midle = (y_upper+y_lower)/2;  //положение середины элемента по вертикали
        TouchAction action = new TouchAction(driver);
        action
                .press(x_right,y_midle)
                .waitAction(450)
                .moveTo(x_left, y_midle)
                .release()
                .perform();
    }

    private int getAmountOfElement(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }
}
