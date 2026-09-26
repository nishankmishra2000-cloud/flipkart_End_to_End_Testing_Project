package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtil;

/**
 * Covers only the login ENTRY form (field render + client-side validation).
 * OTP verification itself requires a live phone and is intentionally out
 * of scope — see 01-Manual-Testing/01_Requirements.md, section 3.
 */
public class LoginPage {

    private final WebDriver driver;

    @FindBy(css = "input[type='text'][maxlength='10'], input[class*='_2IX_2-']")
    private WebElement mobileInput;

    @FindBy(xpath = "//button[contains(text(),'Request OTP') or contains(text(),'CONTINUE')]")
    private WebElement requestOtpButton;

    @FindBy(css = "p._3z4d7T, div._2mZa2Q")
    private WebElement validationErrorText;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isMobileInputDisplayed() {
        return mobileInput.isDisplayed();
    }

    public void enterMobileNumber(String value) {
        mobileInput.clear();
        mobileInput.sendKeys(value);
    }

    public void submit() {
        requestOtpButton.click();
    }

    public boolean isValidationErrorShown() {
        By validationError = By.cssSelector("p._3z4d7T, div._2mZa2Q");
        return WaitUtil.isPresent(driver, validationError, 6);
    }
}
