import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class guestForm extends JFrame {
    static final String DB_URL = "jdbc:mysql://localhost/favouriteCities";
    static final String USER = "root";
    static final String PASS = "Portakalelma1122";

    public guestForm() {
        setTitle("Guest Page");
        setSize(450, 300);
        setLayout(new BorderLayout());

        String[] columnNames = {"Country Name", "City Name", "Year Visited", "Season Visited", "Best Feature", "Rating"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM visits";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String countryName = rs.getString("country_name");
                String cityName = rs.getString("city_name");
                int yearVisited = rs.getInt("year_visited");
                String seasonVisited = rs.getString("season_visited");
                String bestFeature = rs.getString("best_feature");
                int rating = rs.getInt("rating");
                tableModel.addRow(new Object[]{countryName, cityName, yearVisited, seasonVisited, bestFeature, rating});
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        JButton registerButton = new JButton("Register if you don't have any account");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterForm();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void goBack() {
        entrence e = new entrence();
        e.setVisible(true);
        this.dispose();
    }

    public void openRegisterForm() {
        RegisterFrame r = new RegisterFrame();
        r.setVisible(true);
        this.dispose();
    }
}