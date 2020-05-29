package views;

import facade.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class PvtKeyView extends Frame implements ActionListener {
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblPath;
    private JLabel lblAlert;
    private JLabel lblSecret;
    private JPasswordField txtSecret;
    private static JTextField txtPath = null;
    private static JButton btnStart;
    private static JButton btnCancel;
    private static String email;
    private static int keyErrors = 0;

    public PvtKeyView() {
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -250, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblText = new JLabel("Forneça sua chave privada:");
        lblText.setBounds(310, 100, 400, 50);
        this.panel.add(lblText);

        lblPath = new JLabel("Caminho para chave privada:");
        lblPath.setBounds(150, 150, 300, 50);
        this.panel.add(lblPath);

        txtPath = new JTextField();
        txtPath.setBounds(360, 160, 200, 30);
        this.panel.add(txtPath);

        lblAlert = new JLabel("Chave privada inválida.");
        lblAlert.setForeground(Color.red);
        lblAlert.setBounds(320, 230, 200, 50);
        this.panel.add(lblAlert);
        lblAlert.setVisible(false);

        lblSecret = new JLabel("Frase secreta da chave privada:");
        lblSecret.setBounds(150, 190, 300, 50);
        this.panel.add(lblSecret);

        txtSecret = new JPasswordField();
        txtSecret.setBounds(360, 200, 200, 30);
        this.panel.add(txtSecret);

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

    public static void showScreen(String email) {
        Facade.registerLogMessage(4001, email, null, LocalDateTime.now());
        PvtKeyView.email = email;
        new PvtKeyView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            lblAlert.setVisible(false);

            try {
                if (this.keyErrors > 2) {
                    this.keyErrors = 0;
                    try {
                        JOptionPane.showMessageDialog(null, "Usuário foi bloqueado por 2 minutos por exceder as tentativas de autenticação.");

                        Facade.lockUser(this.email);
                        Facade.registerLogMessage(4007, email, null, LocalDateTime.now());
                        Facade.registerLogMessage(4002, email, null, LocalDateTime.now());

                        EmailView.showScreen();
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Ocorreu um erro fatal no sistema. O sistema será encerrado.");
                        Facade.registerLogMessage(1002, null, null, LocalDateTime.now());
                        System.exit(1);
                    }
                } else {
                    boolean invalidPath = txtPath.getText() == null || txtPath.getText().trim().equals("");
                    boolean invalidSecret = txtSecret.getText().trim().equals("") || txtSecret.getText() == null;
                    int invalidKeys = Facade.keysValidation(this.email, txtPath.getText(), txtSecret.getText());

                    if (invalidPath || invalidSecret || invalidKeys != 0) {
                        Facade.registerLogMessage(invalidPath ? 4004 : invalidKeys, email, null, LocalDateTime.now());
                        lblAlert.setVisible(true);
                        this.keyErrors++;
                        this.panel.repaint();
                    } else {
                        this.setVisible(false);
                        this.dispose();
                        Facade.makeUserLogged(this.email, txtPath.getText(), txtSecret.getText());
                        Facade.registerLogMessage(4003, email, null, LocalDateTime.now());
                        Facade.registerLogMessage(4002, email, null, LocalDateTime.now());
                        MenuView.showScreen();
                    }
                }
            } catch (Exception exception) {
                lblAlert.setVisible(true);
                this.keyErrors++;
            }
        } else if (e.getSource() == btnCancel) {
            Facade.registerLogMessage(1002, null, null, LocalDateTime.now());
            System.exit(1);
        }
    }
}
