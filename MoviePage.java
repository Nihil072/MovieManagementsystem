package movie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MoviePage extends JFrame {

    JTextField title, genre, year, rating;
    JTable table;
    DefaultTableModel model;

    public MoviePage() {

        setTitle("Movie Management");
        setSize(800, 450);
        setLayout(null);

        getContentPane().setBackground(new Color(50, 205, 50));

        // LABELS
        JLabel l1 = new JLabel("Title");
        JLabel l2 = new JLabel("Genre");
        JLabel l3 = new JLabel("Year");
        JLabel l4 = new JLabel("Rating");

        l1.setBounds(30, 50, 100, 25);
        l2.setBounds(30, 90, 100, 25);
        l3.setBounds(30, 130, 100, 25);
        l4.setBounds(30, 170, 100, 25);

        add(l1); add(l2); add(l3); add(l4);

        // TEXTFIELDS
        title = new JTextField();
        genre = new JTextField();
        year = new JTextField();
        rating = new JTextField();

        title.setBounds(120, 50, 150, 25);
        genre.setBounds(120, 90, 150, 25);
        year.setBounds(120, 130, 150, 25);
        rating.setBounds(120, 170, 150, 25);

        add(title); add(genre); add(year); add(rating);

        // BUTTONS
        JButton addBtn = new JButton("Add");
        JButton loadBtn = new JButton("Load");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        addBtn.setBounds(30, 230, 100, 30);
        loadBtn.setBounds(150, 230, 100, 30);
        updateBtn.setBounds(30, 280, 100, 30);
        deleteBtn.setBounds(150, 280, 100, 30);

        add(addBtn); add(loadBtn);
        add(updateBtn); add(deleteBtn);

        // TABLE
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID","Title","Genre","Year","Rating"});

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(300, 50, 450, 300);
        add(sp);

        // TABLE CLICK → FILL FORM
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                int row = table.getSelectedRow();

                title.setText(model.getValueAt(row, 1).toString());
                genre.setText(model.getValueAt(row, 2).toString());
                year.setText(model.getValueAt(row, 3).toString());
                rating.setText(model.getValueAt(row, 4).toString());
            }
        });

        // ADD
        addBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();

                String sql = "INSERT INTO movies(title,genre,year,rating) VALUES (?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, title.getText());
                ps.setString(2, genre.getText());
                ps.setInt(3, Integer.parseInt(year.getText()));
                ps.setDouble(4, Double.parseDouble(rating.getText()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Movie Added!");

                loadBtn.doClick();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // LOAD
        loadBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM movies");

                model.setRowCount(0);

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("genre"),
                            rs.getInt("year"),
                            rs.getDouble("rating")
                    });
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // UPDATE
        updateBtn.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();

                if(row == -1) {
                    JOptionPane.showMessageDialog(this, "Select a row!");
                    return;
                }

                int id = (int) model.getValueAt(row, 0);

                Connection con = DBConnection.getConnection();

                String sql = "UPDATE movies SET title=?, genre=?, year=?, rating=? WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, title.getText());
                ps.setString(2, genre.getText());
                ps.setInt(3, Integer.parseInt(year.getText()));
                ps.setDouble(4, Double.parseDouble(rating.getText()));
                ps.setInt(5, id);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Updated!");

                loadBtn.doClick();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // DELETE
        deleteBtn.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();

                if(row == -1) {
                    JOptionPane.showMessageDialog(this, "Select a row!");
                    return;
                }

                int id = (int) model.getValueAt(row, 0);

                Connection con = DBConnection.getConnection();

                String sql = "DELETE FROM movies WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, id);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Deleted!");

                loadBtn.doClick();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}