package views;

import facade.Facade;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class PasswordView extends Frame implements ActionListener {
    private static EmailView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JLabel lblAlert;
    private static JTextField txtEmail = null;
    private static JPasswordField passPassword = null;
    private static JButton btnStart;
    private static JButton btnCancel;
    private static String email;
    private static int passwordErrors = 0;

    public PasswordView(){
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -250, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblText = new JLabel("Digite seu login:");
        lblText.setBounds(340, 100, 200, 50);
        this.panel.add(lblText);

        lblEmail = new JLabel("Senha:");
        lblEmail.setBounds(250, 150, 100, 50);
        this.panel.add(lblEmail);

        passPassword = new JPasswordField(8);
        passPassword.setBounds(320, 160, 200, 30);
        this.panel.add(passPassword);

        lblAlert = new JLabel("Senha incorreta.");
        lblAlert.setForeground(Color.red);
        lblAlert.setBounds(320, 200, 200, 50);
        this.panel.add(lblAlert);
        lblAlert.setVisible(false);

        btnStart = new JButton("Entrar");
        btnStart.setBounds(280, 300, 100, 40);
        this.panel.add(btnStart);
        btnStart.addActionListener(this);

        btnCancel = new JButton("Cancelar");
        btnCancel.setBounds(390, 300, 100, 40);
        this.panel.add(btnCancel);
        btnCancel.addActionListener(this);

        this.panel.setLayout(null);
        this.panel.setVisible(true);
        this.panel.setBackground(Color.WHITE);
        this.getContentPane().add(panel);

        this.setLayout(null);
        this.setVisible(true);
    }

    public static void showScreen(String email){
        PasswordView.email = email;
        new PasswordView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnStart){
            if(this.passwordErrors > 2){
                EmailView.showScreen();
            }
            try {
                System.out.println(passPassword.getText());
                if(Facade.getFacadeInstance().checkPassword(PasswordView.email, passPassword.getText())){
                    this.setVisible(false);
                    this.dispose();
                    MenuView.showScreen();
                } else {
                    this.passwordErrors++;
                    lblAlert.setVisible(true);
                    this.panel.repaint();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if(e.getSource() == btnCancel){
            System.exit(1);
        }
    }
}
