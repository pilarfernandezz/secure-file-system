package facade;

import models.User;
import services.AuthenticationService;
import views.EmailView;

import java.sql.SQLException;

public class Facade {
    private static Facade instancia;

    public static Facade getFacadeInstance() {
        if (instancia == null)
            instancia = new Facade();
        return instancia;
    }

    public static void start(){
        new EmailView();
    }

    public static void registerUser(String certificatePath, String group, String password, String passwordConfirmation) throws Exception {
        AuthenticationService.getAuthenticationInstance().registerUser(certificatePath, group, password, passwordConfirmation);
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

    public static boolean verifyPassword(String email,int cont,  String n1, String n2) throws SQLException {
        return AuthenticationService.getAuthenticationInstance().verifyPassword(email,cont, n1,n2);
    }

    public static void makeUserLogged(String email) throws SQLException {
        AuthenticationService.getAuthenticationInstance().makeUserLogged(email);
    }
}
