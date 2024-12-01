import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagement02 {

    // Lớp Student để đại diện cho một sinh viên
    public static class Student {
        private String id;
        private String name;
        private double marks;

        public Student(String id, String name, double marks) {
            this.id = id;
            this.name = name;
            this.marks = marks;
        }

        // Getter và Setter
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getMarks() {
            return marks;
        }

        public void setMarks(double marks) {
            this.marks = marks;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Marks: " + marks;
        }
    }

    // Lớp Node cho cây nhị phân
    public static class TreeNode {
        Student student;
        TreeNode left, right;

        public TreeNode(Student student) {
            this.student = student;
            this.left = this.right = null;
        }
    }

    // Lớp BinaryTree để quản lý cây nhị phân
    public static class BinaryTree {
        private TreeNode root;

        public BinaryTree() {
            root = null;
        }

        // Thêm sinh viên vào cây
        public void insert(Student student) {
            root = insertRec(root, student);
        }

        private TreeNode insertRec(TreeNode root, Student student) {
            if (root == null) {
                root = new TreeNode(student);
                return root;
            }

            if (student.getId().compareTo(root.student.getId()) < 0) {
                root.left = insertRec(root.left, student);
            } else if (student.getId().compareTo(root.student.getId()) > 0) {
                root.right = insertRec(root.right, student);
            }

            return root;
        }

        // In-order traversal để lấy tất cả sinh viên theo thứ tự
        public List<Student> inorder() {
            List<Student> students = new ArrayList<>();
            inorderRec(root, students);
            return students;
        }

        private void inorderRec(TreeNode root, List<Student> students) {
            if (root != null) {
                inorderRec(root.left, students);
                students.add(root.student);
                inorderRec(root.right, students);
            }
        }

        // Tìm kiếm sinh viên theo ID
        public Student search(String id) {
            return searchRec(root, id);
        }

        private Student searchRec(TreeNode root, String id) {
            if (root == null || root.student.getId().equals(id)) {
                return root == null ? null : root.student;
            }

            if (id.compareTo(root.student.getId()) < 0) {
                return searchRec(root.left, id);
            } else {
                return searchRec(root.right, id);
            }
        }

        // Xóa sinh viên theo ID
        public void delete(String id) {
            root = deleteRec(root, id);
        }

        private TreeNode deleteRec(TreeNode root, String id) {
            if (root == null) {
                return root;
            }

            if (id.compareTo(root.student.getId()) < 0) {
                root.left = deleteRec(root.left, id);
            } else if (id.compareTo(root.student.getId()) > 0) {
                root.right = deleteRec(root.right, id);
            } else {
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                }

                root.student = minValue(root.right);

                root.right = deleteRec(root.right, root.student.getId());
            }

            return root;
        }

        private Student minValue(TreeNode root) {
            Student minVal = root.student;
            while (root.left != null) {
                minVal = root.left.student;
                root = root.left;
            }
            return minVal;
        }
    }

    // Lớp quản lý sinh viên
    private BinaryTree tree;

    public StudentManagement02() {
        tree = new BinaryTree();
    }

    // Thêm sinh viên vào hệ thống
    public void addStudent(String id, String name, double marks) {
        Student student = new Student(id, name, marks);
        tree.insert(student);
        System.out.println("Student added successfully.");
    }

    // Đọc dữ liệu sinh viên từ file CSV
    public void loadStudentsFromCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Bỏ qua dòng đầu tiên chứa tiêu đề (header)
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                String name = data[1].trim();
                double marks = Double.parseDouble(data[2].trim());
                addStudent(id, name, marks); // Thêm sinh viên vào hệ thống
            }
            System.out.println("Students loaded from CSV file.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Quick Sort để sắp xếp sinh viên theo ID
    public void quickSort(List<Student> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSort(list, low, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, high);
        }
    }

    // Phân vùng (partition) để sắp xếp các phần tử
    private int partition(List<Student> list, int low, int high) {
        Student pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).getId().compareTo(pivot.getId()) < 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    // Hoán đổi hai phần tử trong danh sách
    private void swap(List<Student> list, int i, int j) {
        Student temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    // Hiển thị tất cả sinh viên
    public void displayStudents() {
        List<Student> studentList = tree.inorder(); // Lấy danh sách sinh viên theo thứ tự
        quickSort(studentList, 0, studentList.size() - 1); // Sắp xếp trước khi hiển thị
        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    // Tìm kiếm sinh viên theo ID
    public void searchStudentById(String id) {
        Student student = tree.search(id);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("No student found with ID: " + id);
        }
    }

    // Xóa sinh viên theo ID
    public void deleteStudent(String id) {
        tree.delete(id);
        System.out.println("Student deleted successfully.");
    }

    // Sửa thông tin sinh viên theo ID
    public void editStudent(String id) {
        Student student = tree.search(id);
        if (student != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Editing student with ID: " + id);
            System.out.print("Enter new name (current: " + student.getName() + "): ");
            String newName = scanner.nextLine();
            System.out.print("Enter new marks (current: " + student.getMarks() + "): ");
            double newMarks = scanner.nextDouble();

            student.setName(newName);
            student.setMarks(newMarks);

            System.out.println("Student updated successfully!");
        } else {
            System.out.println("No student found with ID: " + id);
        }
    }

    public static void main(String[] args) {
        StudentManagement02 management = new StudentManagement02();
        Scanner scanner = new Scanner(System.in);

        // Load dữ liệu từ file CSV khi chương trình khởi động
        management.loadStudentsFromCSV("student.csv");

        int choice;
        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Display All Students");
            System.out.println("2. Add a Student");
            System.out.println("3. Edit a Student");
            System.out.println("4. Search a Student by ID");
            System.out.println("5. Delete a Student by ID");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Nhận newline

            switch (choice) {
                case 1:
                    management.displayStudents();
                    break;
                case 2:
                    System.out.print("Enter student ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student marks: ");
                    double marks = scanner.nextDouble();
                    management.addStudent(id, name, marks);
                    break;
                case 3:
                    System.out.print("Enter student ID to edit: ");
                    String editId = scanner.nextLine();
                    management.editStudent(editId);
                    break;
                case 4:
                    System.out.print("Enter student ID to search: ");
                    String searchId = scanner.nextLine();
                    management.searchStudentById(searchId);
                    break;
                case 5:
                    System.out.print("Enter student ID to delete: ");
                    String deleteId = scanner.nextLine();
                    management.deleteStudent(deleteId);
                    break;
                case 0:
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}

