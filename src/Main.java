import jdk.nashorn.internal.objects.NativeJSON;
import models.User;
import repositories.UserRepository;
import views.*;

import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {
//        User user = new User("user1@gmail.com", "23456", "pilar", "AAA");
//        User user2 = new User("user2@gmail.com", "2345111116", "pilar", "AAA");
//        UserRepository userRepository = new UserRepository();
//
//        userRepository.createUser(user);
//        userRepository.createUser(user2);
//
//        user2 = userRepository.getUser("user2@gmail.com");
//        user2.setEmail("user2@hotmail.com");
//        userRepository.updateUser(user2);
//
//       // userRepository.deleteUser(user);
//        User temp;
//        temp = userRepository.getUser("user1@gmail.com");
//
//        System.out.println(temp);
         LoginView.showScreen();
        //new RegisterView();
       //new ChangePwView();
        //new ExitView();
        //new MenuView();
    }
}
