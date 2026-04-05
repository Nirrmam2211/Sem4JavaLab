import java.time.LocalDate;

public class FullTimeEmployee extends Employee {
    private final String role;
    protected double baseSalary;
    protected double perfBonus;
    protected double hiringCommission;

    public FullTimeEmployee(
            String name,
            String panNo,
            LocalDate joiningDate,
            String designation,
            String empId,
            String role,
            double baseSalary,
            double perfBonus,
            double hiringCommission) {
        super(name, panNo, joiningDate, designation, empId);
        this.role = role;
        this.baseSalary = baseSalary;
        this.perfBonus = perfBonus;
        this.hiringCommission = hiringCommission;
    }

    public String getRole() {
        return role;
    }

    @Override
    public double calcCTC() {
        if ("HR".equalsIgnoreCase(role)) {
            return baseSalary + hiringCommission;
        }
        return baseSalary + perfBonus;
    }
}
