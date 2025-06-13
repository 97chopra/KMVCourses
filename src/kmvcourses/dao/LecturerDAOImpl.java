/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import kmvcourses.model.Courses;
import kmvcourses.model.Lecturer;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author aarti
 */
public class LecturerDAOImpl implements LecturerDAO
{
    private static final String DB_URL = "jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true";
    private static final Logger LOGGER = Logger.getLogger(LecturerDAOImpl.class.getName());

    public List<Lecturer> getAllLecturers() {
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT * FROM LECTURERS";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lecturers.add(new Lecturer(
                    rs.getString("lecturer_id"),
                    rs.getString("name"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lecturers;
    }

    public Lecturer getLecturerById(String lecturerId) {
        String sql = "SELECT * FROM LECTURERS WHERE lecturer_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecturerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Lecturer(
                        rs.getString("lecturer_id"),
                        rs.getString("name"),
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addLecturer(Lecturer lecturer) {
        String sql = "INSERT INTO LECTURERS (lecturer_id, name, email) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecturer.getlecturerId());
            stmt.setString(2, lecturer.getName());
            stmt.setString(3, lecturer.getEmail());
            int updated = stmt.executeUpdate();
            if(updated > 0)
            {
               LOGGER.log(Level.INFO, "Lecturer added successfully: {0}", lecturer.getlecturerId());
            return true; 
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding lecturer: " + lecturer.getlecturerId(), e);
            return false;
        }
        
        return false;
    }

    public boolean updateLecturer(Lecturer lecturer) {
        String sql = "UPDATE LECTURERS SET name = ?, email = ? WHERE lecturer_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecturer.getName());
            stmt.setString(2, lecturer.getEmail());
            stmt.setString(3, lecturer.getlecturerId());
            int updated = stmt.executeUpdate();
            if(updated > 0)
            {
              LOGGER.log(Level.INFO, "Lecturer updated successfully: {0}", lecturer.getlecturerId());
            return true;  
            }
        } catch (SQLException e) {
           LOGGER.log(Level.SEVERE, "Error updating lecturer: " + lecturer.getlecturerId(), e); 
            return false;
        }
        return false;
    }

    public boolean deleteLecturer(String lecturerId) {
        String sql = "DELETE FROM LECTURERS WHERE lecturer_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecturerId);
            int updated = stmt.executeUpdate();
            if(updated > 0)
            {
              LOGGER.log(Level.INFO, "Lecturer deleted successfully: {0}", lecturerId);
            return true;  
            }
        } catch (SQLException e) {
             LOGGER.log(Level.SEVERE, "Error deleting lecturer: " + lecturerId, e);
            return false;
        }
        
        return false;
    }

    public boolean assignToCourse(String courseId, String lecturerId) {
        String sql = "INSERT INTO COURSE_LECTURERS (course_id, lecturer_id) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            stmt.setString(2, lecturerId);
            int updated = stmt.executeUpdate();
            if(updated > 0)
            {
                LOGGER.log(Level.INFO, "Lecturer {0} assigned to course {1}", new Object[]{lecturerId, courseId});
            return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error assigning lecturer " + lecturerId + " to course " + courseId, e);
            return false;
        }
        
        return false;
    }

    public boolean unassignFromCourse(String courseId, String lecturerId) {
        String sql = "DELETE FROM COURSE_LECTURERS WHERE course_id = ? AND lecturer_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            stmt.setString(2, lecturerId);
            int updated = stmt.executeUpdate();
            if(updated > 0)
            {
              
        LOGGER.log(Level.INFO, "Lecturer {0} unassigned from course {1}", new Object[]{lecturerId, courseId});
            }
        } catch (SQLException e) {
           LOGGER.log(Level.SEVERE, "Error unassigning lecturer " + lecturerId + " from course " + courseId, e);
            return false;
        }
        
        return false;
    }
    
    public List<Courses> getAssignedCourses(String lecturerId)
    {
    List<Courses> assignedCourses = new ArrayList<>();
    String sql = "SELECT c.* FROM COURSES c " +
                 "JOIN COURSE_LECTURERS cl ON c.course_id = cl.course_id " +
                 "WHERE cl.lecturer_id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, lecturerId);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                assignedCourses.add(new Courses(
                    rs.getString("course_id"),
                    rs.getString("course_name"),
                    rs.getString("department"),
                    rs.getInt("credits"),
                    rs.getInt("level"),
                    rs.getString("type")
                ));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return assignedCourses;
    }

    public List<Lecturer> getLecturersForCourse(String courseId) {
        
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT l.lecturer_id, l.name, l.email FROM LECTURERS l " +
                     "JOIN COURSE_LECTURERS cl ON l.lecturer_id = cl.lecturer_id " +
                     "WHERE cl.course_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lecturers.add(new Lecturer(
                        rs.getString("lecturer_id"),
                        rs.getString("name"),
                        rs.getString("email")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lecturers;
    }
    
    
}
