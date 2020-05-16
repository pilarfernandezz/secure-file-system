package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends Frame implements ActionListener {
    private static RegisterView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblCertificate;
    private JLabel lblGroup;
    private JLabel lblPassword;
    private JLabel lblPasswordConfirmation;
    private JLabel lblTotal;
    private JLabel lblTotalQtd;
    private static JTextField certificatePath = null;
    private static JTextField group = null;
    private static JPasswordField password = null;
    private static JPasswordField passwordConfirmation = null;
    private static JButton btnRegister;
    private static JButton btnReturn;
    private int totalQtd =0;

    public RegisterView(){
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

        group = new JTextField();
        group.setBounds(250, 250, 500, 30);
        this.panel.add(group);

        lblPassword = new JLabel("Senha:");
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
        /* verifica botão clicado*/
        if(e.getSource() == btnRegister){
            this.setVisible(false);
            this.dispose();
            //TODO REALIZAR AÇOES DE CADASTRO E APÓS ISSO IR PARA MENU
            MenuView.showScreen();
        } else if(e.getSource() == btnReturn){
            this.setVisible(false);
            this.dispose();
            MenuView.showScreen();
        }
    }

    public static void showScreen(){
        new RegisterView();
    }

    public int getTotalQtd() {
        return totalQtd;
    }

    public void setTotalQtd(int totalQtd) {
        this.totalQtd = totalQtd;
    }
}
