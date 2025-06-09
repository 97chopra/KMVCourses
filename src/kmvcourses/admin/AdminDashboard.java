/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import kmvcourses.view.admin.CourseManagementPanel;
import kmvcourses.view.admin.StudentManagementPanel;
import kmvcourses.view.admin.ReportManagementPanel;

/**
 *
 * @author aarti
 */
public class AdminDashboard extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String adminId;
    

    
    // Menu buttons
    private JButton btnStudentManagement;
    private JButton btnCourseManagement;
    private JButton btnReports;
    private JButton btnLogout;
    private JButton btnMainMenu;
    
    // Panel instances
    private CourseManagementPanel coursePanel;
    private StudentManagementPanel studentPanel;
    private ReportManagementPanel reportPanel; 
    
    public AdminDashboard(String adminId) {
        this.adminId = adminId;
        initializeComponents();
        setupLayout();
        addEventListeners();
        setupWindowClosing();
        setVisible(true);

    }
    
    private void initializeComponents() {
        setTitle("KMV Courses - Admin Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Important change
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create menu panel
        createMenuPanel();
        
        // Create management panels
        coursePanel = new CourseManagementPanel(this, adminId);
        studentPanel = new StudentManagementPanel(this, adminId);
        reportPanel = new ReportManagementPanel(this, adminId); 
         
    }
    
    private void createMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout());
        
        // Header with better styling
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel welcomeLabel = new JLabel("Welcome, Admin ID: " + adminId);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(welcomeLabel);
        
        // Main menu buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        btnStudentManagement = createMenuButton(" Student Management");
        btnCourseManagement = createMenuButton(" Course Management");
        btnReports = createMenuButton(" Reports & Analytics");
        btnLogout = createMenuButton(" Logout");
        
        buttonsPanel.add(btnStudentManagement);
        buttonsPanel.add(btnCourseManagement);
        buttonsPanel.add(btnReports);
        buttonsPanel.add(btnLogout);
        
        menuPanel.add(headerPanel, BorderLayout.NORTH);
        menuPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        mainPanel.add(menuPanel, "MENU");
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(300, 60));
        button.addActionListener(this);
        return button;
    }
    
    private void setupLayout() {
        mainPanel.add(studentPanel, "STUDENTS");
        mainPanel.add(coursePanel, "COURSES");
       mainPanel.add(reportPanel, "REPORTS");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "MENU");
    }
    
    private void addEventListeners() {
        // Event listeners added in createMenuPanel()
    }
    
    // CRITICAL: Handle window closing properly (requirement from rubric)
    private void setupWindowClosing() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleExit();
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        
        if (source == btnStudentManagement) {
            studentPanel.refreshData();
            cardLayout.show(mainPanel, "STUDENTS");
        } else if (source == btnCourseManagement) {
            coursePanel.refreshData();
            cardLayout.show(mainPanel, "COURSES");
        } else if (source == btnReports) {
            reportPanel.refreshData();
            cardLayout.show(mainPanel, "REPORTS");
        } else if (source == btnLogout) {
            handleLogout();
        }
    }
    
    // CRITICAL: Allow users to return to main menu anytime (requirement)
    public void showMainMenu() {
        cardLayout.show(mainPanel, "MENU");
    }
    
    private void handleLogout() {
        if (JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", "Confirm Logout", 
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            
            dispose();
            new AdminLogin().setVisible(true);
        }
    }
    
    private void handleExit() {
        if (JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to exit the application?", "Confirm Exit", 
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
