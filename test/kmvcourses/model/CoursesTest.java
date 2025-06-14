/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package kmvcourses.model;

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
public class CoursesTest {
    
    public CoursesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetCourseIdAndName() {
        Courses c = new Courses("C101", "Software Engineering", "Computer Science", 15, 3, "Core");
        c.setCourseId("C102");
        c.setCourseName("Advanced Software Engineering");
        assertEquals("C102", c.getCourseId());
        assertEquals("Advanced Software Engineering", c.getCourseName());
    }

    @Test
    public void testSetAndGetDepartmentAndType() {
        Courses c = new Courses("C101", "Software Engineering", "Computer Science", 15, 3, "Core");
        c.setDepartment("Information Technology");
        c.setType("Elective");
        assertEquals("Information Technology", c.getDepartment());
        assertEquals("Elective", c.getType());
    }

    @Test
    public void testSetAndGetCreditsAndLevel() {
        Courses c = new Courses("C101", "Software Engineering", "Computer Science", 15, 3, "Core");
        c.setCredits(20);
        c.setLevel(4);
        assertEquals(20, c.getCredits());
        assertEquals(4, c.getLevel());
    }

    @Test
    public void testToString() {
        Courses c = new Courses("C101", "Software Engineering", "Computer Science", 15, 3, "Core");
        // Adjust expected value to match your Courses.toString() implementation
        String expected = "C101 - Software Engineering";
        assertEquals(expected, c.toString());
    }

    @Test
    public void testFullObject() {
        Courses c = new Courses("C102", "Databases", "IT", 10, 2, "Elective");
        assertEquals("C102", c.getCourseId());
        assertEquals("Databases", c.getCourseName());
        assertEquals("IT", c.getDepartment());
        assertEquals("Elective", c.getType());
        assertEquals(10, c.getCredits());
        assertEquals(2, c.getLevel());
    }
}
