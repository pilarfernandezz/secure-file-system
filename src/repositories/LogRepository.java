package repositories;

import models.User;

import java.sql.*;

public class LogRepository {
    private Connection conn;
    private static LogRepository instance;

    public static LogRepository getInstance() throws SQLException {
        if (instance == null)
            instance = new LogRepository();
        return instance;
    }

    private LogRepository() {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://root@localhost/secure_file_system");
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao conectar com o banco: \n" + e.getMessage());
        }
    }

    public String getMessagePattern(int id) {
        try {
            String query = "select * from messages where id = '" + id + "';";
            ResultSet res = this.conn.createStatement().executeQuery(query);
            if (res.next()) {
               return res.getString("message");
            }
            return null;
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao buscar o mensagem: \n" + e.getMessage());
            return null;
        }
    }

    public void insertLogMessage(String msg){
        try{
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
            //TODO LOG
            System.out.println("Usuário criado com sucesso");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao inserir a mensagem de log: \n" + e.getMessage());
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
