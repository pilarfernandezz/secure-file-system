package views;

import facade.Facade;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicDirectoryModel;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.jar.JarEntry;

public class ConsultView extends Frame implements ActionListener, MouseListener {
    private static ConsultView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblTotal;
    private JLabel lblTotalQtd;
    private JList jList;
    private JLabel lblpath;
    private JTextField txtpath;
    private static JButton btnReturn;
    private static JButton btnConsult;
    private JScrollPane scrollPane;
    private JTable jTable;
    private int totalQtd = 0;
    private JLabel[][] table;
    private TableView tableView;

    public ConsultView() throws SQLException {
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(580, 40, 1600, 100);
        lblTitle.setFont(titleFont);
        this.panel.add(lblTitle);

//        lblTotal = new JLabel("Total de consultas do usuário: " + Facade.getLoggedUser().getTotalConsults());
//        lblTotal.setBounds(280, -160, 800, 600);
//        this.panel.add(lblTotal);

        lblText = new JLabel(" Consultar Pasta de Arquivos Secretos:");
        lblText.setBounds(680, 150, 1600, 30);
        this.panel.add(lblText);

        lblText = new JLabel("Caminho da pasta:");
        lblText.setBounds(30, 180, 300, 50);
        this.panel.add(lblText);

        txtpath = new JTextField();
        txtpath.setBounds(150, 190, 1300, 30);
        this.panel.add(txtpath);

        btnConsult = new JButton("Listar");
        btnConsult.setBounds(1460, 190, 100, 30);
        this.panel.add(btnConsult);
        btnConsult.addActionListener(this);

        this.tableView = new TableView();
        this.tableView.setBounds(10, 230, 1580,630);
        this.panel.add(tableView);

        btnReturn = new JButton("Voltar");
        btnReturn.setBounds(750, 885, 100, 20);
        this.panel.add(btnReturn);
        btnReturn.addActionListener(this);

        this.panel.setLayout(null);
        this.panel.setVisible(true);
        this.panel.setBackground(Color.WHITE);
        this.getContentPane().add(panel);

        //this.showHeader();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConsult) {
            try {
                //Facade.getFacadeInstance().updateConsultNumber();

                this.table = new JLabel[Facade.getFacadeInstance().getRowSize() + 1][4];
                //Definindo as colunas
                this.table[0][0] = new JLabel("Nome do arquivo");
                this.table[0][1] = new JLabel("Dono");
                this.table[0][2] = new JLabel("Grupo");
                this.table[0][3] = new JLabel("Status");

                int x = 10, y = 240;
                for(int i = 0; i < Facade.getFacadeInstance().getRowSize();i++){
                    for(int j = 0 ; j < 4 ; j++){
                        if(i != 0){ //Adiciona informações dos arquivos na tabela
                            this.table[i][j] = new JLabel("texto");
                        }
                        this.table[i][j].setBounds(x, y, 195,20);
                        x+=200;

                        this.panel.add( this.table[i][j]);
                    }
                    y+=23;

                }
                this.panel.repaint();
            } catch (Exception exc) {
                throw exc;
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

    public static void showScreen() throws SQLException {
        new ConsultView();
    }

    public int getTotalQtd() {
        return totalQtd;
    }

    public void setTotalQtd(int totalQtd) {
        this.totalQtd = totalQtd;
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
