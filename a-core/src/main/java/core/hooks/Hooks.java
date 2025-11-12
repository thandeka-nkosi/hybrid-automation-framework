package core.hooks;

import core.driver.WebDriverManagerUtil;
import core.reporting.ExtentReportManager;
import core.utils.ConfigReader;
import core.utils.ScreenshotUtility;
import core.utils.TagUtil;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

import java.util.*;

public class Hooks {

    private WebDriver driver;
    private String moduleName;

    @Before
    public void setup(Scenario scenario) {
        // 1️⃣ Module resolution first
        moduleName = TagUtil.extractModuleName(scenario.getSourceTagNames());
        if ("UnknownModule".equals(moduleName)) {
            moduleName = deriveModuleFromPath(scenario);
        }
        String override = System.getProperty("moduleName");
        if (override != null && !override.isBlank()) {
            moduleName = override;
        }

        // 2️⃣ Publish environment info before report init
        if (System.getProperty("execution") == null) {
            System.setProperty("execution", ConfigReader.getProperty("execution", "local"));
        }
        if (System.getProperty("browser") == null) {
            System.setProperty("browser", ConfigReader.getProperty("browser", "chrome"));
        }
        System.setProperty("moduleName", moduleName);

        // 3️⃣ Initialize report after module + environment are ready
        ExtentReportManager.initReports();
        ExtentReportManager.createTest(scenario.getName(), moduleName);

        // 4️⃣ Start fresh driver
        WebDriverManagerUtil.quitDriver();
        driver = WebDriverManagerUtil.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed() && driver != null) {
                ScreenshotUtility ss = new ScreenshotUtility(driver);
                String name = scenario.getName().replaceAll("[^a-zA-Z0-9-_]", "_");
                String path = ss.captureScreenshot(moduleName, name);
                System.out.println("📸 Screenshot saved to: " + path);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Screenshot capture failed: " + e.getMessage());
        } finally {
            WebDriverManagerUtil.quitDriver();
            ExtentReportManager.flushReports();
            ExtentReportManager.unload();
        }
    }

    @AfterAll
    public static void afterAll() {
        // final safety flush
        ExtentReportManager.flushReports();
    }

    // ===== helpers =====
    private String deriveModuleFromPath(Scenario scenario) {
        try {
            // e.g. .../Automation_Project/ui-tests/src/test/java/.../LoginPage.feature
            String path = scenario.getUri().getPath();
            if (path == null) return "UnknownModule";
            String[] parts = path.split("/+");
            for (int i = 0; i < parts.length; i++) {
                if ("src".equalsIgnoreCase(parts[i]) && i > 0) return parts[i - 1];
            }
        } catch (Exception ignored) {}
        return "UnknownModule";
    }
}
