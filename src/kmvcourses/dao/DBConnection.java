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
    private static Connection conn;

    static {
        establishConnection(); // Called once when class is loaded
    }

    //  Returns the shared connection
    public static Connection getConnection() {
        return conn;
    }

    //  Establish Derby Embedded connection
    private static void establishConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String url = "jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true";
            conn = DriverManager.getConnection(url);
            System.out.println(" Embedded DB connection established.");
        } catch (ClassNotFoundException e) {
            System.err.println(" JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println(" SQL Error: " + e.getMessage());
        }
    }

    
}
