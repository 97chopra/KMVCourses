/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.lecturer;

/**
 *
 * @author aarti
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import kmvcourses.view.admin.AssignStudentsPanel;
import kmvcourses.view.admin.CourseManagementPanel;
import kmvcourses.view.admin.GradingPanel;
import kmvcourses.view.admin.MainInterface;
import kmvcourses.view.admin.MarkAttendancePanel;
import kmvcourses.view.admin.StudentManagementPanel;

public class LecturerDashboard extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String lecturerId;

    // Menu buttons
    private JButton btnAssignStudents;
    private JButton btnMarkAttendance;
    private JButton btnGrading;
    private JButton btnLogout;

    // Feature panels
    private AssignStudentsPanel assignStudentsPanel;
    private MarkAttendancePanel markAttendancePanel;
    private GradingPanel gradingPanel;

    public LecturerDashboard(String lecturerId) {
        this.lecturerId = lecturerId;
        setTitle("KMV Courses - Lecturer Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize feature panels, passing this dashboard as parent for navigation
        assignStudentsPanel = new AssignStudentsPanel(this,lecturerId);
        markAttendancePanel = new MarkAttendancePanel( this, lecturerId);
        gradingPanel = new GradingPanel(this, lecturerId);

        // Add panels to mainPanel
        mainPanel.add(createMenuPanel(), "MENU");
        mainPanel.add(assignStudentsPanel, "ASSIGN");
        mainPanel.add(markAttendancePanel, "ATTENDANCE");
        mainPanel.add(gradingPanel, "GRADING");

        add(mainPanel);

        // Handle window closing for logout/exit confirmation
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                handleExit();
            }
        });

        cardLayout.show(mainPanel, "MENU");
        setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel welcomeLabel = new JLabel("Welcome, Lecturer ID: " + lecturerId);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(welcomeLabel);

        // Vertical menu buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        btnAssignStudents = createMenuButton("Assign Students");
        btnMarkAttendance = createMenuButton("Mark Attendance");
        btnGrading = createMenuButton("Grading");
        btnLogout = createMenuButton("Logout");

        buttonsPanel.add(btnAssignStudents);
        buttonsPanel.add(btnMarkAttendance);
        buttonsPanel.add(btnGrading);
        buttonsPanel.add(btnLogout);

        menuPanel.add(headerPanel, BorderLayout.NORTH);
        menuPanel.add(buttonsPanel, BorderLayout.CENTER);

        return menuPanel;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(300, 60));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnAssignStudents) {
            cardLayout.show(mainPanel, "ASSIGN");
        } else if (source == btnMarkAttendance) {
            cardLayout.show(mainPanel, "ATTENDANCE");
        } else if (source == btnGrading) {
            cardLayout.show(mainPanel, "GRADING");
        } else if (source == btnLogout) {
            handleLogout();
        }
    }

    public void showMainMenu() {
        cardLayout.show(mainPanel, "MENU");
    }

    private void handleLogout() {
        if (JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?", "Confirm Logout",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dispose();
            new LecturerLogin().setVisible(true);
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