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
       //TableManager.createChaptersTable();
      // TableManager.createLessonsTable();
       
       //TableManager.createLecturersTable();
       //TableManager.createCourseLecturersTable();
        


        // Load course data from a text file (optional if you have one)
        // CourseFileLoader.loadCoursesFromFile("Courses.txt");

        // View the loaded courses (optional)
        // CourseViewer.viewCourses();
       // TableManager.createAttendenceTable();
        //  TableManager.createGradesTable();
      //  TableManager.addLecturerPasswordColumn();
      //  TableManager.setLecturerPasswords();   
       // new kmvcourses.admin.AdminLogin();
      // TableManager.createEnrollmentsTable();
      
       // Set Nimbus Look and Feel before any GUI is shown
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
               if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
       } catch (Exception e) {
            // fallback: use default look and feel
        }
        
        // Launch your main interface 
      javax.swing.SwingUtilities.invokeLater(() -> {
        new kmvcourses.view.admin.MainInterface().setVisible(true);
    });
        
        
    }
    
}
