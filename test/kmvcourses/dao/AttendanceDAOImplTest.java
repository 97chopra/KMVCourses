/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package kmvcourses.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import kmvcourses.model.Attendance;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aarti
 */
public class AttendanceDAOImplTest {
    
   private AttendanceDAO dao;
    private List<String> testAttendanceIds;

    @Before
    public void setUp() {
        dao = new AttendanceDAOImpl();
        testAttendanceIds = new ArrayList<>();
    }

    @After
    public void tearDown() {
        
    }

    @Test
    public void testMarkAttendance() {
        // Use real student and course IDs from your database
        String studentId = "S101";
        String courseId = "ITC101";
        Date date = Date.valueOf("2025-06-14");
        String status = "Present";
        boolean result = dao.markAttendance(studentId, courseId, date, status);
        assertTrue("Attendance should be marked successfully", result);
    }

    @Test
    public void testGetAttendanceByStudent() {
        String studentId = "S101";
        List<Attendance> records = dao.getAttendanceByStudent(studentId);
        assertNotNull("Attendance records list should not be null", records);
        // Optionally: assertTrue(records.size() > 0);
    }
}
