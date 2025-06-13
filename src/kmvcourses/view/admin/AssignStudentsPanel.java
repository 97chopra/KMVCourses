/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.view.admin;


import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import java.util.List;
import kmvcourses.dao.CoursesDAO;
import kmvcourses.dao.StudentDAO;
import kmvcourses.dao.StudentDAOImpl;
import kmvcourses.lecturer.LecturerDashboard;
import kmvcourses.model.Courses;
import kmvcourses.model.Student;

/**
 *
 * @author aarti
 */
public class AssignStudentsPanel extends JPanel {
    private JComboBox<Courses> courseComboBox;
    private JList<Student> studentList;
    private DefaultListModel<Student> studentListModel;
    private JButton assignButton, viewAssignedButton, viewUnassignedButton, mainMenuButton;
    private LecturerDashboard parent;

    public AssignStudentsPanel(LecturerDashboard parent, String lecturerId) {
        this.parent = parent;
        setLayout(new BorderLayout());
        initializeComponents(lecturerId);
    }

    private void initializeComponents(String lecturerId) {
        // Top: Select course
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Course:"));
        courseComboBox = new JComboBox<>();
        loadCourses(lecturerId);
        courseComboBox.addActionListener(e -> refreshStudentList(false)); // Default to unassigned on course change
        topPanel.add(courseComboBox);
        JButton unassignButton = new JButton("Unassign"); 
         unassignButton.addActionListener(this::unassignStudent);

        // Center: Student list
        studentListModel = new DefaultListModel<>();
        studentList = new JList<>(studentListModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(studentList);

        // Bottom: Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        assignButton = new JButton("Assign");
        assignButton.addActionListener(this::assignStudent);
        viewAssignedButton = new JButton("View Assigned");
        viewAssignedButton.addActionListener(e -> refreshStudentList(true));
        viewUnassignedButton = new JButton("View Unassigned");
        viewUnassignedButton.addActionListener(e -> refreshStudentList(false));
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> parent.showMainMenu());
        bottomPanel.add(viewAssignedButton);
        bottomPanel.add(viewUnassignedButton);
        bottomPanel.add(assignButton);
        bottomPanel.add(unassignButton); 
        bottomPanel.add(mainMenuButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Initial load: show unassigned students
        refreshStudentList(false);
    }

    private void loadCourses(String lecturerId) {
        courseComboBox.removeAllItems();
        List<Courses> courses = new CoursesDAO().getAllCourses();
        for (Courses c : courses) {
            courseComboBox.addItem(c);
        }
    }

    /**
     * @param showAssigned if true, shows assigned students; if false, shows unassigned
     */
    private void refreshStudentList(boolean showAssigned) {
        studentListModel.clear();
        Courses selectedCourse = (Courses) courseComboBox.getSelectedItem();
        if (selectedCourse == null) return;

        CoursesDAO dao = new CoursesDAO();
        List<Student> students = showAssigned ?
            dao.getAssignedStudents(selectedCourse.getCourseId()) :
            dao.getUnassignedStudents(selectedCourse.getCourseId());

        for (Student s : students) {
            studentListModel.addElement(s);
        }
    }

    private void assignStudent(ActionEvent e) {
        Courses selectedCourse = (Courses) courseComboBox.getSelectedItem();
        Student selectedStudent = studentList.getSelectedValue();

        if (selectedCourse == null || selectedStudent == null) {
            JOptionPane.showMessageDialog(this, "Select a course and a student.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CoursesDAO dao = new CoursesDAO();
boolean success = dao.assignStudentToCourse(selectedStudent.getStudentId(), selectedCourse.getCourseId());
if (success) {
    JOptionPane.showMessageDialog(this, "Student assigned successfully!");
    refreshStudentList(false);
} else {
    JOptionPane.showMessageDialog(this, "Student is already assigned to this course.", "Assignment Error", JOptionPane.WARNING_MESSAGE);
}
    }
    
 private void unassignStudent(ActionEvent e) {
    Courses selectedCourse = (Courses) courseComboBox.getSelectedItem();
    Student selectedStudent = studentList.getSelectedValue();

    if (selectedCourse == null || selectedStudent == null) {
        JOptionPane.showMessageDialog(this, 
            "Select a course and a student to unassign.",
            "Input Error", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    CoursesDAO dao = new CoursesDAO();
    boolean success = dao.unassignStudentFromCourse(
        selectedStudent.getStudentId(), 
        selectedCourse.getCourseId()
    );

    if (success) {
        JOptionPane.showMessageDialog(this, "Student unassigned successfully!");
        refreshStudentList(true); // Refresh the current view (assigned/unassigned)
    } else {
        JOptionPane.showMessageDialog(this, 
            "Failed to unassign student.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
}

    