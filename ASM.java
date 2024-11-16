import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    String firstName;
    String lastName;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getInfo() {
        return lastName + " " + firstName; // Định dạng "Họ, Tên"
    }

    public void update(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

class ASM {
    private static final List<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Student Manager System");
        while (true) {
            showMenu();
            int choice = getValidatedInteger(sc, "Enter your choice: ");

            switch (choice) {
                case 1 -> enterStudent(sc);
                case 2 -> findLastName(sc);
                case 3 -> findAndEditFullName(sc);
                case 4 -> {
                    System.out.println("Exiting the program.");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n1. Enter Student List");
        System.out.println("2. Find Students by Last Name");
        System.out.println("3. Find and Edit Students by Full Name");
        System.out.println("4. End");
    }

    private static int getValidatedInteger(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static void enterStudent(Scanner sc) {
        int numStudents = getValidatedInteger(sc, "Enter number of students: ");
        for (int i = 0; i < numStudents; i++) {
            String firstName = getNonEmptyInput(sc, "Enter first name for student " + (i + 1) + ": ");
            String lastName = getNonEmptyInput(sc, "Enter last name for student " + (i + 1) + ": ");
            studentList.add(new Student(firstName, lastName));
        }
        System.out.println("Students added successfully.");
    }

    private static void findLastName(Scanner sc) {
        String lastName = getNonEmptyInput(sc, "Enter last name to search: ");
        List<Student> foundStudents = studentList.stream()
                .filter(student -> student.lastName.equalsIgnoreCase(lastName))
                .toList();

        if (foundStudents.isEmpty()) {
            System.out.println("No students found with last name: " + lastName);
        } else {
            System.out.println("Students with last name '" + lastName + "':");
            foundStudents.forEach(student -> System.out.println(student.getInfo()));
        }
    }

    private static void findAndEditFullName(Scanner sc) {
        String fullName = getNonEmptyInput(sc, "Enter full name (last name and first name) to edit: ");
        String[] nameParts = fullName.split(" ", 2);

        if (nameParts.length < 2) {
            System.out.println("Please enter both last name and first name.");
            return;
        }

        String lastName = nameParts[0];
        String firstName = nameParts[1];

        for (Student student : studentList) {
            if (student.lastName.equalsIgnoreCase(lastName) && student.firstName.equalsIgnoreCase(firstName)) {
                String newFirstName = getNonEmptyInput(sc, "Enter new first name: ");
                String newLastName = getNonEmptyInput(sc, "Enter new last name: ");
                student.update(newFirstName, newLastName);
                System.out.println("Student details updated successfully.");
                return;
            }
        }
        System.out.println("No student found with the name: " + fullName);
    }

    private static String getNonEmptyInput(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }
}