package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AlertUtil;

/**
 * Demonstrates native alert/confirm handling (utils.AlertUtil). Flipkart's
 * own guest-flow warning messages are in-page DOM toasts, not window.alert
 * popups, so there is no reliable live-site trigger for a real native
 * dialog. To still exercise (and prove) the alert-handling capability the
 * rubric requires, this test triggers a controlled JS confirm() dialog via
 * executeScript, then drives it with the same AlertUtil that would be used
 * against any genuine native alert.
 */
public class AlertHandlingTests extends BaseTest {

    @Test(description = "AlertUtil accepts a native JS confirm() dialog")
    public void acceptsNativeConfirmDialog() {
        ((JavascriptExecutor) driver).executeScript(
                "window.confirmResult = window.confirm('Flipkart QA Project - Alert Handling Demo');");

        String alertText = AlertUtil.acceptAlert(driver);
        Assert.assertEquals(alertText, "Flipkart QA Project - Alert Handling Demo");

        Object result = ((JavascriptExecutor) driver).executeScript("return window.confirmResult;");
        Assert.assertEquals(result, Boolean.TRUE, "Accepting the confirm dialog should resolve it to true");
    }

    @Test(description = "AlertUtil dismisses a native JS confirm() dialog")
    public void dismissesNativeConfirmDialog() {
        ((JavascriptExecutor) driver).executeScript(
                "window.confirmResult = window.confirm('Flipkart QA Project - Alert Handling Demo');");

        AlertUtil.dismissAlert(driver);

        Object result = ((JavascriptExecutor) driver).executeScript("return window.confirmResult;");
        Assert.assertEquals(result, Boolean.FALSE, "Dismissing the confirm dialog should resolve it to false");
    }
}
