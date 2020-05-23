import models.User;
import repositories.UserRepository;
import views.EmailView;

import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {
//        User user2 = new User("", "" ,"", "");
//        UserRepository userRepository = new UserRepository();
//////
//        userRepository.createUser(user2);

//       Authentication authentication = new Authentication("pilar@gmail.com", "23456");
//       boolean res = authentication.login();
//       User logged = authentication.getLoggedUser();
//       System.out.println(logged);
//       authentication.logout();
//       logged = authentication.getLoggedUser();
//       System.out.println(logged);
//       System.out.println(res);
//        UserRepository userRepository = new UserRepository();
//        User u=userRepository.getUser("pilar@gmail.com");
//
//
//        System.out.println(u);
//        userRepository.createUser(user2);
//
//        user2 = userRepository.getUser("user2@gmail.com");
//        user2.setEmail("user2@hotmail.com");
//        userRepository.updateUser(user2);
//
        //userRepository.deleteUser(user.getId());
//        User temp;
//        temp = userRepository.getUser("user1@gmail.com");
//

        new EmailView();
//        System.out.println(temp);
        // LoginView.showScreen();
        //new RegisterView();
       //new ChangePwView();
        //new ExitView();
        //new MenuView();
    }
}
