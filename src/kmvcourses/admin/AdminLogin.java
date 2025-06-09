/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.admin;


import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import kmvcourses.dao.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author aarti
 */
public class AdminLogin extends JFrame
{
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton, quitButton;
    private JLabel messageLabel;

    /**
     * Constructor that initializes and displays the admin login form.
     */
    public AdminLogin() {
        setTitle("Admin Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        idField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        quitButton = new JButton("Quit");
        messageLabel = new JLabel("", SwingConstants.CENTER);

        add(new JLabel("Enter Admin ID:", SwingConstants.CENTER));
        add(idField);
        add(new JLabel("Enter Password:", SwingConstants.CENTER));
        add(passwordField);
        add(messageLabel);

        JPanel panel = new JPanel();
        panel.add(loginButton);
        panel.add(quitButton);
        add(panel);

        // Button listeners
        loginButton.addActionListener(e -> authenticateAdmin());
        quitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    /**
     * Method to authenticate the admin using database credentials.
     */
    private void authenticateAdmin() {
        String adminId = idField.getText().trim();
        String password = new String(passwordField.getPassword());

        DBConnection db = new DBConnection(); // Create an instance to get connection
        Connection conn = db.getConnection();

        // Handle case where connection failed
        if (conn == null) {
            messageLabel.setText(" DB connection failed.");
            return;
        }

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM ADMINS WHERE admin_id = ? AND password = ?")) {

            stmt.setString(1, adminId);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs != null && rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful");
                dispose(); // Close login window
                new AdminDashboard(adminId).setVisible(true); // Load dashboard
            } else {
                messageLabel.setText(" Invalid ID or Password");
            }

        } catch (SQLException e) {
            messageLabel.setText("️ DB Error: " + e.getMessage());
        }
    }

    /**
     * Main method to start the admin login GUI.
     */
    
    
}
