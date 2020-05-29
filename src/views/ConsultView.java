package views;

import facade.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class ConsultView extends FrameConsult implements ActionListener, MouseListener {
    private static ConsultView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblTotal;
    private JLabel lblAlert;
    private JTextField txtpath;
    private static JButton btnReturn;
    private static JButton btnConsult;
    private int totalQtd = 0;
    private TableView tableView;
    private byte[] folderContent;

    public ConsultView() throws SQLException, UnsupportedEncodingException {
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(580, 40, 1600, 100);
        lblTitle.setFont(titleFont);
        this.panel.add(lblTitle);

        lblTotal = new JLabel("Total de consultas do usuário: " + Facade.getLoggedUser().getTotalConsults());
        lblTotal.setBounds(700, -170, 800, 600);
        this.panel.add(lblTotal);

        lblText = new JLabel(" Consultar Pasta de Arquivos Secretos:");
        lblText.setBounds(680, 150, 1600, 30);
        this.panel.add(lblText);

        lblText = new JLabel("Caminho da pasta:");
        lblText.setBounds(30, 180, 300, 50);
        this.panel.add(lblText);

        lblAlert = new JLabel("Caminho inválido");
        lblAlert.setBounds(740, 220, 1600, 30);
        lblAlert.setForeground(Color.red);
        this.panel.add(lblAlert);
        lblAlert.setVisible(false);

        txtpath = new JTextField();
        txtpath.setBounds(150, 190, 1300, 30);
        this.panel.add(txtpath);

        btnConsult = new JButton("Listar");
        btnConsult.setBounds(1460, 190, 100, 30);
        this.panel.add(btnConsult);
        btnConsult.addActionListener(this);

        this.tableView = new TableView();
        this.tableView.setBounds(10, 230, 1580, 630);

        btnReturn = new JButton("Voltar");
        btnReturn.setBounds(750, 885, 100, 20);
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
        if (e.getSource() == btnConsult) {
            this.lblAlert.setVisible(false);
            this.tableView.setVisible(false);
            this.panel.repaint();

            try {
                Facade.updateConsultNumber();
                if(txtpath.getText()!= null && txtpath.getText().trim().length()>0){
                    this.folderContent = Facade.decryptFile(Facade.getLoggedUser().getEmail(), txtpath.getText(),false, null);
                    tableView.setContent(this.folderContent);
                    tableView.setPath(txtpath.getText().substring(0,txtpath.getText().lastIndexOf("/")+1));
                    tableView.showInfo();
                    tableView.setVisible(true);
                    this.panel.add(tableView);
                    this.panel.repaint();
                } else {
                    this.lblAlert.setVisible(true);
                    this.panel.repaint();
                }
            } catch (Exception exc) {
                this.lblAlert.setVisible(true);
                this.tableView.setVisible(false);
                this.panel.repaint();
            }
        } else if (e.getSource() == btnReturn) {
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

    public static void showScreen() throws SQLException, UnsupportedEncodingException {
        new ConsultView();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
