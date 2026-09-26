package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductDetailPage;
import pages.SearchResultsPage;

public class ProductDetailTests extends BaseTest {

    @Test(description = "TC-017: PDP renders core product info (image, title, price, rating)")
    public void pdpShowsCoreInfo() {
        HomePage home = new HomePage(driver);
        home.search("laptop");

        SearchResultsPage results = new SearchResultsPage(driver);
        results.clickFirstProduct();
        switchToNewTabIfOpened();

        ProductDetailPage pdp = new ProductDetailPage(driver);
        Assert.assertTrue(pdp.isImageDisplayed(), "Product image should be visible on PDP");
        Assert.assertFalse(pdp.getTitle().isEmpty(), "Product title should not be empty");
        Assert.assertTrue(pdp.getPrice() > 0, "Product price should be a positive number");
    }

    @Test(description = "TC-020: Add to Cart button is visible and enabled for an in-stock item")
    public void addToCartButtonEnabled() {
        HomePage home = new HomePage(driver);
        home.search("laptop");

        SearchResultsPage results = new SearchResultsPage(driver);
        results.clickFirstProduct();
        switchToNewTabIfOpened();

        ProductDetailPage pdp = new ProductDetailPage(driver);
        Assert.assertTrue(pdp.isAddToCartEnabled(), "Add to Cart should be enabled for an in-stock product");
    }

    /** Flipkart PDPs sometimes open in a new tab depending on the entry point. */
    private void switchToNewTabIfOpened() {
        if (driver.getWindowHandles().size() > 1) {
            driver.getWindowHandles().stream()
                    .filter(handle -> !handle.equals(driver.getWindowHandle()))
                    .findFirst()
                    .ifPresent(driver::switchTo);
        }
    }
}
