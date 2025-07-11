// ChatbotGUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.*;

public class ChatbotGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField userInput;
    private JButton sendButton;
    private ChatbotEngine chatbot;
    
    public ChatbotGUI() {
        chatbot = new ChatbotEngine();
        createGUI();
    }
    
    private void createGUI() {
        setTitle("Java AI Chatbot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());
        
        // Chat display area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        
        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        userInput = new JTextField();
        userInput.setFont(new Font("Arial", Font.PLAIN, 16));
        userInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });
        
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });
        
        inputPanel.add(userInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        
        // Initial greeting
        chatArea.append("Bot: Hello! I'm your AI assistant. How can I help you today?\n");
    }
    
    private void processInput() {
        String input = userInput.getText().trim();
        if (!input.isEmpty()) {
            chatArea.append("You: " + input + "\n");
            userInput.setText("");
            
            String response = chatbot.getResponse(input);
            chatArea.append("Bot: " + response + "\n");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChatbotGUI().setVisible(true);
            }
        });
    }
}

class ChatbotEngine {
    // Knowledge base for rule-based responses
    private Map<String, String> knowledgeBase;
    // For sentiment analysis - simple implementation
    private Set<String> positiveWords = new HashSet<>(Arrays.asList(
        "happy", "good", "great", "excellent", "awesome", "fantastic"
    ));
    private Set<String> negativeWords = new HashSet<>(Arrays.asList(
        "bad", "terrible", "awful", "horrible", "sad"
    ));
    
    public ChatbotEngine() {
        initializeKnowledgeBase();
    }
    
    private void initializeKnowledgeBase() {
        knowledgeBase = new HashMap<>();
        // FAQ responses
        knowledgeBase.put("hello", "Hello! How can I help you today?");
        knowledgeBase.put("hi", "Hi there! What's on your mind?");
        knowledgeBase.put("how are you", "I'm just a program, so I don't have feelings, but thanks for asking!");
        knowledgeBase.put("what is your name", "I'm ChatBot, your virtual assistant.");
        knowledgeBase.put("what can you do", "I can answer questions, help with information, and chat with you.");
        knowledgeBase.put("help", "I can answer questions on various topics. Try asking me something!");
        // Add more questions and answers as needed
    }
    
    public String getResponse(String input) {
        String lowerInput = input.toLowerCase();
        
        // Check for exact matches first
        if (knowledgeBase.containsKey(lowerInput)) {
            return knowledgeBase.get(lowerInput);
        }
        
        // Pattern matching for more complex queries
        if (Pattern.matches(".*(name|called).*", lowerInput)) {
            return "I'm ChatBot, your virtual assistant.";
        }
        
        if (Pattern.matches(".*(help|assist).*", lowerInput)) {
            return "I can help with various topics. What specifically do you need help with?";
        }
        
        if (Pattern.matches(".*(thank|thanks).*", lowerInput)) {
            return "You're welcome! Is there anything else I can help you with?";
        }
        
        // Simple sentiment analysis
        if (containsAny(lowerInput, positiveWords)) {
            return "I'm glad to hear you're feeling positive!";
        }
        
        if (containsAny(lowerInput, negativeWords)) {
            return "I'm sorry to hear you're feeling down. Is there anything I can do to help?";
        }
        
        // Fallback response if no matches found
        return "I'm not sure I understand. Could you rephrase that or ask something else?";
    }
    
    private boolean containsAny(String input, Set<String> words) {
        for (String word : words) {
            if (input.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
