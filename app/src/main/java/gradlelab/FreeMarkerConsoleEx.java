package gradlelab;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Component
public class FreeMarkerConsoleEx {
    @Scheduled(fixedRate = 5000) // Executes every 5 seconds
    public void performScheduledTask02() throws IOException, TemplateException {
        // Configuration for FreeMarker template engine
        Configuration cfg = configureTemplateEngine();

        // Load the FreeMarker template
        Template template = loadTemplate(cfg, "template.ftl");

        // Retrieve records from API
        List<Record> records = retrieveRecordsFromAPI();

        // Process the template with records
        String htmlBody = processTemplate(template, records);

        // Send emails with HTML body
        sendEmails(htmlBody);
    }

    private Configuration configureTemplateEngine() {
        Configuration cfg = new Configuration(new Version("2.3.31"));
        cfg.setClassForTemplateLoading(FreeMarkerConsoleEx.class, "/views");
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }

    private Template loadTemplate(Configuration cfg, String templateName) throws IOException {
        return cfg.getTemplate(templateName);
    }

    private List<Record> retrieveRecordsFromAPI() throws IOException {
        List<Record> records = new ArrayList<>();

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

                // Convert the response string to a list of Record objects
                ObjectMapper objectMapper = new ObjectMapper();
                records = objectMapper.readValue(response.toString(), new TypeReference<List<Record>>() {
                });
            } else {
                // Handle error response from the API
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    System.out.println("errorMessage:" + errorResponse.toString());
                }
            }
        } catch (Exception e) {
            // Handle exception
            System.out.println(e.getLocalizedMessage());
        }

        return records;
    }

    private String processTemplate(Template template, List<Record> records) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("records", records);
        template.process(dataModel, writer);
        return writer.toString();
    }

    private void sendEmails(String htmlBody) {
        System.out.println(htmlBody);

        final String senderEmail = "hi.chuntachen@gmail.com";
        final String password = "iullnefuekuqnguj";
        List<String> recipientEmails = Arrays.asList("uck729@gmail.com",
                "chunta.chen@lookout.com");

        // Configure email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password); // Replace with your actual password
            }
        });

        try {
            for (String recipientEmail : recipientEmails) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
                message.setSubject("Email Subject");
                message.setContent(htmlBody, "text/html");

                Transport.send(message);
                System.out.println("Email sent successfully to: " + recipientEmail);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}