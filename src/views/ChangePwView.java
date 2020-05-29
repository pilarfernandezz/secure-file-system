package views;

import exceptions.InvalidCertificateException;
import exceptions.InvalidExtractionCertificateOwnerInfoException;
import facade.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ChangePwView extends Frame implements ActionListener {
    private static ChangePwView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblCertificate;
    private JLabel lblPassword;
    private JLabel lblPasswordConfirmation;
    private JLabel lblTotal;
    private JLabel lblAlertPw;
    private JLabel lblAlertCert;
    private static JTextField certificatePath = null;
    private static JPasswordField password = null;
    private static JPasswordField passwordConfirmation = null;
    private static JButton btnRegister;
    private static JButton btnReturn;
    private static String _password;
    private static String _passwordConfirmation;
    private static String _certificatePath;

    public ChangePwView() {
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -200, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblTotal = new JLabel("Total de acessos do usuário: " + Facade.getLoggedUser().getTotalAccess());
        lblTotal.setBounds(280, -160, 800, 600);
        this.panel.add(lblTotal);

        lblText = new JLabel("Alterar Senha Pessoal e Certificado Digital:");
        lblText.setBounds(270, -120, 800, 600);
        this.panel.add(lblText);

        lblCertificate = new JLabel("Caminho do certificado digital:");
        lblCertificate.setBounds(50, 190, 300, 50);
        this.panel.add(lblCertificate);

        certificatePath = new JTextField();
        if (_certificatePath != null) certificatePath.setText(_certificatePath);
        certificatePath.setBounds(250, 200, 500, 30);
        this.panel.add(certificatePath);

        lblPassword = new JLabel("Senha:");
        lblPassword.setBounds(50, 240, 300, 50);
        this.panel.add(lblPassword);

        password = new JPasswordField(8);
        if (_password != null) password.setText(_password);
        password.setBounds(250, 250, 500, 30);
        this.panel.add(password);

        lblPasswordConfirmation = new JLabel("Confirmação senha:");
        lblPasswordConfirmation.setBounds(50, 290, 300, 50);
        this.panel.add(lblPasswordConfirmation);

        passwordConfirmation = new JPasswordField(8);
        if (_passwordConfirmation != null) passwordConfirmation.setText(_passwordConfirmation);
        passwordConfirmation.setBounds(250, 300, 500, 30);
        this.panel.add(passwordConfirmation);

        lblAlertCert = new JLabel("Certificado inválido");
        lblAlertCert.setForeground(Color.red);
        lblAlertCert.setBounds(60, 350, 700, 50);
        this.panel.add(lblAlertCert);
        lblAlertCert.setVisible(false);

        lblAlertPw = new JLabel("Senha deve ter entre 6 e 8 caracteres numéricos e não pode conter sequências e repetições de caracteres");
        lblAlertPw.setForeground(Color.red);
        lblAlertPw.setBounds(60, 390, 700, 50);
        this.panel.add(lblAlertPw);
        lblAlertPw.setVisible(false);

        btnRegister = new JButton("Salvar");
        btnRegister.setBounds(280, 440, 100, 40);
        this.panel.add(btnRegister);
        btnRegister.addActionListener(this);

        btnReturn = new JButton("Voltar");
        btnReturn.setBounds(390, 440, 100, 40);
        this.panel.add(btnReturn);
        btnReturn.addActionListener(this);

        this.panel.setLayout(null);
        this.panel.setVisible(true);
        this.panel.setBackground(Color.WHITE);
        this.getContentPane().add(panel);
        this.showHeader();
        this.setVisible(true);
    }

    public static void showScreen(String certificatePath, String password, String passwordConfirmation) {
        _certificatePath = certificatePath;
        _password = password;
        _passwordConfirmation = passwordConfirmation;

        Facade.registerLogMessage(7001, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
        new ChangePwView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            try {
                lblAlertCert.setVisible(false);
                lblAlertPw.setVisible(false);

                boolean invalidPw = password.getText().length() < 6 || password.getText().length() > 8 || !password.getText().matches("[0-9]+") || !Facade.validatePassword(password.getText());
                boolean invalidCert = certificatePath.getText().trim().equals("") || certificatePath.getText() == null || !Facade.validateCertificate(certificatePath.getText());
                lblAlertPw.setVisible(invalidPw);
                lblAlertCert.setVisible(invalidCert);
                this.panel.repaint();

                if (!invalidCert || !invalidPw) {
                    Facade.registerLogMessage(invalidPw ? 7002 : 7003, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
                    this.setVisible(false);
                    this.dispose();
                    ConfirmationView.showScreen(false, certificatePath.getText(), null, password.getText(), passwordConfirmation.getText());
                }
            } catch (FileNotFoundException | InvalidCertificateException | InvalidExtractionCertificateOwnerInfoException ex) {
                lblAlertCert.setVisible(true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro fatal no sistema. O sistema será encerrado.");
                Facade.registerLogMessage(1002, null, null, LocalDateTime.now());
                System.exit(1);
            }
        } else if (e.getSource() == btnReturn) {
            Facade.registerLogMessage(7006, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
            this.setVisible(false);
            this.dispose();
            try {
                MenuView.showScreen();
            } catch (Exception throwables) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro fatal no sistema. O sistema será encerrado.");
                Facade.registerLogMessage(1002, null, null, LocalDateTime.now());
                System.exit(1);
            }
        }
    }


}
