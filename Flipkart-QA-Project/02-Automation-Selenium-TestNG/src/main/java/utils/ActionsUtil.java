package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/** Wraps Selenium's Actions class for mouse hover / event-driven interactions. */
public class ActionsUtil {

    public static void hoverOver(WebDriver driver, By locator) {
        WebElement element = WaitUtil.waitForVisible(driver, locator);
        new Actions(driver).moveToElement(element).pause(java.time.Duration.ofMillis(300)).perform();
    }

    public static void hoverOver(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).pause(java.time.Duration.ofMillis(300)).perform();
    }

    public static void clickAndHold(WebDriver driver, By locator) {
        WebElement element = WaitUtil.waitForVisible(driver, locator);
        new Actions(driver).clickAndHold(element).release().perform();
    }
}
