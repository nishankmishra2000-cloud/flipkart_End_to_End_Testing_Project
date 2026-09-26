package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

/**
 * Only the login ENTRY FORM is exercised here. Actual OTP verification
 * is out of scope (see 01-Manual-Testing/01_Requirements.md, section 3) —
 * it cannot be automated without a live, receivable phone number, and
 * this suite does not attempt to fake or bypass that step.
 */
public class LoginValidationTests extends BaseTest {

    @Test(description = "TC-026: Login page renders a mobile/email input field")
    public void loginPageRendersInputField() {
        HomePage home = new HomePage(driver);
        home.clickLogin();

        LoginPage login = new LoginPage(driver);
        Assert.assertTrue(login.isMobileInputDisplayed(), "Mobile/email input should be visible on login form");
    }

    @Test(description = "TC-027: Invalid mobile number shows a validation error")
    public void invalidMobileNumberShowsValidationError() {
        HomePage home = new HomePage(driver);
        home.clickLogin();

        LoginPage login = new LoginPage(driver);
        login.enterMobileNumber("123");
        login.submit();

        Assert.assertTrue(login.isValidationErrorShown(),
                "Expected a validation error for a malformed mobile number");
    }
}
