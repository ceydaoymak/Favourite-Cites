import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class loginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton login, back, register;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favouriteCities";
    static final String USER = "root";
    static final String PASS = "Portakalelma1122";

    public loginFrame() {
        setTitle("Login");
        setSize(500, 450);
        setLayout(new GridLayout(7, 4, 5, 5));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        login = new JButton("Login");
        register = new JButton("Register");

        back = new JButton("Back");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);

        add(login);
        add(back);


        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                String enteredPassword = new String(passwordField.getPassword());
                if (validateLogin(enteredUsername, enteredPassword)) {
                    FunctionOptions f = new FunctionOptions(enteredUsername);
                    f.setVisible(true);
                    dispose();
                }

            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
                dispose();
            }
        });
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterForm();
                dispose();
            }
        });

    }
    private boolean validateLogin(String enteredusername, String enteredpassword) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isValidUser = false;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT * FROM userinfo WHERE user_name = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, enteredusername);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (enteredpassword.equals(storedPassword)) {
                    isValidUser = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect password. Please try again.");
                }
            } else {
                add(register);
                revalidate();
                repaint();
                JOptionPane.showMessageDialog(this, "User does not exist. Please register first.");
            }

        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred.");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return isValidUser;
    }

    public void goBack() {
        entrence entrence = new entrence();
        entrence.setVisible(true);
        this.setVisible(false);
    }

    public void openRegisterForm() {
        RegisterFrame registerFrame = new RegisterFrame();
        registerFrame.setVisible(true);
        this.setVisible(false);
    }
}