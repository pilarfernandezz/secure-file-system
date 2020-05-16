package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends Frame implements ActionListener {
    private static LoginView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private static JTextField txtEmail = null;
    private static JPasswordField passPassword = null;
    private static JButton btnStart;
    private static JButton btnCancel;

    public LoginView(){
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -250, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblText = new JLabel("Faça seu login:");
        lblText.setBounds(340, 100, 200, 50);
        this.panel.add(lblText);

        lblEmail = new JLabel("Email:");
        lblEmail.setBounds(250, 150, 100, 50);
        this.panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(320, 160, 200, 30);
        this.panel.add(txtEmail);

        lblPassword = new JLabel("Senha:");
        lblPassword.setBounds(250, 200, 100, 50);
        this.panel.add(lblPassword);

        passPassword = new JPasswordField(8);
        passPassword.setBounds(320, 210, 200, 30);
        this.panel.add(passPassword);

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

    public static void showScreen(){
        new LoginView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* verifica botão clicado*/
        if(e.getSource() == btnStart){
			this.setVisible(false);
			this.dispose();
			MenuView.showScreen();
			//Facade.getFacadeInstance().criarJogadores(getNomeJogador(1), getNomeJogador(2));
			//Facade.getFacadeInstance().posicionarArmasJogador(1);

		} else if(e.getSource() == btnCancel){
			System.exit(1);
		}
    }
}
