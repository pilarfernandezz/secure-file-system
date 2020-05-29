package views;

import facade.Facade;

import javax.swing.*;
import java.awt.*;

public class FrameConsult extends JFrame{
    protected JPanel panel = new JPanel();
    JLabel login;
    JLabel userGroup;
    JLabel name;

    public FrameConsult() {
        this.setSize(1600, 950);
        setLayout(null);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.WHITE);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sistema de arquivos seguro");
        this.panel.setBounds(0, 0, 1600, 950);

        if(Facade.getLoggedUser()!= null) {
            login = new JLabel(Facade.getLoggedUser().getEmail());
            login.setBounds(150, -250, 800, 600);
            this.login.setVisible(false);
            this.panel.add(login);

            userGroup = new JLabel(Facade.getLoggedUser().getGroup());
            userGroup.setBounds(700, -250, 800, 600);
            this.userGroup.setVisible(false);
            this.panel.add(userGroup);

            name = new JLabel(Facade.getLoggedUser().getName());
            name.setBounds(1400, -250, 800, 600);
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