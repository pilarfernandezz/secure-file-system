package repositories;

import models.LockedUser;
import models.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LockedUserRepository {
    private Connection conn;
    private static LockedUserRepository instance;

    public static LockedUserRepository getInstance() {
        if (instance == null)
            instance = new LockedUserRepository();
        return instance;
    }

    private LockedUserRepository() {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://root@localhost/secure_file_system");
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao conectar com o banco: \n" + e.getMessage());
        }
    }

    public void createLockedUser(User user) {
        try {
            String query = "Insert into locked_users (id, user_id , is_active) values (?,?,? );";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setInt(1, this.getNextId());
            ps.setInt(2, user.getId());
            ps.setInt(3, 1);

            ps.execute();
            //TODO LOG
            System.out.println("Usuário bloqueado com sucesso");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao bloquear o usuario: " + user.getEmail() + ". \n" + e.getMessage());
        }
    }

    public void updateLockedUser(String email, boolean isActive) {
        try {
            LockedUser lockedUser = this.getLockedUser(email);
            String query = "update locked_users set  is_active = ? where user_id = ?";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setInt(1, isActive ? 1 : 0);
            ps.setInt(2, lockedUser.getUserId());

            ps.execute();
            //TODO LOG
            System.out.println("Usuário desbloqueado com sucesso");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao desbloquear o usuario: \n" + e.getMessage());
        }
    }

    public LockedUser getLockedUser(String email) throws SQLException {
        try {
            String query = "select l.*, u.email " +
                    "from locked_users l " +
                    "left join users u on l.user_id = u.id " +
                    "where u.email = ? " +
                    "and l.is_active = 1 order by l.id desc limit 1;";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                LockedUser lockedUser = new LockedUser(res.getInt("id"), res.getInt("user_id"), LocalDateTime.parse(res.getString("lock_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), res.getInt("is_active") == 1 ? true : false);
                System.out.println("Usuário buscado com sucesso");
                return lockedUser;
            }
            //todo log
            System.out.println("Usuário nao encontrado");
            return null;
        } catch (SQLException e){
            System.out.println("Ocorreu um erro ao buscar o usuario: \n" + e.getMessage());
            return null;
        }
    }

    // Busca o próximo id válido para um usuário a ser cadastrado na tabela locked_users
    public int getNextId() throws SQLException {
        try{
            String query = "select id from locked_users order by id desc limit 1;";
            ResultSet res = this.conn.createStatement().executeQuery(query);
            if (res.next())
                return res.getInt(1) + 1;
            return 1;
        } catch (SQLException e){
            return 0;
        }
    }
}
