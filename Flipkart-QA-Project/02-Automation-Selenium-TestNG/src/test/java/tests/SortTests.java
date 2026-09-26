package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;

import java.util.List;

public class SortTests extends BaseTest {

    @Test(description = "TC-007: Sorting by Price -- Low to High orders ascending")
    public void sortByPriceLowToHigh() {
        HomePage home = new HomePage(driver);
        home.search("laptop");

        SearchResultsPage results = new SearchResultsPage(driver);
        results.sortBy("Price -- Low to High");
        List<Double> prices = results.getVisiblePrices();

        Assert.assertTrue(prices.size() > 1, "Need at least 2 prices to verify ordering");
        for (int i = 1; i < prices.size(); i++) {
            Assert.assertTrue(prices.get(i) >= prices.get(i - 1),
                    "Prices should be non-decreasing; found " + prices.get(i - 1) + " then " + prices.get(i));
        }
    }

    @Test(description = "TC-008: Sorting by Price -- High to Low orders descending")
    public void sortByPriceHighToLow() {
        HomePage home = new HomePage(driver);
        home.search("laptop");

        SearchResultsPage results = new SearchResultsPage(driver);
        results.sortBy("Price -- High to Low");
        List<Double> prices = results.getVisiblePrices();

        Assert.assertTrue(prices.size() > 1, "Need at least 2 prices to verify ordering");
        for (int i = 1; i < prices.size(); i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i - 1),
                    "Prices should be non-increasing; found " + prices.get(i - 1) + " then " + prices.get(i));
        }
    }
}
