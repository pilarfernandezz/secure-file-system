package views;

import exceptions.InvalidExtractionCertificateOwnerInfoException;
import facade.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;

public class ConfirmationView extends Frame implements ActionListener {
    private static ConfirmationView instance;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private static String password;
    private static String passwordConfirmation;
    private static String certificatePath;
    private static String group;
    private JLabel lblPassword;
    private JLabel lblCertificate;
    private JLabel lblGroup;
    private JLabel lblTitle;
    private JLabel lblText;
    private JLabel lblTotal;
    private JLabel lblEmail;
    private JLabel lblName;
    private JLabel lblTotalQtd;
    private Map<String, String> certInfo;
    private JLabel lblVersion;
    private JLabel lblIssuer;
    private JLabel lblSerie;
    private JLabel lblValidUntil;
    private JLabel lblSignType;
    private static JButton btnRegister;
    private static JButton btnReturn;
    private static boolean register;
    private int totalQtd =0;

    public ConfirmationView() throws InvalidExtractionCertificateOwnerInfoException, SQLException {
        super();

        this.certInfo = Facade.getFacadeInstance().extractCertificate(certificatePath);

        this.setBackground(Color.WHITE);

        lblTitle = new JLabel("Sistema de arquivos seguro");
        lblTitle.setBounds(160, -200, 800, 600);
        lblTitle.setFont(titleFont);
        this.panel.setBounds(0, 0, 800, 600);
        this.panel.add(lblTitle);

        lblTotal = new JLabel("Total de usuários do sistema:");
        lblTotal.setBounds(280, -160, 800, 600);
        this.panel.add(lblTotal);

        lblTotalQtd = new JLabel(String.valueOf(Facade.getFacadeInstance().getNumberOfUsersRegistered()));
        lblTotalQtd.setBounds(470, -160, 800, 600);
        this.panel.add(lblTotalQtd);

        lblText = new JLabel("Confirmação cadastro:");
        lblText.setBounds(300, -120, 800, 600);
        this.panel.add(lblText);

        lblCertificate = new JLabel("Caminho do certificado digital: " + ConfirmationView.certificatePath );
        lblCertificate.setBounds(110, 215, 500, 20);
        this.panel.add(lblCertificate);

        lblSerie = new JLabel("Serie: " + this.certInfo.get("SERIE") );
        lblSerie.setBounds(110, 215, 500, 20);
        this.panel.add(lblSerie);

        lblIssuer = new JLabel("Emissor: " + this.certInfo.get("ISSUER") );
        lblSerie.setBounds(110, 240, 500, 20);
        this.panel.add(lblSerie);

        lblSignType = new JLabel("Tipo assinatura: " + this.certInfo.get("SIGNATURETYPE") );
        lblSignType.setBounds(110, 265, 500, 20);
        this.panel.add(lblSignType);

        lblVersion = new JLabel("Versão: " + this.certInfo.get("VERSION") );
        lblVersion.setBounds(110, 290, 500, 20);
        this.panel.add(lblVersion);

        lblValidUntil = new JLabel("Válido até: " + this.certInfo.get("VALIDUNTIL"));
        lblValidUntil.setBounds(110, 315, 500, 20);
        this.panel.add(lblValidUntil);

        lblGroup = new JLabel("Grupo: " + ConfirmationView.group);
        lblGroup.setBounds(110, 340, 300, 20);
        this.panel.add(lblGroup);

        lblValidUntil = new JLabel("Email: " + this.certInfo.get("EMAILADDRESS"));
        lblValidUntil.setBounds(110, 365, 500, 20);
        this.panel.add(lblValidUntil);

        lblGroup = new JLabel("Nome: " + this.certInfo.get("CN"));
        lblGroup.setBounds(110, 390, 300, 20);
        this.panel.add(lblGroup);

        btnRegister = new JButton("Registrar");
        btnRegister.setBounds(280, 450, 100, 40);
        this.panel.add(btnRegister);
        btnRegister.addActionListener(this);

        btnReturn = new JButton("Voltar");
        btnReturn.setBounds(390, 450, 100, 40);
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
        if(e.getSource() == btnRegister){
            this.setVisible(false);
            this.dispose();
            try {
                if(register) {
                    Facade.getFacadeInstance().registerUser(certificatePath, group, password, passwordConfirmation);
                } else {
                    Facade.getFacadeInstance().updateUser(certificatePath, password, passwordConfirmation);
                }
                MenuView.showScreen();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if(e.getSource() == btnReturn){
            this.setVisible(false);
            this.dispose();
            try {
                RegisterView.showScreen();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void showScreen(boolean register,String certificatePath,String group, String password, String passwordConfirmation) throws InvalidExtractionCertificateOwnerInfoException, SQLException {
        ConfirmationView.register = register;
        ConfirmationView.certificatePath = certificatePath;
        ConfirmationView.group = group;
        ConfirmationView.password = password;
        ConfirmationView.passwordConfirmation = passwordConfirmation;
        new ConfirmationView();
    }

    public int getTotalQtd() {
        return totalQtd;
    }

    public void setTotalQtd(int totalQtd) {
        this.totalQtd = totalQtd;
    }
}
