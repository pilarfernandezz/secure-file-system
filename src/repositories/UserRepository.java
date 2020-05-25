package repositories;

import models.User;

import java.sql.*;

public class UserRepository {
    private Connection conn;
    private static UserRepository instancia;

    public static UserRepository getUserRepositoryInstance() throws SQLException {
        if (instancia == null)
            instancia = new UserRepository();
        return instancia;
    }

    private UserRepository() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:mysql://root@localhost/secure_file_system");
    }

    public int countUsers() throws SQLException {
        try{
            String query = "select count(*) from users;";
            ResultSet res = this.conn.createStatement().executeQuery(query);
            if (res.next()) {
                return res.getInt("count(*)");
            }

            return 0;
        } catch (Exception e){
            throw e;
        }
    }

    public int getTotalAccess(int id) throws SQLException {
        String query = "select total_access from users where id = " + id + ";";
        ResultSet res = this.conn.createStatement().executeQuery(query);
        if (res.next()) {
            return res.getInt("total_access");
        }
        return 0;
    }

    public void updateTotalAccess(int id, int totalAccess) throws SQLException {
        try {
            String query = "update users set total_access = ? where id = ?;";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setInt(1, totalAccess);
            ps.setInt(2, id);
            ps.execute();
        } catch(Exception e){
            throw e;
        }
    }

    public void updateTotalConsults(int id, int totalConsults) throws SQLException {
        try {
            String query = "update users set total_consults = ? where id = ?;";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setInt(1, totalConsults);
            ps.setInt(2, id);
            ps.execute();
        } catch(Exception e){
            throw e;
        }
    }

    public void createUser(User user) {
        try {
            String query = "insert into users (id,email,password,name,user_group, certificate,salt) values(?,?,?,?,?,?,?);";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setInt(1, this.getNextId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getName());
            ps.setString(5, user.getGroup());
            ps.setString(6, user.getCertificate());
            ps.setString(7, user.getSalt());

            ps.execute();
            System.out.println("Usuário criado com sucesso");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao criar o usuario: " + user.getEmail() + ". \n" + e.getMessage());
        }
    }

    public void updateUser(User user) throws Exception {
        try {

            System.out.println("------- " + user.getCertificate());
            String query = "update users set email = ?, password = ?, name = ?, user_group = ?, certificate = ?, salt = ? where id = ?";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getGroup());
            ps.setString(5, user.getCertificate());
            ps.setString(6, user.getSalt());
            ps.setInt(7, user.getId());

            ps.execute();
            System.out.println("Usuário atualizado com sucesso");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao atualizar o usuario: \n" + e.getMessage());
        }
    }

    public void deleteUser(int id) throws SQLException {
        try {
            String query = "delete from users where id = '" + id + "';";
            this.conn.createStatement().execute(query);
            System.out.println("Usuário deletado com sucesso");
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao deletar o usuario \n" + e.getMessage());
        }
    }

    public User getUser(String email) throws SQLException {
        String query = "select * from users where email = '" + email + "';";
        ResultSet res = this.conn.createStatement().executeQuery(query);
        if (res.next()) {
            User user = new User(res.getString("password"), res.getString("password"), res.getString("user_group"),null, res.getInt("total_access"), res.getInt("total_consults"));
            user.setEmail(email);
            user.setCertificate(res.getString("certificate"));
            user.setName(res.getString("name"));
            user.setSalt(res.getString("salt"));
            user.setId(res.getInt("id"));
            return user;
        }
        return null;
    }

    public int getNextId() throws SQLException {
        String query = "select id from users order by id desc limit 1;";
        ResultSet res = this.conn.createStatement().executeQuery(query);
        if (res.next())
            return res.getInt(1) + 1;
        return 1;
    }
}
