package facade;

import models.User;
import repositories.UserRepository;

import java.sql.SQLException;

public class Facade {
    private static Facade instancia;

    public static Facade getFacadeInstance(){
        if(instancia == null)
            instancia = new Facade();
        return instancia;
    }

    public static void registraUsuário(String certificatePath, String group,String password, String passwordConfirmation) throws SQLException {
        User user = new User(password,passwordConfirmation, group,certificatePath);
        UserRepository userRepository = new UserRepository();

        userRepository.createUser(user);
    }
}
