package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utils.WaitUtil;

public class ProductDetailPage {

    private final WebDriver driver;

    @FindBy(css = "span.B_NuCI, span.VU-ZEz")
    private WebElement productTitle;

    @FindBy(css = "div._30jeq3._16Jk6d, div.Nx9bqj.CxhGGd")
    private WebElement productPrice;

    @FindBy(css = "img._396cs4, img.DByuf4")
    private WebElement productImage;

    @FindBy(css = "div._3LWZlK")
    private WebElement ratingBadge;

    @FindBy(xpath = "//*[contains(text(),'Specifications') or contains(text(),'Description')]")
    private WebElement specsSection;

    @FindBy(xpath = "//button[contains(text(),'ADD TO CART')]")
    private WebElement addToCartButton;

    @FindBy(xpath = "//button[contains(text(),'BUY NOW')]")
    private WebElement buyNowButton;

    /** Only present on some product types (e.g. accessories sold as multi-packs). */
    @FindBy(css = "select#quantity, select[name='quantity']")
    private WebElement quantityNativeSelect;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return productTitle.getText();
    }

    public double getPrice() {
        return Double.parseDouble(productPrice.getText().replaceAll("[^0-9]", ""));
    }

    public boolean isImageDisplayed() {
        return productImage.isDisplayed();
    }

    public boolean isRatingDisplayed() {
        try {
            return ratingBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSpecsSectionPresent() {
        return WaitUtil.isPresent(driver, By.xpath("//*[contains(text(),'Specifications') or contains(text(),'Description')]"), 8);
    }

    public boolean isAddToCartEnabled() {
        return addToCartButton.isEnabled();
    }

    public void clickAddToCart() {
        addToCartButton.click();
    }

    public boolean isBuyNowVisible() {
        return WaitUtil.isPresent(driver, By.xpath("//button[contains(text(),'BUY NOW')]"), 6);
    }

    /**
     * True dropdown handling via Selenium's Select class, which only works on a
     * native &lt;select&gt; element. Flipkart's own quantity control on the
     * standard PDP is a custom, non-native dropdown, so a native
     * &lt;select&gt;-backed quantity picker is not guaranteed on every product
     * page — this method is written against the case where it is (some
     * accessory/multi-pack listings), and returns false rather than throwing
     * when the product page in front of it doesn't expose one, so callers can
     * assert on capability rather than assume it.
     */
    public boolean selectQuantityIfNativeDropdownPresent(String visibleText) {
        if (!WaitUtil.isPresent(driver, By.cssSelector("select#quantity, select[name='quantity']"), 4)) {
            return false;
        }
        new Select(quantityNativeSelect).selectByVisibleText(visibleText);
        return true;
    }
}
