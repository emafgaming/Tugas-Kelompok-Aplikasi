package librarymanagement.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BukuModel - Model untuk entitas Buku
 * Relasi ke tabel kategori
 */
public class BukuModel {
    
    // ============================================================
    // ATRIBUT (Encapsulation)
    // ============================================================
    private int idBuku;
    private String judul;
    private String penulis;
    private String penerbit;
    private int tahun;
    private int stok;
    private int idKategori;
    private String namaKategori; // Field join dari tabel kategori
    
    // ============================================================
    // KONSTRUKTOR
    // ============================================================
    public BukuModel() {}
    
    public BukuModel(int idBuku, String judul, String penulis, String penerbit,
                     int tahun, int stok, int idKategori) {
        this.idBuku      = idBuku;
        this.judul       = judul;
        this.penulis     = penulis;
        this.penerbit    = penerbit;
        this.tahun       = tahun;
        this.stok        = stok;
        this.idKategori  = idKategori;
    }
    
    // ============================================================
    // GETTER & SETTER
    // ============================================================
    public int getIdBuku()           { return idBuku; }
    public void setIdBuku(int id)    { this.idBuku = id; }
    
    public String getJudul()               { return judul; }
    public void setJudul(String judul)     { this.judul = judul; }
    
    public String getPenulis()               { return penulis; }
    public void setPenulis(String penulis)   { this.penulis = penulis; }
    
    public String getPenerbit()                { return penerbit; }
    public void setPenerbit(String penerbit)   { this.penerbit = penerbit; }
    
    public int getTahun()            { return tahun; }
    public void setTahun(int tahun)  { this.tahun = tahun; }
    
    public int getStok()           { return stok; }
    public void setStok(int stok)  { this.stok = stok; }
    
    public int getIdKategori()               { return idKategori; }
    public void setIdKategori(int id)        { this.idKategori = id; }
    
    public String getNamaKategori()                      { return namaKategori; }
    public void setNamaKategori(String namaKategori)     { this.namaKategori = namaKategori; }
    
    // ============================================================
    // DATABASE OPERATIONS
    // ============================================================
    
    private Connection getConn() {
        return DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Mendapatkan semua buku (dengan join kategori)
     */
    public List<BukuModel> getAllBuku() {
        List<BukuModel> list = new ArrayList<>();
        String sql = "SELECT b.*, k.nama_kategori FROM buku b " +
                     "LEFT JOIN kategori k ON b.id_kategori = k.id_kategori " +
                     "ORDER BY b.judul";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BukuModel buku = mapResultSet(rs);
                list.add(buku);
            }
        } catch (SQLException e) {
            System.err.println("Error getAllBuku: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Mendapatkan buku berdasarkan ID
     */
    public BukuModel getBukuById(int id) {
        String sql = "SELECT b.*, k.nama_kategori FROM buku b " +
                     "LEFT JOIN kategori k ON b.id_kategori = k.id_kategori " +
                     "WHERE b.id_buku = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getBukuById: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Cari buku berdasarkan keyword (judul atau penulis)
     */
    public List<BukuModel> searchBuku(String keyword) {
        List<BukuModel> list = new ArrayList<>();
        String sql = "SELECT b.*, k.nama_kategori FROM buku b " +
                     "LEFT JOIN kategori k ON b.id_kategori = k.id_kategori " +
                     "WHERE b.judul LIKE ? OR b.penulis LIKE ? OR b.penerbit LIKE ? " +
                     "ORDER BY b.judul";
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
            System.err.println("Error searchBuku: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Filter buku berdasarkan kategori
     */
    public List<BukuModel> filterByKategori(int idKategori) {
        List<BukuModel> list = new ArrayList<>();
        String sql = "SELECT b.*, k.nama_kategori FROM buku b " +
                     "LEFT JOIN kategori k ON b.id_kategori = k.id_kategori " +
                     "WHERE b.id_kategori = ? ORDER BY b.judul";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, idKategori);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error filterByKategori: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Tambah buku baru
     */
    public boolean addBuku(BukuModel buku) {
        String sql = "INSERT INTO buku (judul, penulis, penerbit, tahun, stok, id_kategori) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setString(3, buku.getPenerbit());
            ps.setInt(4, buku.getTahun());
            ps.setInt(5, buku.getStok());
            ps.setInt(6, buku.getIdKategori());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error addBuku: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update buku
     */
    public boolean updateBuku(BukuModel buku) {
        String sql = "UPDATE buku SET judul=?, penulis=?, penerbit=?, tahun=?, stok=?, id_kategori=? WHERE id_buku=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setString(3, buku.getPenerbit());
            ps.setInt(4, buku.getTahun());
            ps.setInt(5, buku.getStok());
            ps.setInt(6, buku.getIdKategori());
            ps.setInt(7, buku.getIdBuku());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updateBuku: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Hapus buku
     */
    public boolean deleteBuku(int id) {
        // Cek apakah buku sedang dipinjam
        String checkSql = "SELECT COUNT(*) FROM detail_peminjaman dp " +
                          "JOIN peminjaman p ON dp.id_pinjam = p.id_pinjam " +
                          "WHERE dp.id_buku = ? AND p.status = 'Dipinjam'";
        try (PreparedStatement ps = getConn().prepareStatement(checkSql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Sedang dipinjam, tidak bisa dihapus
            }
        } catch (SQLException e) {
            System.err.println("Error check buku: " + e.getMessage());
            return false;
        }
        
        String sql = "DELETE FROM buku WHERE id_buku = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleteBuku: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update stok buku (kurangi saat peminjaman)
     */
    public boolean updateStok(int idBuku, int jumlah) {
        String sql = "UPDATE buku SET stok = stok + ? WHERE id_buku = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, jumlah);
            ps.setInt(2, idBuku);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updateStok: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mendapatkan stok buku saat ini
     */
    public int getStokBuku(int idBuku) {
        String sql = "SELECT stok FROM buku WHERE id_buku = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, idBuku);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("stok");
            }
        } catch (SQLException e) {
            System.err.println("Error getStokBuku: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Buku paling sering dipinjam (untuk laporan)
     */
    public List<Object[]> getBukuTerpopuler() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT b.judul, b.penulis, COUNT(dp.id_detail) as total_pinjam " +
                     "FROM detail_peminjaman dp " +
                     "JOIN buku b ON dp.id_buku = b.id_buku " +
                     "GROUP BY b.id_buku " +
                     "ORDER BY total_pinjam DESC " +
                     "LIMIT 10";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getInt("total_pinjam")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error getBukuTerpopuler: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Cek apakah judul buku sudah ada (untuk validasi duplikasi)
     * @param judul judul buku yang akan dicek
     * @param excludeId ID buku yang dikecualikan (untuk update), 0 untuk tambah baru
     * @return true jika judul sudah ada
     */
    public boolean isJudulExists(String judul, int excludeId) {
        String sql = "SELECT COUNT(*) FROM buku WHERE LOWER(judul) = LOWER(?) AND id_buku != ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, judul.trim());
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Error isJudulExists: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Helper: Map ResultSet ke BukuModel
     */
    private BukuModel mapResultSet(ResultSet rs) throws SQLException {
        BukuModel buku = new BukuModel();
        buku.setIdBuku(rs.getInt("id_buku"));
        buku.setJudul(rs.getString("judul"));
        buku.setPenulis(rs.getString("penulis"));
        buku.setPenerbit(rs.getString("penerbit"));
        buku.setTahun(rs.getInt("tahun"));
        buku.setStok(rs.getInt("stok"));
        buku.setIdKategori(rs.getInt("id_kategori"));
        buku.setNamaKategori(rs.getString("nama_kategori"));
        return buku;
    }
    
    @Override
    public String toString() {
        return judul + " - " + penulis;
    }
}
