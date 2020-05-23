package services;

import facade.Facade;
import models.User;
import repositories.UserRepository;

import java.sql.SQLException;

public class Authentication {
    private static Authentication instancia;

    private String email;
    private String password;
    private User loggedUser;

    public static Authentication getAuthenticationInstance(){
        if(instancia == null)
            instancia = new Authentication();
        return instancia;
    }

    public boolean login(String email, String password) throws SQLException {
        if(this.loggedUser != null){
            System.out.println("Já existe um usuário logado");
            return false;
        }
        if(this.email == "" || this.password == ""){
            System.out.println("Email ou senha em branco");
            return false;
        }
        else {
            UserRepository userRepository = new UserRepository();
            System.out.println("ss " +email);

            User user = userRepository.getUser(email);
            System.out.println(user);
            if (user == null){
                return false;
            } else if (user.getPassword().substring(0,user.getPassword().length() - 10).equals(password)){
                System.out.println(user);
                this.loggedUser = user;
                return true;
            } else {
                System.out.println("Senha incorreta");
            }
        }
        return false;
    }

    public void logout(){
        this.loggedUser = null;
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
