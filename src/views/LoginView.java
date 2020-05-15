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
    private JLabel lblName1;
    private JLabel lblName2;
    public static JTextField txtfName1 = null;
    public static JTextField txtfName2 = null;
    public static JButton btnStart;
    public static JButton btnCancel;

    public LoginView(){
        // VER PORQUE TA BUGADO SÁ CASSETA LABELSSSSSS  :@
        super();
        this.panel = new JPanel();
        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -250, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblText = new JLabel("Faça seu login:");
        lblText.setBounds(295, 100, 200, 50);
        this.panel.add(lblText);

        lblName1 = new JLabel("Email:");
        lblName1.setBounds(250, 150, 100, 50);
        this.panel.add(lblName1);

        txtfName1 = new JTextField();
        txtfName1.setBounds(320, 160, 200, 30);
        this.panel.add(txtfName1);

        lblName2 = new JLabel("Senha:");
        lblName2.setBounds(250, 200, 100, 50);
        this.panel.add(lblName2);

        txtfName2 = new JTextField();
        txtfName2.setBounds(320, 210, 200, 30);
        this.panel.add(txtfName2);

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
        if(instance == null){
            instance = new LoginView();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* verifica botão clicado*/
        if(e.getSource() == btnStart){
			this.setVisible(false);
			this.dispose();
			//Facade.getFacadeInstance().criarJogadores(getNomeJogador(1), getNomeJogador(2));
			//Facade.getFacadeInstance().posicionarArmasJogador(1);

		} else if(e.getSource() == btnCancel){
			System.exit(1);
		}
    }
}
