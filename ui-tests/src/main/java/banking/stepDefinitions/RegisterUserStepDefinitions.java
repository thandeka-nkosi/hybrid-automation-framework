package banking.stepDefinitions;

import banking.actions.RegisterUserActions;
import core.driver.WebDriverManagerUtil;
import core.utils.StepLogger;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;


public class RegisterUserStepDefinitions {

    private WebDriver driver;
    private RegisterUserActions registerUserActions;

    public RegisterUserStepDefinitions(){

        this.driver = WebDriverManagerUtil.getDriver();
        this.registerUserActions = new RegisterUserActions(driver);
    }

    @Given("the user on the landing page")
    public void the_user_on_the_landing_page() {

        StepLogger.logStep(
                "User on the landing page",
                "Failed to launch the user on the landing page",
                ()-> registerUserActions.launchBrowser()

        );
    }

    @When("user clicks on register link")
    public void user_clicks_on_register_link() {

        StepLogger.logStep(
                "Register link clicked",
                "Failed to click on register link",
                () -> registerUserActions.clickRegisterLink()
        );

    }

    @And("sign up form is displayed")
    public void sign_up_form_is_displayed() {

        StepLogger.logStep(

                "Register form is displayed",
                "Failed to display register form",
                () -> registerUserActions.registerFormDisplayed()
        );

    }

    @And("the user captures {string},{string},{string}, {string}, {string}, {string}, {string}, {string}")
    public void the_user_captures(String firstName, String lastName,
                                  String address, String city,
                                  String state, String zipCode,
                                  String phone, String ssn) {

        StepLogger.logStep(

                "Register form details captured",
                "Failed to capture register form",
                ()-> registerUserActions.captureFormDetails(firstName,lastName,address,city,state,zipCode,phone,ssn)
        );

    }

    @And("the user clicks register button")
    public void theUserClicksRegisterButton() {
        StepLogger.logStep(

                "Register button clicked",
                "Failed to click register button",
                ()-> registerUserActions.clickOnRegisterButton()
        );
    }

    @Then("the user is registered and logged in")
    public void the_user_is_registered_and_logged_in() {

        StepLogger.logStep(
                "User is registered and logged in",
                "User not registered and logged in",
                ()-> registerUserActions.userIsRegistered()

        );

    }

}
