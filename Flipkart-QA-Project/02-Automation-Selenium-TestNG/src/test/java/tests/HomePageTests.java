package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomePageTests extends BaseTest {

    @Test(description = "TC-001: Homepage loads successfully with search bar visible")
    public void homepageLoadsSuccessfully() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isSearchBoxDisplayed(), "Search box should be visible on homepage load");
        Assert.assertTrue(home.getCurrentUrl().contains("flipkart.com"), "URL should be on flipkart.com");
    }

    @Test(description = "TC-002: Category navigation menu shows multiple categories")
    public void categoryMenuShowsCategories() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.categoryCount() > 0, "Expected at least one category nav item to be present");
    }
}
