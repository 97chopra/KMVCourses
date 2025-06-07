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
public class TableManager 
{
    private static final String DB_URL = "jdbc:derby:kmvcoursesDB;create=true";

    //  1. Create courses table
    public static void createCoursesTable() {
        String sql = "CREATE TABLE courses (" +
                "code VARCHAR(10) PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "major VARCHAR(50), " +
                "creditPoints INT, " +
                "level INT, " +
                "type VARCHAR(20))";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("✅ 'courses' table created.");
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("ℹ️ 'courses' table already exists.");
            } else {
                System.err.println("⚠️ Error creating 'courses' table: " + e.getMessage());
            }
        }
    }

    //  You can later add: createStudentsTable(), createEnrollmentTable(), etc.
}
