/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.model;

/**
 *
 * @author aarti
 */
public class PassFailSummary 
{
    private String courseId;
    private String courseName;
    private int passedCount;
    private int failedCount;

    // Constructor
    public PassFailSummary(String courseId, String courseName, int passedCount, int failedCount) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.passedCount = passedCount;
        this.failedCount = failedCount;
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(int passedCount) {
        this.passedCount = passedCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }
}
