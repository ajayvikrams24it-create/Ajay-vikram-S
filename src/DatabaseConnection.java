package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/police_records";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Ajay@2007"; // Change this!
    
    static {
        try {
            // For MySQL Connector/J 9.5.0 - the driver class has changed
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL Connector/J 9.5.0 Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: MySQL JDBC Driver not found!");
            System.out.println("You're using MySQL Connector/J 9.5.0");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to MySQL using Connector/J 9.5.0 successfully!");
            return connection;
        } catch (SQLException e) {
            System.out.println("MySQL connection failed!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("Using MySQL Connector/J 9.5.0");
            return null;
        }
    }
    
    // Test connection
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Connection test successful!");
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection test failed!");
        }
    }
}