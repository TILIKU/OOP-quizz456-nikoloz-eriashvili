package quiz6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SpecialCommunicationManager {
    private String commonServiceUrl;
    private String specialServiceUrl;

    public SpecialCommunicationManager(String commonServiceUrl, String specialServiceUrl) {
        this.commonServiceUrl = commonServiceUrl;
        this.specialServiceUrl = specialServiceUrl;
    }

    public String sendMessage(String message, List<String> conversationHistory) {
        String serviceUrl = determineServiceUrl(message);

        try {
            // Prepare the URL and connection
            URL url = new URL(serviceUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Create the JSON payload
            String jsonInputString = "{\"message\": \"" + message + "\", \"history\": " + conversationHistory + "}";

            // Send the POST request with the JSON payload
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            int status = connection.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Return the response received from the API
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to connect to the service.";
        }
    }

    private String determineServiceUrl(String message) {
        // Check if the message contains "help"
        if (message.toLowerCase().contains("help")) {
            return specialServiceUrl;
        } else {
            return commonServiceUrl;
        }
    }
}
