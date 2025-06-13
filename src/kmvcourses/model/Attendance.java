/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.model;

/**
 *
 * @author aarti
 */

import java.sql.Date;


public class Attendance 
{
    private int attendanceId;
    private String studentId;
    private String courseId;
    private Date date;
    private String status;

    public Attendance(int attendanceId, String studentId, String courseId, Date date, String status) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.date = date;
        this.status = status;
    }

    // Getters
    public int getAttendanceId() 
    { 
        return attendanceId; 
    }
    public String getStudentId() 
    { 
        return studentId; 
    }
    public String getCourseId() 
    { 
        return courseId; 
    }
    public Date getDate() 
    { 
        return date; 
    }
    public String getStatus() 
    { 
        return status; 
    }
}
