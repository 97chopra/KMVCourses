/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.model;

/**
 *
 * @author aarti
 */
public class Courses 
{
    private String courseCode;
    private String courseName;
    private String major;
    private int creditPoints;
    private int level;
    private String type;

    // Constructor
    public Courses(String courseCode, String courseName, String major, int creditPoints, int level, String type) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.major = major;
        this.creditPoints = creditPoints;
        this.level = level;
        this.type = type;
    }

    //  Getters and Setters
    public String getCode() {
        return courseCode;
    }

    public void setCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return courseName;
    }

    public void setName(String courseName) {
        this.courseName = courseName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public void setCreditPoints(int creditPoints) {
        this.creditPoints = creditPoints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //  For easy printing
    @Override
    public String toString() {
        return courseCode + " - " + courseName + " (" + major + ", Level " + level + ", " + type + ")";
    }
}