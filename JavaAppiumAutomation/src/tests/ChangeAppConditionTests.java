package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {
    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        String article_title = "Khinkali";
        String search_line = "Khinkali";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickOnArticleWithSubstring(article_title);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String article_title_before_rotation = ArticlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String article_title_after_rotation = ArticlePageObject.getArticleTitle();
        assertEquals(
                "title have been changed after screen rotation",
                article_title_before_rotation,
                article_title_after_rotation
        );
        this.rotateScreenPortrait();
        String article_title_after_second_rotation = ArticlePageObject.getArticleTitle();
        assertEquals(
                "title have been changed after screen rotation",
                article_title_before_rotation,
                article_title_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
