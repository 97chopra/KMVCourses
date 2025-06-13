/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

/**
 *
 * @author aarti
 */

import kmvcourses.model.Student;
import java.util.List;

public interface StudentDAO 
{
    List<Student> getAllStudents();
    Student getStudentById(String studentId);
    boolean addStudent(Student student);
    boolean updateStudent(Student student);
    boolean deleteStudent(String studentId);
    List<Student> searchStudents(String keyword); // For functionality.
    
    
}
