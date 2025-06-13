/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.model;

/**
 *
 * @author aarti
 */
public class Lecturer 
{
    private String lecturerId;
    private String name;
    private String email;
    
    // Construtors
    public Lecturer() {}
    
    public Lecturer(String lecturerId, String name, String email)
    {
        this.lecturerId = lecturerId;
        this.name = name;
        this.email = email;
    }
    
    // get set Methods
    public String getlecturerId()
    {
        return lecturerId;
    }
    
    public void setlecturerId(String lecturerId)
    {
        this.lecturerId = lecturerId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
}
