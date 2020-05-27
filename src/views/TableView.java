package views;

import facade.Facade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

public class TableView extends JPanel implements MouseListener {
    private JLabel table[][];
    public TableView(){
        super();

        this.setBackground(Color.WHITE);

        int x=0, y=0;
        this.table = new JLabel[Facade.getFacadeInstance().getRowSize()+1][4];

        this.table[0][0] = new JLabel("Nome do arquivo");
        this.table[0][1] = new JLabel("Dono");
        this.table[0][2] = new JLabel("Grupo");
        this.table[0][3] = new JLabel("Status");
        for(int i = 0; i < 4;i++)  this.table[0][i].setFont(new Font("Arial",Font.BOLD, 15));

        for(int i = 0; i < Facade.getFacadeInstance().getRowSize()+1; i++){
            for(int j = 0 ; j < 4 ; j++){
                if(i != 0){ //Adiciona informações dos arquivos na tabela
                    this.table[i][j] = new JLabel("texto");
                    if(j==0)this.table[i][j].addMouseListener(this);
                }
                this.table[i][j].setBounds(x, y, 395,20);

                this.add( this.table[i][j]);
                x+=400;
            }
            y+=23; x=0;
        }

        this.setSize(1580,630);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof JLabel) {
            System.out.println(((JLabel) e.getSource()).getText());
        }
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
