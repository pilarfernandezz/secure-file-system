package views;

import facade.Facade;

import javax.swing.*;
import javax.swing.plaf.basic.BasicDirectoryModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.jar.JarEntry;

public class ConsultView extends Frame implements ActionListener {
    private static ConsultView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblTotal;
    private JLabel lblTotalQtd;
    private JList jList;
    private static JButton btnReturn;
    private static JButton btnConsult;

    private BasicDirectoryModel basicDirectoryModel;
    private JFileChooser jFileChooser;
    private JTable jTable;
    private int totalQtd = 0;

    public ConsultView() throws SQLException {
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -200, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblTotal = new JLabel("Total de consultas do usuário: " + Facade.getLoggedUser().getTotalConsults());
        lblTotal.setBounds(280, -160, 800, 600);
        this.panel.add(lblTotal);

        lblText = new JLabel(" Consultar Pasta de Arquivos Secretos:");
        lblText.setBounds(250, -120, 800, 600);
        this.panel.add(lblText);

//        jFileChooser = new JFileChooser("/Users/pilarfernandez/Documents");
//        basicDirectoryModel = new BasicDirectoryModel(jFileChooser);
////        jList = new JList(basicDirectoryModel);
////        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
////        jList.setLayoutOrientation(JList.VERTICAL);
////        jList.setBounds(300, 250, 800, 600);
//       this.panel.add(jFileChooser);
//        jFileChooser.showOpenDialog(this);
//        Object column[]={"Nome Código","Nome Secreto","Dono", "Grupo"};
//        Object data[][] = {{"efefewf", "feerg", "dsfdf","dwfewf"}};
//        jTable = new JTable(data, column);
//        jTable.setBounds(300, 250, 800, 600);
//        this.panel.add(jTable);
        
        btnReturn = new JButton("Voltar");
        btnReturn.setBounds(390, 450, 100, 40);
        this.panel.add(btnReturn);
        btnReturn.addActionListener(this);

        btnConsult = new JButton("Consultar");
        btnConsult.setBounds(100, 450, 100, 40);
        this.panel.add(btnConsult);
        btnConsult.addActionListener(this);

        this.panel.setLayout(null);
        this.panel.setVisible(true);
        this.panel.setBackground(Color.WHITE);
        this.getContentPane().add(panel);

        this.showHeader();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnConsult){
            try {
                Facade.getFacadeInstance().updateConsultNumber();
                this.panel.repaint();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        else if(e.getSource() == btnReturn){
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
}
