/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

/**
 *
 * @author aarti
 */

import kmvcourses.model.Attendance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO
{
    private static final String DB_URL = "jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true";
    @Override
    public List<Attendance> getAttendanceByStudent(String studentId) 
    {
        List<Attendance> records = new ArrayList<>();
        String sql = "SELECT * FROM ATTENDANCE WHERE student_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    records.add(new Attendance(
                        rs.getInt("attendance_id"),
                        rs.getString("student_id"),
                        rs.getString("course_id"),
                        rs.getDate("date"),
                        rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
    
    @Override
    public boolean markAttendance(String studentId, String courseId, java.sql.Date date, String status) {
        String sql = "INSERT INTO ATTENDANCE (student_id, course_id, date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            stmt.setDate(3, date);
            stmt.setString(4, status); // "Present" or "Absent"
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
public String getGradeForStudent(String studentId, String courseId) {
    String sql = "SELECT grade FROM GRADES WHERE student_id = ? AND course_id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, studentId);
        stmt.setString(2, courseId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("grade");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "";
}

public boolean setGradeForStudent(String studentId, String courseId, String grade) {
    String updateSql = "UPDATE GRADES SET grade = ? WHERE student_id = ? AND course_id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement stmt = conn.prepareStatement(updateSql)) {
        
        // Convert String grade to double
        if (grade == null || grade.isEmpty()) {
            stmt.setNull(1, Types.DOUBLE); // Handle empty grades as NULL
        } else {
            double numericGrade = Double.parseDouble(grade);
            stmt.setDouble(1, numericGrade);
        }
        
        stmt.setString(2, studentId);
        stmt.setString(3, courseId);
        int rows = stmt.executeUpdate();
        if (rows > 0) return true;
        
        // Insert new grade if no existing record
        String insertSql = "INSERT INTO GRADES (student_id, course_id, grade) VALUES (?, ?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            if (grade == null || grade.isEmpty()) {
                insertStmt.setNull(3, Types.DOUBLE);
            } else {
                insertStmt.setDouble(3, Double.parseDouble(grade));
            }
            insertStmt.setString(1, studentId);
            insertStmt.setString(2, courseId);
            return insertStmt.executeUpdate() > 0;
        }
    } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
}
