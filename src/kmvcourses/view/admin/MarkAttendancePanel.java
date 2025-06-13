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
import java.sql.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import kmvcourses.dao.CoursesDAO;
import kmvcourses.dao.AttendanceDAO;
import kmvcourses.dao.AttendanceDAOImpl;
import kmvcourses.lecturer.LecturerDashboard;
import kmvcourses.model.Courses;
import kmvcourses.model.Student;

/**
 *
 * @author aarti
 */
public class MarkAttendancePanel extends JPanel {
    private JComboBox<Courses> courseComboBox;
    private JSpinner dateSpinner;
    private JTable attendanceTable;
    private JButton submitButton, mainMenuButton;
    private LecturerDashboard parent;
    private AttendanceTableModel tableModel;

    public MarkAttendancePanel(LecturerDashboard parent, String lecturerId) {
        this.parent = parent;
        setLayout(new BorderLayout());

        // Top: Course and date selection
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Course:"));
        courseComboBox = new JComboBox<>();
        List<Courses> courses = new CoursesDAO().getAllCourses();
        for (Courses c : courses) courseComboBox.addItem(c);
        topPanel.add(courseComboBox);

        topPanel.add(new JLabel("Date:"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        topPanel.add(dateSpinner);

        add(topPanel, BorderLayout.NORTH);

        // Center: Attendance table
        tableModel = new AttendanceTableModel();
        attendanceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom: Submit and Main Menu buttons
        JPanel bottomPanel = new JPanel();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this::submitAttendance);
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> parent.showMainMenu());
        bottomPanel.add(submitButton);
        bottomPanel.add(mainMenuButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load students when course changes
        courseComboBox.addActionListener(e -> loadStudents());
        loadStudents(); // Initial load
    }

    private void loadStudents() {
        tableModel.clear();
        Courses selectedCourse = (Courses) courseComboBox.getSelectedItem();
        if (selectedCourse == null) return;
        List<Student> students = new CoursesDAO().getAssignedStudents(selectedCourse.getCourseId());
        for (Student s : students) {
            tableModel.addStudent(s);
        }
    }

    private void submitAttendance(ActionEvent e) {
        Courses selectedCourse = (Courses) courseComboBox.getSelectedItem();
        java.util.Date utilDate = (java.util.Date) dateSpinner.getValue();
        Date sqlDate = new Date(utilDate.getTime());
        if (selectedCourse == null) {
            JOptionPane.showMessageDialog(this, "Please select a course.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        AttendanceDAO attendanceDAO = new AttendanceDAOImpl();
        int success = 0, fail = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Student s = tableModel.getStudentAt(i);
            String status = (Boolean) tableModel.getValueAt(i, 2) ? "Present" : "Absent";
            boolean result = attendanceDAO.markAttendance(s.getStudentId(), selectedCourse.getCourseId(), sqlDate, status);
            if (result) success++; else fail++;
        }
        JOptionPane.showMessageDialog(this, success + " attendance records saved. " +
                (fail > 0 ? (fail + " failed.") : ""), "Attendance Submission", JOptionPane.INFORMATION_MESSAGE);
    }

    // Custom Table Model for Attendance
    static class AttendanceTableModel extends AbstractTableModel {
        private final String[] columns = {"Student ID", "Name", "Present"};
        private final java.util.List<Object[]> data = new java.util.ArrayList<>();

        public void addStudent(Student s) {
            data.add(new Object[]{s.getStudentId(), s.getFirstName() + " " + s.getLastName(), Boolean.TRUE});
            fireTableDataChanged();
        }
        public void clear() {
            data.clear();
            fireTableDataChanged();
        }
        public Student getStudentAt(int row) {
            // You need a way to get Student by ID from your StudentDAO if needed
            String id = (String) data.get(row)[0];
            String name = (String) data.get(row)[1];
            return new Student(id, name.split(" ")[0], name.split(" ")[1], ""); // password blank
        }
        @Override public int getRowCount() { return data.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }
        @Override public Object getValueAt(int row, int col) { return data.get(row)[col]; }
        @Override public boolean isCellEditable(int row, int col) { return col == 2; }
        @Override public void setValueAt(Object value, int row, int col) { data.get(row)[col] = value; fireTableCellUpdated(row, col); }
        @Override public Class<?> getColumnClass(int col) { return col == 2 ? Boolean.class : String.class; }
    }
}