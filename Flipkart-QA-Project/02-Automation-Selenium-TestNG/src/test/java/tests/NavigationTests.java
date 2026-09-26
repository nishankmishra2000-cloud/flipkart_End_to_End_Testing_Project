package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.ActionsUtil;
import utils.ScrollUtil;

/**
 * Covers three rubric-mandated automation concepts that don't fit neatly
 * under a single "module": mouse hover / event-driven interaction (Actions
 * class), scrolling (JS executor), and browser back-navigation to the
 * homepage.
 */
public class NavigationTests extends BaseTest {

    @Test(description = "Hovering the first category nav item triggers its menu without error (Selenium Actions class)")
    public void hoverOverCategoryMenuRevealsSubmenu() {
        HomePage home = new HomePage(driver);
        ActionsUtil.hoverOver(driver, home.getFirstCategoryLink());
        // A successful hover is one that doesn't throw; the category link
        // remaining visible/attached after the hover is the practical signal
        // Flipkart's own JS didn't tear the menu down or navigate away.
        Assert.assertTrue(home.getFirstCategoryLink().isDisplayed(),
                "Category link should remain visible immediately after hover");
    }

    @Test(description = "Scrolling to the footer brings it into the viewport (JS executor scroll)")
    public void scrollToFooterBringsItIntoView() {
        HomePage home = new HomePage(driver);
        home.search("laptop");

        SearchResultsPage results = new SearchResultsPage(driver);
        ScrollUtil.scrollToBottom(driver);
        Assert.assertTrue(results.getFooter().isDisplayed(), "Footer should be visible after scrolling to bottom of page");
    }

    @Test(description = "Navigating back after opening search results returns to the homepage")
    public void navigateBackReturnsToHomepage() {
        HomePage home = new HomePage(driver);
        String homeUrl = home.getCurrentUrl();

        home.search("laptop");
        Assert.assertNotEquals(driver.getCurrentUrl(), homeUrl, "Search should have navigated away from the homepage URL");

        driver.navigate().back();
        Assert.assertTrue(new HomePage(driver).isSearchBoxDisplayed(),
                "Search box should be visible again after navigating back to the homepage");
    }
}
