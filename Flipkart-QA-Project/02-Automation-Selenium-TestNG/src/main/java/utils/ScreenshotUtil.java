package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/** Captures a screenshot on test failure into /screenshots/failures/. */
public class ScreenshotUtil {

    private static final String DIR = "screenshots/failures";

    public static void capture(WebDriver driver, String testName) {
        try {
            Files.createDirectories(Paths.get(DIR));
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(DIR, testName + "_" + timestamp + ".png");
            Files.copy(src.toPath(), dest.toPath());
            System.out.println("Screenshot saved: " + dest.getPath());
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot for " + testName + ": " + e.getMessage());
        }
    }
}
