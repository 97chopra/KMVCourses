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
    
}
