import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class mainFrame extends JFrame {
    private JTextField CountryName, CityName, BestFeature, Comments;
    JRadioButton Winter, Spring, Summer, Autumn;
    private JComboBox<Integer> Year, Rating;
    private JButton submit,back;
    private String selectedSeason;
    private String name;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favouriteCities";
    static final String USER = "root";
    static final String PASS = "Portakalelma1122";
    private String username;

    mainFrame(String name) {
        this.username = name;

        setTitle("Add Page");
        setSize(500, 350);
        setLayout(new GridLayout(10, 2, 5, 5));
        Year = new JComboBox<>();

        for (int i = 1980; i <= 2024; i++) {
            Year.addItem(i);
        }
        Rating = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            Rating.addItem(i);
        }
        loginFrame l = new loginFrame();


        CountryName = new JTextField();
        CityName = new JTextField();
        BestFeature = new JTextField();
        Comments = new JTextField();

        ButtonGroup b = new ButtonGroup();
        Winter = new JRadioButton("Winter");
        Spring = new JRadioButton("Spring");
        Summer = new JRadioButton("Summer");
        Autumn = new JRadioButton("Autumn");
        submit = new JButton("Submit");

        back=new JButton("Back");

        b.add(Winter);
        b.add(Spring);
        b.add(Summer);
        b.add(Autumn);

        add(new JLabel("Country Name:"));
        add(CountryName);
        add(new JLabel("City Name:"));
        add(CityName);
        add(new JLabel("Year Visited:"));
        add(Year);


        add(new JLabel("Season:"));
        add(new JLabel(""));
        add(Winter);
        add(Spring);

        add(Summer);
        add(Autumn);
        add(new JLabel("Best Feature:"));
        add(BestFeature);
        add(new JLabel("Comments"));
        add(Comments);
        add(new JLabel("Rating"));
        add(Rating);
        add(submit);
        add(back);

        Winter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSeason = "Winter";
            }
        });

        Spring.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSeason = "Spring";
            }
        });

        Summer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSeason = "Summer";
            }
        });

        Autumn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSeason = "Autumn";
            }
        });

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String countryname = CountryName.getText();
                String cityname = CityName.getText();

                String selectedseason = selectedSeason;
                String bestfeature = BestFeature.getText();
                String comments = Comments.getText();

                int year = (int) Year.getSelectedItem();
                int rating = (int) Rating.getSelectedItem();

                saveToDatabase( username,countryname, cityname, year,  selectedseason,bestfeature, comments, rating);
                setVisible(false);
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }

    private void saveToDatabase( String username,String countryname, String cityname, int year, String selectedseason, String bestfeature, String comments,int rating) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO visits (user_name,country_name, city_name, year_visited , season_visited, best_feature,  comments, rating) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, countryname);
            st.setString(3, cityname);
            st.setInt(4, year);
            st.setString(5, selectedseason);
            st.setString(6, bestfeature);
            st.setString(7, comments);
            st.setInt(8, rating);

            st.executeUpdate();

            JOptionPane.showMessageDialog(this, "The informations saved to database successfully!!");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void goBack(){
        FunctionOptions functionOptions=new FunctionOptions(username);
        functionOptions.setVisible(true);

    }
}
