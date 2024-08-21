import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EditLocations extends JFrame {
    private JComboBox<String> feature;
    private JLabel IDLabel, CountryNameLabel, CityNameLabel, YearLabel, SeasonLabel, BestFeatureLabel, CommentLabel, RatingLabel;
    private JTextField IDField, CountryNameField, CityNameField, YearField, SeasonField, BestFeatureField, CommentField, RatingField;
    private JButton edit,back;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favouriteCities";
    static final String USER = "root";
    static final String PASS = "Portakalelma1122";
    private String username;

    EditLocations(String name) {
        this.username = name;
        setTitle("Edit Page");
        setSize(500, 350);
        setLayout(new GridLayout(10, 2, 5, 5));

        deleteLocations d = new deleteLocations(username);
        int maxid = d.getMaxId();

        String[] features = {"Choose an option",
                "Country Name",
                "City Name",
                "Year",
                "Season",
                "Best Feature",
                "Comment",
                "Rating"
        };
        IDField = new JTextField();
        IDLabel = new JLabel("Choose the location's id that you want to update");
        feature = new JComboBox<>(features);
        CountryNameLabel = new JLabel("Country Name:");
        CountryNameField = new JTextField();
        CityNameField = new JTextField();
        CityNameLabel = new JLabel("City Name:");
        YearField = new JTextField();
        YearLabel = new JLabel("Year:");
        SeasonField = new JTextField();
        SeasonLabel = new JLabel("Season:");
        BestFeatureField = new JTextField();
        BestFeatureLabel = new JLabel("Best Feature:");
        CommentField = new JTextField();
        CommentLabel = new JLabel("Comments:");
        RatingField = new JTextField();
        RatingLabel = new JLabel("Rating: ");
        edit = new JButton("Edit");
        back=new JButton("Back");

        add(IDLabel);
        add(IDField);

        add(new JLabel("Choose the feature you want to edit/update"));
        add(feature);


        feature.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) feature.getSelectedItem();
                if (selectedOption.equals("Country Name")) {
                    removeFeatures();
                    edit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            editCountryName();
                        }
                    });

                    add(CountryNameLabel);
                    add(CountryNameField);
                    add(edit);
                    revalidate();
                    repaint();

                }
                if (selectedOption.equals("City Name")) {
                    removeFeatures();
                    edit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            editCityName();
                        }
                    });
                    add(CityNameLabel);
                    add(CityNameField);
                    add(edit);
                    revalidate();
                    repaint();
                }
                if (selectedOption.equals("Year")) {
                    removeFeatures();
                    edit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            editYear();
                        }
                    });
                    add(YearLabel);
                    add(YearField);
                    add(edit);
                    revalidate();
                    repaint();
                }
                if (selectedOption.equals("Season")) {
                    removeFeatures();
                    edit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            editSeason();
                        }
                    });
                    add(SeasonLabel);
                    add(SeasonField);
                    add(edit);
                    revalidate();
                    repaint();
                }
                if (selectedOption.equals("Rating")) {
                    removeFeatures();
                    edit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            editRating();
                        }
                    });
                    add(RatingLabel);
                    add(RatingField);
                    add(edit);
                    revalidate();
                    repaint();
                }
                if (selectedOption.equals("Comment")) {
                    removeFeatures();
                    edit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            editComment();
                        }
                    });
                    add(CommentLabel);
                    add(CommentField);
                    add(edit);
                    revalidate();
                    repaint();
                }
                if(selectedOption.equals("Best Feature")){
                    removeFeatures();
                    edit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            editBestFeature();
                        }
                    });
                    add(BestFeatureLabel);
                    add(BestFeatureField);
                    add(edit);
                    revalidate();
                    repaint();
                }
            }
        });

        add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FunctionOptions f=new FunctionOptions(username);
                f.setVisible(true);
                dispose();
            }
        });
    }

    private void editCountryName() {
        String EnteredCountryName = CountryNameField.getText();
        String newCountryName = EnteredCountryName;
        String ID = IDField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "UPDATE visits SET country_name = '" + newCountryName + "' WHERE ID = " + ID;
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Update successful!!.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    private void editCityName() {
        String EnteredCiyName = CityNameField.getText();
        String newCityName = EnteredCiyName;
        String ID = IDField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "UPDATE visits SET city_name = '" + newCityName + "' WHERE ID = " + ID;
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Update successful!!.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void editYear() {
        String EnteredYear = YearField.getText();
        String newYear = EnteredYear;
        String ID = IDField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "UPDATE visits SET year_visited = '" + newYear + "' WHERE ID = " + ID;
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Update successful!!.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void editSeason() {
        String EnteredSeason = SeasonField.getText();
        String newSeason = EnteredSeason;
        String ID = IDField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "UPDATE visits SET season_visited = '" + newSeason + "' WHERE ID = " + ID;
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Update successful!!.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void editRating() {
        String EnteredRating = RatingField.getText();
        String newSeason = EnteredRating;
        String ID = IDField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "UPDATE visits SET rating = '" + newSeason + "' WHERE ID = " + ID;
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Update successful!!.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        } catch (SQLException se) {
            se.printStackTrace();
        }

    }

    private void editComment() {
        String EnteredComment = CommentField.getText();
        String newComment = EnteredComment;
        String ID = IDField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "UPDATE visits SET comments = '" + newComment + "' WHERE ID = " + ID;
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Update successful!!.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    private void editBestFeature() {
        String EnteredBestFeature = BestFeatureField.getText();
        String newBestFeature = EnteredBestFeature;
        String ID = IDField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "UPDATE visits SET best_feature = '" + newBestFeature + "' WHERE ID = " + ID;
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Update successful!!.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    private void removeFeatures() {
        remove(CountryNameLabel);
        remove(CountryNameField);
        remove(CityNameField);
        remove(CityNameLabel);
        remove(CountryNameLabel);
        remove(YearField);
        remove(YearLabel);
        remove(SeasonField);
        remove(SeasonLabel);
        remove(BestFeatureLabel);
        remove(BestFeatureField);
        remove(CommentField);
        remove(CommentLabel);
        remove(CityNameLabel);
        remove(RatingLabel);
        remove(RatingField);
        remove(edit);

    }
}