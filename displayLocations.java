import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class displayLocations extends JFrame {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favouriteCities";
    static final String USER = "root";
    static final String PASS = "Portakalelma1122";
    private JComboBox<Integer> idNo;
    private JButton display, back;
    private JButton displayMoreSpesific;
    String username;

    displayLocations(String name) {
        this.username=name;
        setTitle("Display Page");
        setSize(400, 350);
        setLayout(new GridLayout(5, 5, 2, 2));

        display = new JButton("Display");
        displayMoreSpesific = new JButton("Click to display more spesific");
        back = new JButton("Back");

        deleteLocations d = new deleteLocations(username);

        idNo = new JComboBox<>();
        int maxid = d.getMaxId();

        for (int i = 1; i <= maxid; i++) {
            idNo.addItem(i);
        }
        add(idNo);
        add(display);
        add(displayMoreSpesific);
        add(back);
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedId = (Integer) idNo.getSelectedItem();
                displayData(selectedId);
            }
        });
        displayMoreSpesific.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opendisplayingSpesificForm();
                dispose();
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }

    private void displayData(int id) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT city_name,country_name,comments,season_visited,rating,best_feature FROM visits WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String city = rs.getString("city_name");
                String country = rs.getString("country_name");
                String comment = rs.getString("comments");
                String season = rs.getString("season_visited");
                String bestFeature = rs.getString("best_feature");
                int rating = rs.getInt("rating");
                String message = "City: " + city + "\nCountry: " + country + "\nComment: " + comment + "\nSeason Visited: " +  season + "\nBest Feature:" + bestFeature + "\nRating: " + rating;
                JOptionPane.showMessageDialog(null, message);
            } else {
                JOptionPane.showMessageDialog(null, "No data found for ID: " + id);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void opendisplayingSpesificForm() {
        displayingSpesific d = new displayingSpesific(username);
        d.setVisible(true);
    }

    public void goBack() {
        FunctionOptions functionOptions = new FunctionOptions(username);
        functionOptions.setVisible(true);
        this.dispose();
    }
}