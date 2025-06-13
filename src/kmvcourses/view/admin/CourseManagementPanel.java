/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.view.admin;

/**
 *
 * @author aarti
 */
import kmvcourses.dao.CoursesDAO;
import kmvcourses.model.Courses;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import kmvcourses.dao.ChapterDAO;
import kmvcourses.dao.ChapterDAOImpl;
import kmvcourses.model.Chapter;
public class CourseManagementPanel extends JPanel
{
    private JFrame parentFrame;
    private String adminId;
    private CoursesDAO courseDAO;
    private JTable courseTable;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete, btnMainMenu;
    private JTextField txtCourseId, txtCourseName, txtDepartment, txtCredits, txtLevel, txtType;
    
    // chapter Management components
    private JButton btnManageChapters;
    private ChapterDAO chapterDAO;
    
    
    
    public CourseManagementPanel(JFrame parentFrame, String adminId) {
        this.parentFrame = parentFrame;
        this.adminId = adminId;
        this.courseDAO = new CoursesDAO();
        this.chapterDAO = new ChapterDAOImpl();
        initializeComponents();
        setupLayout();
        addEventListeners();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Course Management");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        
        // Table setup
        String[] columns = {"Course ID", "Course Name", "Department", "Credits", "Level", "Type"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        courseTable = new JTable(tableModel);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Form panel
        JPanel formPanel = createFormPanel();
        
        // Button panel
        JPanel buttonPanel = createButtonPanel();
        
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(courseTable), BorderLayout.CENTER);
        add(formPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Course Details"));
        panel.setPreferredSize(new Dimension(300, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        txtCourseId = new JTextField(15);
        txtCourseName = new JTextField(15);
        txtDepartment = new JTextField(15);
        txtCredits = new JTextField(15);
        txtLevel = new JTextField(15);
        txtType = new JTextField(15);
        
        addFormField(panel, "Course ID:", txtCourseId, gbc, 0);
        addFormField(panel, "Course Name:", txtCourseName, gbc, 1);
        addFormField(panel, "Department:", txtDepartment, gbc, 2);
        addFormField(panel, "Credits:", txtCredits, gbc, 3);
        addFormField(panel, "Level:", txtLevel, gbc, 4);
        addFormField(panel, "Type:", txtType, gbc, 5);
        
        return panel;
    }
    
    
    
    private void addFormField(JPanel panel, String label, JTextField field, 
                             GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        btnAdd = new JButton("Add Course");
        btnEdit = new JButton("Edit Course");
        btnDelete = new JButton("Delete Course");
        btnManageChapters = new JButton("Manage Chapters");
        btnMainMenu = new JButton("← Main Menu");
        
        panel.add(btnAdd);
        panel.add(btnEdit);
        panel.add(btnDelete);
        panel.add(btnManageChapters);
        panel.add(btnMainMenu);
        
        return panel;
    }
    
    private void setupLayout() {
        // Already set in initializeComponents
    }
    
    private void addEventListeners() {
        btnAdd.addActionListener(e -> addCourse());
        btnEdit.addActionListener(e -> editCourse());
        btnDelete.addActionListener(e -> deleteCourse());
        btnMainMenu.addActionListener(e -> {
            if (parentFrame instanceof kmvcourses.admin.AdminDashboard) {
                ((kmvcourses.admin.AdminDashboard) parentFrame).showMainMenu();
            }
        });
        
        courseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedCourse();
            }
        });
        
        // NEW: Chapter management listener
        btnManageChapters.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a course first", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String courseId = (String) tableModel.getValueAt(selectedRow, 0);
            new ChapterManagerDialog(courseId).setVisible(true);
        });
    }
    
    
    private class ChapterManagerDialog extends JDialog {
        private final String courseId;
        private JTable chaptersTable;
        private DefaultTableModel chaptersModel;

        public ChapterManagerDialog(String courseId) {
            this.courseId = courseId;
            initializeDialog();
            loadChapters();
        }

        private void initializeDialog() {
            setTitle("Manage Chapters - Course: " + courseId);
            setSize(600, 400);
            setLocationRelativeTo(CourseManagementPanel.this);
            setModal(true);

            // Table setup
            String[] columns = {"Chapter ID", "Title", "Order"};
            chaptersModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            chaptersTable = new JTable(chaptersModel);
            
            // Buttons
            JButton btnAddChapter = new JButton("Add Chapter");
            btnAddChapter.addActionListener(e -> addChapter());

            add(new JScrollPane(chaptersTable), BorderLayout.CENTER);
            add(btnAddChapter, BorderLayout.SOUTH);
        }

        private void loadChapters() {
            chaptersModel.setRowCount(0);
            chapterDAO.getChaptersByCourse(courseId).forEach(chapter -> 
                chaptersModel.addRow(new Object[]{
                    chapter.getChapterId(),
                    chapter.getChapterTitle(),
                    chapter.getChapterOrder()
                })
            );
        }

        private void addChapter() 
        {
            // Create input fields
    JTextField titleField = new JTextField();
    JSpinner orderSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

    JPanel inputPanel = new JPanel(new GridLayout(2, 2));
    inputPanel.add(new JLabel("Chapter Title:"));
    inputPanel.add(titleField);
    inputPanel.add(new JLabel("Order:"));
    inputPanel.add(orderSpinner);

    int result = JOptionPane.showConfirmDialog(
        this, inputPanel, "Add New Chapter", JOptionPane.OK_CANCEL_OPTION
    );

    if (result == JOptionPane.OK_OPTION) {
        String title = titleField.getText().trim();
        int order = (Integer) orderSpinner.getValue();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chapter title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Chapter chapter = new Chapter();
        chapter.setCourseId(courseId); // courseId is a field in your dialog
        chapter.setChapterTitle(title);
        chapter.setChapterOrder(order);

        boolean success = chapterDAO.addChapter(chapter);
        if (success) {
            loadChapters(); // refresh the table to show the new chapter
            JOptionPane.showMessageDialog(this, "Chapter added successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add chapter.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        }
    }

    
    public void refreshData() {
        tableModel.setRowCount(0);
        List<Courses> courses = courseDAO.getAllCourses();
        
        for (Courses course : courses) {
            Object[] row = {
                course.getCourseId(),
                course.getCourseName(),
                course.getDepartment(),
                course.getCredits(),
                course.getLevel(),
                course.getType()
            };
            tableModel.addRow(row);
        }
    }
    
    private void addCourse() {
        if (validateForm()) {
            try {
                Courses course = createCourseFromForm();
                if (courseDAO.addCourse(course)) {
                    JOptionPane.showMessageDialog(this, "Course added successfully!");
                    clearForm();
                    refreshData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add course. Course ID may already exist.", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding course: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editCourse() {
        int selectedRow = courseTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a course to edit.",
                                      "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String courseId = (String) tableModel.getValueAt(selectedRow, 0);

    // Show dialog with two options
    JDialog dialog = new JDialog(parentFrame, "Update Course: " + courseId, true);
    dialog.setLayout(new GridLayout(2, 1, 10, 10));

    JButton btnAddChapter = new JButton("Add New Chapter");
    JButton btnDeleteChapter = new JButton("Delete Existing Chapter");

    btnAddChapter.addActionListener(e -> {
        dialog.dispose();
        showAddChapterDialog(courseId);
    });

    btnDeleteChapter.addActionListener(e -> {
        dialog.dispose();
        showDeleteChapterDialog(courseId);
    });

    dialog.add(btnAddChapter);
    dialog.add(btnDeleteChapter);

    dialog.setSize(300, 150);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
    }
    
    private void showAddChapterDialog(String courseId) {
    JTextField titleField = new JTextField();
    JSpinner orderSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

    JPanel inputPanel = new JPanel(new GridLayout(2, 2));
    inputPanel.add(new JLabel("Chapter Title:"));
    inputPanel.add(titleField);
    inputPanel.add(new JLabel("Order:"));
    inputPanel.add(orderSpinner);

    int result = JOptionPane.showConfirmDialog(
        this, inputPanel, "Add New Chapter", JOptionPane.OK_CANCEL_OPTION
    );

    if (result == JOptionPane.OK_OPTION) {
        String title = titleField.getText().trim();
        int order = (Integer) orderSpinner.getValue();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chapter title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Chapter chapter = new Chapter();
        chapter.setCourseId(courseId);
        chapter.setChapterTitle(title);
        chapter.setChapterOrder(order);

        boolean success = chapterDAO.addChapter(chapter);
        if (success) {
            JOptionPane.showMessageDialog(this, "Chapter added successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add chapter.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void showDeleteChapterDialog(String courseId) {
    java.util.List<Chapter> chapters = chapterDAO.getChaptersByCourse(courseId);
    if (chapters.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No chapters to delete.", "Info", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    String[] chapterTitles = chapters.stream()
        .map(Chapter::getChapterTitle)
        .toArray(String[]::new);

    String selected = (String) JOptionPane.showInputDialog(
        this,
        "Select chapter to delete:",
        "Delete Chapter",
        JOptionPane.PLAIN_MESSAGE,
        null,
        chapterTitles,
        null
    );

    if (selected != null) {
        Chapter toDelete = chapters.stream()
            .filter(c -> c.getChapterTitle().equals(selected))
            .findFirst()
            .orElse(null);

        if (toDelete != null && chapterDAO.deleteChapter(toDelete.getChapterId())) {
            JOptionPane.showMessageDialog(this, "Chapter deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete chapter.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    
    private void deleteCourse() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to delete.", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String courseId = (String) tableModel.getValueAt(selectedRow, 0);
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete course: " + courseId + "?", 
                                        "Confirm Delete", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (courseDAO.deleteCourse(courseId)) {
                    JOptionPane.showMessageDialog(this, "Course deleted successfully!");
                    clearForm();
                    refreshData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete course.", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting course: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void loadSelectedCourse() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow != -1) {
            txtCourseId.setText((String) tableModel.getValueAt(selectedRow, 0));
            txtCourseName.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtDepartment.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtCredits.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
            txtLevel.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
            txtType.setText((String) tableModel.getValueAt(selectedRow, 5));
        }
    }
    
    private Courses createCourseFromForm() {
        return new Courses(
            txtCourseId.getText().trim(),
            txtCourseName.getText().trim(),
            txtDepartment.getText().trim(),
            Integer.parseInt(txtCredits.getText().trim()),
            Integer.parseInt(txtLevel.getText().trim()),
            txtType.getText().trim()
        );
    }
    
    private boolean validateForm() {
        if (txtCourseId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Course ID is required.", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtCourseName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Course Name is required.", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(txtCredits.getText().trim());
            Integer.parseInt(txtLevel.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Credits and Level must be valid numbers.", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void clearForm() {
        txtCourseId.setText("");
        txtCourseName.setText("");
        txtDepartment.setText("");
        txtCredits.setText("");
        txtLevel.setText("");
        txtType.setText("");
    }
}

