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

public class RegisterView extends Frame implements ActionListener {
    private static RegisterView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblCertificate;
    private JLabel lblGroup;
    private JLabel lblPassword;
    private JLabel lblPasswordConfirmation;
    private JLabel lblAlertPw;
    private JLabel lblAlertCert;
    private JLabel lblTotal;
    private JLabel lblTotalQtd;
    private static JRadioButton btnadministrator;
    private static JRadioButton btnuser;
    private static ButtonGroup btngroup;
    private static JTextField certificatePath = null;
    private static JPasswordField password = null;
    private static JPasswordField passwordConfirmation = null;
    private static JButton btnRegister;
    private static JButton btnReturn;

    public RegisterView() throws SQLException {
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -200, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblTotal = new JLabel("Total de usuários do sistema:");
        lblTotal.setBounds(280, -160, 800, 600);
        this.panel.add(lblTotal);

        lblTotalQtd = new JLabel(String.valueOf(Facade.getNumberOfUsersRegistered()));
        lblTotalQtd.setBounds(470, -160, 800, 600);
        this.panel.add(lblTotalQtd);

        lblText = new JLabel("Cadastro:");
        lblText.setBounds(350, -120, 800, 600);
        this.panel.add(lblText);

        lblCertificate = new JLabel("Caminho do certificado digital:");
        lblCertificate.setBounds(50, 190, 300, 50);
        this.panel.add(lblCertificate);

        //TODO TIRAR CERTIFICADO
        certificatePath = new JTextField("Keys/user01-x509.crt");
        certificatePath.setBounds(250, 200, 500, 30);
        this.panel.add(certificatePath);

        lblGroup = new JLabel("Grupo:");
        lblGroup.setBounds(50, 240, 300, 50);
        this.panel.add(lblGroup);

        btnadministrator = new JRadioButton("Administrador");
        btnadministrator.setBounds(250, 250, 150, 30);
        btnuser = new JRadioButton("Usuário");
        btnuser.setBounds(400, 250, 100, 30);
        btngroup = new ButtonGroup();
        btngroup.add(btnadministrator);
        btngroup.add(btnuser);
        this.panel.add(btnuser);
        this.panel.add(btnadministrator);

        lblPassword = new JLabel("Senha númerica:");
        lblPassword.setBounds(50, 290, 300, 50);
        this.panel.add(lblPassword);

        password = new JPasswordField(8);
        password.setBounds(250, 300, 500, 30);
        this.panel.add(password);

        lblPasswordConfirmation = new JLabel("Confirmação senha:");
        lblPasswordConfirmation.setBounds(50, 340, 300, 50);
        this.panel.add(lblPasswordConfirmation);

        passwordConfirmation = new JPasswordField(8);
        passwordConfirmation.setBounds(250, 350, 500, 30);
        this.panel.add(passwordConfirmation);

        lblAlertCert = new JLabel("Certificado inválido");
        lblAlertCert.setForeground(Color.red);
        lblAlertCert.setBounds(60, 430, 700, 50);
        this.panel.add(lblAlertCert);
        lblAlertCert.setVisible(false);

        lblAlertPw = new JLabel("Senha deve ter entre 6 e 8 caracteres numéricos e não pode conter sequências e repetições de caracteres");
        lblAlertPw.setForeground(Color.red);
        lblAlertPw.setBounds(60, 390, 700, 50);
        this.panel.add(lblAlertPw);
        lblAlertPw.setVisible(false);

        btnRegister = new JButton("Registrar");
        btnRegister.setBounds(280, 470, 100, 40);
        this.panel.add(btnRegister);
        btnRegister.addActionListener(this);

        btnReturn = new JButton("Voltar");
        btnReturn.setBounds(390, 470, 100, 40);
        this.panel.add(btnReturn);
        btnReturn.addActionListener(this);

        this.panel.setLayout(null);
        this.panel.setVisible(true);
        this.panel.setBackground(Color.WHITE);
        this.getContentPane().add(panel);
        this.showHeader();
        this.setVisible(true);
    }

    public static void showScreen() throws SQLException {
        Facade.registerLogMessage(6001, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
        new RegisterView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            Facade.registerLogMessage(6002, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
            try {
                boolean invalidPw = password.getText().length() < 6 || password.getText().length() > 8 || !password.getText().matches("[0-9]+") || !Facade.validatePassword(password.getText());
                boolean invalidCert = certificatePath.getText().trim().equals("") || certificatePath.getText() == null || !Facade.validateCertificate(certificatePath.getText());
                lblAlertPw.setVisible(invalidPw);
                lblAlertCert.setVisible(invalidCert);
                this.panel.repaint();

                if (!invalidCert || !invalidPw) {
                    Facade.registerLogMessage(invalidPw ? 6003 : 6004, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
                    this.setVisible(false);
                    this.dispose();
                    try {
                        ConfirmationView.showScreen(true, certificatePath.getText(), (btnuser.isSelected() ? "Usuário" : "Administrador"), password.getText(), passwordConfirmation.getText());
                    } catch (InvalidExtractionCertificateOwnerInfoException ex) {
                        //todo log
                        lblAlertCert.setVisible(true);
                    } catch (SQLException invalidExtractionCertificateOwnerInfoException) {
                        JOptionPane.showMessageDialog(null, "Ocorreu um erro fatal no sistema. O sistema será encerrado.");
                        Facade.registerLogMessage(1002, null, null, LocalDateTime.now());
                        System.exit(1);
                    }
                }
            } catch (FileNotFoundException | InvalidCertificateException ex) {
                //todo log
                lblAlertCert.setVisible(true);
            }
        } else if (e.getSource() == btnReturn) {
            Facade.registerLogMessage(6007, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
            this.setVisible(false);
            this.dispose();
            try {
                MenuView.showScreen();
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro fatal no sistema. O sistema será encerrado.");
                Facade.registerLogMessage(1002, null, null, LocalDateTime.now());
                System.exit(1);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro fatal no sistema. O sistema será encerrado.");
                Facade.registerLogMessage(1002, null, null, LocalDateTime.now());
                System.exit(1);
            }
        }
    }
}
