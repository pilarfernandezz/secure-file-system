package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends Frame implements ActionListener {

    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblCertificate;
    private JLabel lblGroup;
    private JLabel lblPassword;
    private JLabel lblPasswordConfirmation;
    public static JTextField certificatePath = null;
    public static JTextField group = null;
    public static JTextField password = null;
    public static JTextField passwordConfirmation = null;
    public static JButton btnRegister;
    public static JButton btnReturn;

    public RegisterView(){
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -200, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblText = new JLabel("Cadastro:");
        lblText.setBounds(350, -120, 800, 600);
        this.panel.add(lblText);

        lblCertificate = new JLabel("Caminho do certificado digital:");
        lblCertificate.setBounds(50, 190, 300, 50);
        this.panel.add(lblCertificate);

        certificatePath = new JTextField();
        certificatePath.setBounds(250, 200, 500, 30);
        this.panel.add(certificatePath);

        lblCertificate = new JLabel("Grupo:");
        lblCertificate.setBounds(50, 240, 300, 50);
        this.panel.add(lblCertificate);

        group = new JTextField();
        group.setBounds(250, 250, 500, 30);
        this.panel.add(group);

        lblCertificate = new JLabel("Senha:");
        lblCertificate.setBounds(50, 290, 300, 50);
        this.panel.add(lblCertificate);

        password = new JTextField();
        password.setBounds(250, 300, 500, 30);
        this.panel.add(password);

        lblCertificate = new JLabel("Confirmação senha:");
        lblCertificate.setBounds(50, 340, 300, 50);
        this.panel.add(lblCertificate);

        passwordConfirmation = new JTextField();
        passwordConfirmation.setBounds(250, 350, 500, 30);
        this.panel.add(passwordConfirmation);

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

    }
}
