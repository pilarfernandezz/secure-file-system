package repositories;

import models.User;

import java.sql.*;

public class UserRepository {
    private Connection conn;

    public UserRepository() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:mysql://root@localhost/secure_file_system");

    }

    public void createUser(User user){
        try{
            if(this.getUser(user.getEmail()) != null){
                throw new Exception("Usuário "+ user.getEmail() + " já existe");
            }
            String query = "insert into users values(?,?,?,?,?);";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setInt(1, this.getNextId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getName());
            ps.setString(5, user.getGroup());

            ps.execute();
            System.out.println("Usuário criado com sucesso");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao criar o usuario: " + user.getEmail()+ ". \n" + e.getMessage());
        }
    }

    public void updateUser(User user) throws SQLException {
        try{
            if(this.getUser(user.getEmail()) != null && this.getUser(user.getEmail()).getId() != user.getId()){
                throw new Exception("Usuário "+ user.getEmail() + " já existe");
            }
            String query = "update users set email = ?, password = ?, name = ?, user_group = ? where id = ?";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getGroup());
            ps.setInt(5, user.getId());


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
            User user = new User(res.getString("email"), res.getString("password"),res.getString("name"),res.getString("user_group"));
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
}