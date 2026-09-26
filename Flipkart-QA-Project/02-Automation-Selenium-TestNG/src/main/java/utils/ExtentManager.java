package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * One shared ExtentReports instance for the whole suite run, so every
 * test class/thread logs into the same HTML report instead of each
 * producing its own file.
 */
public class ExtentManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String path = ConfigReader.get("extentReportPath");
            ExtentSparkReporter spark = new ExtentSparkReporter(path);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Flipkart QA Automation Report");
            spark.config().setReportName("Flipkart UI Automation - Selenium/TestNG");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Application Under Test", ConfigReader.baseUrl());
            extent.setSystemInfo("Browser", ConfigReader.get("browser"));
            extent.setSystemInfo("Headless", ConfigReader.get("headless"));
        }
        return extent;
    }
}
