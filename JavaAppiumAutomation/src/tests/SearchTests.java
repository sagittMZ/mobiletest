package tests;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;


public class SearchTests extends CoreTestCase {

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
        assertTrue(
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
}
