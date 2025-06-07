/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

/**
 *
 * @author aarti
 */
import kmvcourses.model.Courses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
public class CoursesDAO 
{
    private static final String DB_URL = "jdbc:derby:kmvcoursesDB;create=true";
    //  Insert a course into the 'courses' table
    public static void insertCourse(Courses course) {
        String sql = "INSERT INTO courses (code, name, major, creditPoints, level, type) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCode());
            pstmt.setString(2, course.getName());
            pstmt.setString(3, course.getMajor());
            pstmt.setInt(4, course.getCreditPoints());
            pstmt.setInt(5, course.getLevel());
            pstmt.setString(6, course.getType());

            pstmt.executeUpdate();
            System.out.println("✅ Inserted: " + course.getCode());
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                System.out.println("⚠️ Duplicate course skipped: " + course.getCode());
            } else {
                System.err.println("⚠️ Error inserting course: " + e.getMessage());
            }
        }
    }
}
