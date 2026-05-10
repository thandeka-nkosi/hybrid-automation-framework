package core.utils;

import core.reporting.ExtentReportManager;

public class ApiStepLogger {

    public static void logStep(String successMessage, String failureMessage, Runnable action) {
        try {
            ExtentReportManager.getTest().info(successMessage);
            action.run();
            ExtentReportManager.getTest().pass("✅ " + successMessage);
        } catch (AssertionError | Exception e) {
            ExtentReportManager.getTest().fail("❌ " + failureMessage + ": " + e.getMessage());
            throw e;
        }
    }
}
