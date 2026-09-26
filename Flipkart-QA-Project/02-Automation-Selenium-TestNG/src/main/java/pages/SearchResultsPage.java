package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Locators here target Flipkart's product-grid card structure. Flipkart
 * ships no stable data-testid attributes on the public site, so these
 * selectors rely on structural/class patterns and are the single place
 * to update if Flipkart changes its markup (that's the point of POM).
 *
 * Fixed, always-present elements use @FindBy (PageFactory); elements whose
 * locator depends on a runtime value (a specific sort label, a specific
 * brand name) stay as dynamic By lookups, since PageFactory fields cannot
 * be parameterized.
 */
public class SearchResultsPage {

    private final WebDriver driver;

    @FindBy(css = "div._1AtVbE, div[data-id]")
    private List<WebElement> productCards;

    @FindBy(css = "div._30jeq3")
    private List<WebElement> productPriceElements;

    @FindBy(css = "div._4rR01T, a.s1Q9rs")
    private List<WebElement> productTitleElements;

    @FindBy(xpath = "//a[text()='Next']")
    private WebElement nextPageButton;

    @FindBy(css = "div._3879cV")
    private List<WebElement> ratingFilterOptions;

    @FindBy(css = "footer, div._1uzKPy")
    private WebElement footer;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> getProductCards() {
        WaitUtil.waitForAllVisible(driver, By.cssSelector("div._1AtVbE, div[data-id]"));
        return productCards;
    }

    public List<Double> getVisiblePrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement e : productPriceElements) {
            String digits = e.getText().replaceAll("[^0-9]", "");
            if (!digits.isEmpty()) {
                prices.add(Double.parseDouble(digits));
            }
        }
        return prices;
    }

    public List<String> getVisibleTitles() {
        return productTitleElements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void sortBy(String option) {
        By dynamicOption = By.xpath(String.format(
                "//div[contains(text(),'Sort By')]/following::div[text()='%s'][1]", option));
        WaitUtil.waitForClickable(driver, dynamicOption).click();
    }

    public void applyBrandFilter(String brand) {
        By brandCheckbox = By.xpath(String.format("//div[text()='%s']/preceding-sibling::input", brand));
        WaitUtil.waitForClickable(driver, brandCheckbox).click();
    }

    public boolean isNoResultsShown() {
        By noResultsMessage = By.xpath("//*[contains(text(),'did not match any product') or contains(text(),'No results found')]");
        return WaitUtil.isPresent(driver, noResultsMessage, 8);
    }

    public void goToNextPage() {
        nextPageButton.click();
    }

    public boolean isRatingFilterVisible() {
        return !ratingFilterOptions.isEmpty();
    }

    public void clickFirstProduct() {
        List<WebElement> cards = getProductCards();
        cards.get(0).click();
    }

    public WebElement getFooter() {
        return footer;
    }
}
