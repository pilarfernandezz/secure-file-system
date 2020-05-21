package repositories;

import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserRepository {
    private Connection conn;

    public UserRepository() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:mysql://root@localhost/secure_file_system");

    }

    public void createUser(User user){
        try{
            String Errors = this.verifyFields(user);
            if(Errors != "" ){
                throw new Exception(Errors);
            }

            if(this.getUser(user.getEmail()) != null){
                throw new Exception("Usuário "+ user.getEmail() + " já existe");
            }
            if(user.getPassword() != user.getPasswordConfirmation()){
                throw new Exception("Senhas não coincidem");
            }
            String query = "insert into users values(?,?,?,?,?,?);";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setInt(1, this.getNextId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword() + this.saltGenerator());
            ps.setString(4, user.getName());
            ps.setString(5, user.getGroup());
            ps.setString(6, user.getCertificatePath());

            ps.execute();
            System.out.println("Usuário criado com sucesso");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao criar o usuario: " + user.getEmail()+ ". \n" + e.getMessage());
        }
    }

    public void updateUser(User user) throws Exception {
        String Errors = this.verifyFields(user);
        if(Errors != "" ){
            throw new Exception(Errors);
        }

        try{
            if(this.getUser(user.getEmail()) != null && this.getUser(user.getEmail()).getId() != user.getId()){
                throw new Exception("Usuário "+ user.getEmail() + " já existe");
            }

            String query = "update users set email = ?, password = ?, name = ?, user_group = ?, certificate = ? where id = ?";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getGroup());
            ps.setString(5, user.getCertificatePath());
            ps.setInt(6, user.getId());

            ps.execute();
            System.out.println("Usuário atualizado com sucesso");
        } catch (Exception e){
            System.out.println("Ocorreu um erro ao atualizar o usuario: \n" + e.getMessage());
        }
    }

    public void deleteUser(int id) throws SQLException {
        try{
            String query = "delete from users where id = '" +  id + "';";
            this.conn.createStatement().execute(query);
            System.out.println("Usuário deletado com sucesso");

        } catch (SQLException e){
            System.out.println("Ocorreu um erro ao deletar o usuario \n" + e.getMessage());
        }
    }

    public User getUser(String email) throws SQLException {
        String query = "select * from users where email = '" +  email + "';";
        ResultSet res = this.conn.createStatement().executeQuery(query);
        if(res.next()){
            User user = new User(res.getString("password"),res.getString("password"), res.getString("user_group"), res.getString("certificate"));
            user.setId(res.getInt("id"));
            return user;
        }
        return null;
    }

    public int getNextId() throws SQLException {
        String query = "select id from users order by id desc limit 1;";
        ResultSet res = this.conn.createStatement().executeQuery(query);
        if(res.next())
            return res.getInt(1)+1;
        return 1;
    }

    public String saltGenerator() {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String salt = "";
        Random rand = new Random(); //instance of random class

        int upperbound = alphabet.length();

        for (int i = 0 ; i < 10 ; i ++){
            int int_random = rand.nextInt(upperbound);

            salt += alphabet.substring(int_random, int_random+1);
        }

        return salt;
    }

    public String verifyFields(User user){
        String Errors = "";
        if(user.getPassword() == null || user.getPassword() == ""){
            Errors += "Senha não inserida\n";
        }
        if (user.getPasswordConfirmation() == null || user.getPasswordConfirmation() == ""){
            Errors += "Confirmação da senha não inserida\n";
        }
        if (user.getGroup() == null || user.getGroup() == ""){
            Errors += "Grupo não inserido";
        }
        if (user.getCertificatePath() == null || user.getCertificatePath() == ""){
            Errors += "Caminho do certificado não inserido\n";
        }
        return Errors;
    }
}
