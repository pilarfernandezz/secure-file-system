package views;

import facade.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class ExitView extends Frame implements ActionListener {
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblConfirmation;
    private JLabel lblTotal;
    private static JButton btnExit;
    private static JButton btnReturn;

    public ExitView() {
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

        lblText = new JLabel("Sair do sistema:");
        lblText.setBounds(340, -120, 800, 600);
        this.panel.add(lblText);

        lblConfirmation = new JLabel("Pressione o botão Sair para confirmar.");
        lblConfirmation.setBounds(250, 190, 300, 50);
        this.panel.add(lblConfirmation);

        btnExit = new JButton("Sair");
        btnExit.setBounds(280, 250, 100, 40);
        this.panel.add(btnExit);
        btnExit.addActionListener(this);

        btnReturn = new JButton("Voltar");
        btnReturn.setBounds(390, 250, 100, 40);
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
        if (e.getSource() == btnExit) {
            Facade.registerLogMessage(9003, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
            System.exit(0);
        } else if (e.getSource() == btnReturn) {
            Facade.registerLogMessage(9004, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
            this.setVisible(false);
            this.dispose();
            try {
                MenuView.showScreen();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro fatal no sistema. O sistema será encerrado.");
                System.exit(1);
            }
        }
    }

    public static void showScreen() {
        Facade.registerLogMessage(9001, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
        new ExitView();
    }
}
