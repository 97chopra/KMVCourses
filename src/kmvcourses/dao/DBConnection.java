/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

/**
 *
 * @author aarti
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection 
{
    private Connection conn;

    public DBConnection() {
        establishConnection();
    }

    public Connection getConnection() {
        return conn;
    }

    private void establishConnection() {
        try {
            // Embedded driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            // Path to your embedded DB (create=true will create if not exists)
            String url = "jdbc:derby:C:/Users/aarti/Desktop/CourseDB;create=true";

            conn = DriverManager.getConnection(url);
            System.out.println("✅ Embedded DB connection established.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ SQL Error: " + e.getMessage());
        }
    }
}
