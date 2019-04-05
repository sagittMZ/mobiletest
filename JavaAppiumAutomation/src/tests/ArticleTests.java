package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {
    @Test
    public void testCompareArticleTitle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickOnArticleWithSubstring("Object-oriented programming language");
        SearchPageObject.findArticleTitle();
        ArticlePageObject ArticlePageObject = new ArticlePageObject (driver);
        String article_title = ArticlePageObject.getArticleTitle();

        assertEquals(
                "unexpected title",
                "Java (programming language)",
                article_title
        );
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


}
