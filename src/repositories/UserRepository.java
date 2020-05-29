package repositories;

import models.User;

import java.sql.*;

public class UserRepository {
    private Connection conn;
    private static UserRepository instance;

    public static UserRepository getInstance() throws SQLException {
        if (instance == null)
            instance = new UserRepository();
        return instance;
    }

    private UserRepository() {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://root@localhost/secure_file_system");
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao conectar com o banco: \n" + e.getMessage());
        }
    }

    // Retorna a quantidade de usuários cadastrados no banco na tabela users
    public int countUsers() {
        try {
            String query = "select count(*) from users;";
            ResultSet res = this.conn.createStatement().executeQuery(query);
            if (res.next()) {
                return res.getInt("count(*)");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao contar número de usuários cadastrados no banco: \n" + e.getMessage());
            return 0;
        }
    }

    // Acrescenta 1 ao total de acessos do usuário
    public void updateTotalAccess(int id, int totalAccess) {
        try {
            String query = "update users set total_access = ? where id = ?;";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setInt(1, totalAccess);
            ps.setInt(2, id);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao atualizar o total de acessos do usuario: \n" + e.getMessage());
        }
    }

    // Acrescenta 1 ao total de consultas realizadas pelo usuário
    public void updateTotalConsults(int id, int totalConsults) {
        try {
            String query = "update users set total_consults = ? where id = ?;";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setInt(1, totalConsults);
            ps.setInt(2, id);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao atualizar o total de consultas do usuario: \n" + e.getMessage());
        }
    }

    public void createUser(User user) {
        try {
            String query = "insert into users (id,email,password,name,user_group, certificate,salt) values(?,?,?,?,?,?,?);";
            System.out.println(user.getCertificate());
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setInt(1, this.getNextId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getName());
            ps.setString(5, user.getGroup());
            ps.setString(6, user.getCertificate());
            ps.setString(7, user.getSalt());

            ps.execute();
            //TODO LOG
            System.out.println("Usuário criado com sucesso");
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao criar o usuario: " + user.getEmail() + ". \n" + e.getMessage());
        }
    }

    public void updateUser(User user) {
        try {
            String query = "update users set email = ?, password = ?, name = ?, user_group = ?, certificate = ?, salt = ? where id = ?";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getGroup());
            ps.setString(5, user.getCertificate());
            ps.setString(6, user.getSalt());
            ps.setInt(7, user.getId());

            //TODO LOG
            ps.execute();
            System.out.println("Usuário atualizado com sucesso");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao atualizar o usuario: \n" + e.getMessage());
        }
    }

    public User getUser(String email) {
        try {
            String query = "select * from users where email = '" + email + "';";
            ResultSet res = this.conn.createStatement().executeQuery(query);
            if (res.next()) {
                User user = new User(res.getString("password"), res.getString("password"), res.getString("user_group"), null, res.getInt("total_access"), res.getInt("total_consults"));
                user.setEmail(email);
                user.setCertificate(res.getString("certificate"));
                user.setName(res.getString("name"));
                user.setSalt(res.getString("salt"));
                user.setId(res.getInt("id"));
                //todo log
                return user;
            }
            //todo log
            return null;
        } catch (Exception e) {
            //todo log
            System.out.println("Ocorreu um erro ao buscar o usuario: \n" + e.getMessage());
            return null;
        }
    }

    public int getNextId() throws SQLException {
        String query = "select id from users order by id desc limit 1;";
        ResultSet res = this.conn.createStatement().executeQuery(query);
        if (res.next())
            return res.getInt(1) + 1;
        return 1;
    }
}
