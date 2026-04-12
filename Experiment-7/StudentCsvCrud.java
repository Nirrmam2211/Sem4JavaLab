import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class Student {
    String studentId;
    String name;
    String branch;
    int marks1;
    int marks2;
    int marks3;
    int marks4;
    int marks5;
    double percentage;

    Student(String studentId, String name, String branch, int marks1, int marks2, int marks3, int marks4, int marks5) {
        this.studentId = studentId;
        this.name = name;
        this.branch = branch;
        this.marks1 = marks1;
        this.marks2 = marks2;
        this.marks3 = marks3;
        this.marks4 = marks4;
        this.marks5 = marks5;
        this.percentage = 0.0;
    }

    String toCsvRow() {
        return String.format(
            Locale.US,
            "%s,%s,%s,%d,%d,%d,%d,%d,%.2f",
            studentId,
            name,
            branch,
            marks1,
            marks2,
            marks3,
            marks4,
            marks5,
            percentage
        );
    }

    static Student fromCsvRow(String row) {
        String[] parts = row.split(",");
        Student student = new Student(
            parts[0],
            parts[1],
            parts[2],
            Integer.parseInt(parts[3]),
            Integer.parseInt(parts[4]),
            Integer.parseInt(parts[5]),
            Integer.parseInt(parts[6]),
            Integer.parseInt(parts[7])
        );
        student.percentage = Double.parseDouble(parts[8]);
        return student;
    }
}

public class StudentCsvCrud {
    private static final String HEADER = "studentId,name,branch,marks1,marks2,marks3,marks4,marks5,percentage";

    public static void main(String[] args) {
        Path csvPath = Paths.get("Students.csv");

        System.out.println("Experiment 7 - CSV CRUD Operations in Java");
        System.out.println("------------------------------------------------------------");

        System.out.println("\n1. Creating Students.csv with two initial rows");
        createInitialCsv(csvPath);
        displayCsv(csvPath);

        List<Student> students = readStudents(csvPath);
        if (students.isEmpty()) {
            System.out.println("Program stopped because the student list could not be loaded.");
            return;
        }

        System.out.println("\n2. Adding 3 more students with marks4 and marks5 as zero");
        students.add(new Student("S103", "Kunal Patil", "ENTC", 74, 81, 79, 0, 0));
        students.add(new Student("S104", "Riya Nair", "AIML", 88, 90, 86, 0, 0));
        students.add(new Student("S105", "Soham Joshi", "Civil", 69, 73, 77, 0, 0));
        writeStudents(csvPath, students);
        displayCsv(csvPath);

        System.out.println("\n3. Updating marks4 and marks5 for all students");
        updateStudentMarks(students, "S101", 76, 88);
        updateStudentMarks(students, "S102", 84, 86);
        updateStudentMarks(students, "S103", 83, 80);
        updateStudentMarks(students, "S104", 92, 89);
        updateStudentMarks(students, "S105", 72, 75);
        writeStudents(csvPath, students);
        displayCsv(csvPath);

        System.out.println("\n4. Calculating percentage and updating the file");
        calculateAndUpdatePercentages(students, csvPath);
        displayCsv(csvPath);

        System.out.println("\n5. Deleting student row with ID S104");
        deleteStudentById(students, "S104", csvPath);
        displayCsv(csvPath);

        System.out.println("\n6. Showing exception condition in output");
        Path missingFile = Paths.get("MissingStudents.csv");
        readStudents(missingFile);

        System.out.println("\nProgram completed.");
    }

    private static void createInitialCsv(Path csvPath) {
        List<Student> initialStudents = new ArrayList<>();
        Student student1 = new Student("S101", "Aarav Mehta", "Computer", 78, 82, 80, 76, 88);
        Student student2 = new Student("S102", "Diya Shah", "IT", 85, 79, 91, 84, 86);

        student1.percentage = 80.80;
        student2.percentage = 85.00;

        initialStudents.add(student1);
        initialStudents.add(student2);
        writeStudents(csvPath, initialStudents);
    }

    private static void calculateAndUpdatePercentages(List<Student> students, Path csvPath) {
        for (Student student : students) {
            int total = student.marks1 + student.marks2 + student.marks3 + student.marks4 + student.marks5;
            student.percentage = total / 5.0;
        }
        writeStudents(csvPath, students);
    }

    private static void updateStudentMarks(List<Student> students, String studentId, int marks4, int marks5) {
        for (Student student : students) {
            if (student.studentId.equals(studentId)) {
                student.marks4 = marks4;
                student.marks5 = marks5;
                System.out.println("Updated marks for " + student.name + " (" + student.studentId + ")");
                return;
            }
        }
        System.out.println("Student not found for update: " + studentId);
    }

    private static void deleteStudentById(List<Student> students, String studentId, Path csvPath) {
        boolean removed = students.removeIf(student -> student.studentId.equals(studentId));
        if (removed) {
            System.out.println("Deleted row for student ID: " + studentId);
            writeStudents(csvPath, students);
        } else {
            System.out.println("No row found for deletion: " + studentId);
        }
    }

    private static List<Student> readStudents(Path csvPath) {
        List<Student> students = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(csvPath, StandardCharsets.UTF_8);
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (!line.isEmpty()) {
                    students.add(Student.fromCsvRow(line));
                }
            }
            System.out.println("Read operation successful for file: " + csvPath.getFileName());
        } catch (IOException e) {
            System.out.println("IOException caught while reading file: " + e.getMessage());
        }
        return students;
    }

    private static void writeStudents(Path csvPath, List<Student> students) {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (Student student : students) {
            lines.add(student.toCsvRow());
        }
        try {
            Files.write(
                csvPath,
                lines,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
            System.out.println("Write operation successful for file: " + csvPath.getFileName());
        } catch (IOException e) {
            System.out.println("IOException caught while writing file: " + e.getMessage());
        }
    }

    private static void displayCsv(Path csvPath) {
        try {
            List<String> lines = Files.readAllLines(csvPath, StandardCharsets.UTF_8);
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("IOException caught while displaying file: " + e.getMessage());
        }
    }
}
