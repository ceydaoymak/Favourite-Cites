import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class displayingSpesific extends JFrame {
    private JComboBox<Integer> year;
    private JButton display, back;
    private JComboBox<String> displayPrefers;
    private JTextField id;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favouriteCities";
    static final String USER = "root";
    static final String PASS = "Portakalelma1122";
    String username;

    public class CountryRating {
        private String countryName;
        private int rating;

        public CountryRating(String countryName, int rating) {
            this.countryName = countryName;
            this.rating = rating;
        }

        public String getCountryName() {
            return countryName;
        }

        public int getRating() {
            return rating;
        }
    }

    displayingSpesific(String name) {
        this.username = name;
        setTitle("Display Page");
        setSize(500, 350);
        setLayout(new BorderLayout(5, 5));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 2, 5, 5));

        display = new JButton("Display");
        displayPrefers = new JComboBox<>();
        deleteLocations d = new deleteLocations(username);
        year = new JComboBox<>();
        back = new JButton("Back");


        String[] preferences = {
                "Select an option",
                "The countries who's best feature is food, sorted by their ratings",
                "Display the images of the locations",
                "Display the information of all visits done in the given year",
                "Display the country names that you visited the most",
                "Display the country name(s) that are visited only in spring"
        };

        displayPrefers = new JComboBox<>(preferences);
       id=new JTextField();

        mainPanel.add(displayPrefers);

        JLabel yearLabel = new JLabel("Please enter the year you want to display");

        displayPrefers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) displayPrefers.getSelectedItem();
                mainPanel.remove(yearLabel);
                mainPanel.remove(year);
                mainPanel.remove(id);
                mainPanel.remove(display);

                if (selectedOption.equals("Display the information of all visits done in the given year")) {
                    mainPanel.add(yearLabel);
                    mainPanel.add(year);
                    loadYears();
                } else if (selectedOption.equals("Display the images of the locations")) {
                    mainPanel.add(id);
                }
                mainPanel.add(display);
                revalidate();
                repaint();
            }
        });

        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) displayPrefers.getSelectedItem();
                if (selectedOption.equals("Display the information of all visits done in the given year")) {
                    int selectedYear = (int) year.getSelectedItem();
                    locationsAccordingtoYears(selectedYear);
                } else if (selectedOption.equals("The countries who's best feature is food, sorted by their ratings")) {
                    LocationsBestFeatureFood();
                } else if (selectedOption.equals("Display the country name(s) that are visited only in spring")) {
                    LocationsVisitedinSpring();
                } else if (selectedOption.equals("Display the country names that you visited the most")) {
                    LocationVisitedMost();
                } else if (selectedOption.equals("Display the images of the locations")) {
                    displayImageForCity();
                }
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        add(mainPanel, BorderLayout.CENTER);
        add(back, BorderLayout.SOUTH);
    }

    private void loadYears() {
        year.removeAllItems();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT DISTINCT year_visited FROM visits";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int yearValue = rs.getInt("year_visited");
                year.addItem(yearValue);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public void showResultsInNewWindow(DefaultListModel<String> listModel, String title) {
        JFrame resultFrame = new JFrame(title);
        resultFrame.setSize(350, 200);
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JList<String> resultList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(resultList);
        resultFrame.add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultFrame.dispose();
                displayingSpesific.this.setVisible(true);
            }
        });

        resultFrame.add(backButton, BorderLayout.SOUTH);

        resultFrame.setVisible(true);
        this.dispose();
    }

    public void locationsAccordingtoYears(int year) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM visits WHERE year_visited = " + year;
            ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String countryName = rs.getString("country_name");
                    listModel.addElement(countryName);
                }
                showResultsInNewWindow(listModel, "Countries visited in " + year);
            }
         catch (SQLException se) {
             se.printStackTrace();
         }
    }

    public void LocationsBestFeatureFood() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        ArrayList<CountryRating> countryRatings = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT country_name, rating FROM visits WHERE best_feature = 'food'";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.isBeforeFirst()) {
                listModel.addElement("No visits with the best feature as food recorded yet.");
            } else {
                while (rs.next()) {
                    String countryName = rs.getString("country_name");
                    int rating = rs.getInt("rating");
                    countryRatings.add(new CountryRating(countryName, rating));
                }

                countryRatings.sort(Comparator.comparingInt(CountryRating::getRating).reversed());

                for (CountryRating countryRating : countryRatings) {
                    listModel.addElement(countryRating.getCountryName());
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        showResultsInNewWindow(listModel, "Countries with Best Food Feature");
    }

    public void LocationsVisitedinSpring() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT country_name FROM visits WHERE season_visited = 'spring'";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "No visits recorded in spring.");
            } else {
                while (rs.next()) {
                    String countryName = rs.getString("country_name");
                    listModel.addElement(countryName);
                }
                showResultsInNewWindow(listModel, "Countries visited in Spring");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void LocationVisitedMost() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT country_name, COUNT(country_name) FROM visits GROUP BY country_name ORDER BY COUNT(*) DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.isBeforeFirst()) {
                listModel.addElement("No visits recorded yet.");
            } else {
                if (rs.next()) {
                    String mostVisitedCountry = rs.getString("country_name");
                    listModel.addElement(mostVisitedCountry);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        showResultsInNewWindow(listModel, "Most Visited Country");
    }
    private void displayImageForCity() {
        String enteredID=(String) id.getText();
        Map<String, String> countryImageMap = new HashMap<>();
        countryImageMap.put("Istanbul", "src/istanbul.jpeg");
        countryImageMap.put("Madrid", "src/madrid.jpeg");
        countryImageMap.put("Paris", "src/paris.jpeg");
        countryImageMap.put("Venice", "src/venice.jpeg");
        countryImageMap.put("Rome", "src/rome.jpeg");

        String query = "SELECT city_name FROM visits WHERE id = " + enteredID;
        String cityName = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                cityName = rs.getString("city_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (cityName != null) {
            String imagePath = countryImageMap.get(cityName);

            if (imagePath != null) {
                JFrame frame = new JFrame(cityName + " The image:");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ImageIcon imageIcon = new ImageIcon(imagePath);
                JLabel label = new JLabel(imageIcon);

                frame.getContentPane().add(label, BorderLayout.CENTER);
                frame.setSize(800, 600);
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "There is not any picture downloaded for this city.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Could not find and city according to this ID.");
        }
    }
    public void goBack() {
        displayLocations displayLocations = new displayLocations(username);
        displayLocations.setVisible(true);
        this.dispose();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            displayingSpesific frame = new displayingSpesific("username");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
