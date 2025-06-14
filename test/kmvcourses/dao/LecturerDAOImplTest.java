/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package kmvcourses.dao;

import java.util.List;
import kmvcourses.model.Courses;
import kmvcourses.model.Lecturer;
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
public class LecturerDAOImplTest {
    
     private LecturerDAO dao;

    @Before
    public void setUp() {
        dao = new LecturerDAOImpl();
    }

    @Test
    public void testGetLecturerById() {
        Lecturer lecturer = dao.getLecturerById("KMV001");
        assertNotNull("Should find lecturer KMV001", lecturer);
        assertEquals("KMV001", lecturer.getlecturerId());
        // Check name if you know it
        // assertEquals("John Smith", lecturer.getName());
    }

    @Test
    public void testGetAllLecturers() {
        List<Lecturer> lecturers = dao.getAllLecturers();
        assertNotNull("Lecturer list should not be null", lecturers);
        assertTrue("Should have at least 1 lecturer", lecturers.size() >= 1);
    }

    @Test
    public void testUpdateLecturer() {
        Lecturer lecturer = dao.getLecturerById("KMV001");
        assertNotNull("Should find lecturer KMV001", lecturer);

        String oldName = lecturer.getName();
        lecturer.setName("TestName");
        boolean updated = dao.updateLecturer(lecturer);
        assertTrue("Lecturer should be updated successfully", updated);

        Lecturer updatedLecturer = dao.getLecturerById("KMV001");
        assertEquals("TestName", updatedLecturer.getName());

        // Restore original name
        lecturer.setName(oldName);
        dao.updateLecturer(lecturer);
    }

    @Test
    public void testGetLecturerByInvalidId() {
        Lecturer lecturer = dao.getLecturerById("INVALID_ID");
        assertNull("Should not find a lecturer with invalid ID", lecturer);
    }

    @Test
    public void testLecturerDataIntegrity() {
        List<Lecturer> lecturers = dao.getAllLecturers();
        for (Lecturer l : lecturers) {
            assertNotNull("Lecturer name should not be null", l.getName());
            assertNotNull("Lecturer email should not be null", l.getEmail());
        }
    }
}
