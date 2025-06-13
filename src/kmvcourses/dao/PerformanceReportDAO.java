/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;


import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import kmvcourses.model.PassFailSummary;
import kmvcourses.model.StudentPerformance;

/**
 *
 * @author aarti
 */
public class PerformanceReportDAO 
{
     private static final String DB_URL = "jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true";

    public List<PassFailSummary> getPassFailSummary() {
        List<PassFailSummary> list = new ArrayList<>();
        String sql = "SELECT c.course_id, c.course_name, "
                   + "SUM(CASE WHEN g.grade >= 50 THEN 1 ELSE 0 END) AS passed, "
                   + "SUM(CASE WHEN g.grade < 50 THEN 1 ELSE 0 END) AS failed "
                   + "FROM GRADES g JOIN COURSES c ON g.course_id = c.course_id "
                   + "GROUP BY c.course_id, c.course_name";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new PassFailSummary(
                    rs.getString("course_id"),
                    rs.getString("course_name"),
                    rs.getInt("passed"),
                    rs.getInt("failed")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<StudentPerformance> getAtRiskStudents() {
    List<StudentPerformance> list = new ArrayList<>();
    String sql =
        "SELECT s.student_id, " +
        "       s.first_name || ' ' || s.last_name AS name, " +
        "       g.course_id, " +
        "       g.grade, " +
        "       CAST((100.0 * SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) / COUNT(*)) AS DECIMAL(10,2)) AS attendance_percent " +
        "FROM STUDENTS s " +
        "JOIN GRADES g ON s.student_id = g.student_id " +
        "JOIN ATTENDANCE a ON s.student_id = a.student_id AND g.course_id = a.course_id " +
        "GROUP BY s.student_id, s.first_name, s.last_name, g.course_id, g.grade " +
        // Repeat the full attendance calculation in HAVING (no alias use!)
        "HAVING g.grade < 50 OR (100.0 * SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) / COUNT(*)) < 75";

    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            list.add(new StudentPerformance(
                rs.getString("student_id"),
                rs.getString("name"),
                rs.getString("course_id"),
                rs.getDouble("grade"),
                rs.getDouble("attendance_percent")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
}
