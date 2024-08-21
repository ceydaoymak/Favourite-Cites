import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class shareLocations extends JFrame {
    private JTextField friendsName, visitID;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favouriteCities";
    static final String USER = "root";
    static final String PASS = "Portakalelma1122";
    private String username;
    private JButton share, display,back;
    private JTable table;
    private DefaultTableModel tableModel;

    shareLocations(String name) {
        this.username = name;
        setTitle("Sharing Page");
        setSize(500, 350);
        setLayout(new GridLayout(10, 2, 5, 5));
        friendsName = new JTextField();
        visitID = new JTextField();
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        share = new JButton("Share");
        display = new JButton("Display shared visits");
        back=new JButton("Back");

        add(new JLabel("Enter your friend's username:"));
        add(friendsName);
        add(new JLabel("Enter the visit id that you want to share with your friend:"));
        add(visitID);
        add(share);
        add(display);
        add(back);

        share.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shareVisit();

            }
        });
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySharedVisits();
            }

        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

    }

    private void shareVisit() {
        String friendName = friendsName.getText();
        int visitId = Integer.parseInt(visitID.getText());
        String yourUserName = this.username;

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO sharedvisits (visit_id, friend_username, user_username) VALUES (?, ?, ?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, visitId);
            preparedStatement.setString(2, friendName);
            preparedStatement.setString(3, yourUserName);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Visit shared successfully!");
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "This visit is already shared with the specified friend.");
        } catch (SQLException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error sharing the visit.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred.");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void displaySharedVisits() {
        try {

            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT v.country_name as Country, v.city_name as City, v.best_feature as 'Best feature', v.season_visited as Season,v.year_visited as Year,v.comments as Comment,v.rating as Rate,s.friend_username as Friend , s.user_username as User " +
                    "FROM visits v " +
                    "JOIN sharedvisits s ON v.id = s.visit_id";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnLabel(columnIndex));
            }

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);
            }

            rs.close();
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame tableFrame = new JFrame("Shared Visits");
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.getContentPane().add(new JScrollPane(table));
        tableFrame.pack();
        tableFrame.setVisible(true);

    }
    public void goBack(){
        FunctionOptions f=new FunctionOptions(username);
        f.setVisible(true);
        this.setVisible(false);
    }
}