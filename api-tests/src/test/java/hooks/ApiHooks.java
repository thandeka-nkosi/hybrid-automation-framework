package hooks;

import core.reporting.ExtentReportManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ApiHooks {

    @Before
    public void setUpScenario(Scenario scenario) {

        System.out.println(">>> API HOOK STARTED: " + scenario.getName());

        String moduleName = System.getProperty("moduleName", "api-tests");
        ExtentReportManager.createTest(
                scenario.getName(),
                moduleName
        );

        ExtentReportManager.getTest().info("Starting API scenario: " + scenario.getName());
    }

    @After
    public void tearDownScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentReportManager.getTest().fail("API scenario failed: " + scenario.getName());
        } else {
            ExtentReportManager.getTest().pass("API scenario passed: " + scenario.getName());
        }

        ExtentReportManager.flushReports();
    }
}
