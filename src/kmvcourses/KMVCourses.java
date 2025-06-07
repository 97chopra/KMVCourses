/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kmvcourses;

/**
 *
 * @author aarti
 */

import kmvcourses.dao.TableManager;
import kmvcourses.dao.CourseFileLoader;
import kmvcourses.dao.CourseViewer;
public class KMVCourses {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
        TableManager.createCoursesTable();  // ? Calls the table creation
        
        CourseFileLoader.loadCoursesFromFile("Courses.txt");
        CourseViewer.viewCourses(); 
    }
    
}
