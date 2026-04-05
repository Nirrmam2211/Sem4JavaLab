import java.time.LocalDate;

public class EmployeePayrollDemo {
    public static void main(String[] args) {
        FullTimeEmployee softwareEngineer = new FullTimeEmployee(
                "Riya Sharma",
                "ABCDE1234F",
                LocalDate.of(2023, 7, 10),
                "Software Engineer",
                "EMP101",
                "SWE",
                720000,
                85000,
                0);

        FullTimeEmployee hrExecutive = new FullTimeEmployee(
                "Arjun Mehta",
                "PQRSX4567K",
                LocalDate.of(2022, 3, 14),
                "HR Executive",
                "EMP102",
                "HR",
                540000,
                0,
                60000);

        ContractEmployee contractDeveloper = new ContractEmployee(
                "Neha Verma",
                "LMNOP9876T",
                LocalDate.of(2024, 1, 8),
                "Contract Developer",
                "EMP103",
                160,
                950);

        Manager engineeringManager = new Manager(
                "Kabir Rao",
                "ZXCVB2468P",
                LocalDate.of(2021, 9, 1),
                "Engineering Manager",
                "EMP104",
                "SWE",
                980000,
                120000,
                0,
                75000,
                40000);

        Employee[] employees = {softwareEngineer, hrExecutive, contractDeveloper, engineeringManager};

        System.out.println("Experiment 6 - Employee Payroll Management");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-16s %-22s %-10s %-14s%n", "Name", "Designation", "Emp ID", "CTC (Rs.)");
        System.out.println("---------------------------------------------------------------");

        for (Employee employee : employees) {
            System.out.printf(
                    "%-16s %-22s %-10s %-14.2f%n",
                    employee.getName(),
                    employee.getDesignation(),
                    employee.getEmpId(),
                    employee.calcCTC());
        }

        System.out.println("---------------------------------------------------------------");
        System.out.println();
        System.out.println("Inheritance Summary");
        System.out.println("1. Employee -> FullTimeEmployee");
        System.out.println("2. Employee -> ContractEmployee");
        System.out.println("3. Employee -> FullTimeEmployee -> Manager");
    }
}
