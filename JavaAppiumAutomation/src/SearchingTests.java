import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

public class SearchingTests extends CoreTestCase {
     private MainPageObject mainPageObject;
     protected void setUp() throws Exception {
         super.setUp();
         mainPageObject = new MainPageObject(driver);
     }

    @Test
    public void testSearchAndRightApp() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        System.out.println("application - ok, searching - ok, result - ok");
    }

    @Test
    public void testSearchCanceling() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
      /* //check there are at least 2 search results
        mainPageObject.waitForElementPresent(
                By.xpath("//*[@class='android.widget.FrameLayout'][2]"),
                "there are less than two search results on the page",
                15
        );*/
        SearchPageObject.clearSearchInputText();
        SearchPageObject.waitForSearchCloseBtnToAppear();
        SearchPageObject.clickSearchCloseBtn();
        SearchPageObject.checkSearchCloseBtnNotPresent();
    }

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        SearchPageObject.findArticleTitle();

        WebElement article_title_element = mainPageObject.waitForElementPresent(
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
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );

        mainPageObject.swipeUp(2000);
        mainPageObject.swipeUp(2000);
    }

    @Test
    public void testSaveCoupleArticleToMyListAndDeleteOne() {

        String article_name1 = "Java (programming language)";
        String search_word = "Java";
        String name_of_folder = "Learning programming";
        mainPageObject.startSearchingArticleAndGoTo(article_name1, search_word);
        mainPageObject.firstSavingArticle(name_of_folder);
        mainPageObject.startSearchingArticleAndGoTo("Java version history", search_word);
        mainPageObject.anotherSavingArticles(name_of_folder);
        mainPageObject.goToMyList();
        mainPageObject.selectTheFolder(name_of_folder);
        mainPageObject.deleteArticleByLeftSwipe("Java version history");
        mainPageObject.waitForElementPresent(
                By.xpath("//*[@text='" + article_name1 + "']"),
                "can't find saved article",
                15
        );

    }

    @Test
    public void testAssertElementPresent() {
        String article_name = "Khinkali";
        String search_phrase = "Khinkali";
        mainPageObject.articleSearchAndGoToWithoutWaitTitle(article_name, search_phrase);
        mainPageObject.presenceOfElementLocatedBy(
                By.id("org.wikipedia:id/view_page_title_text"),
                "cant find title of article"
        );
    }

    @Test
    public void testSaveFirstArticleToMyList() {
        //start search
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "can't find 'more options button",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "can't find option to adding article to reading list",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "can't find 'GOT IT' tip overlay",
                5
        );

        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "cant find input to set name of articles folder",
                5
        );

        String name_of_folder = "Learning programming";
        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "cant put text to set name of articles folder",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "can't press to 'OK' button",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "can't find the 'X' button, cant close article",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "can't find the navigation button to 'My list' option",
                5
        );

        mainPageObject.waitForElementAndClick(
                //  By.xpath("//org.wikipedia:id/item_title[@text='Learning programming']"),
                By.xpath("//*[@text='" + name_of_folder + "']"), // добавляем переменную используя конкатенацию, чтоб не слился xpath "+ %var_name% +"
                "can't find the 'Learning programming' folder",
                15
        );

        mainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "can't find saved article"
        );

        mainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "can't delete saved article",
                5
        );

    }

    @Test
    public void testSwipeToArticleElement() {
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "can't find search input",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "can't find search input",
                5
        );
        mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find article title",
                15
        );

        mainPageObject.swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "cant find the end of the article",
                25
        );

    }

    @Test
    public void testAmountOfNotEmptySearch() {
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );

        //org.wikipedia:id/search_results_list - локатор общего контейнера с результатами поиска
        //org.wikipedia:id/page_list_item_container - локатор элемента в общем контейнере
        String search_phrase = "Linkin Park Discography";
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_phrase,
                "can't find search input",
                5
        );
        // в данном случае /* спуск к дочернему элементу
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        mainPageObject.waitForElementPresent(
                By.xpath(search_result_locator),
                "can't find anything by the request " + search_phrase,
                15
        );
        int amountOfSearchResults = mainPageObject.getAmountOfElement(
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
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "can't find search input",
                5
        );

        //org.wikipedia:id/search_results_list - локатор общего контейнера с результатами поиска
        //org.wikipedia:id/page_list_item_container - локатор элемента в общем контейнере
        String search_phrase = "iewrdo4384umoi";
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_phrase,
                "can't find search input",
                5
        );
        // в данном случае /* спуск к дочернему элементу
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";
        mainPageObject.waitForElementPresent(
                By.xpath(empty_result_label),
                "cant find empty result label",
                15
        );
        mainPageObject.assertElementNotPresent(
                By.xpath(search_result_locator),
                "we found some results by request " + search_phrase
        );

    }

    @Test
    public void testWordExist() {
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text,'Search Wikipedia')]");
        element_to_init_search.click();
        WebElement element_to_init_search_line = mainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@text,'Search…')]"),
                "can't find search input"
        );

        Assert.assertTrue(element_to_init_search_line.isDisplayed());
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        String article_name = "Khinkali";
        String search_phrase = "Khinkali";
        mainPageObject.startSearchingArticleAndGoTo(article_name, search_phrase);
        String article_title_before_rotation = mainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "can't find article title",
                15
        );
        driver.rotate(ScreenOrientation.LANDSCAPE);
        String article_title_after_rotation = mainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "can't find article title",
                15
        );

        driver.rotate(ScreenOrientation.PORTRAIT);
        String article_title_after_second_rotation = mainPageObject.waitForElementAndGetAttribute(
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
