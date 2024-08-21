import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FunctionOptions extends JFrame {
    private JButton add, delete, display, edit,back,share;
    private String username;
    FunctionOptions(String username){
        this.username=username;
        setTitle("Menu");
        setSize(500, 450);
        setLayout(new GridLayout(7, 4, 5, 5));

        add = new JButton("Add new locations");
        delete = new JButton("Delete locations");
        display = new JButton("Display your favourite locations");
        edit = new JButton("Edit your favourite locations");
        share=new JButton("Share your favourite locations");
        back=new JButton("Back");

        add(add);
        add(delete);
        add(display);
        add(edit);
        add(share);
        add(back);

        add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               openMainForm(username);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            openDeletingForm();}
        });
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDisplayForm();
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditForm();
            }
        });
        share.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShareForm();
            }
        });
    }
    public String getUsername() {
        return username;
    }


    private void openMainForm(String name) {
        mainFrame mainForm = new mainFrame(name);
        mainForm.setVisible(true);

    }

    private void openDeletingForm() {
        deleteLocations d = new deleteLocations(username);
        d.setVisible(true);
        this.dispose();
    }

    private void openDisplayForm() {
        displayLocations de = new displayLocations(username);
        de.setVisible(true);
        this.setVisible(false);

    }
    private void openEditForm(){
        EditLocations e=new EditLocations(username);
        e.setVisible(true);
        this.setVisible(false);

    }
    private void openShareForm(){
        shareLocations s=new shareLocations(username);
        s.setVisible(true);
        this.setVisible(false);
    }
    public void goBack(){
        entrence entrence=new entrence();
        entrence.setVisible(true);
        this.setVisible(false);

    }
}
