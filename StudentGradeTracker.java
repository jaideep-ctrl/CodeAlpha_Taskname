import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    public static void main(String[] args) {
        // Use an ArrayList to store student names and another for their grades
        ArrayList<String> studentNames = new ArrayList<>();
        ArrayList<Double> studentGrades = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the Student Grade Tracker!");

        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add a new student and grade");
            System.out.println("2. Display summary report");
            System.out.println("3. Exit");
            System.out.print("Please enter your choice: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from the menu.");
                continue;
            }

            switch (choice) {
                case 1:
                    addStudent(studentNames, studentGrades, scanner);
                    break;
                case 2:
                    displaySummary(studentNames, studentGrades);
                    break;
                case 3:
                    running = false;
                    System.out.println("Thank you for using the Student Grade Tracker. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select an option from 1 to 3.");
            }
        }
        scanner.close();
    }

    /**
     * Adds a new student's name and grade to the ArrayLists.
     */
    public static void addStudent(ArrayList<String> names, ArrayList<Double> grades, Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter student grade: ");
        double grade = -1;
        try {
            grade = Double.parseDouble(scanner.nextLine());
            if (grade < 0 || grade > 100) {
                System.out.println("Invalid grade. Please enter a value between 0 and 100.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid grade. Please enter a numeric value.");
            return;
        }

        names.add(name);
        grades.add(grade);
        System.out.println("Student added successfully!");
    }

    /**
     * Displays a summary report including all students' grades and statistics.
     */
    public static void displaySummary(ArrayList<String> names, ArrayList<Double> grades) {
        if (names.isEmpty()) {
            System.out.println("No student data available. Please add some grades first.");
            return;
        }

        System.out.println("\n--- Student Grade Report ---");
        
        // Display individual student grades
        for (int i = 0; i < names.size(); i++) {
            System.out.printf("%s: %.2f\n", names.get(i), grades.get(i));
        }

        // Calculate and display statistics
        double sum = 0;
        double highest = grades.get(0);
        double lowest = grades.get(0);

        for (double grade : grades) {
            sum += grade;
            if (grade > highest) {
                highest = grade;
            }
            if (grade < lowest) {
                lowest = grade;
            }
        }

        double average = sum / grades.size();
        
        System.out.println("\n--- Statistics ---");
        System.out.printf("Average Score: %.2f\n", average);
        System.out.printf("Highest Score: %.2f\n", highest);
        System.out.printf("Lowest Score: %.2f\n", lowest);
    }
}