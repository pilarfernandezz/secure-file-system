package views;

import facade.Facade;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;

@SuppressWarnings("serial")
public class Frame extends JFrame{
    protected JPanel panel = new JPanel();
    JLabel login;
    JLabel userGroup;
    JLabel name;

    public Frame() throws SQLException {
        this.setSize(800, 600);
        setLayout(null);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.WHITE);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Secure File System");

        if(Facade.getLoggedUser()!= null) {
            login = new JLabel(Facade.getLoggedUser().getEmail());
            login.setBounds(50, -250, 800, 600);
            this.panel.setBounds(0, 0, 800, 600);
            this.login.setVisible(false);
            this.panel.add(login);

            userGroup = new JLabel(Facade.getLoggedUser().getGroup());
            userGroup.setBounds(350, -250, 800, 600);
            this.panel.setBounds(0, 0, 800, 600);
            this.userGroup.setVisible(false);
            this.panel.add(userGroup);

            name = new JLabel(Facade.getLoggedUser().getName());
            name.setBounds(550, -250, 800, 600);
            this.panel.setBounds(0, 0, 800, 600);
            this.name.setVisible(false);
            this.panel.add(name);
        }

        this.panel.setBackground(Color.WHITE);
        this.panel.setVisible(true);
        this.getContentPane().add(panel);
    }

    public void showHeader(){
        this.login.setVisible(true);
        this.name.setVisible(true);
        this.userGroup.setVisible(true);
    }
}