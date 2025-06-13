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
import java.sql.*;
import kmvcourses.dao.DBConnection;

public class LecturerLogin extends JFrame
{
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton, quitButton;
    private JLabel messageLabel;

    public LecturerLogin() {
        setTitle("Lecturer Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        idField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        quitButton = new JButton("Quit");
        messageLabel = new JLabel("", SwingConstants.CENTER);

        add(new JLabel("Enter Lecturer ID:", SwingConstants.CENTER));
        add(idField);
        add(new JLabel("Enter Password:", SwingConstants.CENTER));
        add(passwordField);
        add(messageLabel);

        JPanel panel = new JPanel();
        panel.add(loginButton);
        panel.add(quitButton);
        add(panel);

        loginButton.addActionListener(e -> authenticateLecturer());
        quitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void authenticateLecturer() {
        String lecturerId = idField.getText().trim();
        String password = new String(passwordField.getPassword());

        DBConnection db = new DBConnection();
        Connection conn = db.getConnection();

        if (conn == null) {
            messageLabel.setText("DB connection failed.");
            return;
        }

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM LECTURERS WHERE lecturer_id = ? AND password = ?")) {
            stmt.setString(1, lecturerId);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs != null && rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful");
                dispose();
                new LecturerDashboard(lecturerId).setVisible(true);
            } else {
                messageLabel.setText("Invalid ID or Password");
            }
        } catch (SQLException e) {
            messageLabel.setText("DB Error: " + e.getMessage());
        }
    }

    
    
}
