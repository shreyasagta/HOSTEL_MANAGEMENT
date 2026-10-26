
import java.sql.*;
import java.util.Scanner;

public class hostel_Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/hostel";
        String username = "root";
        String password = "chandni@0214";

        try (Connection con = DriverManager.getConnection(url, username, password)) {

            System.out.println("Database Connected successfully!");

            int choice=0;

            do {
                System.out.println("\n------ HOSTEL MANAGEMENT SYSTEM ------");
                System.out.println("1. Add Student");
                System.out.println("2. View Student");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter Your Choice: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Please enter a valid number!");
                    sc.nextLine();
                    continue;
                }

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1: {
                        System.out.print("Enter Student ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Student Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Course: ");
                        String course = sc.nextLine();

                        System.out.print("Enter Room Number: ");
                        int room_no = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Contact Number: ");
                        String contact = sc.nextLine();

                        String sql = "INSERT INTO student "
                                + "(id, name, course, room_no, contact) "
                                + "VALUES (?, ?, ?, ?, ?)";

                        try (PreparedStatement ps = con.prepareStatement(sql)) {
                            ps.setInt(1, id);
                            ps.setString(2, name);
                            ps.setString(3, course);
                            ps.setInt(4, room_no);
                            ps.setString(5, contact);

                            int rows = ps.executeUpdate();

                            if (rows > 0) {
                                System.out.println("Student Added Successfully!");
                            }
                        }
                        break;
                    }

                    case 2: {
                        String sql = "SELECT id, name, course, room_no, contact "
                                + "FROM student";

                        try (Statement st = con.createStatement();
                             ResultSet rs = st.executeQuery(sql)) {

                            System.out.println("\nID\tNAME\tCOURSE\tROOM NO\tCONTACT");

                            boolean found = false;

                            while (rs.next()) {
                                found = true;

                                System.out.println(
                                    rs.getInt("id") + "\t"
                                    + rs.getString("name") + "\t"
                                    + rs.getString("course") + "\t"
                                    + rs.getInt("room_no") + "\t"
                                    + rs.getString("contact")
                                );
                            }

                            if (!found) {
                                System.out.println("No students found.");
                            }
                        }
                        break;
                    }

                    case 3: {
                        System.out.print("Enter Student ID to Update: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter New Student Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter New Course: ");
                        String course = sc.nextLine();

                        System.out.print("Enter New Room Number: ");
                        int room_no = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter New Contact Number: ");
                        String contact = sc.nextLine();

                        String sql = "UPDATE student SET name = ?, "
                                + "course = ?, room_no = ?, contact = ? "
                                + "WHERE id = ?";

                        try (PreparedStatement ps = con.prepareStatement(sql)) {
                            ps.setString(1, name);
                            ps.setString(2, course);
                            ps.setInt(3, room_no);
                            ps.setString(4, contact);
                            ps.setInt(5, id);

                            int rows = ps.executeUpdate();

                            if (rows > 0) {
                                System.out.println("Student Updated Successfully!");
                            } else {
                                System.out.println("Student ID not found.");
                            }
                        }
                        break;
                    }

                    case 4: {
                        System.out.print("Enter Student ID to Delete: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        String sql = "DELETE FROM student WHERE id = ?";

                        try (PreparedStatement ps = con.prepareStatement(sql)) {
                            ps.setInt(1, id);

                            int rows = ps.executeUpdate();

                            if (rows > 0) {
                                System.out.println("Student Deleted Successfully!");
                            } else {
                                System.out.println("Student ID not found.");
                            }
                        }
                        break;
                    }

                    case 5:
                        System.out.println("Exiting Hostel Management System...");
                        break;

                    default:
                        System.out.println("Invalid choice! Please choose 1-5.");
                }

            } while (choice != 5);

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
