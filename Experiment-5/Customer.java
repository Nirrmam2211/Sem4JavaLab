import java.time.LocalDate;
import java.util.ArrayList;

public class Customer {
    private final int customerId;
    private String name;
    private String pan;
    private String aadhar;
    private LocalDate dateOfBirth;
    private String addressProof;
    private final ArrayList<Account> accounts;

    public Customer(int customerId, String name, String pan, String aadhar,
                    LocalDate dateOfBirth, String addressProof) {
        this.customerId = customerId;
        this.name = name;
        this.pan = pan;
        this.aadhar = aadhar;
        this.dateOfBirth = dateOfBirth;
        this.addressProof = addressProof;
        this.accounts = new ArrayList<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPan() {
        return pan;
    }

    public String getAadhar() {
        return aadhar;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddressProof() {
        return addressProof;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddressProof(String addressProof) {
        this.addressProof = addressProof;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void viewTransactionHistory() {
        System.out.println("Transaction history is available for " + name);
    }

    public void downloadStatement() {
        System.out.println("Statement downloaded for customer " + customerId);
    }

    public void updatePersonalDetails(String name, String addressProof) {
        this.name = name;
        this.addressProof = addressProof;
    }

    public void viewDashboard() {
        System.out.println("Dashboard for " + name + " has " + accounts.size() + " account(s).");
    }
}
