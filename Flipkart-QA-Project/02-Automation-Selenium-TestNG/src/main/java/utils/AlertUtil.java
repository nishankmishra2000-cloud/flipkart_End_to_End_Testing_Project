package utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Handles native browser alert/confirm/prompt dialogs.
 * Flipkart's guest shopping flow does not reliably trigger a native
 * JS alert (its warning/toast messages are in-page DOM elements, not
 * window.alert popups), so the demo test that exercises this utility
 * triggers a controlled JS confirm() dialog via executeScript rather
 * than relying on an unpredictable live-site trigger. The utility
 * itself is fully general and works against any native dialog.
 */
public class AlertUtil {

    public static Alert waitForAlert(WebDriver driver, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.alertIsPresent());
    }

    public static String acceptAlert(WebDriver driver) {
        Alert alert = waitForAlert(driver, 5);
        String text = alert.getText();
        alert.accept();
        return text;
    }

    public static String dismissAlert(WebDriver driver) {
        Alert alert = waitForAlert(driver, 5);
        String text = alert.getText();
        alert.dismiss();
        return text;
    }

    public static boolean isAlertPresent(WebDriver driver, int timeoutSeconds) {
        try {
            waitForAlert(driver, timeoutSeconds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
