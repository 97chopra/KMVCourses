/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.view.admin;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author aarti
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import kmvcourses.dao.CoursesDAO;
import kmvcourses.dao.AttendanceDAO;
import kmvcourses.dao.AttendanceDAOImpl;
import kmvcourses.lecturer.LecturerDashboard;
import kmvcourses.model.Courses;
import kmvcourses.model.Student;


public class GradingPanel extends JPanel {
    private JComboBox<Courses> courseComboBox;
    private JTable gradesTable;
    private JButton submitButton, mainMenuButton, refreshButton;
    private LecturerDashboard parent;
    private GradesTableModel tableModel;

    public GradingPanel(LecturerDashboard parent, String lecturerId) {
        this.parent = parent;
        setLayout(new BorderLayout());

        // Top: Course selection and refresh
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Course:"));
        courseComboBox = new JComboBox<>();
        List<Courses> courses = new CoursesDAO().getCoursesByLecturer(lecturerId);
        for (Courses c : courses) courseComboBox.addItem(c);
        topPanel.add(courseComboBox);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadStudentsAndGrades());
        topPanel.add(refreshButton);

        add(topPanel, BorderLayout.NORTH);

        // Center: Grades table
        tableModel = new GradesTableModel();
        gradesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(gradesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom: Submit and Main Menu buttons
        JPanel bottomPanel = new JPanel();
        submitButton = new JButton("Submit Grades");
        submitButton.addActionListener(this::submitGrades);
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> parent.showMainMenu());
        bottomPanel.add(submitButton);
        bottomPanel.add(mainMenuButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load students/grades when course changes
        courseComboBox.addActionListener(e -> loadStudentsAndGrades());
        loadStudentsAndGrades(); // Initial load
    }

    private void loadStudentsAndGrades() {
        tableModel.clear();
        Courses selectedCourse = (Courses) courseComboBox.getSelectedItem();
        if (selectedCourse == null) return;
        List<Student> students = new CoursesDAO().getAssignedStudents(selectedCourse.getCourseId());
        AttendanceDAO gradingDAO = new AttendanceDAOImpl(); // Using AttendanceDAO for grade methods
        for (Student s : students) {
            String grade = gradingDAO.getGradeForStudent(s.getStudentId(), selectedCourse.getCourseId());
            tableModel.addStudent(s, grade);
        }
    }

    private void submitGrades(ActionEvent e) {
        Courses selectedCourse = (Courses) courseComboBox.getSelectedItem();
        if (selectedCourse == null) {
            JOptionPane.showMessageDialog(this, "Please select a course.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        AttendanceDAO gradingDAO = new AttendanceDAOImpl();
        int success = 0, fail = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Student s = tableModel.getStudentAt(i);
            String grade = (String) tableModel.getValueAt(i, 2);
            boolean result = gradingDAO.setGradeForStudent(s.getStudentId(), selectedCourse.getCourseId(), grade);
            if (result) success++; else fail++;
        }
        JOptionPane.showMessageDialog(this, success + " grades saved. " +
                (fail > 0 ? (fail + " failed.") : ""), "Grade Submission", JOptionPane.INFORMATION_MESSAGE);
    }

    // Custom Table Model for Grades
    static class GradesTableModel extends AbstractTableModel {
        private final String[] columns = {"Student ID", "Name", "Grade"};
        private final java.util.List<Object[]> data = new java.util.ArrayList<>();

        public void addStudent(Student s, String grade) {
            data.add(new Object[]{s.getStudentId(), s.getFirstName() + " " + s.getLastName(), grade == null ? "" : grade});
            fireTableDataChanged();
        }
        public void clear() {
            data.clear();
            fireTableDataChanged();
        }
        public Student getStudentAt(int row) {
            String id = (String) data.get(row)[0];
            String name = (String) data.get(row)[1];
            String[] names = name.split(" ", 2);
            return new Student(id, names[0], names.length > 1 ? names[1] : "", "");
        }
        
        private boolean isValidGrade(String grade) {
    try {
        if (grade == null || grade.isEmpty()) return true; // Allow empty grades
        Double.parseDouble(grade);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
    
    
}



        @Override public int getRowCount() { return data.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }
        @Override public Object getValueAt(int row, int col) { return data.get(row)[col]; }
        @Override public boolean isCellEditable(int row, int col) { return col == 2; }
        @Override public void setValueAt(Object value, int row, int col) { data.get(row)[col] = value; fireTableCellUpdated(row, col); }
        @Override public Class<?> getColumnClass(int col) { return String.class; }
    }
}