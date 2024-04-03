import java.sql.*;
public class Conn {
    static final String DB_URL = "jdbc:mysql://localhost:3306/EBS";
    static final String USER = "username";
    static final String PASS = "password";
    public static void main(String[] args) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM Customer";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                // Process the ResultSet
                // while (rs.next()) {
                //     int customerId = rs.getInt("customer_id");
                //     String meterNumber = rs.getString("meter_number");
                //     String username = rs.getString("username");
                //     String password = rs.getString("password");
                //     String name = rs.getString("name");
                //     System.out.println("Customer ID: " + customerId + ", Meter Number: " + meterNumber +
                //                        ", Username: " + username + ", Password: " + password +
                //                        ", Name: " + name);
                // }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }
}
