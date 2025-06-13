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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import kmvcourses.admin.AdminDashboard;
import kmvcourses.dao.StudentDAO;
import kmvcourses.dao.StudentDAOImpl;
import kmvcourses.model.Student;
import java.util.List;
import java.util.ArrayList;
import kmvcourses.dao.AttendanceDAO;
import kmvcourses.dao.AttendanceDAOImpl;
import kmvcourses.model.Attendance;


public class StudentManagementPanel extends JPanel {
    private JFrame parentFrame;
    private String adminId;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private StudentDAO  studentDAO;
    private AttendanceDAO attendanceDAO = new AttendanceDAOImpl();
    
    // Search componnets
   // private JTextField searchField;
    //private JButton  searchButton;
    private JButton btnAdd;
    
    private JButton btnDelete;
    private JButton btnMainMenu;
    private JButton btnViewAttendance;
   // private JButton btnViewStudents;
    
    
    public StudentManagementPanel(JFrame parentFrame, String adminId) {
        this.parentFrame = parentFrame;
        this.adminId = adminId;
        this.studentDAO = new StudentDAOImpl();
        initializeComponents();
        loadStudents();
    }
    
    private void initializeComponents() 
    {
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Student Management");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
       // searchField = new JTextField(20);
      //  searchButton = new JButton("Search");
      //  searchButton.addActionListener(e -> searchStudents());
        searchPanel.add(new JLabel("Search:"));
       // searchPanel.add(searchField);
      //  searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        // Student Table
        String[] columns = {"Student ID", "First Name", "Last Name", "Password"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        studentTable = new JTable(tableModel);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);
       // btnViewStudents = new JButton("View Students");
        btnAdd = new JButton("Add Student");
        btnDelete = new JButton("Delete Student");
        btnMainMenu = new JButton("Main Menu");
        btnViewAttendance = new JButton("View Attendance");
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
       
        btnAdd.addActionListener(e -> showAddDialog());
        
        btnDelete.addActionListener(e -> deletStudent());
        btnViewAttendance.addActionListener(e -> showAttendanceDialog());
        btnMainMenu.addActionListener(e -> {
            if (parentFrame instanceof AdminDashboard) {
                ((AdminDashboard) parentFrame).showMainMenu();
            }
        });
       // btnViewStudents.addActionListener(e -> loadStudents());
        // Add action listeners for these buttons as needed...
        buttonPanel.add(btnMainMenu);
       // buttonPanel.add(btnViewStudents);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewAttendance);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadStudents() 
    {
        tableModel.setRowCount(0);
        for (Student student : studentDAO.getAllStudents()) {
            tableModel.addRow(new Object[]{
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName(),
                student.getPassword()
            });
        }
    }

   // private void searchStudents() 
   // {
      //  String keyword = searchField.getText().trim();
     //   List<Student> results = studentDAO.searchStudents(keyword);
      //  tableModel.setRowCount(0);
      //  for (Student s : results) {
       //     tableModel.addRow(new Object[]{
      //          s.getStudentId(), s.getFirstName(), s.getLastName(), s.getPassword()
      //      });
      //  }
   // }
    
    private void showAddDialog()
    {
        JTextField idField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Student ID:"));
        panel.add(idField);
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(
            this, panel, "Add New Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            Student newStudent = new Student(id, firstName, lastName, password);
            if (studentDAO.addStudent(newStudent)) {
                loadStudents();
                JOptionPane.showMessageDialog(this, "Student added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student! (Duplicate ID?)");
            }
        } 
    }
    
    private void showEditDialog()
    {
      int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit!");
            return;
        }

        String studentId = (String) tableModel.getValueAt(selectedRow, 0);
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Student not found!");
            return;
        }

        JTextField firstNameField = new JTextField(student.getFirstName());
        JTextField lastNameField = new JTextField(student.getLastName());
        JPasswordField passwordField = new JPasswordField(student.getPassword());

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(
            this, panel, "Edit Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            student.setFirstName(firstNameField.getText().trim());
            student.setLastName(lastNameField.getText().trim());
            student.setPassword(new String(passwordField.getPassword()));

            if (studentDAO.updateStudent(student)) {
                loadStudents();
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update student!");
            }
        }  
    }
    
    private void deletStudent()
    {
       int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete!");
            return;
        }

        String studentId = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(
            this, "Are you sure you want to delete student " + studentId + "?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (studentDAO.deleteStudent(studentId)) {
                loadStudents();
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete student!");
            }
        } 
    }
    
    private void showAttendanceDialog() {
    int selectedRow = studentTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a student to view attendance!");
        return;
    }
    String studentId = (String) tableModel.getValueAt(selectedRow, 0);
    List<Attendance> records = attendanceDAO.getAttendanceByStudent(studentId);

    if (records.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No attendance records found for this student.");
        return;
    }

    StringBuilder sb = new StringBuilder();
    sb.append("Date\tCourse\tStatus\n");
    for (Attendance a : records) {
        sb.append(a.getDate()).append("\t")
          .append(a.getCourseId()).append("\t")
          .append(a.getStatus()).append("\n");
    }
    JTextArea area = new JTextArea(sb.toString());
    area.setEditable(false);
    JScrollPane scroll = new JScrollPane(area);
    scroll.setPreferredSize(new Dimension(400, 200));
    JOptionPane.showMessageDialog(this, scroll, "Attendance for Student " + studentId, JOptionPane.INFORMATION_MESSAGE);
}

    public void refreshData() 
    {
        loadStudents();
    }
}
