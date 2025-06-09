/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.view.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author aarti
 */
public class ReportManagementPanel extends JPanel {
    private JFrame parentFrame;
    private String adminId;
    
    public ReportManagementPanel(JFrame ParentFrame, String adminId) {
        this.parentFrame = parentFrame;
        this.adminId = adminId;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Reports & Analytics");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        
        // Content (placeholder for now)
        JPanel contentPanel = new JPanel(new GridBagLayout());
        JLabel placeholderLabel = new JLabel("Reports & Analytics Features Coming Soon...");
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
        System.out.println("Refreshing reports data...");
    }

}
