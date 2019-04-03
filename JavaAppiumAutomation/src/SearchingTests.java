import lib.CoreTestCase;
import lib.ui.*;
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
        SearchPageObject.clickOnArticleWithSubstring("Object-oriented programming language");
        SearchPageObject.findArticleTitle();
        ArticlePageObject ArticlePageObject = new ArticlePageObject (driver);
        String article_title = ArticlePageObject.getArticleTitle();

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
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickOnArticleWithSubstring("Appium");
        ArticlePageObject ArticlePageObject = new ArticlePageObject (driver);
        ArticlePageObject.waitForTitleElement();

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
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickOnArticleWithSubstring("Object-oriented programming language");
        SearchPageObject.findArticleTitle();
        ArticlePageObject ArticlePageObject = new ArticlePageObject (driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();
        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyList();
        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSwipeToArticleElement() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickOnArticleWithSubstring("Appium");
        ArticlePageObject ArticlePageObject = new ArticlePageObject (driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }


    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);

        //old
        //org.wikipedia:id/search_results_list - локатор общего контейнера с результатами поиска
        //org.wikipedia:id/page_list_item_container - локатор элемента в общем контейнере
        // в данном случае /* спуск к дочернему элементу
        int amountOfSearchResults = SearchPageObject.getAmountOfPublicArticles();
        System.out.println(amountOfSearchResults);
        Assert.assertTrue(
                "there are few search results",
                amountOfSearchResults > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "iewrdo4384umoi";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoAnySearchResultHere();
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
