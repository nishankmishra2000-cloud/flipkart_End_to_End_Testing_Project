package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtil;

import java.util.List;

public class CartPage {

    private final WebDriver driver;

    @FindBy(css = "div._2rW76A, div.cPP81N")
    private List<WebElement> cartItemRows;

    @FindBy(xpath = "//div[@class='_1PVGz']//span[text()='+']")
    private WebElement quantityIncrement;

    @FindBy(xpath = "//div[@class='_1PVGz']//span[text()='-']")
    private WebElement quantityDecrement;

    @FindBy(xpath = "//div[text()='Remove']")
    private WebElement removeButton;

    @FindBy(css = "div._1eS9CD div._3I9_wc, div.qmC8n5")
    private WebElement grandTotalElement;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean hasItems() {
        return !cartItemRows.isEmpty();
    }

    public List<WebElement> getItems() {
        return cartItemRows;
    }

    public void increaseQuantity() {
        quantityIncrement.click();
    }

    public void decreaseQuantity() {
        quantityDecrement.click();
    }

    public void removeFirstItem() {
        removeButton.click();
    }

    public double getGrandTotal() {
        return Double.parseDouble(grandTotalElement.getText().replaceAll("[^0-9]", ""));
    }

    public boolean isEmptyStateShown() {
        By emptyCartMessage = By.xpath("//*[contains(text(),'cart is empty') or contains(text(),'Empty Cart')]");
        return WaitUtil.isPresent(driver, emptyCartMessage, 6);
    }
}
