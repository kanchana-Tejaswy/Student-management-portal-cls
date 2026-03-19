import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StudentMain implements Operations {
    private Scanner scanner = new Scanner(System.in);

    public StudentMain() {
        // Automatically initialize database on startup
        DatabaseHelper.initializeDatabase();
    }

    @Override
    public void register() {
        System.out.println("\n--- Student Registration ---");
        System.out.print("Enter ID (e.g. STU-100): ");
        String id = scanner.nextLine().trim();

        // Regex validation for ID
        if (!Pattern.matches("^[A-Z0-9-]+$", id) || id.isEmpty()) {
            System.out.println("❌ Error: Invalid ID format. Use alphanumeric chars and hyphens only.");
            return;
        }

        if (isStudentExists(id)) {
            System.out.println("❌ Error: Student with ID " + id + " already exists in Database!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Branch: ");
        String branch = scanner.nextLine().trim();
        System.out.print("Enter Password (min 4 chars): ");
        String password = scanner.nextLine().trim();

        if (name.isEmpty() || branch.isEmpty() || password.length() < 4) {
            System.out.println("❌ Error: All fields are required and password must be at least 4 chars.");
            return;
        }

        String sql = "INSERT INTO students(id, name, branch, password) VALUES(?,?,?,?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, branch);
            pstmt.setString(4, password);
            pstmt.executeUpdate();
            System.out.println("✅ Student registered securely to SQLite Database.");
        } catch (SQLException e) {
            System.out.println("❌ Error inserting to database: " + e.getMessage());
        }
    }

    @Override
    public void logIn() {
        System.out.println("\n--- Student Login ---");
        System.out.print("Enter ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        String sql = "SELECT * FROM students WHERE id = ? AND password = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n✅ Login Successful!");
                System.out.println("Welcome, " + rs.getString("name"));
                
                Student student = new Student(
                    rs.getString("id"), 
                    rs.getString("name"), 
                    rs.getString("branch"), 
                    rs.getString("password")
                );
                student.getDetails();
            } else {
                System.out.println("❌ Invalid ID or Password.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    @Override
    public void viewAllStudents() {
        System.out.println("\n--- All Students (Database View) ---");
        System.out.printf("%-15s %-20s %-15s\n", "ID", "Name", "Branch");
        System.out.println("-----------------------------------------------------");
        
        String sql = "SELECT id, name, branch FROM students ORDER BY name ASC";
        
        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            int count = 0;
            while (rs.next()) {
                System.out.printf("%-15s %-20s %-15s\n", rs.getString("id"), rs.getString("name"), rs.getString("branch"));
                count++;
            }
            if (count == 0) {
                System.out.println("No students found in DB.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    @Override
    public void searchStudent() {
        System.out.println("\n--- Search Student ---");
        System.out.print("Enter ID to search: ");
        String searchId = scanner.nextLine().trim();

        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, searchId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n✅ Student Found in Database:");
                Student student = new Student(
                    rs.getString("id"), 
                    rs.getString("name"), 
                    rs.getString("branch"), 
                    rs.getString("password")
                );
                student.getDetails();
            } else {
                System.out.println("❌ Student with ID " + searchId + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    @Override
    public void updateStudent() {
        System.out.println("\n--- Update Student ---");
        System.out.print("Enter ID of the student to update: ");
        String updateId = scanner.nextLine().trim();

        if (!isStudentExists(updateId)) {
            System.out.println("❌ Student with ID " + updateId + " not found.");
            return;
        }

        System.out.print("Enter New Branch (leave empty to keep current): ");
        String newBranch = scanner.nextLine().trim();
        
        System.out.print("Enter New Password (leave empty to keep current): ");
        String newPassword = scanner.nextLine().trim();

        StringBuilder sqlBuilder = new StringBuilder("UPDATE students SET ");
        boolean needComma = false;

        if (!newBranch.isEmpty()) {
            sqlBuilder.append("branch = ?");
            needComma = true;
        }
        if (!newPassword.isEmpty()) {
            if (needComma) sqlBuilder.append(", ");
            sqlBuilder.append("password = ?");
        }

        if (newBranch.isEmpty() && newPassword.isEmpty()) {
            System.out.println("No fields to update.");
            return;
        }

        sqlBuilder.append(" WHERE id = ?");

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {
            
            int paramIndex = 1;
            if (!newBranch.isEmpty()) {
                pstmt.setString(paramIndex++, newBranch);
            }
            if (!newPassword.isEmpty()) {
                pstmt.setString(paramIndex++, newPassword);
            }
            pstmt.setString(paramIndex, updateId);
            
            pstmt.executeUpdate();
            System.out.println("✅ Student details updated successfully.");
            
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    @Override
    public void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        System.out.print("Enter ID of the student to delete: ");
        String deleteId = scanner.nextLine().trim();

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, deleteId);
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✅ Student deleted successfully from Database.");
            } else {
                System.out.println("❌ Student with ID " + deleteId + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    @Override
    public void viewStatistics() {
        System.out.println("\n--- Student Statistics ---");
        String sql = "SELECT branch, COUNT(id) as total FROM students GROUP BY branch";
        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("Branch: %-15s | Total Students: %d\n", 
                                  rs.getString("branch"), rs.getInt("total"));
            }
            if (!hasData) {
                System.out.println("No students registered yet.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    @Override
    public void exit() {
        System.out.println("Exiting System. Goodbye!");
        System.exit(0);
    }

    private boolean isStudentExists(String id) {
        String sql = "SELECT id FROM students WHERE id = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        StudentMain app = new StudentMain();
        Scanner scanner = app.scanner;
        
        System.out.println("===========================================");
        System.out.println(" ADVANCED Student Management System (JDBC) ");
        System.out.println("===========================================");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Register Student");
            System.out.println("2. Student Login");
            System.out.println("3. View All Students");
            System.out.println("4. Search Student");
            System.out.println("5. Update Student");
            System.out.println("6. Delete Student");
            System.out.println("7. View Statistics (DB Stats)");
            System.out.println("8. 🌐 Start Backend API Server");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            
            String choiceStr = scanner.nextLine().trim();
            int choice = -1;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                 System.out.println("❌ Invalid input. Please enter a valid number.");
                 continue;
            }

            switch (choice) {
                case 1: app.register(); break;
                case 2: app.logIn(); break;
                case 3: app.viewAllStudents(); break;
                case 4: app.searchStudent(); break;
                case 5: app.updateStudent(); break;
                case 6: app.deleteStudent(); break;
                case 7: app.viewStatistics(); break;
                case 8: 
                    System.out.println("Starting Backend REST API...");
                    ApiServer.startServer(); 
                    break;
                case 9: app.exit(); break;
                default:
                    System.out.println("❌ Invalid choice. Please choose a valid option (1-9).");
            }
        }
    }
}
