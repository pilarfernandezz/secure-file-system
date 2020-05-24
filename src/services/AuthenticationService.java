package services;

import facade.Facade;
import models.LockedUser;
import models.User;
import repositories.LockedUserRepository;
import repositories.UserRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class AuthenticationService {
    private static AuthenticationService instancia;

    private String email;
    private String password;
    private User loggedUser;
    private static UserRepository userRepository;
    private static LockedUserRepository lockedUserRepository;

    public static AuthenticationService getAuthenticationInstance() throws SQLException {
        userRepository = UserRepository.getUserRepositoryInstance();
        lockedUserRepository = LockedUserRepository.getLockedUserRepositoryInstance();
        if (instancia == null)
            instancia = new AuthenticationService();
        return instancia;
    }

    public boolean checkEmail(String email) throws SQLException {
        if (this.loggedUser != null) {
            System.out.println("Já existe um usuário logado");
            return false;
        }
        else if (email.trim().length() == 0) {
            System.out.println("Email em branco");
            return false;
        } else {
            User user = userRepository.getUser(email);
            if (user != null)
                return true;
        }
        return false;
    }

    public void makeUserLogged(String email) throws SQLException {
            this.loggedUser= userRepository.getUser(email);
    }

    public void registerUser(String certificatePath, String group, String password, String passwordConfirmation) throws Exception {
        String salt = this.saltGenerator();
        User user = new User(password + salt, passwordConfirmation + salt, group, certificatePath);
        user.setSalt(salt);
        String errors = this.verifyFields(user);
        if (errors != "") {
            throw new Exception(errors);
        }

        if (userRepository.getUser(user.getEmail()) != null) {
            throw new Exception("Usuário " + user.getEmail() + " já existe");
        }
        if (user.getPassword() != user.getPasswordConfirmation()) {
            throw new Exception("Senhas não coincidem");
        }

        userRepository.createUser(user);
    }

    public void updateUser(String certificatePath, String password, String passwordConfirmation) throws Exception {
        System.out.println(loggedUser);
        this.loggedUser.setCertificatePath(certificatePath);
        this.loggedUser.setPassword(password + this.loggedUser.getSalt());
        this.loggedUser.setPasswordConfirmation(passwordConfirmation + this.loggedUser.getSalt());
        System.out.println(loggedUser);
        String errors = this.verifyFields(this.loggedUser);
        if (errors != "") {
            System.out.println("1");
            throw new Exception(errors);
        }

        if (!this.loggedUser.getPassword().equals(this.loggedUser.getPasswordConfirmation())) {
            System.out.println("2");
            throw new Exception("Senhas não coincidem");
        }

        if (this.loggedUser.getPassword() == null || this.loggedUser.getPasswordConfirmation() == null || this.loggedUser.getCertificatePath() == null) {
            System.out.println("3");
            throw new Exception("Campos em branco");
        }
        System.out.println("4");
        userRepository.updateUser(this.loggedUser);
    }

    public User findUser(String email) throws Exception {
        try {
            if (email != null && email.length() > 0) {
                return userRepository.getUser(email);
            }
        } catch (Exception e) {
            throw new Exception("Email procurado é inválido");
        }
        return null;
    }

    public void logout() {
        this.loggedUser = null;
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean verifyIsLocked(String email) throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        LockedUser lockedUser = LockedUserRepository.getLockedUserRepositoryInstance().getLockedUser(email);

        if(lockedUser != null) {
            LocalDateTime lockDate = lockedUser.getLockDate();
            System.out.println(localDateTime.toString() + " " + lockDate.toString());
            if(LocalDateTime.parse(LocalDateTime.now().toString()).minusMinutes(2).compareTo(lockedUser.getLockDate()) < 0){
                return true;
            } else {
                this.unlockUser(email);
            }
        }
        return false;
    }

    public void lockUser(String email) throws SQLException {
        User user = userRepository.getUser(email);
        LockedUserRepository.getLockedUserRepositoryInstance().createLockedUser(user);
    }

    public void unlockUser(String email) throws Exception {
        LockedUserRepository.getLockedUserRepositoryInstance().updateLockedUser(email, false);
    }

    private String saltGenerator() {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String salt = "";
        Random rand = new Random();

        int upperbound = alphabet.length();

        for (int i = 0; i < 10; i++) {
            int int_random = rand.nextInt(upperbound);

            salt += alphabet.substring(int_random, int_random + 1);
        }

        return salt;
    }


    public String verifyFields(User user) {
        String errors = "";
        if (user.getPassword() == null || user.getPassword() == "") {
            errors += "Senha não inserida\n";
        }
        if (user.getPasswordConfirmation() == null || user.getPasswordConfirmation() == "") {
            errors += "Confirmação da senha não inserida\n";
        }
        if (user.getGroup() == null || user.getGroup() == "") {
            errors += "Grupo não inserido";
        }
        if (user.getCertificatePath() == null || user.getCertificatePath() == "") {
            errors += "Caminho do certificado não inserido\n";
        }
        return errors;
    }

    public boolean verifyPassword(String email, int cont, String n1, String n2) throws SQLException {
       User user= userRepository.getUser(email);
       String pw = user.getPassword().substring(0,user.getPassword().length()-10);
       if(cont <= pw.length() && (pw.substring(cont-1, cont).equals(n1) || pw.substring(cont-1, cont).equals(n2))) return true;
       return false;
    }
}