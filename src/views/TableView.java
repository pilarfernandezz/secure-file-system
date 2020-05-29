package views;

import facade.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;

public class TableView extends JPanel implements MouseListener {
    private JLabel table[][];
    private byte[] content;
    private String path;
    private String[][] index;

    public TableView() {
        super();

        this.setBackground(Color.WHITE);

        this.setSize(1580, 630);
        this.setLayout(null);
        this.setVisible(true);
    }

    public void showInfo() throws Exception {
        int x = 0, y = 0;
        this.index = Facade.getIndexInfo(this.content);

        this.table = new JLabel[index.length + 1][3];

        this.table[0][0] = new JLabel("Nome do arquivo");
        this.table[0][1] = new JLabel("Dono");
        this.table[0][2] = new JLabel("Grupo");
        for (int i = 0; i < 3; i++) {
            this.table[0][i].setFont(new Font("Arial", Font.BOLD, 15));
        }
        int j = 0;
        int i = 0;
        for (i = 0; i < index.length; i++) {
            for (j = 0; j < 3; j++) {
                if (i != 0) { //Adiciona informações dos arquivos na tabela
                    String content = index[i - 1][j + 1];
                    this.table[i][j] = new JLabel(content);
                    if (j == 0) this.table[i][j].addMouseListener(this);
                }

                this.table[i][j].setBounds(x, y, 526, 20);

                this.add(this.table[i][j]);
                x += 530;
            }
            x = 0;
            y += 23;
        }

        for (int k = 0; k < 3; k++) {
            this.table[i][k] = new JLabel(index[i - 1][k + 1]);
            this.table[i][k].addMouseListener(this);
            this.table[i][k].setBounds(x, y, 526, 20);

            this.add(this.table[i][k]);
            x += 530;
        }
        this.repaint();
    }

    private String normalizeString(String s) {
        return s.toLowerCase().trim().replace("á", "a").replace("ã", "a")
                .replace("à", "a").replace("é", "e").replace("ê", "e")
                .replace("ô", "o").replace("ó", "o").replace("ç", "c")
                .replace("õ", "o").replace("ú", "u");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JLabel) {
            Facade.registerLogMessage(8010, Facade.getLoggedUser().getEmail(), ((JLabel) e.getSource()).getText(), LocalDateTime.now());

            String nameEncoded = null;
            for (int i = 0; i < this.index.length; i++) {
                if (this.index[i][1].equals(((JLabel) e.getSource()).getText())) {
                    if (this.normalizeString(Facade.getLoggedUser().getName()).equals(this.normalizeString(this.index[i][2]))
                            || this.normalizeString(Facade.getLoggedUser().getGroup()).equals(this.normalizeString(this.index[i][3]))) {
                        Facade.registerLogMessage(8011, Facade.getLoggedUser().getEmail(), ((JLabel) e.getSource()).getText(), LocalDateTime.now());
                        nameEncoded = this.index[i][0];
                        break;
                    } else {
                        Facade.registerLogMessage(8012, Facade.getLoggedUser().getEmail(), ((JLabel) e.getSource()).getText(), LocalDateTime.now());
                        JOptionPane.showMessageDialog(null, "Arquivo " + ((JLabel) e.getSource()).getText() + " não pode ser decriptado. Acesso negado.");
                    }
                }
            }
            try {
                if (nameEncoded != null && Facade.decryptFile(Facade.getLoggedUser().getEmail(), path + "/" + nameEncoded, true, ((JLabel) e.getSource()).getText()) != null) {
                    JOptionPane.showMessageDialog(null, "Arquivo " + ((JLabel) e.getSource()).getText() + " decriptado e salvo com sucesso.");
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao decriptar e salvar o arquivo " + ((JLabel) e.getSource()).getText() + ".");
            }
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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
