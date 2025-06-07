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
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


public class CourseViewer 
{
    public static void viewCourses() {
        DBConnection db = new DBConnection();
        Connection conn = db.getConnection();
        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM courses")) {

            System.out.println(" List of Courses:");
            System.out.printf("%-10s %-30s %-15s %-5s %-5s %-10s%n",
                              "Code", "Name", "Major", "CP", "Level", "Type");

            while (rs.next()) {
                String code = rs.getString("code");
                String name = rs.getString("name");
                String major = rs.getString("major");
                int cp = rs.getInt("creditPoints");
                int level = rs.getInt("level");
                String type = rs.getString("type");

                System.out.printf("%-10s %-30s %-15s %-5d %-5d %-10s%n",
                                  code, name, major, cp, level, type);
            }

        } catch (SQLException e) {
            System.err.println(" Error retrieving course data: " + e.getMessage());
        }
    }
}
