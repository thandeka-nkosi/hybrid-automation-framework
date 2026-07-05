package banking.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;


import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/banking")                 // src/test/resources/features
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "banking.stepDefinitions,core.hooks")

// Let Docker/CLI pass -Dcucumber.plugin=... so Chrome/Edge write to different folders
@ConfigurationParameter(key = PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")

@ConfigurationParameter(key = "cucumber.execution.parallel.enabled", value = "true")
@ConfigurationParameter(key = "cucumber.execution.parallel.config.strategy", value = "fixed")
@ConfigurationParameter(key = "cucumber.execution.parallel.config.fixed.parallelism", value = "2")

public class BankingTestRunner {
}
