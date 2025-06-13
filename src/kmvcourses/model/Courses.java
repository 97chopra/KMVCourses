/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.model;

/**
 *
 * @author aarti
 */


public class Courses {
    private String courseId;
    private String courseName;
    private String department;
    private int credits;
    private int level;
    private String type;
    
    public Courses(String courseId, String courseName, String department, 
                   int credits, int level, String type) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.department = department;
        this.credits = credits;
        this.level = level;
        this.type = type;
    }
    
    // Getters
    public String getCourseId() 
    { 
        return courseId; 
    }
    public String getCourseName() 
    { 
        return courseName; 
    }
    public String getDepartment() 
    { 
        return department; 
    }
    public int getCredits() 
    { 
        return credits; 
    }
    public int getLevel() 
    { 
        return level; 
    }
    public String getType() 
    { 
        return type; 
    }
    
    // Setters
    public void setCourseId(String courseId) 
    { 
        this.courseId = courseId; 
    }
    public void setCourseName(String courseName) 
    { 
        this.courseName = courseName; 
    }
    public void setDepartment(String department) 
    { 
        this.department = department; 
    }
    public void setCredits(int credits) 
    { 
        this.credits = credits; 
    }
    public void setLevel(int level) 
    { 
        this.level = level; 
    }
    public void setType(String type) 
    { 
        this.type = type; 
    }
    
    @Override
public String toString() 
{
    return courseId + " - " + courseName;
}
}
