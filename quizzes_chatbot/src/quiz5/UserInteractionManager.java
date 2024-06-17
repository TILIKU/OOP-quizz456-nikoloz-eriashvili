package quiz5;

import quiz6.SpecialCommunicationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInteractionManager {
    private DummyCommunicationManager dummyCommManager;
    private SpecialCommunicationManager specialCommManager;
    private List<String> conversationHistory;

    public UserInteractionManager() {
        this.dummyCommManager = new DummyCommunicationManager();  // DummyCommunicationManager instance
        // Replace with actual URLs
        String commonUrl = "http://common.service.api/response";
        String specialUrl = "http://special.service.api/help";
        this.specialCommManager = new SpecialCommunicationManager(commonUrl, specialUrl);  // SpecialCommunicationManager instance
        this.conversationHistory = new ArrayList<>();
    }

    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Chatbot. Type 'exit' to quit.");

        while (true) {
            System.out.print("You: ");
            String userMessage = scanner.nextLine();

            if (userMessage.equalsIgnoreCase("exit")) {
                System.out.println("Chatbot: Goodbye!");
                break;
            }

            // Include the entire conversation history along with the current message
            String response;
            if (shouldUseSpecialService(userMessage)) {
                response = specialCommManager.sendMessage(userMessage, conversationHistory);
            } else {
                response = dummyCommManager.sendMessage(userMessage);
            }

            System.out.println("Chatbot: " + response);
            conversationHistory.add(userMessage);  // Add the message to conversation history
        }

        scanner.close();
    }

    private boolean shouldUseSpecialService(String message) {
        // Check if the message or any previous message in the history contains "help"
        for (String pastMessage : conversationHistory) {
            if (pastMessage.toLowerCase().contains("help")) {
                return true;
            }
        }
        return message.toLowerCase().contains("help");
    }

    public static void main(String[] args) {
        UserInteractionManager uim = new UserInteractionManager();
        uim.startChat();
    }
}
