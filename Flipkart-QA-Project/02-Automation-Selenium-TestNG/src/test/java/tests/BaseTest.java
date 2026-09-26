package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.ScreenshotUtil;

import java.time.Duration;

/**
 * Common WebDriver lifecycle for all UI test classes.
 * Each @Test method gets a fresh browser session (method-scoped) to keep
 * tests independent of one another — important on a live, stateful site
 * like Flipkart where cart/search state can otherwise leak between tests.
 */
public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected static final String BASE_URL = ConfigReader.baseUrl();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        log.info("Setting up WebDriver (headless={})", ConfigReader.get("headless"));
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (ConfigReader.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--start-maximized", "--disable-notifications", "--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicitWaitSeconds")));
        driver.get(BASE_URL);
        log.info("Navigated to {}", BASE_URL);
        utils.LoginPopupHandler.dismissIfPresent(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null) {
            if (ITestResult.FAILURE == result.getStatus()) {
                log.error("Test failed: {}", result.getMethod().getMethodName(), result.getThrowable());
                if (ConfigReader.getBoolean("screenshotOnFailure")) {
                    ScreenshotUtil.capture(driver, result.getMethod().getMethodName());
                }
            } else {
                log.info("Test finished: {} -> {}", result.getMethod().getMethodName(),
                        result.getStatus() == ITestResult.SUCCESS ? "PASS" : "SKIP");
            }
            driver.quit();
        }
    }

    /** Exposed for the ExtentReports TestNG listener, which runs outside this class's package. */
    public WebDriver getDriver() {
        return driver;
    }
}
