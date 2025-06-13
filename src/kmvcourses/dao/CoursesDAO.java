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

import kmvcourses.model.Courses;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kmvcourses.model.Student;

public class CoursesDAO {
    private static final String DB_URL = "jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true";
    
    public List<Courses> getAllCourses() {
        List<Courses> courses = new ArrayList<>();
        String sql = "SELECT * FROM COURSES ORDER BY course_id";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Courses course = new Courses(
                    rs.getString("course_id"),
                    rs.getString("course_name"),
                    rs.getString("department"),
                    rs.getInt("credits"),
                    rs.getInt("level"),
                    rs.getString("type")
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return courses;
    }
    
    public boolean addCourse(Courses course) {
        String sql = "INSERT INTO COURSES (course_id, course_name, department, credits, level, type) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, course.getCourseId());
            stmt.setString(2, course.getCourseName());
            stmt.setString(3, course.getDepartment());
            stmt.setInt(4, course.getCredits());
            stmt.setInt(5, course.getLevel());
            stmt.setString(6, course.getType());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateCourse(Courses course) {
        String sql = "UPDATE COURSES SET course_name=?, department=?, credits=?, level=?, type=? WHERE course_id=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getDepartment());
            stmt.setInt(3, course.getCredits());
            stmt.setInt(4, course.getLevel());
            stmt.setString(5, course.getType());
            stmt.setString(6, course.getCourseId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteCourse(String courseId) {
        String sql = "DELETE FROM COURSES WHERE course_id=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, courseId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Returns students assigned to a course
public List<Student> getAssignedStudents(String courseId) {
    List<Student> students = new ArrayList<>();
    String sql = "SELECT s.student_id, s.first_name, s.last_name, s.password FROM STUDENTS s " +
                 "JOIN ENROLLMENTS e ON s.student_id = e.student_id WHERE e.course_id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, courseId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            students.add(new Student(
                rs.getString("student_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("password")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return students;
}
   // Lecturer-only method: Assign a student to a course
    public boolean assignStudentToCourse(String studentId, String courseId) {
        String sql = "INSERT INTO ENROLLMENTS (student_id, course_id) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Optionally handle duplicate enrollments or constraint violations here
            e.printStackTrace();
            return false;
        }
    }
    
    // Returns students NOT assigned to a course
public List<Student> getUnassignedStudents(String courseId) {
    List<Student> students = new ArrayList<>();
    String sql = "SELECT s.student_id, s.first_name, s.last_name, s.password FROM STUDENTS s " +
                 "WHERE s.student_id NOT IN (SELECT e.student_id FROM ENROLLMENTS e WHERE e.course_id = ?)";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, courseId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            students.add(new Student(
                rs.getString("student_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("password")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return students;
}

    // Optional: Get courses assigned to a lecturer
    public List<Courses> getCoursesByLecturer(String lecturerId) {
        List<Courses> courses = new ArrayList<>();
        String sql = "SELECT c.* FROM COURSES c JOIN COURSE_LECTURERS cl ON c.course_id = cl.course_id WHERE cl.lecturer_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecturerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Courses course = new Courses(
                        rs.getString("course_id"),
                        rs.getString("course_name"),
                        rs.getString("department"),
                        rs.getInt("credits"),
                        rs.getInt("level"),
                        rs.getString("type")
                    );
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    } 
    
    public boolean unassignStudentFromCourse(String studentId, String courseId) {
    String sql = "DELETE FROM ENROLLMENTS WHERE student_id = ? AND course_id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, studentId);
        stmt.setString(2, courseId);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}
    
    
}
