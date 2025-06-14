/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package kmvcourses.view.admin;

import java.util.List;
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
public class AssignStudentsPanelTest {
    
    private AssignStudentsPanel panel;

    @Before
    public void setUp() {
        // Create panel with null parent (since we're not testing GUI interactions)
        panel = new AssignStudentsPanel(null, "KMV001");
    }

    @Test
    public void testPanelCreation() {
        assertNotNull("Panel should be created successfully", panel);
    }

    @Test
    public void testPanelIsJPanel() {
        assertTrue("Panel should be instance of JPanel", panel instanceof javax.swing.JPanel);
    }

    @Test
    public void testPanelHasComponents() {
        // Test that the panel has been initialized with components
        assertNotNull("Panel should have components", panel.getComponents());
        assertTrue("Panel should have at least one component", panel.getComponents().length > 0);
    }

    @Test
    public void testPanelVisible() {
        // Test panel visibility
        panel.setVisible(true);
        assertTrue("Panel should be visible when set to visible", panel.isVisible());
    }

    @Test
    public void testPanelLayout() {
        // Test that panel uses BorderLayout
        assertTrue("Panel should use BorderLayout", 
                   panel.getLayout() instanceof java.awt.BorderLayout);
    }
}
