package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ExitView extends Frame implements ActionListener {
    private static ExitView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblConfirmation;
    private JLabel lblTotal;
    private JLabel lblTotalQtd;
    private static JButton btnExit;
    private static JButton btnReturn;
    private int totalQtd = 0;

    public ExitView() throws SQLException {
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -200, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblTotal = new JLabel("Total de acessos do usuário:");
        lblTotal.setBounds(280, -160, 800, 600);
        this.panel.add(lblTotal);

        lblTotalQtd = new JLabel(String.valueOf(totalQtd));
        lblTotalQtd.setBounds(465, -160, 800, 600);
        this.panel.add(lblTotalQtd);

        lblText = new JLabel("Sair do sistema:");
        lblText.setBounds(340, -120, 800, 600);
        this.panel.add(lblText);

        lblConfirmation = new JLabel("Pressione o botão Sair para confirmar.");
        lblConfirmation.setBounds(320, 190, 300, 50);
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
        if(e.getSource() == btnExit){
            System.exit(0);
        } else if(e.getSource() == btnReturn){
            this.setVisible(false);
            this.dispose();
            try {
                MenuView.showScreen();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void showScreen() throws SQLException {
        new ExitView();
    }

    public int getTotalQtd() {
        return totalQtd;
    }

    public void setTotalQtd(int totalQtd) {
        this.totalQtd = totalQtd;
    }
}
