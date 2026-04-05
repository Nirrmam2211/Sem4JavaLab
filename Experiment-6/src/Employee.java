import java.time.LocalDate;

public abstract class Employee {
    private final String name;
    private final String panNo;
    private final LocalDate joiningDate;
    private final String designation;
    private final String empId;

    protected Employee(String name, String panNo, LocalDate joiningDate, String designation, String empId) {
        this.name = name;
        this.panNo = panNo;
        this.joiningDate = joiningDate;
        this.designation = designation;
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public String getPanNo() {
        return panNo;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public String getDesignation() {
        return designation;
    }

    public String getEmpId() {
        return empId;
    }

    public abstract double calcCTC();

    public String basicDetails() {
        return String.format("%s (%s) | PAN: %s | Joined: %s", name, designation, panNo, joiningDate);
    }
}
