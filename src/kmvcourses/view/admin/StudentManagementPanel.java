/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author aarti
 */
package kmvcourses.view.admin;

import javax.swing.*;
import java.awt.*;

public class StudentManagementPanel extends JPanel {
    private JFrame parentFrame;
    private String adminId;
    
    public StudentManagementPanel(JFrame parentFrame, String adminId) {
        this.parentFrame = parentFrame;
        this.adminId = adminId;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Student Management");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        
        // Content (placeholder for now)
        JPanel contentPanel = new JPanel(new GridBagLayout());
        JLabel placeholderLabel = new JLabel("Student Management Features Coming Soon...");
        placeholderLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(placeholderLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnMainMenu = new JButton("← Main Menu");
        btnMainMenu.addActionListener(e -> {
            if (parentFrame instanceof kmvcourses.admin.AdminDashboard) {
                ((kmvcourses.admin.AdminDashboard) parentFrame).showMainMenu();
            }
        });
        buttonPanel.add(btnMainMenu);
        
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void refreshData() {
        // Placeholder method - will implement later
        System.out.println("Refreshing student data...");
    }
}
