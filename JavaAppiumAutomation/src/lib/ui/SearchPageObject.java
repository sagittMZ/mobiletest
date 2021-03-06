package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{
    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search…')]",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'{SUBSTRING}')]",
            SEARCH_INPUT_CLEARING = "org.wikipedia:id/search_src_text",
            SEARCH_CLOSE_BTN = "org.wikipedia:id/search_close_btn",
            ARTICLE_TITLE_ELEMENT = "org.wikipedia:id/view_page_title_text",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']";


    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }
    /*templates methods*/
    private static String getResultSearchElement(String substring)
    {
        return  SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
    }
    /*templates methods*/
    public void initSearchInput()
    {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "can't find and click search ini element", 5);
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),"can't find search input after licking search init element");
    }
    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT),search_line,"can't search search input and type into it",5);
    }
    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath),"can't find search result for substring " + substring);
    }

    public void clearSearchInputText()
    {
        this.waitForElementAndClear(By.id(SEARCH_INPUT_CLEARING),"search input-box clearing unsuccessful",15);
    }

    public void waitForSearchCloseBtnToAppear()
    {
        this.waitForElementPresent(By.id(SEARCH_CLOSE_BTN),"can't find search close button",15);
    }
    public void checkSearchCloseBtnNotPresent()
    {
        this.waitForElementNotPresent(By.id(SEARCH_CLOSE_BTN),"search close button still present on page",15);
    }
    public void clickSearchCloseBtn()
    {
        this.waitForElementAndClick(By.id(SEARCH_CLOSE_BTN),"can't find and click search close button",15);
    }
    public void findArticleTitle()
    {
        this.waitForElementAndClick(By.id(ARTICLE_TITLE_ELEMENT),"can't find article title",15);
    }

    public void clickOnArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath),"can't find and click search result for substring " + substring,10);
    }

    public int getAmountOfPublicArticles()
    {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "can't find anything by the request ",
                15
        );
        return this.getAmountOfElement(By.xpath(SEARCH_RESULT_ELEMENT));
    }
    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),"cant find empty result label", 15);
    }

    public void assertThereIsNoAnySearchResultHere()
    {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT),"we found some results by request");
    }

}
