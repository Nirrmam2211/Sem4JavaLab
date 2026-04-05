import java.time.LocalDate;

public class Manager extends FullTimeEmployee {
    private final double travelAllowance;
    private final double educationAllowance;

    public Manager(
            String name,
            String panNo,
            LocalDate joiningDate,
            String designation,
            String empId,
            String role,
            double baseSalary,
            double perfBonus,
            double hiringCommission,
            double travelAllowance,
            double educationAllowance) {
        super(name, panNo, joiningDate, designation, empId, role, baseSalary, perfBonus, hiringCommission);
        this.travelAllowance = travelAllowance;
        this.educationAllowance = educationAllowance;
    }

    @Override
    public double calcCTC() {
        return baseSalary + perfBonus + travelAllowance + educationAllowance;
    }
}
