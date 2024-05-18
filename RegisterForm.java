package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class RegisterForm extends JFrame implements ActionListener {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton registerButton;
    private JButton btnNewButton;

    public RegisterForm() {
        setTitle("Register Form");
        setSize(800, 450);
        
        JLabel background = new JLabel();
        
        try {
			background.setIcon(new ImageIcon(LoginForm.class.getResource("/Tha.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        JPanel panel = new JPanel();
        
        panel.setSize(800,450);
        
        panel.setOpaque(false);
        
        background.add(panel);
        getContentPane().add(background);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setLayout(null);
        usernameField = new JTextField();
        usernameField.setBounds(456, 142, 308, 35);
        passwordField = new JPasswordField();
        passwordField.setBounds(456, 233, 308, 41);
        registerButton = new JButton("Register");
        registerButton.setForeground(Color.RED);
        registerButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        registerButton.setBounds(456, 294, 308, 35);
        panel.setLayout(null);
        panel.add(usernameField);
        panel.add(passwordField);
        JLabel label = new JLabel();
        label.setBounds(0, 75, 143, 37);
        panel.add(label);
        panel.add(registerButton);
        
        btnNewButton = new JButton("close");
        btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose(); // Đóng cửa sổ RegisterForm
        	}
        });
        btnNewButton.setBounds(544, 339, 136, 35);
        panel.add(btnNewButton);

        registerButton.addActionListener(this);
        
        JLabel lblNewLabel = new JLabel("Register");
        lblNewLabel.setForeground(Color.BLUE);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        lblNewLabel.setBounds(551, 45, 143, 56);
        panel.add(lblNewLabel);
        
        JLabel lblUsermane = new JLabel("Usermane");
        lblUsermane.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblUsermane.setBounds(456, 111, 165, 21);
        panel.add(lblUsermane);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblPassword.setBounds(456, 202, 165, 21);
        panel.add(lblPassword);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Mã hóa mật khẩu bằng SHA-256
            String hashedPassword = hashPassword(password);

            try {
                // Kết nối đến cơ sở dữ liệu
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/logink2", "root", "12345");

                // Tạo câu truy vấn để chèn dữ liệu vào bảng
                String query = "INSERT INTO logink2.users (username, password) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashedPassword);

                // Thực thi câu truy vấn
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Registration successful!");

                // Đóng kết nối
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Registration failed!");
            }
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        new RegisterForm();
    }
}
