package views;

import facade.Facade;
import models.User;
import services.AuthenticationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class MenuView extends Frame implements ActionListener {
    private static MenuView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblTotal;
    private JLabel lblTotalQtd;
    private static JButton btnRegister;
    private static JButton btnChange;
    private static JButton btnConsult;
    private static JButton btnExit;
    private int totalQtd = 0;

    public MenuView() throws Exception {
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

        lblText = new JLabel("Menu principal:");
        lblText.setBounds(350, -120, 800, 600);
        this.panel.add(lblText);

        btnRegister = new JButton("Cadastrar usuário");
        btnRegister.setBounds(180, 250, 200, 40);
        this.panel.add(btnRegister);
        btnRegister.addActionListener(this);
        if(!this.isAdmin()) btnRegister.setEnabled(false);

        btnChange = new JButton("Alterar senha e certificado");
        btnChange.setBounds(410, 250, 200, 40);
        this.panel.add(btnChange);
        btnChange.addActionListener(this);

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
        if(e.getSource() == btnRegister){
			this.setVisible(false);
			this.dispose();
            try {
                RegisterView.showScreen();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (e.getSource() == btnChange){
            this.setVisible(false);
            this.dispose();
            try {
                ChangePwView.showScreen();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (e.getSource() == btnConsult){
            this.setVisible(false);
            this.dispose();
            try {
                ConsultView.showScreen();
            } catch (SQLException | UnsupportedEncodingException throwables) {
                throwables.printStackTrace();
            }
        } else if (e.getSource() == btnExit){
            this.setVisible(false);
            this.dispose();
            try {
                ExitView.showScreen();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.exit(1);
        }
    }

    public boolean isAdmin() throws SQLException {
        User user = AuthenticationService.getAuthenticationInstance().getLoggedUser();
        if(user.getGroup().equals("Administrador")) return true;
        return false;
    }

    public static void showScreen() throws Exception {
        new MenuView();
    }


    public int getTotalQtd() {
        return totalQtd;
    }

    public void setTotalQtd(int totalQtd) {
        this.totalQtd = totalQtd;
    }
}
