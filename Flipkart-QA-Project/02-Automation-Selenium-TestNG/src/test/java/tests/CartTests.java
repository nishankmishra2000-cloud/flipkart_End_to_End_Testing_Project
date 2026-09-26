package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailPage;
import pages.SearchResultsPage;
import utils.WaitUtil;

public class CartTests extends BaseTest {

    private void addFirstLaptopToCart() {
        HomePage home = new HomePage(driver);
        home.search("laptop");
        SearchResultsPage results = new SearchResultsPage(driver);
        results.clickFirstProduct();
        if (driver.getWindowHandles().size() > 1) {
            driver.getWindowHandles().stream()
                    .filter(h -> !h.equals(driver.getWindowHandle()))
                    .findFirst().ifPresent(driver::switchTo);
        }
        new ProductDetailPage(driver).clickAddToCart();
        WaitUtil.waitForUrlContains(driver, "viewcart");
    }

    @Test(description = "TC-021: Adding a product to cart shows correct item")
    public void addProductToCart() {
        addFirstLaptopToCart();
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.hasItems(), "Cart should contain the item just added");
    }

    @Test(description = "TC-022: Increasing quantity updates the grand total")
    public void increaseQuantityUpdatesTotal() {
        addFirstLaptopToCart();
        CartPage cart = new CartPage(driver);
        double before = cart.getGrandTotal();
        cart.increaseQuantity();
        double after = cart.getGrandTotal();
        Assert.assertTrue(after > before,
                "Grand total should increase after incrementing quantity (before=" + before + ", after=" + after + ")");
    }

    @Test(description = "TC-024: Removing an item empties the cart's line item")
    public void removeItemFromCart() {
        addFirstLaptopToCart();
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.hasItems(), "Precondition: cart should have the item before removal");
        cart.removeFirstItem();
        Assert.assertTrue(cart.isEmptyStateShown() || !cart.hasItems(),
                "Cart should be empty (or show empty state) after removing the only item");
    }
}
