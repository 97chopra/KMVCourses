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
import java.util.List;


public interface AttendanceDAO 
{
    List<Attendance> getAttendanceByStudent(String studentId);
    boolean markAttendance(String studentId, String courseId, java.sql.Date date, String status);
    String getGradeForStudent(String studentId, String courseId);
    boolean setGradeForStudent(String studentId, String courseId, String grade);
}
