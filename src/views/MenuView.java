package views;

import facade.Facade;
import models.User;
import services.AuthenticationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class MenuView extends Frame implements ActionListener {
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblTotal;
    private static JButton btnRegister;
    private static JButton btnChange;
    private static JButton btnConsult;
    private static JButton btnExit;
    private int totalQtd = 0;

    public MenuView() {
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
        if (!this.isAdmin()) btnRegister.setEnabled(false);

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

    public static void showScreen() {
        Facade.registerLogMessage(5001, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
        new MenuView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            this.setVisible(false);
            this.dispose();
            Facade.registerLogMessage(5002, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
            RegisterView.showScreen(null, null, null, null);
        } else if (e.getSource() == btnChange) {
            this.setVisible(false);
            this.dispose();
            Facade.registerLogMessage(5003, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
            ChangePwView.showScreen(null, null, null);
        } else if (e.getSource() == btnConsult) {
            this.setVisible(false);
            this.dispose();
            Facade.registerLogMessage(5004, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
            ConsultView.showScreen();
        } else if (e.getSource() == btnExit) {
            this.setVisible(false);
            this.dispose();
            Facade.registerLogMessage(5005, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
            ExitView.showScreen();
        } else {
            Facade.registerLogMessage(1002, null, null, LocalDateTime.now());
            System.exit(1);
        }
    }

    public boolean isAdmin() {
        User user = AuthenticationService.getInstance().getLoggedUser();
        if (user.getGroup().equals("Administrador")) return true;
        return false;
    }
}
