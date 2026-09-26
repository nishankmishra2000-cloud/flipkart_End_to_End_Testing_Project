package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Flipkart shows a login modal with a stray "✕" close icon on most
 * fresh page loads. It has no reliable data-testid, so we fall back to
 * an XPath text/attribute match and swallow failures — the popup does
 * not always appear (e.g. if a session cookie already dismissed it),
 * and tests must not fail just because it didn't show up this time.
 */
public class LoginPopupHandler {

    private static final By CLOSE_ICON = By.xpath("//button[contains(@class,'_2KpZ6l') and text()='✕']");

    public static void dismissIfPresent(WebDriver driver) {
        try {
            if (WaitUtil.isPresent(driver, CLOSE_ICON, 4)) {
                WebElement close = driver.findElement(CLOSE_ICON);
                close.click();
            }
        } catch (Exception ignored) {
            // Popup didn't appear this run — not a failure condition.
        }
    }
}
