/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package kmvcourses.dao;

import java.util.List;
import kmvcourses.model.Student;
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
public class StudentDAOTest {
    
    private StudentDAO dao;

    @Before
    public void setUp() {
        dao = new StudentDAOImpl();
    }

    @Test
    public void testGetStudentById() {
    Student student = dao.getStudentById("S101");
    assertNotNull("Should find student S101", student);
    assertEquals("S101", student.getStudentId());
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = dao.getAllStudents();
        assertNotNull("Students list should not be null", students);
        assertTrue("Should have at least 27 students", students.size() >= 27);
    }

    @Test
    public void testUpdateStudent() {
    // Use a real student ID from your database
    Student student = dao.getStudentById("S102");
    assertNotNull("Should find student S102", student);

    // Change the last name and update
    String oldLastName = student.getLastName();
    student.setLastName("TestLastName");
    boolean updated = dao.updateStudent(student);
    assertTrue("Student should be updated successfully", updated);

    // Verify the update
    Student updatedStudent = dao.getStudentById("S102");
    assertEquals("TestLastName", updatedStudent.getLastName());

    // Restore original last name for cleanliness
    student.setLastName(oldLastName);
    dao.updateStudent(student);
}

    @Test
    public void testGetStudentByInvalidId() {
        Student student = dao.getStudentById("nonexistentid");
        assertNull("Should not find a student with invalid ID", student);
    }

    @Test
    public void testStudentDataIntegrity() {
        // Check that all students have non-null first and last names
        List<Student> students = dao.getAllStudents();
        for (Student s : students) {
            assertNotNull("Student first name should not be null", s.getFirstName());
            assertNotNull("Student last name should not be null", s.getLastName());
        }
    }
    
    
}
