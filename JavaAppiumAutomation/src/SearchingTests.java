import lib.CoreTestCase;
import lib.ui.MainPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

public class SearchingTests extends CoreTestCase {
     private MainPageObject MainPageObect;
     protected void setUp() throws Exception {
         super.setUp();
         MainPageObect = new MainPageObject(driver);
     }

    @Test
    public void testRightApp() {
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        MainPageObect.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "can't find search input",
                5
        );
        MainPageObect.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "required search result is not present on page",
                15
        );
        System.out.println("application - ok, searching - ok, result - ok");
    }

    @Test
    public void testSearchCancelTest() {

        MainPageObect.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find search input",
                5
        );
        MainPageObect.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "can't find search input",
                5
        );
        MainPageObect.waitForElementPresent(
                By.xpath("//*[@class='android.widget.FrameLayout'][2]"),
                "there are less than two search results on the page",
                15
        );

        MainPageObect.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "clear unsuccessful",
                5
        );

        MainPageObect.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "can't find search close button",
                5
        );

        MainPageObect.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "close button still present on the page",
                10
        );
    }

    @Test
    public void testCompareArticleTitle() {
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        MainPageObect.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "can't find search input",
                5
        );
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "can't find right article",
                5
        );
        WebElement article_title_element = MainPageObect.waitForElementPresent(
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
    public void testSwipeArticle() {
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        MainPageObect.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "can't find search input",
                5
        );
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "can't find search input",
                5
        );
        MainPageObect.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );

        MainPageObect.swipeUp(2000);
        MainPageObect.swipeUp(2000);
    }

    @Test
    public void testSaveCoupleArticleToMyListAndDeleteOne() {

        String article_name1 = "Java (programming language)";
        String search_word = "Java";
        String name_of_folder = "Learning programming";
        MainPageObect.startSearchingArticleAndGoTo(article_name1, search_word);
        MainPageObect.firstSavingArticle(name_of_folder);
        MainPageObect.startSearchingArticleAndGoTo("Java version history", search_word);
        MainPageObect.anotherSavingArticles(name_of_folder);
        MainPageObect.goToMyList();
        MainPageObect.selectTheFolder(name_of_folder);
        MainPageObect.deleteArticleByLeftSwipe("Java version history");
        MainPageObect.waitForElementPresent(
                By.xpath("//*[@text='" + article_name1 + "']"),
                "can't find saved article",
                15
        );

    }

    @Test
    public void testAssertElementPresent() {
        String article_name = "Khinkali";
        String search_phrase = "Khinkali";
        MainPageObect.articleSearchAndGoToWithoutWaitTitle(article_name, search_phrase);
        MainPageObect.presenceOfElementLocatedBy(
                By.id("org.wikipedia:id/view_page_title_text"),
                "cant find title of article"
        );
    }

    @Test
    public void testSaveFirstArticleToMyList() {
        //start search
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        MainPageObect.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "can't find search input",
                5
        );
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "can't find right article",
                5
        );
        MainPageObect.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );
        MainPageObect.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "can't find 'more options button",
                5
        );
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "can't find option to adding article to reading list",
                5
        );
        MainPageObect.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "can't find 'GOT IT' tip overlay",
                5
        );

        MainPageObect.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "cant find input to set name of articles folder",
                5
        );

        String name_of_folder = "Learning programming";
        MainPageObect.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "cant put text to set name of articles folder",
                5
        );

        MainPageObect.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "can't press to 'OK' button",
                5
        );

        MainPageObect.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "can't find the 'X' button, cant close article",
                5
        );

        MainPageObect.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "can't find the navigation button to 'My list' option",
                5
        );

        MainPageObect.waitForElementAndClick(
                //  By.xpath("//org.wikipedia:id/item_title[@text='Learning programming']"),
                By.xpath("//*[@text='" + name_of_folder + "']"), // добавляем переменную используя конкатенацию, чтоб не слился xpath "+ %var_name% +"
                "can't find the 'Learning programming' folder",
                15
        );

        MainPageObect.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "can't find saved article"
        );

        MainPageObect.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "can't delete saved article",
                5
        );

    }

    @Test
    public void testSwipeToArticleElement() {
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        MainPageObect.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "can't find search input",
                5
        );
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "can't find search input",
                5
        );
        MainPageObect.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );

        MainPageObect.swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "cant find the end of the article",
                25
        );

    }

    @Test
    public void testAmountOfNotEmptySearch() {
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );

        //org.wikipedia:id/search_results_list - локатор общего контейнера с результатами поиска
        //org.wikipedia:id/page_list_item_container - локатор элемента в общем контейнере
        String search_phrase = "Linkin Park Discography";
        MainPageObect.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_phrase,
                "can't find search input",
                5
        );
        // в данном случае /* спуск к дочернему элементу
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        MainPageObect.waitForElementPresent(
                By.xpath(search_result_locator),
                "can't find anything by the request " + search_phrase,
                15
        );
        int amountOfSearchResults = MainPageObect.getAmountOfElement(
                By.xpath(search_result_locator)
        );
        System.out.println(amountOfSearchResults);
        Assert.assertTrue(
                "there are few search results",
                amountOfSearchResults > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        MainPageObect.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );

        //org.wikipedia:id/search_results_list - локатор общего контейнера с результатами поиска
        //org.wikipedia:id/page_list_item_container - локатор элемента в общем контейнере
        String search_phrase = "iewrdo4384umoi";
        MainPageObect.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_phrase,
                "can't find search input",
                5
        );
        // в данном случае /* спуск к дочернему элементу
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";
        MainPageObect.waitForElementPresent(
                By.xpath(empty_result_label),
                "cant find empty result label",
                15
        );
        MainPageObect.assertElementNotPresent(
                By.xpath(search_result_locator),
                "we found some results by request " + search_phrase
        );

    }

    @Test
    public void testWordExist() {
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text,'Search Wikipedia')]");
        element_to_init_search.click();
        WebElement element_to_init_search_line = MainPageObect.waitForElementPresent(
                By.xpath("//*[contains(@text,'Search…')]"),
                "can't find search input"
        );

        Assert.assertTrue(element_to_init_search_line.isDisplayed());
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        String article_name = "Khinkali";
        String search_phrase = "Khinkali";
        MainPageObect.startSearchingArticleAndGoTo(article_name, search_phrase);
        String article_title_before_rotation = MainPageObect.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "can't find article title",
                15
        );
        driver.rotate(ScreenOrientation.LANDSCAPE);
        String article_title_after_rotation = MainPageObect.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "can't find article title",
                15
        );

        driver.rotate(ScreenOrientation.PORTRAIT);
        String article_title_after_second_rotation = MainPageObect.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "can't find article title",
                15
        );
        Assert.assertEquals(
                "title have been changed after screen rotation",
                article_title_before_rotation,
                article_title_after_rotation
        );

        Assert.assertEquals(
                "title have been changed after screen rotation",
                article_title_before_rotation,
                article_title_after_second_rotation
        );

    }


}
