public class SavingsAccount extends Account {
    private final double minimumBalance;

    public SavingsAccount(String accountNumber, Customer customer, double balance, double minimumBalance) {
        super(accountNumber, customer, balance);
        this.minimumBalance = minimumBalance;
    }

    @Override
    public void deposit(double amount) throws InvalidAmountException {
        super.deposit(amount);
    }

    @Override
    public void withdraw(double amount)
            throws InvalidAmountException, InsufficientBalanceException, MinimumBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance in savings account " + accountNumber);
        }
        if (balance - amount < minimumBalance) {
            throw new MinimumBalanceException(
                    "Cannot withdraw. Minimum balance of " + minimumBalance + " must be maintained.");
        }
        balance -= amount;
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }

    @Override
    public String getSummary() {
        return String.format("Type: %s, Account No: %s, Balance: %.2f, Minimum Balance: %.2f",
                getAccountType(), accountNumber, balance, minimumBalance);
    }
}
