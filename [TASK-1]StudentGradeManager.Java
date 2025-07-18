import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeManager {
    private static ArrayList<String> studentNames = new ArrayList<>();
    private static ArrayList<double[]> studentGrades = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Student Grade Management System ===");
        displayMenu();
    }

    private static void displayMenu() {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Add Student and Grades");
            System.out.println("2. View All Students and Grades");
            System.out.println("3. Calculate Class Statistics");
            System.out.println("4. View Individual Student Report");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    calculateClassStatistics();
                    break;
                case 4:
                    viewIndividualReport();
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("\nEnter student name: ");
        String name = scanner.nextLine();
        studentNames.add(name);

        System.out.print("How many grades for this student? ");
        int numGrades = scanner.nextInt();
        double[] grades = new double[numGrades];

        System.out.println("Enter the grades:");
        for (int i = 0; i < numGrades; i++) {
            System.out.print("Grade " + (i + 1) + ": ");
            grades[i] = scanner.nextDouble();
        }
        studentGrades.add(grades);

        System.out.println("Student added successfully!");
    }

    private static void viewAllStudents() {
        if (studentNames.isEmpty()) {
            System.out.println("\nNo students in the system yet.");
            return;
        }

        System.out.println("\n=== All Students and Grades ===");
        for (int i = 0; i < studentNames.size(); i++) {
            System.out.print((i + 1) + ". " + studentNames.get(i) + ": ");
            double[] grades = studentGrades.get(i);
            for (double grade : grades) {
                System.out.print(grade + " ");
            }
            System.out.println();
        }
    }

    private static void calculateClassStatistics() {
        if (studentNames.isEmpty()) {
            System.out.println("\nNo students in the system yet.");
            return;
        }

        double classTotal = 0;
        int totalGrades = 0;
        double highestGrade = Double.MIN_VALUE;
        double lowestGrade = Double.MAX_VALUE;

        for (double[] grades : studentGrades) {
            for (double grade : grades) {
                classTotal += grade;
                totalGrades++;
                if (grade > highestGrade) {
                    highestGrade = grade;
                }
                if (grade < lowestGrade) {
                    lowestGrade = grade;
                }
            }
        }

        double classAverage = classTotal / totalGrades;

        System.out.println("\n=== Class Statistics ===");
        System.out.printf("Class Average: %.2f\n", classAverage);
        System.out.println("Highest Grade: " + highestGrade);
        System.out.println("Lowest Grade: " + lowestGrade);
        System.out.println("Total Students: " + studentNames.size());
    }

    private static void viewIndividualReport() {
        if (studentNames.isEmpty()) {
            System.out.println("\nNo students in the system yet.");
            return;
        }

        System.out.println("\nSelect a student:");
        for (int i = 0; i < studentNames.size(); i++) {
            System.out.println((i + 1) + ". " + studentNames.get(i));
        }
        System.out.print("Enter student number: ");
        int studentNum = scanner.nextInt() - 1;

        if (studentNum < 0 || studentNum >= studentNames.size()) {
            System.out.println("Invalid student number.");
            return;
        }

        String name = studentNames.get(studentNum);
        double[] grades = studentGrades.get(studentNum);
        double sum = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;

        for (double grade : grades) {
            sum += grade;
            if (grade > highest) {
                highest = grade;
            }
            if (grade < lowest) {
                lowest = grade;
            }
        }

        double average = sum / grades.length;

        System.out.println("\n=== Student Report: " + name + " ===");
        System.out.print("Grades: ");
        for (double grade : grades) {
            System.out.print(grade + " ");
        }
        System.out.printf("\nAverage: %.2f\n", average);
        System.out.println("Highest Grade: " + highest);
        System.out.println("Lowest Grade: " + lowest);
    }
}

