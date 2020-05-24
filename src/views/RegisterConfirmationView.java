package views;

import facade.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterConfirmationView extends Frame implements ActionListener {
    private static RegisterConfirmationView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private static String password;
    private static String passwordConfirmation;
    private static String certificatePath;
    private static String group;
    private JLabel lblPassword;
    private JLabel getLblPasswordConfirmation;
    private JLabel lblCertificate;
    private JLabel lblGroup;
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblPasswordConfirmation;
    private JLabel lblTotal;
    private JLabel lblTotalQtd;
    private static JButton btnRegister;
    private static JButton btnReturn;
    private int totalQtd =0;

    public RegisterConfirmationView(){
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

        lblTotalQtd = new JLabel(String.valueOf(totalQtd));
        lblTotalQtd.setBounds(470, -160, 800, 600);
        this.panel.add(lblTotalQtd);

        lblText = new JLabel("Confirmação cadastro:");
        lblText.setBounds(300, -120, 800, 600);
        this.panel.add(lblText);

        lblCertificate = new JLabel("Caminho do certificado digital: " + RegisterConfirmationView.certificatePath );
        lblCertificate.setBounds(110, 190, 300, 50);
        this.panel.add(lblCertificate);

        lblGroup = new JLabel("Grupo: " + RegisterConfirmationView.group);
        lblGroup.setBounds(110, 240, 300, 50);
        this.panel.add(lblGroup);

        lblPassword = new JLabel("Senha: " + RegisterConfirmationView.password);
        lblPassword.setBounds(110, 290, 300, 50);
        this.panel.add(lblPassword);

        lblPassword = new JLabel("Confirmação de senha: " + RegisterConfirmationView.passwordConfirmation);
        lblPassword.setBounds(110, 340, 300, 50);
        this.panel.add(lblPassword);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnRegister){
            this.setVisible(false);
            this.dispose();
            try {
                Facade.getFacadeInstance().registerUser(certificatePath, group, password, passwordConfirmation);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            try {
                MenuView.showScreen();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if(e.getSource() == btnReturn){
            this.setVisible(false);
            this.dispose();
            RegisterView.showScreen();
        }
    }

    public static void showScreen(String certificatePath,String group, String password, String passwordConfirmation){
        RegisterConfirmationView.certificatePath = certificatePath;
        RegisterConfirmationView.group = group;
        RegisterConfirmationView.password = password;
        RegisterConfirmationView.passwordConfirmation = passwordConfirmation;
        new RegisterConfirmationView();
    }

    public int getTotalQtd() {
        return totalQtd;
    }

    public void setTotalQtd(int totalQtd) {
        this.totalQtd = totalQtd;
    }
}
