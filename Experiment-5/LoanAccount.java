import java.util.ArrayList;

public class LoanAccount extends Account {
    private double principalAmount;
    private final double interestRate;
    private double interestAmount;
    private final String loanType;
    private final int tenureInMonths;
    private final ArrayList<String> collaterals;
    private double penalty;

    public LoanAccount(String accountNumber, Customer customer, double principalAmount,
                       double interestRate, String loanType, int tenureInMonths,
                       ArrayList<String> collaterals) {
        super(accountNumber, customer, 0.0);
        if (principalAmount < 0) {
            throw new IllegalArgumentException("Principal amount cannot be negative.");
        }
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        if (tenureInMonths <= 0) {
            throw new IllegalArgumentException("Loan tenure must be greater than zero.");
        }
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.loanType = loanType;
        this.tenureInMonths = tenureInMonths;
        this.collaterals = collaterals;
        updateInterestAmount();
    }

    @Override
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Loan payment amount must be greater than zero.");
        }
        principalAmount -= amount;
        if (principalAmount < 0) {
            principalAmount = 0;
        }
        updateInterestAmount();
    }

    @Override
    public void withdraw(double amount) throws LoanOperationException {
        throw new LoanOperationException("Withdrawal is not allowed from a loan account.");
    }

    public double calculateEmi() {
        if (principalAmount == 0) {
            return 0;
        }
        double monthlyRate = interestRate / (12 * 100);
        if (monthlyRate == 0) {
            return principalAmount / tenureInMonths;
        }
        double factor = Math.pow(1 + monthlyRate, tenureInMonths);
        return (principalAmount * monthlyRate * factor) / (factor - 1);
    }

    public void updatePrincipal(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Principal amount cannot be negative.");
        }
        principalAmount = amount;
        updateInterestAmount();
    }

    public void updateInterestAmount() {
        interestAmount = calculateEmi() * tenureInMonths - principalAmount;
    }

    public double calculateTotalPayable() {
        return principalAmount + interestAmount + penalty;
    }

    public void payPenalty(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Penalty payment must be greater than zero.");
        }
        penalty = Math.max(0, penalty - amount);
    }

    public void addPenalty(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Penalty amount must be greater than zero.");
        }
        penalty += amount;
    }

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getInterestAmount() {
        return interestAmount;
    }

    public String getLoanType() {
        return loanType;
    }

    public int getTenureInMonths() {
        return tenureInMonths;
    }

    public ArrayList<String> getCollaterals() {
        return new ArrayList<>(collaterals);
    }

    public double getPenalty() {
        return penalty;
    }

    public String getLoanDetails() {
        return String.format(
                "Loan Account No: %s, Type: %s, Principal Due: %.2f, Rate: %.2f%%, Tenure: %d months, "
                        + "EMI: %.2f, Interest: %.2f, Penalty: %.2f, Total Payable: %.2f, Collateral: %s",
                accountNumber, loanType, principalAmount, interestRate, tenureInMonths,
                calculateEmi(), interestAmount, penalty, calculateTotalPayable(), collaterals);
    }

    @Override
    public String getAccountType() {
        return "Loan Account";
    }

    @Override
    public String getSummary() {
        return String.format(
                "Type: %s, Account No: %s, Loan Type: %s, Principal Due: %.2f, Interest: %.2f, EMI: %.2f, Penalty: %.2f",
                getAccountType(), accountNumber, loanType, principalAmount, interestAmount, calculateEmi(), penalty);
    }
}
