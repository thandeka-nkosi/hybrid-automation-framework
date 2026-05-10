package stepDefinitions;

import actions.UsersApiActions;
import core.utils.ApiStepLogger;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import validators.ApiResponseValidator;

public class UsersApiStepDefinitions {

    private final UsersApiActions usersApiActions = new UsersApiActions();
    private final ApiResponseValidator apiResponseValidator = new ApiResponseValidator();

    @When("I send a GET request to retrieve user {string}")
    public void i_send_a_get_request_to_retrieve_user(String userId) {

        ApiStepLogger.logStep(
                "GET request sent to retrieve user with ID: " + userId,
                "Failed to send GET request for user ID: " + userId,
                () -> usersApiActions.getUserById(userId)
        );

}

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {

        ApiStepLogger.logStep(
                "Response status code is " + expectedStatusCode,
                "Response status code validation failed",
                () -> apiResponseValidator.validateStatusCode(
                        usersApiActions.getResponse(),
                        expectedStatusCode
                )
        );

    }

    @Then("the response should contain user id {string}")
    public void the_response_should_contain_user_id(String expectedUserId) {

        ApiStepLogger.logStep(
                "Response contains user id: " + expectedUserId,
                "User id validation failed",
                () -> apiResponseValidator.validateFieldValue(
                        usersApiActions.getResponse(),
                        "data.id",
                        expectedUserId
                )
        );

    }

    @Then("the response header {string} should contain {string}")
    public void the_response_header_should_contain(String headerName, String expectedValue) {

        ApiStepLogger.logStep(
                "Response header " + headerName + " contains " + expectedValue,
                "Response header validation failed",
                () -> apiResponseValidator.validateHeaderContains(
                        usersApiActions.getResponse(),
                        headerName,
                        expectedValue
                )
        );
    }



}
