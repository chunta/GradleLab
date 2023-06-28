/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package gradlelab;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public void performAsyncHttpRequest(String url) {
        // Create an AsyncHttpClient instance
        AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

        // Send an asynchronous GET request
        CompletableFuture < Response > futureResponse = asyncHttpClient
            .prepareGet(url)
            .execute()
            .toCompletableFuture();

        // Process the response when it completes
        futureResponse.thenAccept(response -> {
            // Get the response status code and body
            int statusCode = response.getStatusCode();
            String responseBody = response.getResponseBody();

            System.out.println("Response Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);

            // Close the AsyncHttpClient
            try {
                asyncHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Wait for the response to complete (optional)
        try {
            futureResponse.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        App app = new App();
        app.performAsyncHttpRequest("https://6242f044d126926d0c59a15f.mockapi.io/userprofile");

        // Create a DateTime object representing the current date and time
        DateTime now = DateTime.now();

        // Format the DateTime object using a specific pattern
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.print(now);

        // Print the formatted date and time
        System.out.println("Current date and time: " + formattedDateTime);

        System.out.println(new App().getGreeting());

    }
}