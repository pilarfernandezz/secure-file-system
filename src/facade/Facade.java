package facade;

import exceptions.InvalidExtractionCertificateOwnerInfoException;
import models.User;
import services.AuthenticationService;
import services.CipherService;
import services.DigitalCertificateService;
import services.IndexService;
import views.EmailView;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

public class Facade {
    private static Facade instancia;

    public static Facade getFacadeInstance() {
        if (instancia == null)
            instancia = new Facade();
        return instancia;
    }

    public static void start() throws SQLException {
        new EmailView();
    }

    public static int getNumberOfUsersRegistered() throws SQLException {
        return AuthenticationService.getAuthenticationInstance().getNumberOfUsersRegistered();
    }

    public static void updateConsultNumber() throws SQLException {
        AuthenticationService.getAuthenticationInstance().updateNumberConsult();
    }

    public static void registerUser(String certificatePath, String group, String password, String passwordConfirmation) throws Exception {
        AuthenticationService.getAuthenticationInstance().registerUser(certificatePath, group, password, passwordConfirmation);

    }

    public static Map<String, String> extractCertificate(String path) throws InvalidExtractionCertificateOwnerInfoException {
        return DigitalCertificateService.getInstance().extractCertificateOwnerInfo(path);
    }

    public static User getLoggedUser() throws SQLException {
        return AuthenticationService.getAuthenticationInstance().getLoggedUser();
    }

    public static boolean checkEmail(String email) throws SQLException {
        return AuthenticationService.getAuthenticationInstance().checkEmail(email);
    }

    public static User findUser(String email) throws Exception {
        return AuthenticationService.getAuthenticationInstance().findUser(email);
    }

    public static void updateUser(String certificatePath, String password, String passwordConfirmation) throws Exception {
        AuthenticationService.getAuthenticationInstance().updateUser(certificatePath, password, passwordConfirmation);
    }

    public static boolean verifyIsLocked(String email) throws Exception {
        return AuthenticationService.getAuthenticationInstance().verifyIsLocked(email);
    }

    public static void lockUser(String email) throws SQLException {
        AuthenticationService.getAuthenticationInstance().lockUser(email);
    }

    public static boolean verifyPassword(String email, int cont, String n1, String n2) throws SQLException {
        return AuthenticationService.getAuthenticationInstance().verifyPassword(email, cont, n1, n2);
    }

    public static void makeUserLogged(String email) throws Exception {
        AuthenticationService.getAuthenticationInstance().makeUserLogged(email);
    }

    public static int getRowSize(){
        return 3;
        //TODO DESMARRETAR
    }

    public static byte[] decryptFile(String keysRootPath, String fileRootPath, String userName, String fileName, boolean save, String newFileName) throws Exception {
        CipherService.getInstance().setKeysRootPath(keysRootPath);
        CipherService.getInstance().setFileRootPath(fileRootPath);
        System.out.println(keysRootPath + " " + fileRootPath);
        return CipherService.getInstance().decryptFileContent(userName, fileName, save, newFileName);
    }

    public static Map<String,String> getIndexInfo(byte[] index) throws UnsupportedEncodingException {
        return IndexService.getInstance().getIndexInfo(index);
    }
}
