package librarymanagement.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AnggotaModel - Model untuk entitas Anggota Perpustakaan
 */
public class AnggotaModel {
    
    // ============================================================
    // ATRIBUT (Encapsulation)
    // ============================================================
    private int idAnggota;
    private String nama;
    private String alamat;
    private String noHp;
    private String status;
    
    // ============================================================
    // KONSTRUKTOR
    // ============================================================
    public AnggotaModel() {}
    
    public AnggotaModel(int idAnggota, String nama, String alamat, String noHp, String status) {
        this.idAnggota = idAnggota;
        this.nama      = nama;
        this.alamat    = alamat;
        this.noHp      = noHp;
        this.status    = status;
    }
    
    // ============================================================
    // GETTER & SETTER
    // ============================================================
    public int getIdAnggota()            { return idAnggota; }
    public void setIdAnggota(int id)     { this.idAnggota = id; }
    
    public String getNama()              { return nama; }
    public void setNama(String nama)     { this.nama = nama; }
    
    public String getAlamat()                { return alamat; }
    public void setAlamat(String alamat)     { this.alamat = alamat; }
    
    public String getNoHp()              { return noHp; }
    public void setNoHp(String noHp)     { this.noHp = noHp; }
    
    public String getStatus()                { return status; }
    public void setStatus(String status)     { this.status = status; }
    
    // ============================================================
    // DATABASE OPERATIONS
    // ============================================================
    
    private Connection getConn() {
        return DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Mendapatkan semua anggota
     */
    public List<AnggotaModel> getAllAnggota() {
        List<AnggotaModel> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota ORDER BY nama";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getAllAnggota: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Mendapatkan anggota aktif saja
     */
    public List<AnggotaModel> getAnggotaAktif() {
        List<AnggotaModel> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota WHERE status = 'Aktif' ORDER BY nama";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getAnggotaAktif: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Mendapatkan anggota berdasarkan ID
     */
    public AnggotaModel getAnggotaById(int id) {
        String sql = "SELECT * FROM anggota WHERE id_anggota = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getAnggotaById: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Cari anggota
     */
    public List<AnggotaModel> searchAnggota(String keyword) {
        List<AnggotaModel> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota WHERE nama LIKE ? OR no_hp LIKE ? OR alamat LIKE ? ORDER BY nama";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searchAnggota: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Filter anggota berdasarkan status
     */
    public List<AnggotaModel> filterByStatus(String status) {
        List<AnggotaModel> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota WHERE status = ? ORDER BY nama";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error filterByStatus: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Tambah anggota baru
     */
    public boolean addAnggota(AnggotaModel anggota) {
        String sql = "INSERT INTO anggota (nama, alamat, no_hp, status) VALUES (?,?,?,?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, anggota.getNama());
            ps.setString(2, anggota.getAlamat());
            ps.setString(3, anggota.getNoHp());
            ps.setString(4, anggota.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error addAnggota: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update anggota
     */
    public boolean updateAnggota(AnggotaModel anggota) {
        String sql = "UPDATE anggota SET nama=?, alamat=?, no_hp=?, status=? WHERE id_anggota=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, anggota.getNama());
            ps.setString(2, anggota.getAlamat());
            ps.setString(3, anggota.getNoHp());
            ps.setString(4, anggota.getStatus());
            ps.setInt(5, anggota.getIdAnggota());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updateAnggota: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Hapus anggota
     */
    public boolean deleteAnggota(int id) {
        // Cek apakah anggota masih memiliki peminjaman aktif
        String checkSql = "SELECT COUNT(*) FROM peminjaman WHERE id_anggota = ? AND status = 'Dipinjam'";
        try (PreparedStatement ps = getConn().prepareStatement(checkSql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Masih ada peminjaman aktif
            }
        } catch (SQLException e) {
            return false;
        }
        
        String sql = "DELETE FROM anggota WHERE id_anggota = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleteAnggota: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mendapatkan jumlah peminjaman aktif anggota
     */
    public int getJumlahPinjamanAktif(int idAnggota) {
        String sql = "SELECT COUNT(*) FROM peminjaman WHERE id_anggota = ? AND status = 'Dipinjam'";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, idAnggota);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error getJumlahPinjamanAktif: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Cek apakah nama anggota sudah ada (untuk validasi duplikasi)
     * @param nama nama anggota yang akan dicek
     * @param excludeId ID anggota yang dikecualikan (untuk update), 0 untuk tambah baru
     * @return true jika nama sudah ada
     */
    public boolean isNamaExists(String nama, int excludeId) {
        String sql = "SELECT COUNT(*) FROM anggota WHERE LOWER(nama) = LOWER(?) AND id_anggota != ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, nama.trim());
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Error isNamaExists: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Helper: Map ResultSet ke AnggotaModel
     */
    private AnggotaModel mapResultSet(ResultSet rs) throws SQLException {
        return new AnggotaModel(
            rs.getInt("id_anggota"),
            rs.getString("nama"),
            rs.getString("alamat"),
            rs.getString("no_hp"),
            rs.getString("status")
        );
    }
    
    /**
     * Bersihkan duplikasi nama di database
     * Hanya menyimpan 1 record per nama (yang ID paling kecil)
     * @return jumlah record yang dihapus
     */
    public int cleanupDuplicateNama() {
        int deleted = 0;
        String sql = "DELETE FROM anggota WHERE id_anggota NOT IN (" +
                     "SELECT MIN(id_anggota) FROM anggota GROUP BY LOWER(nama)" +
                     ") AND id_anggota NOT IN (" +
                     "SELECT DISTINCT id_anggota FROM peminjaman WHERE status = 'Dipinjam'" +
                     ")";
        try (Statement stmt = getConn().createStatement()) {
            deleted = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error cleanupDuplicateNama: " + e.getMessage());
        }
        return deleted;
    }
    
    @Override
    public String toString() {
        return idAnggota + " - " + nama; // Digunakan untuk ComboBox display
    }
}
