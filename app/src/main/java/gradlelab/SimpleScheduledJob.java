package gradlelab;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class SimpleScheduledJob {

    @Scheduled(fixedRate = 5000) // Executes every 5 seconds
    public void performScheduledTask() throws IOException, InterruptedException {
        // Your scheduled task logic goes here
        System.out.println("Simple scheduled job executed!");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://127.0.0.1:8080/remote_records"))
                .GET()
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        System.out.println("===============");
    }
}