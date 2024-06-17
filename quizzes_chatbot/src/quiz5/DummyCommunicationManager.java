package quiz5;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyCommunicationManager {
    private Map<String, Map<String, String>> responses;
    private List<String> conversationHistory;  // New field to store conversation history

    public DummyCommunicationManager() {
        loadResponses();
    }

    private void loadResponses() {
        // JSON-like data manually parsed into a HashMap
        String jsonData = "{ \"greetings\": { \"hello\": \"Hi!\", \"hi\": \"Hello!\", \"hey\": \"Hey there!\", \"good morning\": \"Good morning!\", \"good evening\": \"Good evening!\" }, \"inquiries\": { \"how are you?\": \"I'm fine, thank you!\", \"what's your name?\": \"I'm your friendly chatbot.\", \"how old are you?\": \"I’m timeless!\", \"what can you do?\": \"I can chat with you and answer your questions.\" }, \"time_related\": { \"what time is it?\": \"{display_time}\", \"what's the date today?\": \"{display_date}\" }, \"farewell\": { \"bye\": \"Goodbye!\", \"see you\": \"See you later!\", \"good night\": \"Good night!\" }, \"unknown\": { \"default\": \"I'm not sure how to respond to that.\" } }";

        responses = new HashMap<>();

        // Manually parsing the JSON-like structure
        responses.put("greetings", new HashMap<>());
        responses.get("greetings").put("hello", "Hi!");
        responses.get("greetings").put("hi", "Hello!");
        responses.get("greetings").put("hey", "Hey there!");
        responses.get("greetings").put("good morning", "Good morning!");
        responses.get("greetings").put("good evening", "Good evening!");

        responses.put("inquiries", new HashMap<>());
        responses.get("inquiries").put("how are you?", "I'm fine, thank you!");
        responses.get("inquiries").put("what's your name?", "I'm your friendly chatbot.");
        responses.get("inquiries").put("how old are you?", "I’m timeless!");
        responses.get("inquiries").put("what can you do?", "I can chat with you and answer your questions.");

        responses.put("time_related", new HashMap<>());
        responses.get("time_related").put("what time is it?", "{display_time}");
        responses.get("time_related").put("what's the date today?", "{display_date}");

        responses.put("farewell", new HashMap<>());
        responses.get("farewell").put("bye", "Goodbye!");
        responses.get("farewell").put("see you", "See you later!");
        responses.get("farewell").put("good night", "Good night!");

        responses.put("unknown", new HashMap<>());
        responses.get("unknown").put("default", "I'm not sure how to respond to that.");
    }

    public String sendMessage(String message) {
        // Check each category for a matching message
        for (Map.Entry<String, Map<String, String>> category : responses.entrySet()) {
            if (category.getValue().containsKey(message)) {
                String response = category.getValue().get(message);

                // Handle dynamic placeholders for time and date
                if (response.contains("{display_time}")) {
                    response = response.replace("{display_time}", getCurrentTime());
                } else if (response.contains("{display_date}")) {
                    response = response.replace("{display_date}", getCurrentDate());
                }

                return response;
            }
        }

        // Return the default unknown response if no match is found
        return responses.get("unknown").get("default");
    }

    public void updateConversationHistory(List<String> conversationHistory) {
        this.conversationHistory = conversationHistory;
        // Optionally, we can log or process the updated history here
    }

    private String getCurrentTime() {
        // Use java.util.Date to get current time
        Date now = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(now);
    }

    private String getCurrentDate() {
        // Use java.util.Date to get current date
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(now);
    }
}
