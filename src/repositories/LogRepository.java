package repositories;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogRepository {
    private Connection conn;
    private static LogRepository instance;

    public static LogRepository getInstance() {
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
            System.out.println("Ocorreu um erro ao buscar a mensagem padrão: \n" + e.getMessage());
            return null;
        }
    }

    public void insertLogMessage(String login, String arq, int code, LocalDateTime creationDatetime) {
        try {
            String query = "insert into registers (id, code, login, arq, creation_datetime) values(?,?,?,?,?);";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setInt(1, this.getNextId());
            ps.setInt(2, code);
            ps.setString(3, login);
            ps.setString(4, arq);
            ps.setString(5, creationDatetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            ps.execute();
            System.out.println("Log registrado com sucesso");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao inserir a mensagem de log: \n" + e.getMessage());
        }
    }

    public int getNextId() throws SQLException {
        String query = "select id from registers order by id desc limit 1;";
        ResultSet res = this.conn.createStatement().executeQuery(query);
        if (res.next())
            return res.getInt(1) + 1;
        return 1;
    }

}
