/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kmvcourses;

/**
 *
 * @author aarti
 */

import kmvcourses.admin.AdminLogin;
import kmvcourses.dao.TableManager;
import kmvcourses.dao.CourseViewer;


public class KMVCourses {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create the required tables (ADMINS, COURSES, STUDENTS, etc.)
        //TableManager.createAdminsNewTable();
        //TableManager.createStudentsTable();
       // TableManager.createCoursesTable();
        //TableManager.createCoursesTable();
       // TableManager.createStudentsTable();
       TableManager.createChaptersTable();
       TableManager.createLessonsTable();
       
       
        


        // Load course data from a text file (optional if you have one)
        // CourseFileLoader.loadCoursesFromFile("Courses.txt");

        // View the loaded courses (optional)
        // CourseViewer.viewCourses();

        
        new kmvcourses.admin.AdminLogin();
        
        
        
    }
    
}
