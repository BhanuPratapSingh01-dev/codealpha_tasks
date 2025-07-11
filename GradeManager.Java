import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class GradeManager extends JFrame {
    private ArrayList<Double> grades = new ArrayList<>();
    private JTextField inputField;
    private JTextArea outputArea;
    private DefaultListModel<String> listModel;
    private JList<String> gradesList;
    private DecimalFormat df = new DecimalFormat("#.##");

    public GradeManager() {
        // Setup the main window
        setTitle("Student Grade Manager");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panels
        JPanel inputPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel outputPanel = new JPanel();

        // Input components
        inputPanel.setLayout(new FlowLayout());
        JLabel inputLabel = new JLabel("Enter Grade (0-100):");
        inputField = new JTextField(10);
        JButton addButton = new JButton("Add Grade");
        
        // List to display grades
        listModel = new DefaultListModel<>();
        gradesList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(gradesList);
        
        // Buttons for operations
        buttonPanel.setLayout(new FlowLayout());
        JButton calculateButton = new JButton("Calculate Stats");
        JButton clearButton = new JButton("Clear All");

        // Output area
        outputPanel.setLayout(new BorderLayout());
        outputArea = new JTextArea(7, 30);
        outputArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Add components to panels
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(addButton);
        
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(outputPanel, BorderLayout.EAST);

        // Event handlers
        addButton.addActionListener(e -> addGrade());
        calculateButton.addActionListener(e -> calculateStats());
        clearButton.addActionListener(e -> clearAll());
        inputField.addActionListener(e -> addGrade());

        // Configure some styling
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        
        // Make window visible
        setVisible(true);
    }

    private void addGrade() {
        try {
            String input = inputField.getText();
            if (input.isEmpty()) {
                showError("Please enter a grade");
                return;
            }

            double grade = Double.parseDouble(input);
            if (grade < 0 || grade > 100) {
                showError("Grade must be between 0 and 100");
                return;
            }

            grades.add(grade);
            listModel.addElement("Grade: " + df.format(grade) + "%");
            inputField.setText("");
            outputArea.setText("Grade added successfully!");
            
        } catch (NumberFormatException ex) {
            showError("Please enter a valid number");
        }
    }

    private void calculateStats() {
        if (grades.isEmpty()) {
            showError("No grades to calculate");
            return;
        }

        double sum = 0;
        double highest = grades.get(0);
        double lowest = grades.get(0);

        for (double grade : grades) {
            sum += grade;
            if (grade > highest) highest = grade;
            if (grade < lowest) lowest = grade;
        }

        double average = sum / grades.size();

        String stats = "GRADE STATISTICS:\n\n" +
                       "Number of Grades: " + grades.size() + "\n" +
                       "Average Grade: " + df.format(average) + "%\n" +
                       "Highest Grade: " + df.format(highest) + "%\n" +
                       "Lowest Grade: " + df.format(lowest) + "%\n\n";

        // Categorize grades
        int[] categories = new int[5]; // For F (0-59), D (60-69), C (70-79), B (80-89), A (90-100)
        for (double grade : grades) {
            if (grade < 60) categories[0]++;
            else if (grade < 70) categories[1]++;
            else if (grade < 80) categories[2]++;
            else if (grade < 90) categories[3]++;
            else categories[4]++;
        }

        stats += "GRADE DISTRIBUTION:\n" +
                 "F (<60): " + categories[0] + " grades\n" +
                 "D (60-69): " + categories[1] + " grades\n" +
                 "C (70-79): " + categories[2] + " grades\n" +
                 "B (80-89): " + categories[3] + " grades\n" +
                 "A (90+): " + categories[4] + " grades";

        outputArea.setText(stats);
    }

    private void clearAll() {
        grades.clear();
        listModel.clear();
        outputArea.setText("");
        inputField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeManager());
    }
}
