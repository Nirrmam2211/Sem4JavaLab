
import java.util.Scanner;

public class ArithmeticMenuProgram {
    // Display menu options

    public static void displayMenu() {
        System.out.println("\n--- Arithmetic Operations Menu ---");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
    // Validate numeric input

    public static double getValidDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number: ");
            sc.next(); // discard invalid input
        }
        return sc.nextDouble();
    }
    // Arithmetic operations

    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static double divide(double a, double b) {
        return a / b;
    }
    // Main method

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            displayMenu();
            while (!sc.hasNextInt()) {
                System.out.print("Invalid choice. Please enter a number between 1 and 5: ");
                sc.next();
            }
            choice = sc.nextInt();
            if (choice >= 1 && choice <= 4) {
                System.out.print("Enter first number: ");
                double num1 = getValidDouble(sc);
                System.out.print("Enter second number: ");
                double num2 = getValidDouble(sc);
                double result = 0;
                switch (choice) {
                    case 1:
                        result = add(num1, num2);
                        break;
                    case 2:
                        result = subtract(num1, num2);
                        break;
                    case 3:
                        result = multiply(num1, num2);
                        break;
                    case 4:
                        result = divide(num1, num2);
                        break;
                }
                System.out.println("Result: " + result);
            } else if (choice != 5) {
                System.out.println("Invalid menu option. Try again. ");
            }
        } while (choice != 5);
        sc.close();
    }
}
