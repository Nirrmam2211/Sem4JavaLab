import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Account> accounts = new ArrayList<>();

        initializeData(customers, accounts);
        runMenu(customers, accounts);
    }

    private static void initializeData(ArrayList<Customer> customers, ArrayList<Account> accounts) {
        Customer customer1 = new Customer(
                101, "Aarav Sharma", "ABCDE1234F", "123412341234",
                LocalDate.of(1995, 5, 10), "Passport");
        Customer customer2 = new Customer(
                102, "Diya Patel", "PQRSX9876K", "567856785678",
                LocalDate.of(1998, 8, 21), "Driving License");

        customers.add(customer1);
        customers.add(customer2);

        ArrayList<String> collateral1 = new ArrayList<>();
        collateral1.add("House Documents");

        ArrayList<String> collateral2 = new ArrayList<>();
        collateral2.add("Fixed Deposit");

        SavingsAccount savings1 = new SavingsAccount("SB1001", customer1, 25000, 5000);
        LoanAccount loan1 = new LoanAccount("LN2001", customer1, 500000, 9.5, "Home Loan", 120, collateral1);
        SavingsAccount savings2 = new SavingsAccount("SB1002", customer2, 40000, 3000);
        LoanAccount loan2 = new LoanAccount("LN2002", customer2, 200000, 11.0, "Education Loan", 60, collateral2);

        accounts.add(savings1);
        accounts.add(loan1);
        accounts.add(savings2);
        accounts.add(loan2);

        customer1.addAccount(savings1);
        customer1.addAccount(loan1);
        customer2.addAccount(savings2);
        customer2.addAccount(loan2);
    }

    private static void runMenu(ArrayList<Customer> customers, ArrayList<Account> accounts) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    displayCustomers(customers);
                    break;
                case 2:
                    displayAccounts(accounts);
                    break;
                case 3:
                    handleDeposit(accounts, scanner);
                    break;
                case 4:
                    handleWithdrawal(accounts, scanner);
                    break;
                case 5:
                    viewLoanDetails(accounts, scanner);
                    break;
                case 6:
                    addLoanPenalty(accounts, scanner);
                    break;
                case 7:
                    payLoanPenalty(accounts, scanner);
                    break;
                case 8:
                    displayConsolidatedInfo(customers);
                    break;
                case 9:
                    viewCustomerDashboard(customers, scanner);
                    break;
                case 10:
                    running = false;
                    System.out.println("Exiting program. Thank you.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid menu option.");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== Banking System Menu =====");
        System.out.println("1. View All Customers");
        System.out.println("2. View All Accounts");
        System.out.println("3. Deposit Amount / Pay Loan EMI");
        System.out.println("4. Withdraw Amount");
        System.out.println("5. View Loan Details and EMI");
        System.out.println("6. Add Loan Penalty");
        System.out.println("7. Pay Loan Penalty");
        System.out.println("8. Display Consolidated Customer Account Information");
        System.out.println("9. View Customer Dashboard");
        System.out.println("10. Exit");
    }

    private static void displayCustomers(ArrayList<Customer> customers) {
        System.out.println("\n===== Customer Details =====");
        for (Customer customer : customers) {
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getName());
            System.out.println("PAN: " + customer.getPan());
            System.out.println("Aadhar: " + customer.getAadhar());
            System.out.println("DOB: " + customer.getDateOfBirth());
            System.out.println("Address Proof: " + customer.getAddressProof());
            System.out.println();
        }
    }

    private static void displayAccounts(ArrayList<Account> accounts) {
        System.out.println("\n===== Account Details =====");
        for (Account account : accounts) {
            System.out.println(account.getSummary());
        }
    }

    private static void handleDeposit(ArrayList<Account> accounts, Scanner scanner) {
        Account account = findAccount(accounts, scanner);
        if (account == null) {
            return;
        }

        double amount = readDouble(scanner, "Enter amount to deposit/pay: ");

        try {
            account.deposit(amount);
            System.out.println("Transaction successful.");
            System.out.println(account.getSummary());
        } catch (InvalidAmountException exception) {
            System.out.println("Transaction failed: " + exception.getMessage());
        }
    }

    private static void handleWithdrawal(ArrayList<Account> accounts, Scanner scanner) {
        Account account = findAccount(accounts, scanner);
        if (account == null) {
            return;
        }

        double amount = readDouble(scanner, "Enter amount to withdraw: ");

        try {
            account.withdraw(amount);
            System.out.println("Transaction successful.");
            System.out.println(account.getSummary());
        } catch (InvalidAmountException | InsufficientBalanceException
                 | MinimumBalanceException | LoanOperationException exception) {
            System.out.println("Transaction failed: " + exception.getMessage());
        }
    }

    private static void viewLoanDetails(ArrayList<Account> accounts, Scanner scanner) {
        LoanAccount loanAccount = findLoanAccount(accounts, scanner);
        if (loanAccount != null) {
            System.out.println("\n===== Loan Details =====");
            System.out.println(loanAccount.getLoanDetails());
        }
    }

    private static void addLoanPenalty(ArrayList<Account> accounts, Scanner scanner) {
        LoanAccount loanAccount = findLoanAccount(accounts, scanner);
        if (loanAccount == null) {
            return;
        }

        double amount = readDouble(scanner, "Enter penalty amount to add: ");

        try {
            loanAccount.addPenalty(amount);
            System.out.println("Penalty added successfully.");
            System.out.println(loanAccount.getLoanDetails());
        } catch (InvalidAmountException exception) {
            System.out.println("Penalty update failed: " + exception.getMessage());
        }
    }

    private static void payLoanPenalty(ArrayList<Account> accounts, Scanner scanner) {
        LoanAccount loanAccount = findLoanAccount(accounts, scanner);
        if (loanAccount == null) {
            return;
        }

        double amount = readDouble(scanner, "Enter penalty amount to pay: ");

        try {
            loanAccount.payPenalty(amount);
            System.out.println("Penalty payment successful.");
            System.out.println(loanAccount.getLoanDetails());
        } catch (InvalidAmountException exception) {
            System.out.println("Penalty payment failed: " + exception.getMessage());
        }
    }

    private static Account findAccount(ArrayList<Account> accounts, Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();

        for (Account account : accounts) {
            if (account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                return account;
            }
        }

        System.out.println("Account not found.");
        return null;
    }

    private static LoanAccount findLoanAccount(ArrayList<Account> accounts, Scanner scanner) {
        Account account = findAccount(accounts, scanner);
        if (account == null) {
            return null;
        }

        if (account instanceof LoanAccount) {
            return (LoanAccount) account;
        }

        System.out.println("The entered account is not a loan account.");
        return null;
    }

    private static void viewCustomerDashboard(ArrayList<Customer> customers, Scanner scanner) {
        int customerId = readInt(scanner, "Enter customer ID: ");

        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                customer.viewDashboard();
                for (Account account : customer.getAccounts()) {
                    System.out.println(account.getSummary());
                }
                return;
            }
        }

        System.out.println("Customer not found.");
    }

    private static void displayConsolidatedInfo(ArrayList<Customer> customers) {
        System.out.println("\n===== Consolidated Customer Account Information =====");
        for (Customer customer : customers) {
            System.out.println("\nCustomer ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getName());
            System.out.println("PAN: " + customer.getPan());
            System.out.println("Aadhar: " + customer.getAadhar());
            System.out.println("DOB: " + customer.getDateOfBirth());
            System.out.println("Address Proof: " + customer.getAddressProof());
            System.out.println("Accounts:");

            for (Account account : customer.getAccounts()) {
                System.out.println("  - " + account.getSummary());
            }
        }
    }

    private static int readInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid numeric amount.");
            }
        }
    }
}
