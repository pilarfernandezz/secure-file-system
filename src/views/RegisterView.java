package views;

import exceptions.InvalidExtractionCertificateOwnerInfoException;
import facade.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterView extends Frame implements ActionListener {
    private static RegisterView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblCertificate;
    private JLabel lblGroup;
    private JLabel lblPassword;
    private JLabel lblPasswordConfirmation;
    private JLabel lblAlert;
    private JLabel lblTotal;
    private JLabel lblTotalQtd;
    private static JRadioButton btnadministrator;
    private static JRadioButton btnuser;
    private static ButtonGroup btngroup;
    private static JTextField certificatePath = null;
    private static JTextField group = null;
    private static JPasswordField password = null;
    private static JPasswordField passwordConfirmation = null;
    private static JButton btnRegister;
    private static JButton btnReturn;
    private int totalQtd =0;

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

        lblTotalQtd = new JLabel(String.valueOf(Facade.getFacadeInstance().getNumberOfUsersRegistered()));
        lblTotalQtd.setBounds(470, -160, 800, 600);
        this.panel.add(lblTotalQtd);

        lblText = new JLabel("Cadastro:");
        lblText.setBounds(350, -120, 800, 600);
        this.panel.add(lblText);

        lblCertificate = new JLabel("Caminho do certificado digital:");
        lblCertificate.setBounds(50, 190, 300, 50);
        this.panel.add(lblCertificate);

        certificatePath = new JTextField();
        certificatePath.setBounds(250, 200, 500, 30);
        this.panel.add(certificatePath);

        lblGroup = new JLabel("Grupo:");
        lblGroup.setBounds(50, 240, 300, 50);
        this.panel.add(lblGroup);

        btnadministrator = new JRadioButton("Administrador");
        btnadministrator.setBounds(250, 250,150,30);
        btnuser = new JRadioButton("Usuário");
        btnuser.setBounds(400, 250,100,30);
        btngroup = new ButtonGroup();
        btngroup.add(btnadministrator); btngroup.add(btnuser);
        this.panel.add(btnuser); this.panel.add(btnadministrator);

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

        lblAlert = new JLabel("Senha deve ter entre 6 e 8 caracteres numéricos e não pode conter sequências e repetições de caracteres");
        lblAlert.setForeground(Color.red);
        lblAlert.setBounds(60, 390, 700, 50);
        this.panel.add(lblAlert);
        lblAlert.setVisible(false);

        btnRegister = new JButton("Registrar");
        btnRegister.setBounds(280, 450, 100, 40);
        this.panel.add(btnRegister);
        btnRegister.addActionListener(this);

        btnReturn = new JButton("Voltar");
        btnReturn.setBounds(390, 450, 100, 40);
        this.panel.add(btnReturn);
        btnReturn.addActionListener(this);

        this.panel.setLayout(null);
        this.panel.setVisible(true);
        this.panel.setBackground(Color.WHITE);
        this.getContentPane().add(panel);
        this.showHeader();
        this.setVisible(true);
    }

    public boolean validatePassword(String pw){
        for(int i = 0; i < pw.length()-1;i++){
            System.out.println(pw.length()-1 + " " + i + " " + pw.charAt(i) + " " + pw.charAt(i+1));
            if(pw.charAt(i) == pw.charAt(i+1) || pw.charAt(i) == pw.charAt(i+1)+1 || pw.charAt(i) == pw.charAt(i+1)-1) {
                System.out.println("entrei");
                return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnRegister){
            if(password.getText().length() < 6 || password.getText().length() > 8 || !password.getText().matches("[0-9]+" )|| !this.validatePassword(password.getText())){
                lblAlert.setVisible(true);
                this.panel.repaint();
            } else {
                this.setVisible(false);
                this.dispose();
                try {
                    if(btnuser.isSelected())
                        RegisterConfirmationView.showScreen(certificatePath.getText(), "Usuário", password.getText(), passwordConfirmation.getText());
                    else
                        RegisterConfirmationView.showScreen(certificatePath.getText(), "Administrador", password.getText(), passwordConfirmation.getText());

                } catch (InvalidExtractionCertificateOwnerInfoException | SQLException invalidExtractionCertificateOwnerInfoException) {
                    invalidExtractionCertificateOwnerInfoException.printStackTrace();
                }
            }
        } else if(e.getSource() == btnReturn){
            this.setVisible(false);
            this.dispose();
            try {
                MenuView.showScreen();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void showScreen() throws SQLException {
        new RegisterView();
    }

    public int getTotalQtd() {
        return totalQtd;
    }

    public void setTotalQtd(int totalQtd) {
        this.totalQtd = totalQtd;
    }
}
