package views;

import facade.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class PasswordView extends Frame implements ActionListener {
    private static EmailView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JLabel lblAlert;
    private static JTextField txtEmail = null;
    private static JPasswordField passPassword = null;
    private static JButton btnStart;
    private static JButton btnCancel;
    private static String email;
    private static int passwordErrors = 0;
    private int [] numbers;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btndelete;
    private List<int[]> typedPw;

    public PasswordView() throws SQLException {
        super();

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -250, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblText = new JLabel("Digite sua senha:");
        lblText.setBounds(340, 100, 200, 50);
        this.panel.add(lblText);

        lblEmail = new JLabel("Senha:");
        lblEmail.setBounds(250, 150, 100, 50);
        this.panel.add(lblEmail);

        passPassword = new JPasswordField(8);
        passPassword.setBounds(320, 160, 200, 30);
        passPassword.setEditable(false);
        this.panel.add(passPassword);

        this.numbers = this.generateOrder();
        btn1 = new JButton(numbers[0] + " ou " + numbers[1]);
        btn1.setBounds(300, 200, 80, 40);
        this.panel.add(btn1);
        btn1.addActionListener(this);

        btn2 = new JButton(numbers[2] + " ou " + numbers[3]);
        btn2.setBounds(380, 200, 80, 40);
        this.panel.add(btn2);
        btn2.addActionListener(this);

        btn3 = new JButton(numbers[4] + " ou " + numbers[5]);
        btn3.setBounds(300, 240, 80, 40);
        this.panel.add(btn3);
        btn3.addActionListener(this);

        btn4 = new JButton(numbers[6] + " ou " + numbers[7]);
        btn4.setBounds(380, 240, 80, 40);
        this.panel.add(btn4);
        btn4.addActionListener(this);

        btn5 = new JButton(numbers[8] + " ou " + numbers[9]);
        btn5.setBounds(300, 280, 80, 40);
        this.panel.add(btn5);
        btn5.addActionListener(this);
        this.generateOrder();

        btndelete = new JButton("LIMPAR");
        btndelete.setBounds(380, 280, 80, 40);
        this.panel.add(btndelete);
        btndelete.addActionListener(this);

        lblAlert = new JLabel("Senha incorreta.");
        lblAlert.setForeground(Color.red);
        lblAlert.setBounds(320, 330, 200, 50);
        this.panel.add(lblAlert);
        lblAlert.setVisible(false);

        btnStart = new JButton("Seguir");
        btnStart.setBounds(280, 400, 100, 40);
        this.panel.add(btnStart);
        btnStart.addActionListener(this);

        btnCancel = new JButton("Cancelar");
        btnCancel.setBounds(390, 400, 100, 40);
        this.panel.add(btnCancel);
        btnCancel.addActionListener(this);

        this.panel.setLayout(null);
        this.panel.setVisible(true);
        this.panel.setBackground(Color.WHITE);
        this.getContentPane().add(panel);

        this.setLayout(null);
        this.setVisible(true);
    }

    public static void showScreen(String email) throws SQLException {
        PasswordView.email = email;
        new PasswordView();
    }

    public int[] generateOrder(){
        int index, temp;
        int[] array = new int[10];
        for (int i = 0 ; i < 10 ; i++) array[i] = i;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        return array;
    }

    public void changeButtons(){
        btn1.setText(numbers[0] + " ou " + numbers[1]);
        btn2.setText(numbers[2] + " ou " + numbers[3]);
        btn3.setText(numbers[4] + " ou " + numbers[5]);
        btn4.setText(numbers[6] + " ou " + numbers[7]);
        btn5.setText(numbers[8] + " ou " + numbers[9]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btndelete){
            passPassword.setText("");
            this.typedPw = null;
            this.panel.repaint();
        }

        if(e.getSource() == btn1 ||e.getSource() == btn2 || e.getSource() == btn3 || e.getSource() == btn4 || e.getSource() == btn5 ){
            String[] nums = ((JButton)e.getSource()).getText().split(" ou ");
            if(this.typedPw == null){
                this.typedPw = new ArrayList<>();
            }

            int[] typed = new int[2];
            typed[0] = Integer.parseInt(nums[0]);
            typed[1] = Integer.parseInt(nums[1]);
            this.typedPw.add(typed);

            passPassword.setText(passPassword.getText() + "*");
            this.panel.repaint();
            try {
                this.numbers = this.generateOrder();
                this.changeButtons();
                this.panel.repaint();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        if(e.getSource() == btnStart){
            lblAlert.setVisible(false);

            if(this.passwordErrors > 2){
                this.passwordErrors=0;
                try {
                    JOptionPane.showMessageDialog(null, "Usuário foi bloqueado por 2 minutos por exceder as tentativas de autenticação.");
                    Facade.lockUser(this.email);
                    EmailView.showScreen();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else {
                try {
                    if (typedPw==null || typedPw.size() == 0 || !Facade.verifyPassword(typedPw, this.email)){
                        this.passwordErrors++;
                        lblAlert.setVisible(true);
                        this.panel.repaint();
                    } else {
                        this.setVisible(false);
                        this.dispose();
                        PvtKeyView.showScreen(this.email);
                    }
                    this.typedPw = null;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } else if(e.getSource() == btnCancel){
            System.exit(1);
        }
    }
}
