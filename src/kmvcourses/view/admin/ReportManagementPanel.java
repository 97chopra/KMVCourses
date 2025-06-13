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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import kmvcourses.dao.PerformanceReportDAO;
import kmvcourses.model.PassFailSummary;
import kmvcourses.model.StudentPerformance;

/**
 *
 * @author aarti
 */
public class ReportManagementPanel extends JPanel {
    private JFrame parentFrame;
    private String adminId;
    private PerformanceReportDAO reportDAO = new PerformanceReportDAO();
    private JTable passFailTable;
    private JTable atRiskTable;
    
    public ReportManagementPanel(JFrame parentFrame, String adminId) { 
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
        
        // Content 
        // Report Tables
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        // Pass/Fail Table
        passFailTable = new JTable();
        String[] passFailColumns = {"Course ID", "Course Name", "Passed", "Failed"};
        passFailTable.setModel(new DefaultTableModel(passFailColumns, 0));
        
        // At-Risk Table
        atRiskTable = new JTable();
        String[] atRiskColumns = {"Student ID", "Name", "Course", "Grade", "Attendance"};
        atRiskTable.setModel(new DefaultTableModel(atRiskColumns, 0));

        // TabbedPane for better organization
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Pass/Fail Summary", new JScrollPane(passFailTable));
        tabbedPane.addTab("At-Risk Students", new JScrollPane(atRiskTable));
        contentPanel.add(tabbedPane, BorderLayout.CENTER);
        
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
    
    public void refreshData() 
    {
        // Load Pass/Fail data
        DefaultTableModel passFailModel = (DefaultTableModel) passFailTable.getModel();
        passFailModel.setRowCount(0);
        for (PassFailSummary summary : reportDAO.getPassFailSummary()) {
            passFailModel.addRow(new Object[]{
                summary.getCourseId(),
                summary.getCourseName(),
                summary.getPassedCount(),
                summary.getFailedCount()
            });
        }

        // Load At-Risk data
        DefaultTableModel atRiskModel = (DefaultTableModel) atRiskTable.getModel();
        atRiskModel.setRowCount(0);
        for (StudentPerformance student : reportDAO.getAtRiskStudents()) {
            atRiskModel.addRow(new Object[]{
                student.getStudentId(),
                student.getName(),
                student.getCourseId(),
                student.getGrade(),
                student.getAttendancePercent() + "%"
            });
        }
    }
}


