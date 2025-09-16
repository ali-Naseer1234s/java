import java.util.Scanner;

class Student {
    int rollNo;
    String name;
    int[] marks = new int[3];
    int total;
    double average;

    // Constructor
    Student(int rollNo, String name, int[] marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
        calculateTotalAndAverage();
    }

    void calculateTotalAndAverage() {
        total = marks[0] + marks[1] + marks[2];
        average = total / 3.0;
    }
}

public class StudentGradeSystem {
    static final int MAX = 50;
    static Student[] students = new Student[MAX];
    static int count = 0; // total students
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\nWelcome to Student Grade Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Update Marks");
            System.out.println("3. Remove Student");
            System.out.println("4. View All Students");
            System.out.println("5. Search Student");
            System.out.println("6. Highest Scorer");
            System.out.println("7. Class Average");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            choice = getIntInput();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> updateMarks();
                case 3 -> removeStudent();
                case 4 -> viewAllStudents();
                case 5 -> searchStudent();
                case 6 -> highestScorer();
                case 7 -> classAverage();
                case 8 -> exitProgram();
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 8);
    }

    // ✅ Add Student
    static void addStudent() {
        if (count >= MAX) {
            System.out.println("Cannot add more students (max 50).");
            return;
        }
        System.out.print("Enter Roll No: ");
        int roll = getIntInput();
        if (findIndexByRoll(roll) != -1) {
            System.out.println("Roll number already exists!");
            return;
        }
        System.out.print("Enter Name: ");
        String name = sc.next();
        int[] marks = new int[3];
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter Marks in Subject " + (i + 1) + ": ");
            marks[i] = getValidatedMarks();
        }
        students[count++] = new Student(roll, name, marks);
        System.out.println("Student added successfully!");
    }

    // ✅ Update Marks
    static void updateMarks() {
        System.out.print("Enter Roll No to update: ");
        int roll = getIntInput();
        int index = findIndexByRoll(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter new Marks in Subject " + (i + 1) + ": ");
            students[index].marks[i] = getValidatedMarks();
        }
        students[index].calculateTotalAndAverage();
        System.out.println("Marks updated successfully!");
    }

    // ✅ Remove Student
    static void removeStudent() {
        System.out.print("Enter Roll No to remove: ");
        int roll = getIntInput();
        int index = findIndexByRoll(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }
        for (int i = index; i < count - 1; i++) {
            students[i] = students[i + 1];
        }
        students[--count] = null;
        System.out.println("Student removed successfully!");
    }

    // ✅ View All Students
    static void viewAllStudents() {
        if (count == 0) {
            System.out.println("No students available.");
            return;
        }
        System.out.printf("%-10s %-15s %-10s %-10s %-10s %-10s %-10s\n",
                "Roll No", "Name", "Sub1", "Sub2", "Sub3", "Total", "Average");
        for (int i = 0; i < count; i++) {
            Student s = students[i];
            System.out.printf("%-10d %-15s %-10d %-10d %-10d %-10d %-10.2f\n",
                    s.rollNo, s.name, s.marks[0], s.marks[1], s.marks[2],
                    s.total, s.average);
        }
    }

    // ✅ Search Student
    static void searchStudent() {
        System.out.print("Enter Roll No to search: ");
        int roll = getIntInput();
        int index = findIndexByRoll(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }
        Student s = students[index];
        System.out.printf("Roll No: %d, Name: %s, Marks: [%d, %d, %d], Total: %d, Average: %.2f\n",
                s.rollNo, s.name, s.marks[0], s.marks[1], s.marks[2], s.total, s.average);
    }

    // ✅ Highest Scorer
    static void highestScorer() {
        if (count == 0) {
            System.out.println("No students available.");
            return;
        }
        Student top = students[0];
        for (int i = 1; i < count; i++) {
            if (students[i].total > top.total) {
                top = students[i];
            }
        }
        System.out.printf("Highest Scorer: %s (Roll No: %d), Total: %d, Average: %.2f\n",
                top.name, top.rollNo, top.total, top.average);
    }

    // ✅ Class Average
    static void classAverage() {
        if (count == 0) {
            System.out.println("No students available.");
            return;
        }
        int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += students[i].total;
        }
        double avg = sum / (count * 3.0);
        System.out.printf("Class Average: %.2f\n", avg);
    }

    // ✅ Exit Program
    static void exitProgram() {
        System.out.println("Exiting... Summary:");
        System.out.println("Total Students: " + count);
        classAverage();
        System.out.println("Goodbye!");
    }

    // ✅ Helpers
    static int findIndexByRoll(int roll) {
        for (int i = 0; i < count; i++) {
            if (students[i].rollNo == roll) return i;
        }
        return -1;
    }

    static int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input! Enter an integer:");
            sc.next();
        }
        return sc.nextInt();
    }

    static int getValidatedMarks() {
        int marks;
        while (true) {
            marks = getIntInput();
            if (marks >= 0 && marks <= 100) break;
            System.out.println("Marks must be between 0 and 100. Try again:");
        }
        return marks;
    }
}
