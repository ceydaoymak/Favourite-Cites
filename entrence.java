    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;

    public class entrence extends JFrame {
        private JButton login, register,guest;

        entrence() {
            setTitle("Entrence");
            setSize(600, 250);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(4, 2, 5, 5));

            login = new JButton("Login if you already have an account");
            register = new JButton("Register if you are a new user");
            guest=new JButton("Guest");

            add(login);
            add(register);
            add(guest);

            login.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openLoginForm();
                }
            });
            register.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openRegisterForm();
                }
            });
            guest.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openGuestForm();
                }
            });

        }

        public void openLoginForm() {
            loginFrame l = new loginFrame();
            l.setVisible(true);
            this.setVisible(false);
        }

        public void openRegisterForm() {
            RegisterFrame r = new RegisterFrame();
            r.setVisible(true);
            this.setVisible(false);
        }
        public void openGuestForm(){
            guestForm g=new guestForm();
            g.setVisible(true);
            this.setVisible(false);
        }
    }