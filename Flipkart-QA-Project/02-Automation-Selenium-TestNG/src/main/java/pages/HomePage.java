package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtil;

import java.util.List;

/** Page Object built with PageFactory — locators are declared as fields via @FindBy,
 *  the Centralized Object Repository pattern for this framework: every homepage
 *  locator lives here and nowhere else. */
public class HomePage {

    private final WebDriver driver;

    @FindBy(name = "q")
    private WebElement searchBox;

    @FindBy(css = "button[type='submit']")
    private WebElement searchSubmitButton;

    @FindBy(css = "a[href='/'] img._2ZrsAr, header a img")
    private WebElement logo;

    @FindBy(css = "nav a, div._1E4a5R a")
    private List<WebElement> categoryNavItems;

    @FindBy(xpath = "//a[contains(text(),'Login')] | //div[contains(text(),'Login')]")
    private WebElement loginButton;

    /** The first-level category link used by the hover/mega-menu demo test. */
    @FindBy(xpath = "(//nav//a | //div[@class='_1E4a5R']//a)[1]")
    private WebElement firstCategoryLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void search(String keyword) {
        WaitUtil.waitForVisible(driver, By.name("q"));
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchSubmitButton.click();
    }

    public boolean isSearchBoxDisplayed() {
        return searchBox.isDisplayed();
    }

    public boolean isLogoDisplayed() {
        try {
            return logo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int categoryCount() {
        return categoryNavItems.size();
    }

    public WebElement getFirstCategoryLink() {
        return firstCategoryLink;
    }

    public void clickLogin() {
        loginButton.click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
