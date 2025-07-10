package New_Origin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    // JDBC URL, username, and password of Oracle database
    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "System";
    private static final String PASSWORD = "Test1234";

    // Establishes a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}









//import java.sql.*;
//
//public class Connect {
//    // JDBC URL, username, and password of Oracle database
//    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:XEPDB1";
//    private static final String USERNAME = "System";
//    private static final String PASSWORD = "Test1234";
//
//    // Establishes a connection to the database
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//    }
//
//    // Executes the provided SQL query and returns the result set
//    public static ResultSet executeQuery(String query) {
//        try (Connection con = getConnection();
//             Statement stmt = con.createStatement()) {
//            return stmt.executeQuery(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null; // Handle exceptions appropriately in your application
//        }
//    }
//
//    // Example usage in main method
//    public static void main(String[] args) {
//        try {
//            // Example query
//            String query = "SELECT * FROM Customer";
//
//            // Execute query and process result set
//            ResultSet rs = executeQuery(query);
//            if (rs != null) {
//                while (rs.next()) {
//                    int customerId = rs.getInt("customer_id");
//                    String meterNumber = rs.getString("meter_number");
//                    String username = rs.getString("username");
//                    String password = rs.getString("password");
//                    String name = rs.getString("name");
//                    System.out.println("Customer ID: " + customerId + ", Meter Number: " + meterNumber +
//                            ", Username: " + username + ", Password: " + password +
//                            ", Name: " + name);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
