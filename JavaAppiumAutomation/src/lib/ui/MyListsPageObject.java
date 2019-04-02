package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {
    public static final String
    FOLDER_BY_NAME_TML = "//*[@text='{FOLDER_NAME}']",
    ARTICLE_BY_TITLE_TML = "//*[@text='{TITLE}']";

    public MyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }
    private static String getFolderXpathByName(String name_of_folder) //const FOLDER_BY_NAME_TML have xpath with var part
    {
        return FOLDER_BY_NAME_TML.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title) //
    {
        return ARTICLE_BY_TITLE_TML.replace("{TITLE}", article_title);
    }
    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(By.xpath(folder_name_xpath),"can't find folder by name "+ name_of_folder,15);
    }
    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(By.xpath(article_xpath),"can't find saved article");
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementPresent(By.xpath(article_xpath), "can't saved article by title "+article_title,5);
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {

        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementNotPresent(By.xpath(article_xpath), "saved article still present with title "+article_title,5);
    }
}
