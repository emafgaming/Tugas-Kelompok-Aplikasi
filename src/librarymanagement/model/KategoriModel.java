package librarymanagement.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * KategoriModel - Model untuk entitas Kategori Buku
 */
public class KategoriModel {
    
    // ============================================================
    // ATRIBUT (Encapsulation)
    // ============================================================
    private int idKategori;
    private String namaKategori;
    
    // ============================================================
    // KONSTRUKTOR
    // ============================================================
    public KategoriModel() {}
    
    public KategoriModel(int idKategori, String namaKategori) {
        this.idKategori   = idKategori;
        this.namaKategori = namaKategori;
    }
    
    // ============================================================
    // GETTER & SETTER
    // ============================================================
    public int getIdKategori()               { return idKategori; }
    public void setIdKategori(int id)        { this.idKategori = id; }
    
    public String getNamaKategori()                    { return namaKategori; }
    public void setNamaKategori(String namaKategori)   { this.namaKategori = namaKategori; }
    
    // ============================================================
    // DATABASE OPERATIONS
    // ============================================================
    
    private Connection getConn() {
        return DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Mendapatkan semua kategori
     */
    public List<KategoriModel> getAllKategori() {
        List<KategoriModel> list = new ArrayList<>();
        String sql = "SELECT * FROM kategori ORDER BY nama_kategori";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new KategoriModel(
                    rs.getInt("id_kategori"),
                    rs.getString("nama_kategori")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getAllKategori: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Mendapatkan kategori berdasarkan ID
     */
    public KategoriModel getKategoriById(int id) {
        String sql = "SELECT * FROM kategori WHERE id_kategori = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new KategoriModel(rs.getInt("id_kategori"), rs.getString("nama_kategori"));
            }
        } catch (SQLException e) {
            System.err.println("Error getKategoriById: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Tambah kategori baru
     */
    public boolean addKategori(KategoriModel kategori) {
        String sql = "INSERT INTO kategori (nama_kategori) VALUES (?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, kategori.getNamaKategori());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error addKategori: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update kategori
     */
    public boolean updateKategori(KategoriModel kategori) {
        String sql = "UPDATE kategori SET nama_kategori = ? WHERE id_kategori = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, kategori.getNamaKategori());
            ps.setInt(2, kategori.getIdKategori());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updateKategori: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Hapus kategori
     */
    public boolean deleteKategori(int id) {
        // Cek apakah kategori masih digunakan
        String checkSql = "SELECT COUNT(*) FROM buku WHERE id_kategori = ?";
        try (PreparedStatement ps = getConn().prepareStatement(checkSql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Masih digunakan, tidak bisa dihapus
            }
        } catch (SQLException e) {
            System.err.println("Error check kategori: " + e.getMessage());
            return false;
        }
        
        String sql = "DELETE FROM kategori WHERE id_kategori = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleteKategori: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Cari kategori berdasarkan nama
     */
    public List<KategoriModel> searchKategori(String keyword) {
        List<KategoriModel> list = new ArrayList<>();
        String sql = "SELECT * FROM kategori WHERE nama_kategori LIKE ? ORDER BY nama_kategori";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new KategoriModel(rs.getInt("id_kategori"), rs.getString("nama_kategori")));
            }
        } catch (SQLException e) {
            System.err.println("Error searchKategori: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Cek apakah nama kategori sudah ada
     */
    public boolean isNamaKategoriExists(String nama) {
        String sql = "SELECT COUNT(*) FROM kategori WHERE LOWER(nama_kategori) = LOWER(?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, nama);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return namaKategori; // Digunakan untuk ComboBox display
    }
}
