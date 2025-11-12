package core.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import core.driver.WebDriverManagerUtil;
import core.reporting.ExtentReportManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Base64;


public class StepLogger {

    private StepLogger() {}

    // Optional: bind Scenario so we can attach PNGs to Cucumber, too
    private static final ThreadLocal<io.cucumber.java.Scenario> TL_SCENARIO = new ThreadLocal<>();
    public static void bind(io.cucumber.java.Scenario scenario) { TL_SCENARIO.set(scenario); }
    public static void unbind() { TL_SCENARIO.remove(); }

    @FunctionalInterface
    public interface StepAction { void execute() throws Exception; }

    public static void logStep(String successMessage, String failureMessage, StepAction action) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) test.info(successMessage);  // pre-step note

        try {
            action.execute();

            if (test != null) {
                test.pass("✅ " + successMessage);
            }
            System.out.println("✅ " + successMessage);

        } catch (AssertionError | Exception e) {
            String errorMsg = "❌ " + failureMessage + (e.getMessage() != null ? ": " + e.getMessage() : "");
            if (test != null) test.fail(errorMsg);
            System.err.println(errorMsg);

            // try to capture screenshot both as path (optional) and base64 (preferred)
            try {
                WebDriver driver = WebDriverManagerUtil.getDriver();
                if (driver instanceof TakesScreenshot ts) {
                    // Base64 (best for embedding in Extent)
                    String base64 = ts.getScreenshotAs(OutputType.BASE64);
                    if (test != null) {
                        test.fail("Screenshot on Failure",
                                MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
                    }

                    // Also attach to Cucumber if bound (optional)
                    io.cucumber.java.Scenario sc = TL_SCENARIO.get();
                    if (sc != null) {
                        byte[] bytes = Base64.getDecoder().decode(base64);
                        sc.attach(bytes, "image/png", successMessage);
                    }
                }
            } catch (Throwable ignore) {
                // never fail logging due to screenshot issues
            }

            throw (e instanceof RuntimeException) ? (RuntimeException)e : new RuntimeException(e);
        }
    }

}





