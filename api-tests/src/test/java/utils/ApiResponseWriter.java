package utils;

import io.restassured.response.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApiResponseWriter {

    public static void saveSuccessfulResponse(Response response, String filePrefix) {

        int statusCode = response.getStatusCode();

        // Save only successful responses: 200, 201, 204, etc.
        if (statusCode < 200 || statusCode >= 300) {
            System.out.println("Response not saved because status code is: " + statusCode);
            return;
        }

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String folderPath = "test-output/responses/txt";
        String fileName = filePrefix + "_" + timestamp + ".txt";

        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, fileName);

        try (FileWriter writer = new FileWriter(file)) {

            writer.write("===== API RESPONSE =====" + System.lineSeparator());
            writer.write("Status Code: " + response.getStatusCode() + System.lineSeparator());
            writer.write("Content-Type: " + response.getContentType() + System.lineSeparator());
            writer.write("Response Time: " + response.getTime() + " ms" + System.lineSeparator());
            writer.write(System.lineSeparator());

            writer.write("===== RESPONSE BODY =====" + System.lineSeparator());
            writer.write(response.asPrettyString());
            writer.write(System.lineSeparator());

            writer.write("========================" + System.lineSeparator());

            System.out.println("Successful API response saved to: " + file.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Failed to save API response to file: " + file.getAbsolutePath(), e);
        }
    }

    public static void saveSuccessfulJsonResponse(Response response, String filePrefix) {

        int statusCode = response.getStatusCode();

        // Save only successful responses: 200, 201, 204, etc.
        if (statusCode < 200 || statusCode >= 300) {
            System.out.println("JSON response not saved because status code is: " + statusCode);
            return;
        }

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String folderPath = "test-output/responses/json";
        String fileName = filePrefix + "_" + timestamp + ".json";

        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, fileName);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(response.asPrettyString());

            System.out.println("Successful JSON response saved to: " + file.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Failed to save API JSON response to file: " + file.getAbsolutePath(), e);
        }
    }
}
