package stepDefinitions;

import Constantns.BaseClass;
import actions.LoginActions;
import core.driver.WebDriverManagerUtil;
import core.reporting.ExtentReportManager;
import core.utils.ConfigReader;
import core.utils.StepLogger;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

public class LoginStepDefinitions extends BaseClass {

    private LoginActions loginActions;
    WebDriver driver = WebDriverManagerUtil.getDriver();
    WebDriverWait wait;

    public LoginStepDefinitions() throws MalformedURLException {
        this.driver = WebDriverManagerUtil.getDriver();
        this.loginActions = new LoginActions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

   /* @Before
    public void beforeScenario(Scenario scenario) {
        ExtentReportManager.createTest(scenario.getName());
    }
*/
    @Given("user is on Sauce Lab login page")
    public void user_is_on_sauce_lab_login_page() throws MalformedURLException {

        StepLogger.logStep(
                "User is navigated to login page",
                "Failed to load login page",
                () -> {
                    driver.get(ConfigReader.getProperty("sauceDemoUrl","local"));
                    driver.getPageSource().contains("Swag Labs");
                    //Assert.assertTrue("Login page is not loaded properly.", loginActions.isOnLoginPage());

                }
        );
}

    @And("user captures login credentials {string} and {string} and clicks on the login button")
    public void userCapturesLoginCredentialsAndAndClicksOnTheLoginButton(String username, String password) throws MalformedURLException {

        StepLogger.logStep(

                "User is logged in",
                "Failed to login",
                ()-> {

            loginToApp(username,password);

                }
        );

    }

}
