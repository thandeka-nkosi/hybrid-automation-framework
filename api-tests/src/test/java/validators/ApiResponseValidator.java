package validators;

import core.reporting.ExtentReportManager;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

public class ApiResponseValidator {

    public void validateStatusCode(Response response, int expectedStatusCode) {
        assertEquals(
                expectedStatusCode,
                response.getStatusCode(),
                "Status code mismatch"
        );

        ExtentReportManager.getTest().pass(
                "Status code validated. Expected: " + expectedStatusCode +
                        ", Actual: " + response.getStatusCode()
        );
    }

    public void validateFieldValue(Response response, String jsonPath, String expectedValue) {
        String actualValue = response.jsonPath().getString(jsonPath);

        assertEquals(
                expectedValue,
                actualValue,
                "Field value mismatch for JSON path: " + jsonPath
        );

        ExtentReportManager.getTest().pass(
                "Field validated: " + jsonPath +
                        " | Expected: " + expectedValue +
                        " | Actual: " + actualValue
        );
    }

    public void validateHeaderContains(Response response, String headerName, String expectedValue) {
        String actualHeader = response.getHeader(headerName);

        assertNotNull(
                actualHeader,
                "Header not found: " + headerName
        );

        assertTrue(
                actualHeader.contains(expectedValue),
                "Expected header '" + headerName + "' to contain '" + expectedValue + "' but found: " + actualHeader
        );

        ExtentReportManager.getTest().pass(
                "Header validated: " + headerName +
                        " contains: " + expectedValue
        );
    }
}
