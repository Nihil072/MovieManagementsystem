package movie;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("Movie Dashboard");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(255, 140, 0)); // ORANGE

        JLabel l = new JLabel("WELCOME TO MOVIE SYSTEM");
        l.setBounds(120, 50, 300, 30);
        l.setFont(new Font("Arial", Font.BOLD, 18));
        add(l);

        JButton movieBtn = new JButton("MANAGE MOVIES");
        movieBtn.setBounds(150, 150, 180, 40);
        add(movieBtn);

        movieBtn.addActionListener(e -> new MoviePage());

        setVisible(true);
    }
}