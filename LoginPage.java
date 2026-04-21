package movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {

    JTextField user;
    JPasswordField pass;

    public LoginPage() {

        setTitle("Movie System Login");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(30, 144, 255)); // BLUE

        JLabel title = new JLabel("MOVIE LOGIN");
        title.setBounds(140, 20, 200, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title);

        JLabel u1 = new JLabel("Username:");
        u1.setBounds(50, 80, 100, 25);
        u1.setForeground(Color.WHITE);
        add(u1);

        user = new JTextField();
        user.setBounds(150, 80, 180, 25);
        add(user);

        JLabel p1 = new JLabel("Password:");
        p1.setBounds(50, 120, 100, 25);
        p1.setForeground(Color.WHITE);
        add(p1);

        pass = new JPasswordField();
        pass.setBounds(150, 120, 180, 25);
        add(pass);

        JButton login = new JButton("LOGIN");
        login.setBounds(150, 170, 100, 30);
        login.setBackground(Color.GREEN);
        add(login);

        login.addActionListener(e -> checkLogin());

        setVisible(true);
    }

    public void checkLogin() {

        String u = user.getText();
        String p = new String(pass.getPassword());

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM login WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u);
            ps.setString(2, p);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Success");
                new Dashboard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}