package actions;

import io.restassured.response.Response;
import utils.ApiConfigReader;
import utils.ApiResponseWriter;

import static io.restassured.RestAssured.given;

public class UsersApiActions {


        private Response response;

        public void getUserById(String userId) {
            response = given()
                    .baseUri(ApiConfigReader.getProperty("api.baseUri"))
                    .header("x-api-key", ApiConfigReader.getProperty("api.key"))
                    .basePath("/api/users/" + userId)
                    .when()
                    .get();

            System.out.println("===== API RESPONSE =====");
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Content-Type: " + response.getContentType());
            System.out.println("Response Body:");
            System.out.println(response.asPrettyString());
            System.out.println("========================");

            ApiResponseWriter.saveSuccessfulResponse(response, "get_user_" + userId);
            ApiResponseWriter.saveSuccessfulJsonResponse(response, "get_user_" + userId);

        }

        public Response getResponse() {
            return response;
        }
}
