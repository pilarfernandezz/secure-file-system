package facade;

import exceptions.InvalidCertificateException;
import exceptions.InvalidExtractionCertificateOwnerInfoException;
import models.User;
import services.*;
import views.EmailView;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Facade {
    public static void start() throws SQLException {
        new EmailView();
    }

    public static int getNumberOfUsersRegistered() {
        return AuthenticationService.getInstance().getNumberOfUsersRegistered();
    }

    public static void updateConsultNumber() {
        AuthenticationService.getInstance().updateNumberConsult();
    }

    public static void registerUser(String certificatePath, String group, String password) {
        AuthenticationService.getInstance().registerUser(certificatePath, group, password);

    }

    public static Map<String, String> extractCertificate(String path) throws InvalidExtractionCertificateOwnerInfoException {
        return DigitalCertificateService.getInstance().extractCertificateOwnerInfo(path, true);
    }

    public static User getLoggedUser() {
        return AuthenticationService.getInstance().getLoggedUser();
    }

    public static boolean checkEmail(String email) {
        return AuthenticationService.getInstance().checkEmail(email);
    }

    public static User findUser(String email) {
        return AuthenticationService.getInstance().findUser(email);
    }

    public static void updateUser(String certificatePath, String password, String passwordConfirmation) {
        AuthenticationService.getInstance().updateUser(certificatePath, password, passwordConfirmation);
    }

    public static boolean verifyIsLocked(String email) {
        return AuthenticationService.getInstance().verifyIsLocked(email);
    }

    public static void lockUser(String email) {
        AuthenticationService.getInstance().lockUser(email);
    }

    public static boolean verifyPassword(List<int[]> typedPw, String email) {
        return AuthenticationService.getInstance().verifyPassword(typedPw, email);
    }

    public static void makeUserLogged(String email, String path, String secret) {
        AuthenticationService.getInstance().makeUserLogged(email, path, secret);
    }

    public static byte[] decryptFile(String email, String fileName, boolean save, String newFileName) throws Exception {
        return CipherService.getInstance().decryptFileContent(Facade.findUser(email), fileName, save, newFileName);
    }

    public static String[][] getIndexInfo(byte[] index) throws Exception {
        return IndexService.getInstance().getIndexInfo(index);
    }

    public static boolean keysValidation(String email, String path, String secret) {
        return AuthenticationService.getInstance().keysValidation(email, path, secret);
    }

    public static boolean validatePassword(String pw) {
        return AuthenticationService.getInstance().validatePassword(pw);
    }

    public static boolean validateCertificate(String path) throws FileNotFoundException, InvalidCertificateException {
        return DigitalCertificateService.getInstance().loadCertificate(path, true) != null ? true : false;
    }

    public static void registerLogMessage(int code, String login, String arq, LocalDateTime creationDatetime){
        LogService.getInstance().registerLogMessage(code, login, arq, creationDatetime);
    }
}
