package facade;

import models.User;
import repositories.UserRepository;
import services.Authentication;

import java.sql.SQLException;

public class Facade {
    private static Facade instancia;

    public static Facade getFacadeInstance(){
        if(instancia == null)
            instancia = new Facade();
        return instancia;
    }

    public static void registerUser(String certificatePath, String group,String password, String passwordConfirmation) throws SQLException {
        User user = new User(password,passwordConfirmation, group,certificatePath);
        UserRepository userRepository = new UserRepository();
        userRepository.createUser(user);
    }

    public static boolean checkEmail (String email) throws SQLException {
        System.out.println(email);
        UserRepository userRepository = new UserRepository();

        if(userRepository.getUser(email) == null ){
            return false;
        }
        return true;
    }

    public static boolean checkPassword (String email, String pw) throws SQLException {
        UserRepository userRepository = new UserRepository();
        User user = userRepository.getUser(email);
        return Authentication.getAuthenticationInstance().login(user.getEmail(), pw);
    }

    public static User findUser (String email) throws SQLException {
        UserRepository userRepository = new UserRepository();
        User user = userRepository.getUser(email);
        return user;
    }

    public static void updateUser(String certificatePath, String password, String passwordConfirmation) throws Exception {
        User user = Authentication.getAuthenticationInstance().getLoggedUser();
        System.out.println(user);
        user.setCertificatePath(certificatePath);
        user.setPassword(password);
        user.setPasswordConfirmation(passwordConfirmation);

        UserRepository userRepository = new UserRepository();
        userRepository.updateUser(user);
    }
}
