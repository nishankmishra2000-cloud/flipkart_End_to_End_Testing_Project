package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;

import java.util.List;

public class SearchTests extends BaseTest {

    @Test(description = "TC-003: Searching a valid keyword returns relevant results")
    public void validSearchReturnsResults() {
        HomePage home = new HomePage(driver);
        home.search("laptop");

        SearchResultsPage results = new SearchResultsPage(driver);
        List<String> titles = results.getVisibleTitles();
        Assert.assertFalse(titles.isEmpty(), "Expected at least one product result for 'laptop'");
    }

    @Test(description = "TC-004: Searching with special characters does not break the page")
    public void searchWithSpecialCharactersDoesNotCrash() {
        HomePage home = new HomePage(driver);
        home.search("@@@###");

        Assert.assertTrue(driver.getCurrentUrl().contains("flipkart.com"),
                "Page should remain on a valid flipkart.com URL after a special-character search");
    }

    @Test(description = "TC-006: Searching a nonexistent product shows a no-results state")
    public void searchForNonexistentProductShowsNoResults() {
        HomePage home = new HomePage(driver);
        home.search("zzxynonexistentproduct123");

        SearchResultsPage results = new SearchResultsPage(driver);
        Assert.assertTrue(results.isNoResultsShown(), "Expected a 'no results' style message for a nonsense query");
    }
}
