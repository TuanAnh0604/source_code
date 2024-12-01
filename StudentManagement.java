import java.io.*;
import java.util.*;

class Student {
    private String id;
    private String name;
    private double marks;

    public Student(String id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getRank() {
        if (marks < 5.0) {
            return "Fail";
        } else if (marks < 6.5) {
            return "Medium";
        } else if (marks < 7.5) {
            return "Good";
        } else if (marks < 9.0) {
            return "Very Good";
        } else {
            return "Excellent";
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Marks: %.1f, Rank: %s", id, name, marks, getRank());
    }
}

public class StudentManagement {
    private List<Student> students = new ArrayList<>();

    // Add student
    public void addStudent(String id, String name, double marks) {
        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully.");
    }
    // // Thêm sinh viên với kiểm tra trùng ID
    // public void addStudent(String id, String name, double marks) {
    // // Kiểm tra xem ID đã tồn tại hay chưa
    // for (Student student : students) {
    // if (student.getId().equalsIgnoreCase(id)) {
    // System.out.println("Error: Student ID " + id + " already exists. Cannot
    // add.");
    // return; // Dừng lại nếu trùng ID
    // }
    // }
    // // Nếu không trùng ID, thêm sinh viên mới
    // students.add(new Student(id, name, marks));
    // System.out.println("Student added successfully: " + id + ", " + name + ", " +
    // marks);

    // Edit information student
    public void editStudent(String id, String newName, double newMarks) {
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                student.setName(newName);
                student.setMarks(newMarks);
                System.out.println("Student updated successfully.");
                return;
            }
        }
        System.out.println("Student not found with ID: " + id);
    }

    // Delete a Student
    public void deleteStudent(String id) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equalsIgnoreCase(id)) {
                iterator.remove();
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student not found with ID: " + id);
    }

    // Hiển thị danh sách sinh viên
    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // sortStudentsByMarks (Bubble Sort)
    public void sortStudentsByMarks() {
        for (int i = 0; i < students.size() - 1; i++) {
            for (int j = 0; j < students.size() - 1 - i; j++) {
                if (students.get(j).getMarks() < students.get(j + 1).getMarks()) {
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
        System.out.println("Students sorted by marks in descending order.");
    }

    // Search student byID
    public void searchStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                System.out.println(student);
                return;
            }
        }
        System.out.println("No student found with ID: " + id);
    }

    // Đọc dữ liệu từ tệp CSV
    public void loadFromCsvFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    try {
                        double marks = Double.parseDouble(parts[2].trim());
                        addStudent(id, name, marks);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid marks value: " + parts[2]);
                    }
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
            System.out.println("Data loaded successfully from file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentManagement management = new StudentManagement();
        Scanner scanner = new Scanner(System.in);
    
        // Đọc dữ liệu từ tệp CSV
        System.out.print("Enter the CSV file name to load student data: ");
        String fileName = scanner.nextLine();
        management.loadFromCsvFile(fileName);
    
        // Hiển thị menu
        int choice = -1;
        do {
            try {
                System.out.println("\n--- Student Management System ---");
                System.out.println("1. Display All Students");
                System.out.println("2. Add a Student");
                System.out.println("3. Edit a Student");
                System.out.println("4. Delete a Student");
                System.out.println("5. Sort Students by Marks");
                System.out.println("6. Search Student by ID");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Tiêu thụ newline sau khi nhập số nguyên
    
                switch (choice) {
                    case 1:
                        management.displayStudents();
                        break;
                    case 2:
                        System.out.print("Enter Student ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter Student Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Student Marks: ");
                        double marks = scanner.nextDouble();
                        management.addStudent(id, name, marks);
                        break;
                    case 3:
                        System.out.print("Enter Student ID to edit: ");
                        id = scanner.nextLine();
                        System.out.print("Enter new Student Name: ");
                        name = scanner.nextLine();
                        System.out.print("Enter new Student Marks: ");
                        marks = scanner.nextDouble();
                        management.editStudent(id, name, marks);
                        break;
                    case 4:
                        System.out.print("Enter Student ID to delete: ");
                        id = scanner.nextLine();
                        management.deleteStudent(id);
                        break;
                    case 5:
                        management.sortStudentsByMarks();
                        management.displayStudents();
                        break;
                    case 6:
                        System.out.print("Enter Student ID to search: ");
                        id = scanner.nextLine();
                        management.searchStudentById(id);
                        break;
                    case 0:
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Xóa dữ liệu không hợp lệ khỏi bộ đệm
                choice = -1; // Đặt lại giá trị để tiếp tục vòng lặp
            }
        } while (choice != 0);
    
        scanner.close();
    }
}
