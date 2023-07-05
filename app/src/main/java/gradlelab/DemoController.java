package gradlelab;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class DemoController {

    @GetMapping("/remote_records")
    public ModelAndView remoteRecords() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("records");

        String apiUrl = "https://6242f044d126926d0c59a15f.mockapi.io/userprofile";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                // Convert response string to JSON objects
                ObjectMapper objectMapper = new ObjectMapper();
                List<Record> records = objectMapper.readValue(response.toString(), new TypeReference<List<Record>>() {});
                System.out.println(records);

                modelAndView.addObject("records", records);

                return modelAndView;
            } else {
                // Handle error response
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;

                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }

                    modelAndView.addObject("errorMessage", errorResponse.toString());
                }
            }
        } catch (Exception e) {
            // Handle exception
            System.out.println(e.getLocalizedMessage());
        }

        return modelAndView;
    }

    @GetMapping("/records")
    public ModelAndView helloWorld() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("records");

        ArrayList<Record> records = new ArrayList<>();

        records.add(new Record("John Doe", 25, "john.doe@example.com"));
        records.add(new Record("Jane Smith", 30, "jane.smith@example.com"));
        records.add(new Record("Bob Johnson", 40, "bob.johnson@example.com"));

        modelAndView.addObject("records", records);

        return modelAndView;
    }

    @GetMapping("/demo")
    public ModelAndView helloWorld(@RequestParam(name="name", required=false, defaultValue="World") String name,
                                   Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("demo");
        modelAndView.addObject("name", name);
        return modelAndView;
    }

    @GetMapping("/demo_introduction")
    public ModelAndView showIntroduction() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("demo_introduction");
        modelAndView.addObject("name", "rex");
        modelAndView.addObject("showMessage", false);
        modelAndView.addObject("numbers", new int[] { 1, 2, 3, 4, 5 });
        return modelAndView;
    }


}