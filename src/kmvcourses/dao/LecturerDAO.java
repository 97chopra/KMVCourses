/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

/**
 *
 * @author aarti
 */
import kmvcourses.model.Lecturer;
import java.util.List;
import kmvcourses.model.Courses;

public interface LecturerDAO {
    List<Lecturer> getAllLecturers();
    Lecturer getLecturerById(String lecturerId);
    boolean addLecturer(Lecturer lecturer);
    boolean updateLecturer(Lecturer lecturer);
    boolean deleteLecturer(String lecturerId);

    // Assignment-related
    boolean assignToCourse(String courseId, String lecturerId);
    boolean unassignFromCourse(String courseId, String lecturerId);
    List<Lecturer> getLecturersForCourse(String courseId);
    List<Courses> getAssignedCourses(String lecturerId);
}

