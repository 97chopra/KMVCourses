/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.view.admin;

/**
 *
 * @author aarti
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import kmvcourses.admin.AdminLogin;


public class MainInterface extends JFrame {
    public MainInterface() {
        setTitle("KMV College Portal");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Choose a background color that matches your logo/buttons
        Color bgColor = new Color(255, 243, 224); // Soft creamy orange (change as needed)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);

        // Left: Vertical button panel, top-aligned
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(bgColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JButton adminButton = new JButton("Admin Login");
        adminButton.setFont(new Font("Arial", Font.BOLD, 18));
        adminButton.setBackground(new Color(70, 130, 180));
        adminButton.setForeground(Color.WHITE);
        adminButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        adminButton.setMaximumSize(new Dimension(180, 50));
        adminButton.setFocusPainted(false);
        adminButton.addActionListener(e -> {
            new AdminLogin().setVisible(true);
            this.dispose();
        });

        JButton lecturerButton = new JButton("Lecturer Login");
        lecturerButton.setFont(new Font("Arial", Font.BOLD, 18));
        lecturerButton.setBackground(new Color(34, 139, 34));
        lecturerButton.setForeground(Color.WHITE);
        lecturerButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        lecturerButton.setMaximumSize(new Dimension(180, 50));
        lecturerButton.setFocusPainted(false);
        lecturerButton.addActionListener(e -> {
            new kmvcourses.lecturer.LecturerLogin().setVisible(true);
            this.dispose();
        });

        JButton exitButton = new JButton("Exit (X)");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setBackground(new Color(220, 53, 69));
        exitButton.setForeground(Color.WHITE);
        exitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(180, 50));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(adminButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(lecturerButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.WEST);

        // Center: Logo above text, both centered
        JPanel logoTextPanel = new JPanel();
        logoTextPanel.setBackground(bgColor);
        logoTextPanel.setLayout(new BoxLayout(logoTextPanel, BoxLayout.Y_AXIS));
        logoTextPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 60, 0));

        // Load and resize the logo
        ImageIcon originalIcon = new ImageIcon("C:/Users/aarti/Downloads/educational logo.jpeg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("KMV College Portal");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoTextPanel.add(logoLabel);
        logoTextPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        logoTextPanel.add(titleLabel);

        mainPanel.add(logoTextPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel("© 2025 KMV College | All rights reserved", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.setForeground(Color.GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(footer, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }
}