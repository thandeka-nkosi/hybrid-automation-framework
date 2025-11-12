package core.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    // Single report instance per JVM run
    private static ExtentReports extent;

    // Per-thread test + module context
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final ThreadLocal<String> moduleName = new ThreadLocal<>();

    private static String reportPath;
    private static String runTimestamp;

    public static synchronized void initReports() {
        if (extent != null) return;

        extent = new ExtentReports();

        String module = System.getProperty("moduleName", "DefaultModule");
        moduleName.set(module);

        if (runTimestamp == null) {
            runTimestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        }

        String baseDir = "test-output/reports";
        reportPath = baseDir + File.separator + module + "_" + runTimestamp + "_ExtentReport.html";
        new File(baseDir).mkdirs();

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        String exec = System.getProperty("execution", "local");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle(module + " - Automation Test Report");
        // 🔎 show mode in the banner title
        spark.config().setReportName("Execution Report for " + module + " [" +
                (exec.equalsIgnoreCase("remote") ? "REMOTE" : "LOCAL") + "]");
        spark.config().setTimelineEnabled(true);
        spark.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

        extent.attachReporter(spark);

        // System info panel
        extent.setSystemInfo("QE", "Thandeka");
        extent.setSystemInfo("Environment", System.getProperty("env", "SIT"));
        extent.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
        extent.setSystemInfo("Execution Mode", exec.equalsIgnoreCase("remote") ? "Remote (Selenium Grid)" : "Local");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    public static synchronized void createTest(String testName) {
        initReports();
        if (test.get() == null) {
            String browser = System.getProperty("browser", "chrome");
            String exec = System.getProperty("execution", "local");
            String threadName = "Thread-" + Thread.currentThread().getId();

            ExtentTest t = extent.createTest(testName + " [" + browser + " | " + threadName + "]");
            // visible badges
            t.assignCategory(browser.toUpperCase());
            t.assignCategory(exec.equalsIgnoreCase("remote") ? "REMOTE" : "LOCAL");
            String mod = moduleName.get();
            if (mod != null) t.assignCategory(mod);

            test.set(t);
        }
    }

    public static synchronized void createTest(String testName, String module) {
        initReports();

        // Keep module in TL for other utilities (e.g., message prefixes)
        if (moduleName.get() == null) moduleName.set(module);

        String exec    = System.getProperty("execution", "local");
        String browser = System.getProperty("browser", "chrome");
        String modeTag = exec.equalsIgnoreCase("remote") ? "REMOTE" : "LOCAL";

        // Title: [module | mode] — no thread, no browser
        String title = testName + " |" + module + " | " + modeTag + "|";

        // Create exactly one test
        ExtentTest t = extent.createTest(title);

        // Badges: always module + mode; browser only when REMOTE
        t.assignCategory(module);
        t.assignCategory(modeTag);
        if ("remote".equalsIgnoreCase(exec)) {
            t.assignCategory(browser.toUpperCase());
        }

        // Store this test for the current thread
        test.set(t);
    }

    public static ExtentTest getTest() { return test.get(); }

    public static String getReportPath() { return reportPath; }

    public static synchronized void flushReports() {
        if (extent != null) {
            try { extent.flush(); }
            catch (Exception e) { System.err.println("⚠️ Extent flush error: " + e.getMessage()); }
        }
    }

    public static void unload() {
        test.remove();
        moduleName.remove();
        // keep 'extent' to have one consolidated file per run
    }
}

