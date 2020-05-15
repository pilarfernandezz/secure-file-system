package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView extends Frame implements ActionListener {
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    public static JButton btnNewGame;
    public static JButton btnLoadGame;
    public static JButton btnConsult;
    public static JButton btnExit;

    public MenuView(){
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -200, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblText = new JLabel("Menu principal:");
        lblText.setBounds(350, -120, 800, 600);
        this.panel.add(lblText);

        btnNewGame = new JButton("Cadastrar usuário");
        btnNewGame.setBounds(180, 250, 200, 40);
        this.panel.add(btnNewGame);
        btnNewGame.addActionListener(this);

        btnLoadGame = new JButton("Alterar senha e certificado");
        btnLoadGame.setBounds(410, 250, 200, 40);
        this.panel.add(btnLoadGame);
        btnLoadGame.addActionListener(this);

        btnConsult = new JButton("Consultar pasta");
        btnConsult.setBounds(180, 310, 200, 40);
        this.panel.add(btnConsult);
        btnConsult.addActionListener(this);

        btnExit = new JButton("Sair");
        btnExit.setBounds(410, 310, 200, 40);
        this.panel.add(btnExit);
        btnExit.addActionListener(this);

        this.panel.setLayout(null);
        this.panel.setVisible(true);
        this.panel.setBackground(Color.WHITE);
        this.getContentPane().add(panel);

        this.showHeader();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnNewGame){
			this.setVisible(false);
			this.dispose();
			//Facade.novoJogo();
		} else if (e.getSource() == btnLoadGame){
            this.setVisible(false);
            this.dispose();
            //Facade.getFacadeInstance().carregarJogo();
        } else if (e.getSource() == btnConsult){
            this.setVisible(false);
            this.dispose();
            //Facade.getFacadeInstance().carregarJogo();
        } else if (e.getSource() == btnExit){
            this.setVisible(false);
            this.dispose();
            //Facade.getFacadeInstance().carregarJogo();
        } else {
            System.exit(1);
        }
    }
}
