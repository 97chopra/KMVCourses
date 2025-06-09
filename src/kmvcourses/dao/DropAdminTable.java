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
import java.sql.Statement;
public class DropAdminTable 
{
    public static void main(String[] args) {
        String url = "jdbc:derby:C:/Users/aarti/Desktop/CourseDB;create=true";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DROP TABLE ADMINS");
            System.out.println(" ADMINS table dropped successfully.");

        } catch (SQLException e) {
            System.out.println("️ Error dropping table: " + e.getMessage());
        }
    }
}
