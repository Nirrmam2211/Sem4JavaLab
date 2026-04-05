import java.time.LocalDate;

public class ContractEmployee extends Employee {
    private final int noOfHours;
    private final double hourlyRate;

    public ContractEmployee(
            String name,
            String panNo,
            LocalDate joiningDate,
            String designation,
            String empId,
            int noOfHours,
            double hourlyRate) {
        super(name, panNo, joiningDate, designation, empId);
        this.noOfHours = noOfHours;
        this.hourlyRate = hourlyRate;
    }

    public int getNoOfHours() {
        return noOfHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    @Override
    public double calcCTC() {
        return noOfHours * hourlyRate;
    }
}
