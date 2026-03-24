public class Account {
    protected final String accountNumber;
    protected final Customer customer;
    protected double balance;

    public Account(String accountNumber, Customer customer, double balance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }
        balance += amount;
    }

    public void withdraw(double amount)
            throws InvalidAmountException, InsufficientBalanceException,
            MinimumBalanceException, LoanOperationException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance in account " + accountNumber);
        }
        balance -= amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return "Account";
    }

    public String getSummary() {
        return String.format("Type: %s, Account No: %s, Balance: %.2f",
                getAccountType(), accountNumber, balance);
    }
}
