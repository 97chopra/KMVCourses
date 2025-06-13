/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.model;

/**
 *
 * @author aarti
 */
public class Student 
{
    private String studentId;
    private String firstName;
    private String lastName;
    private String password;
    
    // Contructor
    public Student(String studentId, String firstName, String lastName, String password)
    {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
    
    // get set method
    
    public String getStudentId()
    {
        return studentId;
    }
    
    public void setStudentId(String studenId)
    {
        this.studentId = studentId;
    }
    
    public String getFirstName()
    {
        return  firstName;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public void setLastName(String lastName)
    {
       this.lastName = lastName;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    @Override
public String toString() 
{
    return studentId + " - " + firstName + " " + lastName;
}
}
