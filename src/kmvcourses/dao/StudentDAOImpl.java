/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

import java.util.ArrayList;
import java.util.List;
import kmvcourses.model.Student;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aarti
 */

public class StudentDAOImpl implements StudentDAO 
{
    private static final String DB_URL = "jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true";
    private static final Logger LOGGER = Logger.getLogger(StudentDAOImpl.class.getName());
    
    @Override
    public List<Student> getAllStudents() 
    {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM STUDENTS"; 
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) 
        {
            while (rs.next()) 
            {
                students.add(new Student(
                    rs.getString("student_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("password")
                ));
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Student getStudentById(String studentId) 
    {
        String sql = "SELECT * FROM STUDENTS WHERE student_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                    return new Student(
                        rs.getString("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addStudent(Student student) 
    {
        String sql = "INSERT INTO STUDENTS (student_id, first_name, last_name, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setString(1, student.getStudentId());
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            stmt.setString(4, student.getPassword());
            int updated = stmt.executeUpdate();
            if(updated > 0)
            {
                LOGGER.log(Level.INFO, "Student add successfully: {0}", student.getStudentId());
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding student: " + student.getStudentId(), e);
            return false;
        }
        return false;
    }

    @Override
    public boolean updateStudent(Student student) 
    {
        String sql = "UPDATE STUDENTS SET first_name = ?, last_name = ?, password = ? WHERE student_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getPassword());
            stmt.setString(4, student.getStudentId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteStudent(String studentId) 
    {
        String sql = "DELETE FROM STUDENTS WHERE student_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            int updated = stmt.executeUpdate();
            if(updated > 0)
            {
               LOGGER.log(Level.INFO, "Student deleted successfully: {0}", studentId);
                return true; 
            }
        } catch (SQLException e) {
           LOGGER.log(Level.SEVERE, "Error deleting student: " + studentId, e); 
            return false;
        }
        
        return false;
    }

    @Override
    public List<Student> searchStudents(String keyword) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM STUDENTS WHERE student_id LIKE ? OR first_name LIKE ? OR last_name LIKE ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("password")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
   
}


