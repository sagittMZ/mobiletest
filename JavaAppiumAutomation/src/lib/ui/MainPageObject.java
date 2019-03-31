package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class MainPageObject {
    protected AppiumDriver driver;
    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        // By by= By.xpath(xpath);
        return wait.until(presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }


    public void startSearchingArticleAndGoTo(String article_name, String search_word) {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_word,
                "can't find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'" + article_name + "')]"),
                "can't find right article",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );
    }

    public void articleSearchAndGoToWithoutWaitTitle(String article_name, String search_word) {
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
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'" + article_name + "')]"),
                "can't find right article",
                5
        );

        //Assert.assertNotNull("cant find title of article", presenceOfElementLocated(By.id("org.wikipedia:id/view_page_title_text")));
    }

    public void assertElementNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElement(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public void firstSavingArticle(String name_of_folder) {
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

    public void anotherSavingArticles(String name_of_folder) {
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
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "can't find earlier saved folder: " + name_of_folder + ".",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "can't find the 'X' button, cant close article",
                15
        );

    }

    public void goToMyList() {
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "can't find the navigation button to 'My list' option",
                10
        );
    }

    public void selectTheFolder(String name_of_folder) {
        waitForElementAndClick(
                //  By.xpath("//org.wikipedia:id/item_title[@text='Learning programming']"),

                By.xpath("//*[@text='" + name_of_folder + "']"), // добавляем переменную используя конкатенацию, чтоб не слился xpath "+ %var_name% +"
                "can't find the 'Learning programming' folder",
                15
        );
    }

    public void deleteArticleByLeftSwipe(String article_name) {
        //String article_name = "Java (programming language";
        swipeElementToLeft(
                By.xpath("//*[@text='" + article_name + "']"),
                "can't find saved article"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='" + article_name + "']"),
                "can't delete saved article",
                25
        );
    }

    public WebElement waitForElementAndDblClick(By by, String error_message, long timeoutInSeconds) {
        //Actions action = new Actions(driver);
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        // action.doubleClick(element).perform();

        element.click();
        element.click();
        return element;
    }
    public void presenceOfElementLocatedBy(By by, String error_message)
    {
        Assert.assertNotNull(error_message, presenceOfElementLocated(by));
        return;
    }

    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");

        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int y_start = (int) (size.height * 0.8);
        int y_end = (int) (size.height * 0.2);
        action
                .press(x, y_start)
                .waitAction(timeOfSwipe)
                .moveTo(x, y_end)
                .release()
                .perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        // driver.findElements(by); //search all elements
        // driver.findElements(by).size(); //return number of elements
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "cant find element during swiping up \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }

    }

    public void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                25);
        int x_left = element.getLocation().getX(); //находим положение самой левой точки элемента по оси Х
        int x_right = x_left + element.getSize().getWidth(); //к найденой точке добавляем размер элемента по ширине
        int y_upper = element.getLocation().getY();
        int y_lower = y_upper + element.getSize().getHeight();
        int y_midle = (y_upper + y_lower) / 2;  //положение середины элемента по вертикали
        TouchAction action = new TouchAction(driver);
        action
                .press(x_right, y_midle)
                .waitAction(450)
                .moveTo(x_left, y_midle)
                .release()
                .perform();
    }

    public int getAmountOfElement(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        return element.getAttribute(attribute);
    }
}
