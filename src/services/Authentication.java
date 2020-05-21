package services;

import models.User;
import repositories.UserRepository;

import java.sql.SQLException;

public class Authentication {
    private String email;
    private String password;
    private User loggedUser;
    UserRepository userRepository = new UserRepository();

    public Authentication(String email, String password) throws SQLException {
        this.email = email;
        this.password = password;
    }

    public boolean login() throws SQLException {
        if(this.loggedUser != null){
            System.out.println("Já existe um usuário logado");
            return false;
        }

        if(this.email == "" || this.password == ""){
            System.out.println("Email ou senha em branco");
            return false;
        }
        else {
            User user = userRepository.getUser(this.email);
            if (user == null){
                System.out.println("Email não encontrado");
                return false;
            } else if (user.getPassword().substring(0,user.getPassword().length() - 10).equals( this.password)){
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
