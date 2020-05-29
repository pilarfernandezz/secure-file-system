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
    private JLabel lblAlert1;
    private static JTextField txtEmail = null;
    private static JButton btnStart;
    private static JButton btnCancel;

    public EmailView() throws SQLException {
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

        //TODO TIRAR EMAIL
        txtEmail = new JTextField("user01@inf1416.puc-rio.br");
        txtEmail.setBounds(320, 160, 200, 30);
        this.panel.add(txtEmail);

        lblAlert = new JLabel("Email não encontrado.");
        lblAlert.setForeground(Color.red);
        lblAlert.setBounds(320, 200, 200, 50);
        this.panel.add(lblAlert);
        lblAlert.setVisible(false);

        lblAlert1 = new JLabel("Usuário bloqueado.");
        lblAlert1.setForeground(Color.red);
        lblAlert1.setBounds(320, 200, 200, 50);
        this.panel.add(lblAlert1);
        lblAlert1.setVisible(false);

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

    public static void showScreen() throws SQLException {
        new EmailView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            lblAlert.setVisible(false);
            lblAlert1.setVisible(false);

            try {
                if (Facade.checkEmail(this.txtEmail.getText())) {
                    if (Facade.verifyIsLocked(this.txtEmail.getText())) {
                        lblAlert1.setVisible(true);
                        this.panel.repaint();
                    } else {
                        this.setVisible(false);
                        this.dispose();
                        PasswordView.showScreen(this.txtEmail.getText());
                    }
                } else {
                    lblAlert.setVisible(true);
                    this.panel.repaint();
                }
            } catch (SQLException throwables) {
                //todo log
                JOptionPane.showMessageDialog(null, "Ocorreu um erro fatal no sistema. O sistema será encerrado.");
                System.exit(1);
            } catch (Exception exception) {
                //todo log
                JOptionPane.showMessageDialog(null, "Ocorreu um erro fatal no sistema. O sistema será encerrado.");
                System.exit(1);
            }
        } else if (e.getSource() == btnCancel) {
            System.exit(1);
        }
    }
}
