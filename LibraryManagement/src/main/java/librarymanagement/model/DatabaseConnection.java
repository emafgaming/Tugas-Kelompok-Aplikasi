package librarymanagement.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

/**
 * DatabaseConnection - Singleton class untuk koneksi SQLite
 */
public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    // Simpan database di folder HOME user agar selalu bisa diakses
    private static final String DB_FOLDER;
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    private static final boolean USE_SUPABASE;

    static {
        String supabaseUrl = getConfig("SUPABASE_DB_URL");
        USE_SUPABASE = supabaseUrl != null && !supabaseUrl.trim().isEmpty();
        DB_USER = getConfig("SUPABASE_DB_USER");
        DB_PASSWORD = getConfig("SUPABASE_DB_PASSWORD");

        String home = System.getProperty("user.home");
        DB_FOLDER = home + File.separator + "perpustakaan_db";
        DB_URL = USE_SUPABASE ? supabaseUrl : "jdbc:sqlite:" + DB_FOLDER + File.separator + "perpustakaan.db";
        System.out.println(">>> Database path: " + DB_URL);
    }

    private DatabaseConnection() {
        initDatabase();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = openConnection();
            }
        } catch (SQLException e) {
            System.err.println("Error mendapatkan koneksi: " + e.getMessage());
        }
        return connection;
    }

    private void initDatabase() {
        if (USE_SUPABASE) {
            initSupabaseDatabase();
            return;
        }

        File dbFolder = new File(DB_FOLDER);
        if (!dbFolder.exists()) {
            boolean created = dbFolder.mkdirs();
            System.out.println(">>> Membuat folder database: " + created);
        }

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            System.out.println(">>> Koneksi SQLite berhasil!");
            createTables();
            insertDefaultData();
            System.out.println(">>> Database berhasil diinisialisasi.");
        } catch (ClassNotFoundException e) {
            System.err.println(">>> ERROR: Driver SQLite tidak ditemukan!");
            System.err.println(">>> Pastikan sqlite-jdbc.jar sudah ada di Dependencies!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(">>> ERROR inisialisasi database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String getConfig(String key) {
        String value = System.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(key);
        }
        return value;
    }

    private Connection openConnection() throws SQLException {
        if (USE_SUPABASE && DB_USER != null && !DB_USER.trim().isEmpty()) {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return DriverManager.getConnection(DB_URL);
    }

    private void initSupabaseDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = openConnection();
            validateSupabaseSchema();
            System.out.println(">>> Koneksi Supabase PostgreSQL berhasil!");
        } catch (ClassNotFoundException e) {
            System.err.println(">>> ERROR: Driver PostgreSQL tidak ditemukan!");
            System.err.println(">>> Jalankan Maven install agar dependency org.postgresql:postgresql terpasang.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(">>> ERROR koneksi Supabase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void validateSupabaseSchema() {
        String sql = "SELECT to_regclass('public.users') IS NOT NULL AS schema_ready";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next() && !rs.getBoolean("schema_ready")) {
                System.err.println(">>> Supabase terhubung, tapi tabel belum ditemukan.");
                System.err.println(">>> Jalankan database/supabase_schema.sql di Supabase SQL Editor.");
            }
        } catch (SQLException e) {
            System.err.println("Warning: gagal memeriksa schema Supabase: " + e.getMessage());
        }
    }

    private void createTables() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("PRAGMA foreign_keys = ON");

        stmt.execute(
            "CREATE TABLE IF NOT EXISTS users (" +
            "id_user INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT NOT NULL UNIQUE," +
            "password TEXT NOT NULL," +
            "role TEXT NOT NULL CHECK(role IN ('Admin','Petugas')))"
        );
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS kategori (" +
            "id_kategori INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nama_kategori TEXT NOT NULL UNIQUE)"
        );
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS buku (" +
            "id_buku INTEGER PRIMARY KEY AUTOINCREMENT," +
            "judul TEXT NOT NULL," +
            "penulis TEXT NOT NULL," +
            "penerbit TEXT NOT NULL," +
            "tahun INTEGER NOT NULL," +
            "stok INTEGER NOT NULL DEFAULT 0 CHECK(stok >= 0)," +
            "id_kategori INTEGER," +
            "FOREIGN KEY (id_kategori) REFERENCES kategori(id_kategori))"
        );
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS anggota (" +
            "id_anggota INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nama TEXT NOT NULL," +
            "alamat TEXT," +
            "no_hp TEXT," +
            "status TEXT NOT NULL DEFAULT 'Aktif' CHECK(status IN ('Aktif','Tidak Aktif')))"
        );
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS peminjaman (" +
            "id_pinjam INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_anggota INTEGER NOT NULL," +
            "tanggal_pinjam TEXT NOT NULL," +
            "tanggal_kembali TEXT NOT NULL," +
            "tanggal_dikembalikan TEXT," +
            "status TEXT NOT NULL DEFAULT 'Dipinjam' CHECK(status IN ('Dipinjam','Kembali','Terlambat','Hilang'))," +
            "denda REAL DEFAULT 0," +
            "FOREIGN KEY (id_anggota) REFERENCES anggota(id_anggota))"
        );
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS detail_peminjaman (" +
            "id_detail INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_pinjam INTEGER NOT NULL," +
            "id_buku INTEGER NOT NULL," +
            "jumlah INTEGER NOT NULL DEFAULT 1," +
            "FOREIGN KEY (id_pinjam) REFERENCES peminjaman(id_pinjam)," +
            "FOREIGN KEY (id_buku) REFERENCES buku(id_buku))"
        );
        stmt.close();

        // Migrasi ringan untuk database lama yang belum memiliki kolom foto PDF anggota
        addColumnIfMissing("anggota", "foto_pdf_path", "TEXT");
        
        // Bersihkan duplikasi nama anggota yang sudah ada
        cleanupDuplicateAnggota();
        
        // Bersihkan duplikasi judul buku yang sudah ada
        cleanupDuplicateBuku();
        
        // Tambahkan UNIQUE index pada nama anggota (jika belum ada)
        try (Statement stIdx = connection.createStatement()) {
            stIdx.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_anggota_nama ON anggota(LOWER(nama))");
        } catch (SQLException e) {
            System.out.println("Note: Index anggota nama: " + e.getMessage());
        }
        
        // Tambahkan UNIQUE index pada judul buku (jika belum ada)
        try (Statement stIdx = connection.createStatement()) {
            stIdx.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_buku_judul ON buku(LOWER(judul))");
        } catch (SQLException e) {
            System.out.println("Note: Index buku judul: " + e.getMessage());
        }
        
        System.out.println(">>> Tabel berhasil dibuat/diverifikasi.");
    }

    private void addColumnIfMissing(String tableName, String columnName, String columnDefinition) {
        if (columnExists(tableName, columnName)) {
            return;
        }

        String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + columnDefinition;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println(">>> Kolom baru ditambahkan: " + tableName + "." + columnName);
        } catch (SQLException e) {
            System.err.println("Warning: gagal menambah kolom " + tableName + "." + columnName + ": " + e.getMessage());
        }
    }

    private boolean columnExists(String tableName, String columnName) {
        String sql = "PRAGMA table_info(" + tableName + ")";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                if (columnName.equalsIgnoreCase(rs.getString("name"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Warning: gagal memeriksa kolom " + tableName + "." + columnName + ": " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Bersihkan data duplikat anggota (satu nama hanya boleh satu)
     */
    private void cleanupDuplicateAnggota() {
        try (Statement stmt = connection.createStatement()) {
            int deleted = stmt.executeUpdate(
                "DELETE FROM anggota WHERE id_anggota NOT IN (" +
                "SELECT MIN(id_anggota) FROM anggota GROUP BY LOWER(nama)" +
                ") AND id_anggota NOT IN (" +
                "SELECT DISTINCT id_anggota FROM peminjaman WHERE status = 'Dipinjam'" +
                ")"
            );
            if (deleted > 0) {
                System.out.println(">>> Dibersihkan " + deleted + " anggota duplikat.");
            }
        } catch (SQLException e) {
            System.err.println("Warning: cleanup duplikat anggota: " + e.getMessage());
        }
    }
    
    /**
     * Bersihkan data duplikat buku (satu judul hanya boleh satu)
     * Untuk duplikat, simpan yang stok paling banyak (jumlahkan stok duplikat)
     */
    private void cleanupDuplicateBuku() {
        try {
            // 1. Cari judul yang duplikat
            Statement stCheck = connection.createStatement();
            ResultSet rs = stCheck.executeQuery(
                "SELECT LOWER(judul) as judul_lower, COUNT(*) as cnt, " +
                "MIN(id_buku) as keep_id, SUM(stok) as total_stok " +
                "FROM buku GROUP BY LOWER(judul) HAVING cnt > 1"
            );
            
            int totalDeleted = 0;
            while (rs.next()) {
                String judulLower = rs.getString("judul_lower");
                int keepId = rs.getInt("keep_id");
                int totalStok = rs.getInt("total_stok");
                
                // 2. Update stok pada buku yang dipertahankan (gabungkan stok)
                try (PreparedStatement psUpdate = connection.prepareStatement(
                        "UPDATE buku SET stok = ? WHERE id_buku = ?")) {
                    psUpdate.setInt(1, totalStok);
                    psUpdate.setInt(2, keepId);
                    psUpdate.executeUpdate();
                }
                
                // 3. Pindahkan detail_peminjaman dari duplikat ke buku yang dipertahankan
                try (PreparedStatement psMove = connection.prepareStatement(
                        "UPDATE detail_peminjaman SET id_buku = ? WHERE id_buku IN (" +
                        "SELECT id_buku FROM buku WHERE LOWER(judul) = ? AND id_buku != ?)")) {
                    psMove.setInt(1, keepId);
                    psMove.setString(2, judulLower);
                    psMove.setInt(3, keepId);
                    psMove.executeUpdate();
                }
                
                // 4. Hapus buku duplikat
                try (PreparedStatement psDelete = connection.prepareStatement(
                        "DELETE FROM buku WHERE LOWER(judul) = ? AND id_buku != ?")) {
                    psDelete.setString(1, judulLower);
                    psDelete.setInt(2, keepId);
                    totalDeleted += psDelete.executeUpdate();
                }
            }
            rs.close();
            stCheck.close();
            
            if (totalDeleted > 0) {
                System.out.println(">>> Dibersihkan " + totalDeleted + " buku duplikat (stok digabungkan).");
            }
        } catch (SQLException e) {
            System.err.println("Warning: cleanup duplikat buku: " + e.getMessage());
        }
    }

    private void insertDefaultData() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(
            "INSERT OR IGNORE INTO users (username, password, role) VALUES " +
            "('admin', 'admin123', 'Admin')," +
            "('petugas', 'petugas123', 'Petugas')"
        );
        stmt.execute(
            "INSERT OR IGNORE INTO kategori (nama_kategori) VALUES " +
            "('Fiksi'),('Non-Fiksi'),('Sains & Teknologi'),('Sejarah'),('Pendidikan'),('Agama'),('Biografi')"
        );
        stmt.execute(
            "INSERT OR IGNORE INTO buku (judul, penulis, penerbit, tahun, stok, id_kategori) VALUES " +
            "('Laskar Pelangi', 'Andrea Hirata', 'Bentang Pustaka', 2005, 5, 1)," +
            "('Negeri 5 Menara', 'Ahmad Fuadi', 'Gramedia', 2009, 4, 1)," +
            "('Bumi Manusia', 'Pramoedya Ananta Toer', 'Hasta Mitra', 1980, 3, 1)," +
            "('Sapiens', 'Yuval Noah Harari', 'KPG', 2014, 4, 2)," +
            "('Atomic Habits', 'James Clear', 'Gramedia', 2018, 5, 2)," +
            "('Clean Code', 'Robert C. Martin', 'Prentice Hall', 2008, 2, 3)," +
            "('Sejarah Indonesia Modern', 'M.C. Ricklefs', 'Serambi', 2001, 6, 4)," +
            "('Biografi Soekarno', 'Lambert Giebels', 'Grasindo', 2001, 4, 7)"
        );
        stmt.execute(
            "INSERT OR IGNORE INTO anggota (nama, alamat, no_hp, status) VALUES " +
            "('Budi Santoso', 'Jl. Merdeka No. 1, Jakarta', '081234567890', 'Aktif')," +
            "('Siti Rahayu', 'Jl. Sudirman No. 5, Bandung', '082345678901', 'Aktif')," +
            "('Ahmad Fauzi', 'Jl. Gatot Subroto No. 10, Surabaya', '083456789012', 'Aktif')," +
            "('Dewi Lestari', 'Jl. Diponegoro No. 15, Yogyakarta', '084567890123', 'Aktif')," +
            "('Eko Prasetyo', 'Jl. Ahmad Yani No. 20, Semarang', '085678901234', 'Tidak Aktif')"
        );
        stmt.close();
        System.out.println(">>> Data default berhasil dimasukkan.");
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error menutup koneksi: " + e.getMessage());
        }
    }
}
