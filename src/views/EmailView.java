package views;

import facade.Facade;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EmailView extends Frame implements ActionListener {
    private static EmailView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblEmail;
    private JLabel lblAlert;
    private static JTextField txtEmail = null;
    private static JButton btnStart;
    private static JButton btnCancel;

    public EmailView(){
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -250, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblText = new JLabel("Faça seu Login:");
        lblText.setBounds(340, 100, 200, 50);
        this.panel.add(lblText);

        lblEmail = new JLabel("Email:");
        lblEmail.setBounds(250, 150, 100, 50);
        this.panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(320, 160, 200, 30);
        this.panel.add(txtEmail);

        lblAlert = new JLabel("Email não encontrado.");
        lblAlert.setForeground(Color.red);
        lblAlert.setBounds(320, 200, 200, 50);
        this.panel.add(lblAlert);
        lblAlert.setVisible(false);

        btnStart = new JButton("Seguir");
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

    public static void showScreen(){
        new EmailView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnStart){
            try {
                if(Facade.getFacadeInstance().checkEmail(this.txtEmail.getText())){
                    this.setVisible(false);
                    this.dispose();
                    PasswordView.showScreen(this.txtEmail.getText());
                } else {
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