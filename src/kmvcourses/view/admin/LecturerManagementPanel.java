/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.view.admin;

import javax.swing.JTable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import kmvcourses.admin.AdminDashboard;
import kmvcourses.dao.CoursesDAO;
import kmvcourses.dao.LecturerDAO;
import kmvcourses.dao.LecturerDAOImpl;
import kmvcourses.model.Courses;
import kmvcourses.model.Lecturer;



/**
 *
 * @author aarti
 */
public class LecturerManagementPanel extends JPanel 
{
    private JTable lecturerTable;
    private DefaultTableModel tableModel;
    private LecturerDAO lecturerDAO;
    private CoursesDAO courseDAO;
    private JButton btnBack;
    private JButton btnAssignToCourse;
    private JButton btnViewAssignments;
    private JButton btnRefresh;

    public LecturerManagementPanel() {
        lecturerDAO = new LecturerDAOImpl();
        courseDAO = new CoursesDAO(); 
        initializeComponents();
        loadLecturers();
        addEventListeners();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Lecturer Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Lecturer ID", "Name", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        lecturerTable = new JTable(tableModel);
        lecturerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lecturerTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(lecturerTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("All Lecturers"));
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        btnAssignToCourse = new JButton("Assign to Course");
        btnViewAssignments = new JButton("View Assignments");
        btnRefresh = new JButton("Refresh");
        btnBack = new JButton("← Back to Dashboard");

        panel.add(btnAssignToCourse);
        panel.add(btnViewAssignments);
        panel.add(btnRefresh);
        panel.add(btnBack);

        return panel;
    }

    private void addEventListeners() {
        btnAssignToCourse.addActionListener(e -> assignLecturerToCourse());
        btnViewAssignments.addActionListener(e -> viewLecturerAssignments());
        btnRefresh.addActionListener(e -> loadLecturers());
        btnBack.addActionListener(e -> goBackToDashboard());

        // Enable/disable buttons based on selection
        lecturerTable.getSelectionModel().addListSelectionListener(e -> {
            boolean hasSelection = lecturerTable.getSelectedRow() != -1;
            btnAssignToCourse.setEnabled(hasSelection);
            btnViewAssignments.setEnabled(hasSelection);
        });

        // Initially disable buttons
        btnAssignToCourse.setEnabled(false);
        btnViewAssignments.setEnabled(false);
    }

    private void loadLecturers() {
        tableModel.setRowCount(0);
        List<Lecturer> lecturers = lecturerDAO.getAllLecturers();

        for (Lecturer lecturer : lecturers) {
            Object[] row = {
                lecturer.getlecturerId(),
                lecturer.getName(),
                lecturer.getEmail()
            };
            tableModel.addRow(row);
        }
    }

    private void assignLecturerToCourse() {
        int selectedRow = lecturerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lecturer first.");
            return;
        }

        String lecturerId = (String) tableModel.getValueAt(selectedRow, 0);
        String lecturerName = (String) tableModel.getValueAt(selectedRow, 1);

        showCourseAssignmentDialog(lecturerId, lecturerName);
    }

    private void showCourseAssignmentDialog(String lecturerId, String lecturerName) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), 
                                     "Assign Courses - " + lecturerName, true);
        dialog.setLayout(new BorderLayout());

        // Get all courses using your CourseDAO
        List<Courses> allCourses = courseDAO.getAllCourses();

        DefaultListModel<String> availableModel = new DefaultListModel<>();
        DefaultListModel<String> assignedModel = new DefaultListModel<>();

        // Populate available courses
        for (Courses course : allCourses) {
            availableModel.addElement(course.getCourseId() + " - " + course.getCourseName());
        }

        JList<String> availableList = new JList<>(availableModel);
        JList<String> assignedList = new JList<>(assignedModel);

        availableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        assignedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane availableScroll = new JScrollPane(availableList);
        JScrollPane assignedScroll = new JScrollPane(assignedList);

        availableScroll.setBorder(BorderFactory.createTitledBorder("Available Courses"));
        assignedScroll.setBorder(BorderFactory.createTitledBorder("Assigned Courses"));

        JButton btnAssign = new JButton("Assign →");
        JButton btnUnassign = new JButton("← Unassign");
        JButton btnClose = new JButton("Close");

        btnAssign.addActionListener(e -> {
            String selected = availableList.getSelectedValue();
            if (selected != null) {
                String courseId = selected.split(" - ")[0]; // Extract course ID
                if (lecturerDAO.assignToCourse(courseId, lecturerId)) {
                    availableModel.removeElement(selected);
                    assignedModel.addElement(selected);
                    JOptionPane.showMessageDialog(dialog, "Lecturer assigned successfully!");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to assign lecturer.");
                }
            }
        });

        btnUnassign.addActionListener(e -> {
            String selected = assignedList.getSelectedValue();
            if (selected != null) {
                String courseId = selected.split(" - ")[0]; // Extract course ID
                if (lecturerDAO.unassignFromCourse(courseId, lecturerId)) {
                    assignedModel.removeElement(selected);
                    availableModel.addElement(selected);
                    JOptionPane.showMessageDialog(dialog, "Lecturer unassigned successfully!");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to unassign lecturer.");
                }
            }
        });

        btnClose.addActionListener(e -> {
            dialog.dispose();
            loadLecturers();
        });

        // Layout
        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listsPanel.add(availableScroll);
        listsPanel.add(assignedScroll);

        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonsPanel.add(btnAssign);
        buttonsPanel.add(btnUnassign);
        buttonsPanel.add(btnClose);

        dialog.add(listsPanel, BorderLayout.CENTER);
        dialog.add(buttonsPanel, BorderLayout.EAST);

        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void viewLecturerAssignments() {
        int selectedRow = lecturerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lecturer first.");
            return;
        }
        
        String lecturerId = (String) tableModel.getValueAt(selectedRow, 0);
        String lecturerName = (String) tableModel.getValueAt(selectedRow,0);
        
        // Fetch assigned course
        List<Courses> assignedCourses = lecturerDAO.getAssignedCourses(lecturerId);

        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                                     "Assignments for " + lecturerName, true);
        dialog.setLayout(new BorderLayout());

        if (assignedCourses.isEmpty()) {
            dialog.add(new JLabel("No courses assigned to this lecturer.", JLabel.CENTER), BorderLayout.CENTER);
        } else {
            String[] columnNames = {"Course ID", "Course Name", "Department", "Credits"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                public boolean isCellEditable(int row, int col) { return false; }
            };
            for (Courses c : assignedCourses) {
                model.addRow(new Object[]{
                    c.getCourseId(),
                    c.getCourseName(),
                    c.getDepartment(),
                    c.getCredits()
                });
            }
            JTable table = new JTable(model);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(table);
            dialog.add(scrollPane, BorderLayout.CENTER);

            JButton btnUnassign = new JButton("Unassign Selected Course");
            btnUnassign.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(dialog, "Select a course to unassign.");
                    return;
                }
                String courseId = (String) model.getValueAt(row, 0);
                if (lecturerDAO.unassignFromCourse(courseId, lecturerId)) {
                    model.removeRow(row);
                    JOptionPane.showMessageDialog(dialog, "Unassigned successfully.");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Unassignment failed.");
                }
            });
            JPanel btnPanel = new JPanel();
            btnPanel.add(btnUnassign);
            dialog.add(btnPanel, BorderLayout.SOUTH);
        }

        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


        //String lecturerId = (String) tableModel.getValueAt(selectedRow, 0);
       // String lecturerName = (String) tableModel.getValueAt(selectedRow, 1);

       // JOptionPane.showMessageDialog(this, 
        //    "Viewing assignments for: " + lecturerName + "\n" +
        //    "Lecturer ID: " + lecturerId + "\n" +
        //    "(Feature can be expanded based on your requirements)");
    

    private void goBackToDashboard() {
        // Navigate back to your AdminDashboard)
        Window window = SwingUtilities.getWindowAncestor(this);
        
        
        
        
        if (window instanceof AdminDashboard) {
            AdminDashboard dashboard = (AdminDashboard) window;
            dashboard.showMainMenu();
           
           
        }
    }
}
    