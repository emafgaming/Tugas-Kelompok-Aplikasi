-- ====================================================================
-- Script SQL - Sistem Manajemen Perpustakaan
-- Database: SQLite
-- Dibuat untuk: Tugas Kelompok Aplikasi Java
-- ====================================================================

-- Aktifkan foreign key support di SQLite
PRAGMA foreign_keys = ON;

-- ====================================================================
-- 1. TABEL USERS
-- ====================================================================
CREATE TABLE IF NOT EXISTS users (
    id_user   INTEGER PRIMARY KEY AUTOINCREMENT,
    username  TEXT    NOT NULL UNIQUE,
    password  TEXT    NOT NULL,
    role      TEXT    NOT NULL CHECK(role IN ('Admin','Petugas'))
);

-- ====================================================================
-- 2. TABEL KATEGORI
-- ====================================================================
CREATE TABLE IF NOT EXISTS kategori (
    id_kategori   INTEGER PRIMARY KEY AUTOINCREMENT,
    nama_kategori TEXT    NOT NULL UNIQUE
);

-- ====================================================================
-- 3. TABEL BUKU
-- ====================================================================
CREATE TABLE IF NOT EXISTS buku (
    id_buku     INTEGER PRIMARY KEY AUTOINCREMENT,
    judul       TEXT    NOT NULL,
    penulis     TEXT    NOT NULL,
    penerbit    TEXT    NOT NULL,
    tahun       INTEGER NOT NULL,
    stok        INTEGER NOT NULL DEFAULT 0,
    id_kategori INTEGER,
    FOREIGN KEY (id_kategori) REFERENCES kategori(id_kategori)
);

-- ====================================================================
-- 4. TABEL ANGGOTA
-- ====================================================================
CREATE TABLE IF NOT EXISTS anggota (
    id_anggota INTEGER PRIMARY KEY AUTOINCREMENT,
    nama       TEXT    NOT NULL,
    alamat     TEXT,
    no_hp      TEXT,
    foto_pdf_path TEXT,
    status     TEXT    NOT NULL DEFAULT 'Aktif' CHECK(status IN ('Aktif','Tidak Aktif'))
);

-- ====================================================================
-- 5. TABEL PEMINJAMAN
-- ====================================================================
CREATE TABLE IF NOT EXISTS peminjaman (
    id_pinjam            INTEGER PRIMARY KEY AUTOINCREMENT,
    id_anggota           INTEGER NOT NULL,
    tanggal_pinjam       TEXT    NOT NULL,
    tanggal_kembali      TEXT    NOT NULL,
    tanggal_dikembalikan TEXT,
    status               TEXT    NOT NULL DEFAULT 'Dipinjam' CHECK(status IN ('Dipinjam','Kembali','Terlambat')),
    denda                REAL    DEFAULT 0,
    FOREIGN KEY (id_anggota) REFERENCES anggota(id_anggota)
);

-- ====================================================================
-- 6. TABEL DETAIL_PEMINJAMAN
-- ====================================================================
CREATE TABLE IF NOT EXISTS detail_peminjaman (
    id_detail  INTEGER PRIMARY KEY AUTOINCREMENT,
    id_pinjam  INTEGER NOT NULL,
    id_buku    INTEGER NOT NULL,
    jumlah     INTEGER NOT NULL DEFAULT 1,
    FOREIGN KEY (id_pinjam) REFERENCES peminjaman(id_pinjam),
    FOREIGN KEY (id_buku)   REFERENCES buku(id_buku)
);

-- ====================================================================
-- DATA DUMMY (INSERT OR IGNORE: aman untuk dijalankan berulang kali)
-- ====================================================================

-- User default
INSERT OR IGNORE INTO users (username, password, role) VALUES
    ('admin',   'admin123',   'Admin'),
    ('petugas', 'petugas123', 'Petugas');

-- Kategori
INSERT OR IGNORE INTO kategori (nama_kategori) VALUES
    ('Fiksi'),
    ('Non-Fiksi'),
    ('Sains & Teknologi'),
    ('Sejarah'),
    ('Pendidikan'),
    ('Agama'),
    ('Biografi');

-- Buku (contoh data dummy)
INSERT OR IGNORE INTO buku (judul, penulis, penerbit, tahun, stok, id_kategori) VALUES
    ('Laskar Pelangi',           'Andrea Hirata',         'Bentang Pustaka', 2005, 5,  1),
    ('Negeri 5 Menara',          'Ahmad Fuadi',           'Gramedia',        2009, 4,  1),
    ('Bumi Manusia',             'Pramoedya Ananta Toer', 'Hasta Mitra',     1980, 3,  1),
    ('Ayah',                     'Andrea Hirata',         'Bentang Pustaka', 2015, 6,  1),
    ('Sapiens',                  'Yuval Noah Harari',     'KPG',             2014, 4,  2),
    ('Atomic Habits',            'James Clear',           'Gramedia',        2018, 5,  2),
    ('Clean Code',               'Robert C. Martin',      'Prentice Hall',   2008, 2,  3),
    ('Artificial Intelligence',  'Stuart Russell',        'Erlangga',        2010, 3,  3),
    ('Sejarah Indonesia Modern', 'M.C. Ricklefs',         'Serambi',         2001, 6,  4),
    ('Sejarah Dunia',            'John M. Roberts',       'Alvabet',         2011, 4,  4),
    ('Matematika SMA',           'Tim Penulis',           'Erlangga',        2020, 8,  5),
    ('Biologi Molekular',        'Tim Peneliti',          'UI Press',        2019, 3,  5),
    ('Al-Quran dan Ilmu',        'Zaghloul El-Naggar',    'Shorouk Int.',    2010, 5,  6),
    ('Biografi Soekarno',        'Lambert Giebels',       'Grasindo',        2001, 4,  7),
    ('Steve Jobs',               'Walter Isaacson',       'Gramedia',        2011, 3,  7);

-- Anggota (contoh data dummy)
INSERT OR IGNORE INTO anggota (nama, alamat, no_hp, status) VALUES
    ('Budi Santoso',   'Jl. Merdeka No. 1, Jakarta',         '081234567890', 'Aktif'),
    ('Siti Rahayu',    'Jl. Sudirman No. 5, Bandung',        '082345678901', 'Aktif'),
    ('Ahmad Fauzi',    'Jl. Gatot Subroto No. 10, Surabaya', '083456789012', 'Aktif'),
    ('Dewi Lestari',   'Jl. Diponegoro No. 15, Yogyakarta',  '084567890123', 'Aktif'),
    ('Eko Prasetyo',   'Jl. Ahmad Yani No. 20, Semarang',    '085678901234', 'Tidak Aktif'),
    ('Rini Wulandari', 'Jl. Kartini No. 8, Malang',          '086789012345', 'Aktif'),
    ('Hendra Wijaya',  'Jl. Pahlawan No. 3, Medan',          '087890123456', 'Aktif'),
    ('Fitri Amalia',   'Jl. Veteran No. 12, Makassar',       '088901234567', 'Aktif');

-- ====================================================================
-- QUERY BERGUNA UNTUK CEK DATA
-- ====================================================================

-- Cek semua buku dengan kategori
-- SELECT b.*, k.nama_kategori FROM buku b LEFT JOIN kategori k ON b.id_kategori = k.id_kategori;

-- Cek peminjaman aktif
-- SELECT p.*, a.nama FROM peminjaman p JOIN anggota a ON p.id_anggota = a.id_anggota WHERE p.status = 'Dipinjam';

-- Cek buku terpopuler
-- SELECT b.judul, COUNT(dp.id_detail) as total FROM detail_peminjaman dp JOIN buku b ON dp.id_buku = b.id_buku GROUP BY b.id_buku ORDER BY total DESC;

-- ====================================================================
-- CATATAN PENTING:
-- - Tanggal menggunakan format TEXT 'YYYY-MM-DD' (ISO 8601)
-- - Denda dihitung Rp1.000 per hari keterlambatan
-- - Stok otomatis berkurang saat peminjaman dan bertambah saat pengembalian
-- - Database file akan tersimpan di: database/perpustakaan.db
-- ====================================================================
