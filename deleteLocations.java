import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class deleteLocations extends JFrame {
    private JComboBox<Integer> idNo;
    private JButton delete, back, clear;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favouriteCities";
    static final String USER = "root";
    static final String PASS = "Portakalelma1122";
    private String username;


    public deleteLocations(String name) {
        this.username = name;
        setTitle("Deleting Page");
        setSize(500, 350);
        setLayout(new GridLayout(10, 2, 5, 5));

        idNo = new JComboBox<Integer>();
        delete = new JButton("Delete");
        back = new JButton("Back");
        clear = new JButton("Clear all tables");

        add(new JLabel("Please enter the id no of the location that you want to delete"));
        add(idNo);
        add(delete);
        add(clear);
        add(back);
        fillComboBox();

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedId = (Integer) idNo.getSelectedItem();
                deleteRecord(selectedId);
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear all tables?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    clearTables();
                    RegisterFrame r=new RegisterFrame();
                    r.setVisible(true);
                    dispose();
                }
            }
        });
    }


    public int getMaxId() {
        Connection conn = null;
        Statement stmt = null;
        int maxId = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS max_id FROM visits");
            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return maxId;
    }

    private void fillComboBox() {
        for (int i = 1; i <= getMaxId(); i++) {
            idNo.addItem(i);
        }
    }

    private void deleteRecord(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "DELETE FROM visits WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record deleted successfully");
            idNo.removeItem(id);
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void goBack() {
        FunctionOptions functionOptions = new FunctionOptions(username);
        functionOptions.setVisible(true);
        this.dispose();
    }

    public void clearTables() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").execute();

            String sql1 = "TRUNCATE TABLE visits";
            String sql2 = "TRUNCATE TABLE Sharedvisits";
            String sql3 = "TRUNCATE TABLE userinfo";

            pstmt = conn.prepareStatement(sql1);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(sql2);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(sql3);
            pstmt.executeUpdate();

            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").execute();

            JOptionPane.showMessageDialog(this, "Records deleted successfully");
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}

