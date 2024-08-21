import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField, passwordField2;
    private JButton register;
    private JButton login, back;
    private JLabel errorMessageLabel;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favouriteCities";
    static final String USER = "root";
    static final String PASS = "Portakalelma1122";

    RegisterFrame() {
        setTitle("Register");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 3, 3, 2));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        passwordField2 = new JPasswordField();
        register = new JButton("Register");
        login = new JButton("Login");
        back = new JButton("Back");
        add(new JLabel("Enter your username:"));
        add(usernameField);

        add(new JLabel("Enter your password:"));
        add(passwordField);

        add(new JLabel("Enter your password again:"));
        add(passwordField2);

        add(register);

        errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(Color.RED);
        add(errorMessageLabel);
        add(back);

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateRegister()) {
                    String Username = usernameField.getText();
                    String Password = new String(passwordField.getPassword());
                    saveToDatabase(Username, Password);
                    add(login);
                    revalidate();
                    repaint();
                    JOptionPane.showMessageDialog(null, "Your registration is done!! Now you have to login to the system.", "Message", JOptionPane.INFORMATION_MESSAGE);

                    login.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            openLoginForm();
                        }
                    });
                }
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }

    private boolean validateRegister() {
        boolean hasDigit = false;
        boolean hasLetter = false;

        if (usernameField.getText().isEmpty()) {
            errorMessageLabel.setText("Please enter a username.");
            return false;
        }
        String password = new String(passwordField.getPassword());

        if (password.isEmpty()) {
            errorMessageLabel.setText("Please enter a password.");
            return false;
        }

        if (!Arrays.equals(passwordField2.getPassword(), passwordField.getPassword())) {
            errorMessageLabel.setText("Please enter the same password");
            return false;
        }

        if (passwordField.getPassword().length < 8) {
            errorMessageLabel.setText("Password should be at least 8 characters long.");
            return false;
        }
        for (char c : passwordField.getPassword()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLetter(c)) {
                hasLetter = true;
            }
        }

        if (!hasDigit || !hasLetter) {
            errorMessageLabel.setText("Password should contain at least one digit and one letter.");
            return false;
        }

        errorMessageLabel.setText("");

        return true;
    }

    private void saveToDatabase(String enteredusername, String enteredpassword) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO userinfo (user_name,password) VALUES (?, ?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, enteredusername);
            preparedStatement.setString(2, enteredpassword);

            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "User info saved successfully.");
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
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

    public void openLoginForm() {
        loginFrame loginFrame = new loginFrame();
        loginFrame.setVisible(true);
        this.setVisible(false);
    }

    public void goBack() {
        entrence entrence = new entrence();
        entrence.setVisible(true);
        this.setVisible(false);
    }
}