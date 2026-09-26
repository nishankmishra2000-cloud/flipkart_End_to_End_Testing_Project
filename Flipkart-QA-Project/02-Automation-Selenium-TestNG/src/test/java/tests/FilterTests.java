package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;

import java.util.List;

public class FilterTests extends BaseTest {

    @Test(description = "TC-011: Brand filter shows only the selected brand's products")
    public void brandFilterNarrowsResults() {
        HomePage home = new HomePage(driver);
        home.search("mobile");

        SearchResultsPage results = new SearchResultsPage(driver);
        results.applyBrandFilter("Samsung");
        List<String> titles = results.getVisibleTitles();

        Assert.assertFalse(titles.isEmpty(), "Expected results after applying brand filter");
        for (String title : titles) {
            Assert.assertTrue(title.toLowerCase().contains("samsung"),
                    "Expected only Samsung products, found: " + title);
        }
    }
}
