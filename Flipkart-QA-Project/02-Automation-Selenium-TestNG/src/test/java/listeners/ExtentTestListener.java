package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;
import utils.ExtentManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Bridges TestNG's lifecycle to ExtentReports (Step 12 deliverable) and to
 * Log4j (Step 12 deliverable). Registered globally via testng.xml's
 * <listeners> block so it applies to every <test> without per-class wiring.
 */
public class ExtentTestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(ExtentTestListener.class);
    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final Map<Long, ExtentTest> testMap = new ConcurrentHashMap<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        testMap.put(Thread.currentThread().getId(), test);
        log.info("START: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testMap.get(Thread.currentThread().getId()).log(Status.PASS, "Test passed");
        log.info("PASS: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testMap.get(Thread.currentThread().getId());
        test.log(Status.FAIL, result.getThrowable());
        log.error("FAIL: {}", result.getMethod().getMethodName());

        Object instance = result.getInstance();
        if (instance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) instance).getDriver();
            if (driver != null) {
                try {
                    String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                    test.addScreenCaptureFromBase64String(base64, result.getMethod().getMethodName());
                } catch (Exception e) {
                    log.warn("Could not attach screenshot to Extent report: {}", e.getMessage());
                }
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testMap.get(Thread.currentThread().getId()).log(Status.SKIP, "Test skipped");
        log.warn("SKIP: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        log.info("Extent report flushed to {}", utils.ConfigReader.get("extentReportPath"));
    }
}
