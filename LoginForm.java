package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        setTitle("Login Form");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel background = new JLabel();

        try {
            background.setIcon(new ImageIcon(LoginForm.class.getResource("/Tha.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setSize(800, 450);
        panel.setOpaque(false);
        background.add(panel);
        getContentPane().add(background);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        userLabel.setBounds(456, 111, 165, 21);
        panel.add(userLabel);
        usernameField = new JTextField();
        usernameField.setBounds(456, 142, 308, 35);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        passwordLabel.setBounds(456, 202, 165, 21);
        panel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(456, 233, 308, 41);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setForeground(Color.RED);
        loginButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        loginButton.setBounds(456, 294, 308, 35);
        loginButton.addActionListener(new LoginAction());
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setForeground(Color.BLACK);
        registerButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        registerButton.setBounds(544, 339, 136, 35);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    RegisterForm registerForm = new RegisterForm();
                    registerForm.setVisible(true);
                    dispose(); // Close the login form
                });
            }
        });
        panel.add(registerButton);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setForeground(Color.BLUE);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        lblNewLabel.setBounds(551, 45, 143, 56);
        panel.add(lblNewLabel);
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String hashedPassword = hashPassword(password);

            if (authenticateUser(username, hashedPassword)) {
                JOptionPane.showMessageDialog(null, "Login successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }
        }

        private String hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(password.getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    hexString.append(String.format("%02x", b));
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                return null;
            }
        }

        private boolean authenticateUser(String username, String hashedPassword) {
            String url = "jdbc:mysql://localhost:3306/logink2";
            String dbUser = "root";
            String dbPassword = "12345";

            try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword)) {
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, hashedPassword);

                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }
}