package librarymanagement.model;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * PeminjamanModel - Model untuk entitas Peminjaman dan Detail Peminjaman
 * Mengelola peminjaman, pengembalian, dan kalkulasi denda
 */
public class PeminjamanModel {
    
    // ============================================================
    // KONSTANTA
    // ============================================================
    public static final double DENDA_PER_HARI = 5000.0;    // Rp5.000 per hari keterlambatan
    public static final double DENDA_BUKU_HILANG = 100000.0; // Rp100.000 per buku hilang
    
    // ============================================================
    // ATRIBUT Peminjaman (Encapsulation)
    // ============================================================
    private int idPinjam;
    private int idAnggota;
    private String tanggalPinjam;
    private String tanggalKembali;
    private String tanggalDikembalikan;
    private String status;
    private double denda;
    
    // Field join (dari tabel anggota)
    private String namaAnggota;
    
    // ============================================================
    // KONSTRUKTOR
    // ============================================================
    public PeminjamanModel() {}
    
    public PeminjamanModel(int idPinjam, int idAnggota, String tanggalPinjam,
                           String tanggalKembali, String status, double denda) {
        this.idPinjam       = idPinjam;
        this.idAnggota      = idAnggota;
        this.tanggalPinjam  = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
        this.status         = status;
        this.denda          = denda;
    }
    
    // ============================================================
    // GETTER & SETTER
    // ============================================================
    public int getIdPinjam()               { return idPinjam; }
    public void setIdPinjam(int id)        { this.idPinjam = id; }
    
    public int getIdAnggota()              { return idAnggota; }
    public void setIdAnggota(int id)       { this.idAnggota = id; }
    
    public String getTanggalPinjam()                       { return tanggalPinjam; }
    public void setTanggalPinjam(String tgl)               { this.tanggalPinjam = tgl; }
    
    public String getTanggalKembali()                      { return tanggalKembali; }
    public void setTanggalKembali(String tgl)              { this.tanggalKembali = tgl; }
    
    public String getTanggalDikembalikan()                 { return tanggalDikembalikan; }
    public void setTanggalDikembalikan(String tgl)         { this.tanggalDikembalikan = tgl; }
    
    public String getStatus()              { return status; }
    public void setStatus(String status)   { this.status = status; }
    
    public double getDenda()               { return denda; }
    public void setDenda(double denda)     { this.denda = denda; }
    
    public String getNamaAnggota()                       { return namaAnggota; }
    public void setNamaAnggota(String nama)              { this.namaAnggota = nama; }
    
    // ============================================================
    // BUSINESS LOGIC
    // ============================================================
    
    /**
     * Hitung denda berdasarkan keterlambatan
     * @param tanggalKembali   - batas tanggal kembali (format: yyyy-MM-dd)
     * @param tanggalAktual    - tanggal aktual dikembalikan (format: yyyy-MM-dd)
     * @return denda dalam Rupiah
     */
    public static double hitungDenda(String tanggalKembali, String tanggalAktual) {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate batas   = LocalDate.parse(tanggalKembali, fmt);
            LocalDate aktual  = LocalDate.parse(tanggalAktual, fmt);
            
            long selisih = ChronoUnit.DAYS.between(batas, aktual);
            if (selisih > 0) {
                return selisih * DENDA_PER_HARI;
            }
        } catch (Exception e) {
            System.err.println("Error hitungDenda: " + e.getMessage());
        }
        return 0.0;
    }
    
    /**
     * Mendapatkan tanggal hari ini dalam format yyyy-MM-dd
     */
    public static String getTanggalHariIni() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    /**
     * Mendapatkan tanggal kembali default (7 hari dari sekarang)
     */
    public static String getTanggalKembaliDefault() {
        return LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    // ============================================================
    // DATABASE OPERATIONS
    // ============================================================
    
    private Connection getConn() {
        return DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Mendapatkan semua peminjaman (dengan join anggota)
     */
    public List<PeminjamanModel> getAllPeminjaman() {
        List<PeminjamanModel> list = new ArrayList<>();
        String sql = "SELECT p.*, a.nama as nama_anggota FROM peminjaman p " +
                     "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                     "ORDER BY p.tanggal_pinjam DESC";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getAllPeminjaman: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Mendapatkan peminjaman yang masih aktif
     */
    public List<PeminjamanModel> getPeminjamanAktif() {
        List<PeminjamanModel> list = new ArrayList<>();
        String sql = "SELECT p.*, a.nama as nama_anggota FROM peminjaman p " +
                     "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                     "WHERE p.status = 'Dipinjam' OR p.status = 'Terlambat' " +
                     "ORDER BY p.tanggal_kembali ASC";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getPeminjamanAktif: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Mendapatkan peminjaman berdasarkan ID
     */
    public PeminjamanModel getPeminjamanById(int id) {
        String sql = "SELECT p.*, a.nama as nama_anggota FROM peminjaman p " +
                     "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                     "WHERE p.id_pinjam = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getPeminjamanById: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Cari peminjaman berdasarkan nama anggota
     */
    public List<PeminjamanModel> searchPeminjaman(String keyword) {
        List<PeminjamanModel> list = new ArrayList<>();
        String sql = "SELECT p.*, a.nama as nama_anggota FROM peminjaman p " +
                     "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                     "WHERE a.nama LIKE ? " +
                     "ORDER BY p.tanggal_pinjam DESC";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searchPeminjaman: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Tambah peminjaman baru (dengan transaksi untuk update stok)
     * @param peminjaman   - data peminjaman
     * @param idBuku       - ID buku yang dipinjam
     * @param jumlah       - jumlah buku yang dipinjam
     * @return true jika berhasil
     */
    public boolean addPeminjaman(PeminjamanModel peminjaman, int idBuku, int jumlah) {
        Connection conn = getConn();
        try {
            conn.setAutoCommit(false); // Mulai transaksi
            
            // Cek stok buku
            BukuModel bukuModel = new BukuModel();
            int stokSaat = bukuModel.getStokBuku(idBuku);
            if (stokSaat < jumlah) {
                conn.setAutoCommit(true);
                return false; // Stok tidak cukup
            }
            
            // Insert peminjaman
            String sqlPinjam = "INSERT INTO peminjaman (id_anggota, tanggal_pinjam, tanggal_kembali, status, denda) VALUES (?,?,?,?,?)";
            int idPinjamBaru;
            try (PreparedStatement ps = conn.prepareStatement(sqlPinjam, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, peminjaman.getIdAnggota());
                ps.setString(2, peminjaman.getTanggalPinjam());
                ps.setString(3, peminjaman.getTanggalKembali());
                ps.setString(4, "Dipinjam");
                ps.setDouble(5, 0);
                ps.executeUpdate();
                ResultSet genKeys = ps.getGeneratedKeys();
                if (genKeys.next()) {
                    idPinjamBaru = genKeys.getInt(1);
                } else {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    return false;
                }
            }
            
            // Insert detail peminjaman
            String sqlDetail = "INSERT INTO detail_peminjaman (id_pinjam, id_buku, jumlah) VALUES (?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                ps.setInt(1, idPinjamBaru);
                ps.setInt(2, idBuku);
                ps.setInt(3, jumlah);
                ps.executeUpdate();
            }
            
            // Kurangi stok buku (dengan proteksi stok tidak minus)
            String sqlStok = "UPDATE buku SET stok = stok - ? WHERE id_buku = ? AND stok >= ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlStok)) {
                ps.setInt(1, jumlah);
                ps.setInt(2, idBuku);
                ps.setInt(3, jumlah);
                int affected = ps.executeUpdate();
                if (affected == 0) {
                    // Stok tidak cukup (race condition protection)
                    conn.rollback();
                    conn.setAutoCommit(true);
                    return false;
                }
            }
            
            conn.commit(); // Commit transaksi
            conn.setAutoCommit(true);
            return true;
            
        } catch (SQLException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Error rollback: " + ex.getMessage());
            }
            System.err.println("Error addPeminjaman: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Proses pengembalian buku
     * @param idPinjam           - ID peminjaman
     * @param tanggalDikembalikan - tanggal aktual pengembalian
     * @return denda yang harus dibayar (0 jika tepat waktu)
     */
    public double prosesPengembalian(int idPinjam, String tanggalDikembalikan) {
        Connection conn = getConn();
        try {
            // Ambil data peminjaman
            PeminjamanModel pinjam = getPeminjamanById(idPinjam);
            if (pinjam == null) return -1;
            
            // Hitung denda
            double denda = hitungDenda(pinjam.getTanggalKembali(), tanggalDikembalikan);
            // Status "Terlambat" jika ada denda, "Kembali" jika tepat waktu
            String statusBaru = denda > 0 ? "Terlambat" : "Kembali";
            
            conn.setAutoCommit(false);
            
            // Update status peminjaman
            String sqlUpdate = "UPDATE peminjaman SET status=?, denda=?, tanggal_dikembalikan=? WHERE id_pinjam=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setString(1, statusBaru); // Fix: gunakan statusBaru, bukan hardcode "Kembali"
                ps.setDouble(2, denda);
                ps.setString(3, tanggalDikembalikan);
                ps.setInt(4, idPinjam);
                ps.executeUpdate();
            }
            
            // Kembalikan stok buku
            String sqlDetail = "SELECT id_buku, jumlah FROM detail_peminjaman WHERE id_pinjam = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                ps.setInt(1, idPinjam);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int idBuku = rs.getInt("id_buku");
                    int jumlah = rs.getInt("jumlah");
                    String sqlStok = "UPDATE buku SET stok = stok + ? WHERE id_buku = ?";
                    try (PreparedStatement psStok = conn.prepareStatement(sqlStok)) {
                        psStok.setInt(1, jumlah);
                        psStok.setInt(2, idBuku);
                        psStok.executeUpdate();
                    }
                }
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            return denda;
            
        } catch (SQLException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Error rollback pengembalian: " + ex.getMessage());
            }
            System.err.println("Error prosesPengembalian: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * Mendapatkan detail buku dari sebuah peminjaman
     */
    public List<Object[]> getDetailPeminjaman(int idPinjam) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT dp.*, b.judul, b.penulis FROM detail_peminjaman dp " +
                     "JOIN buku b ON dp.id_buku = b.id_buku " +
                     "WHERE dp.id_pinjam = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, idPinjam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("id_detail"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getInt("jumlah")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error getDetailPeminjaman: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Mendapatkan laporan semua peminjaman untuk tabel laporan
     */
    public List<Object[]> getLaporanPeminjaman() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT p.id_pinjam, a.nama, b.judul, dp.jumlah, " +
                     "p.tanggal_pinjam, p.tanggal_kembali, p.tanggal_dikembalikan, " +
                     "p.status, p.denda " +
                     "FROM peminjaman p " +
                     "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                     "JOIN detail_peminjaman dp ON p.id_pinjam = dp.id_pinjam " +
                     "JOIN buku b ON dp.id_buku = b.id_buku " +
                     "ORDER BY p.tanggal_pinjam DESC";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("id_pinjam"),
                    rs.getString("nama"),
                    rs.getString("judul"),
                    rs.getInt("jumlah"),
                    rs.getString("tanggal_pinjam"),
                    rs.getString("tanggal_kembali"),
                    rs.getString("tanggal_dikembalikan"),
                    rs.getString("status"),
                    rs.getDouble("denda")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error getLaporanPeminjaman: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Mendapatkan statistik ringkasan
     */
    public int[] getStatistik() {
        int[] stats = new int[4]; // [total, dipinjam, terlambat, kembali]
        String sql = "SELECT " +
                     "COUNT(*) as total, " +
                     "SUM(CASE WHEN status='Dipinjam' THEN 1 ELSE 0 END) as dipinjam, " +
                     "SUM(CASE WHEN status='Terlambat' THEN 1 ELSE 0 END) as terlambat, " +
                     "SUM(CASE WHEN status='Kembali' THEN 1 ELSE 0 END) as kembali " +
                     "FROM peminjaman";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            if (rs.next()) {
                stats[0] = rs.getInt("total");
                stats[1] = rs.getInt("dipinjam");
                stats[2] = rs.getInt("terlambat");
                stats[3] = rs.getInt("kembali");
            }
        } catch (SQLException e) {
            System.err.println("Error getStatistik: " + e.getMessage());
        }
        return stats;
    }
    
    /**
     * Helper: Map ResultSet ke PeminjamanModel
     */
    private PeminjamanModel mapResultSet(ResultSet rs) throws SQLException {
        PeminjamanModel p = new PeminjamanModel();
        p.setIdPinjam(rs.getInt("id_pinjam"));
        p.setIdAnggota(rs.getInt("id_anggota"));
        p.setTanggalPinjam(rs.getString("tanggal_pinjam"));
        p.setTanggalKembali(rs.getString("tanggal_kembali"));
        p.setTanggalDikembalikan(rs.getString("tanggal_dikembalikan"));
        p.setStatus(rs.getString("status"));
        p.setDenda(rs.getDouble("denda"));
        p.setNamaAnggota(rs.getString("nama_anggota"));
        return p;
    }
    
    /**
     * Proses lapor buku hilang
     * Denda = Rp100.000 per buku yang hilang
     * Stok TIDAK dikembalikan (buku hilang)
     */
    public double prosesLaporHilang(int idPinjam) {
        Connection conn = getConn();
        try {
            PeminjamanModel pinjam = getPeminjamanById(idPinjam);
            if (pinjam == null) return -1;
            
            // Hitung total buku yang dipinjam
            List<Object[]> details = getDetailPeminjaman(idPinjam);
            int totalBuku = 0;
            for (Object[] d : details) {
                totalBuku += (int) d[3]; // jumlah
            }
            
            double dendaHilang = totalBuku * DENDA_BUKU_HILANG;
            String tanggalHariIni = getTanggalHariIni();
            
            // Update status peminjaman menjadi "Hilang"
            String sql = "UPDATE peminjaman SET status='Hilang', denda=?, tanggal_dikembalikan=? WHERE id_pinjam=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDouble(1, dendaHilang);
                ps.setString(2, tanggalHariIni);
                ps.setInt(3, idPinjam);
                ps.executeUpdate();
            }
            
            // Stok TIDAK dikembalikan karena buku hilang
            return dendaHilang;
            
        } catch (SQLException e) {
            System.err.println("Error prosesLaporHilang: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * Cek apakah anggota memiliki peminjaman aktif (belum dikembalikan)
     */
    public boolean hasPeminjamanAktif(int idAnggota) {
        String sql = "SELECT COUNT(*) FROM peminjaman WHERE id_anggota = ? AND status = 'Dipinjam'";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, idAnggota);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Error hasPeminjamanAktif: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Mendapatkan statistik per bulan (untuk grafik)
     * Mengembalikan data 6 bulan terakhir
     * Format: List of {bulan, dipinjam, kembali, terlambat, hilang}
     */
    public List<Object[]> getStatistikBulanan() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT strftime('%Y-%m', tanggal_pinjam) as bulan, " +
                     "COUNT(*) as total, " +
                     "SUM(CASE WHEN status='Dipinjam' THEN 1 ELSE 0 END) as dipinjam, " +
                     "SUM(CASE WHEN status='Kembali' THEN 1 ELSE 0 END) as kembali, " +
                     "SUM(CASE WHEN status='Terlambat' THEN 1 ELSE 0 END) as terlambat, " +
                     "SUM(CASE WHEN status='Hilang' THEN 1 ELSE 0 END) as hilang " +
                     "FROM peminjaman " +
                     "GROUP BY strftime('%Y-%m', tanggal_pinjam) " +
                     "ORDER BY bulan DESC LIMIT 6";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("bulan"),
                    rs.getInt("total"),
                    rs.getInt("dipinjam"),
                    rs.getInt("kembali"),
                    rs.getInt("terlambat"),
                    rs.getInt("hilang")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error getStatistikBulanan: " + e.getMessage());
        }
        // Reverse agar urutan dari lama ke baru
        java.util.Collections.reverse(list);
        return list;
    }
}
