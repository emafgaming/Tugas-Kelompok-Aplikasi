package librarymanagement.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserModel - Model untuk entitas User
 * Menggunakan konsep OOP: Encapsulation dengan getter/setter
 */
public class UserModel {
    
    // ============================================================
    // ATRIBUT (Encapsulation - private)
    // ============================================================
    private int idUser;
    private String username;
    private String password;
    private String role;
    
    // ============================================================
    // KONSTRUKTOR
    // ============================================================
    public UserModel() {}
    
    public UserModel(int idUser, String username, String password, String role) {
        this.idUser   = idUser;
        this.username = username;
        this.password = password;
        this.role     = role;
    }
    
    // ============================================================
    // GETTER & SETTER
    // ============================================================
    public int getIdUser()           { return idUser; }
    public void setIdUser(int id)    { this.idUser = id; }
    
    public String getUsername()              { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword()              { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole()          { return role; }
    public void setRole(String role) { this.role = role; }
    
    // ============================================================
    // DATABASE OPERATIONS (DAO - Data Access Object)
    // ============================================================
    
    private Connection getConn() {
        return DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Autentikasi user - login
     * @return UserModel jika berhasil, null jika gagal
     */
    public UserModel login(String username, String password) {
        System.out.println(">>> Login attempt: username=" + username);
        
        Connection conn = getConn();
        if (conn == null) {
            System.err.println(">>> ERROR: Koneksi database NULL saat login!");
            System.err.println(">>> Pastikan sqlite-jdbc.jar sudah ada di Dependencies project!");
            return null;
        }
        
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserModel user = new UserModel(
                    rs.getInt("id_user"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                System.out.println(">>> Login berhasil! Role: " + user.getRole());
                return user;
            } else {
                System.out.println(">>> Login gagal: username/password tidak cocok.");
            }
        } catch (SQLException e) {
            System.err.println(">>> SQLException saat login: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(">>> Exception tidak terduga saat login: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Mendapatkan semua user
     */
    public List<UserModel> getAllUsers() {
        List<UserModel> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id_user";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new UserModel(
                    rs.getInt("id_user"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getAllUsers: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Tambah user baru
     */
    public boolean addUser(UserModel user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error addUser: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update user
     */
    public boolean updateUser(UserModel user) {
        String sql = "UPDATE users SET username=?, password=?, role=? WHERE id_user=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getIdUser());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updateUser: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Hapus user
     */
    public boolean deleteUser(int idUser) {
        String sql = "DELETE FROM users WHERE id_user = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, idUser);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleteUser: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Cek apakah username sudah ada
     */
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "UserModel{id=" + idUser + ", username='" + username + "', role='" + role + "'}";
    }
}
