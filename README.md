# 📚 Sistem Manajemen Perpustakaan

Aplikasi desktop Java untuk manajemen perpustakaan menggunakan arsitektur MVC, Java Swing GUI, dan SQLite database.

---

## 🚀 CARA MENJALANKAN DI NETBEANS

### Prasyarat
- NetBeans IDE (versi 12+)
- Java JDK 8 atau lebih baru
- **sqlite-jdbc.jar** (download di: https://github.com/xerial/sqlite-jdbc/releases)

### Langkah Setup

**1. Buka Project di NetBeans**
```
File → Open Project → Pilih folder "Tugas Kelompok Aplikasi"
```

**2. Tambahkan Library SQLite JDBC**
```
Klik kanan "Libraries" pada project tree
→ Add JAR/Folder
→ Pilih file sqlite-jdbc-x.x.x.jar
```

**3. Set Main Class**
```
Klik kanan Project → Properties
→ Run
→ Main Class: librarymanagement.Main
```

**4. Build & Run**
```
F6 atau klik tombol Run (▶)
```

---

## 📁 STRUKTUR PROJECT

```
LibraryManagement/
├── src/
│   └── librarymanagement/
│       ├── Main.java                          ← Entry point
│       ├── model/
│       │   ├── DatabaseConnection.java        ← Koneksi SQLite (Singleton)
│       │   ├── UserModel.java                 ← Model + DAO User
│       │   ├── BukuModel.java                 ← Model + DAO Buku
│       │   ├── KategoriModel.java             ← Model + DAO Kategori
│       │   ├── AnggotaModel.java              ← Model + DAO Anggota
│       │   └── PeminjamanModel.java           ← Model + DAO Peminjaman
│       ├── view/
│       │   ├── LoginForm.java                 ← Form Login
│       │   ├── Dashboard.java                 ← Halaman Utama + Sidebar
│       │   ├── FormBuku.java                  ← CRUD Buku
│       │   ├── FormKategori.java              ← CRUD Kategori
│       │   ├── FormAnggota.java               ← CRUD Anggota
│       │   ├── FormPeminjaman.java            ← Peminjaman + Pengembalian
│       │   └── PanelLaporan.java              ← Laporan & Statistik
│       └── controller/
│           ├── LoginController.java           ← Logic Login
│           ├── BukuController.java            ← Logic Manajemen Buku
│           ├── KategoriController.java        ← Logic Kategori
│           ├── AnggotaController.java         ← Logic Anggota
│           └── PeminjamanController.java      ← Logic Peminjaman + Denda
├── database/
│   ├── schema.sql                             ← Script SQL (referensi)
│   └── perpustakaan.db                        ← Database SQLite (auto-dibuat)
└── README.md
```

---

## 🔑 AKUN DEFAULT

| Username | Password    | Role    | Akses       |
|----------|-------------|---------|-------------|
| admin    | admin123    | Admin   | Penuh       |
| petugas  | petugas123  | Petugas | Terbatas    |

---

## 🗄️ SKEMA DATABASE

### Tabel `users`
| Kolom    | Tipe    | Keterangan                    |
|----------|---------|-------------------------------|
| id_user  | INTEGER | Primary Key, Auto Increment   |
| username | TEXT    | Unique, Not Null              |
| password | TEXT    | Not Null                      |
| role     | TEXT    | 'Admin' atau 'Petugas'        |

### Tabel `kategori`
| Kolom        | Tipe    | Keterangan                  |
|--------------|---------|-----------------------------|
| id_kategori  | INTEGER | Primary Key, Auto Increment |
| nama_kategori| TEXT    | Unique, Not Null            |

### Tabel `buku`
| Kolom       | Tipe    | Keterangan                    |
|-------------|---------|-------------------------------|
| id_buku     | INTEGER | Primary Key, Auto Increment   |
| judul       | TEXT    | Not Null                      |
| penulis     | TEXT    | Not Null                      |
| penerbit    | TEXT    | Not Null                      |
| tahun       | INTEGER | Tahun terbit                  |
| stok        | INTEGER | Jumlah stok tersedia          |
| id_kategori | INTEGER | Foreign Key → kategori        |

### Tabel `anggota`
| Kolom      | Tipe    | Keterangan                    |
|------------|---------|-------------------------------|
| id_anggota | INTEGER | Primary Key, Auto Increment   |
| nama       | TEXT    | Not Null                      |
| alamat     | TEXT    |                               |
| no_hp      | TEXT    |                               |
| status     | TEXT    | 'Aktif' atau 'Tidak Aktif'    |

### Tabel `peminjaman`
| Kolom                | Tipe    | Keterangan                           |
|----------------------|---------|--------------------------------------|
| id_pinjam            | INTEGER | Primary Key, Auto Increment          |
| id_anggota           | INTEGER | Foreign Key → anggota                |
| tanggal_pinjam       | TEXT    | Format: YYYY-MM-DD                   |
| tanggal_kembali      | TEXT    | Batas tanggal kembali (YYYY-MM-DD)   |
| tanggal_dikembalikan | TEXT    | Tanggal aktual kembali (YYYY-MM-DD)  |
| status               | TEXT    | 'Dipinjam', 'Kembali', 'Terlambat'  |
| denda                | REAL    | Total denda (Rp)                     |

### Tabel `detail_peminjaman`
| Kolom     | Tipe    | Keterangan                    |
|-----------|---------|-------------------------------|
| id_detail | INTEGER | Primary Key, Auto Increment   |
| id_pinjam | INTEGER | Foreign Key → peminjaman      |
| id_buku   | INTEGER | Foreign Key → buku            |
| jumlah    | INTEGER | Jumlah buku dipinjam          |

---

## 🎯 FITUR APLIKASI

### 1. Sistem Login
- Form login dengan validasi username & password
- Role-based access: Admin dan Petugas
- Session management

### 2. Manajemen Buku (CRUD)
- Tambah, Edit, Hapus, Lihat buku
- Relasi ke tabel kategori
- Pencarian berdasarkan judul/penulis/penerbit
- Filter berdasarkan kategori
- Warna stok: hijau (cukup), oranye (sedikit), merah (habis)

### 3. Kategori Buku (CRUD)
- Tambah, Edit, Hapus kategori
- Pencarian kategori
- Validasi: kategori yang sedang dipakai tidak bisa dihapus

### 4. Manajemen Anggota (CRUD)
- Tambah, Edit, Hapus anggota
- Pencarian berdasarkan nama/no.HP/alamat
- Filter berdasarkan status (Aktif/Tidak Aktif)

### 5. Peminjaman Buku
- Form peminjaman dengan pilih anggota dan buku
- Validasi stok (tidak bisa meminjam jika stok habis)
- Stok otomatis berkurang saat peminjaman
- Tanggal pinjam dan batas kembali

### 6. Pengembalian Buku
- Update status peminjaman menjadi "Kembali"
- Stok buku otomatis dikembalikan
- Kalkulasi denda otomatis jika terlambat

### 7. Sistem Denda
- Denda otomatis: **Rp 1.000 per hari keterlambatan**
- Ditampilkan saat proses pengembalian
- Dicatat di database

### 8. Laporan & Statistik
- Total transaksi peminjaman
- Jumlah yang sedang dipinjam
- Jumlah terlambat
- Buku paling sering dipinjam (Top 10)
- Riwayat transaksi terbaru

---

## 📐 ARSITEKTUR MVC

```
Model (Data + Logic DB)
  │
  │  (Controller mengakses Model)
  ▼
Controller (Business Logic)
  │
  │  (Controller memanipulasi View)
  ▼
View (Tampilan GUI Swing)
  │
  │  (View memanggil Controller saat event)
  └→ Controller
```

### Konsep OOP yang Digunakan:
- **Encapsulation**: Semua atribut model private, diakses via getter/setter
- **Singleton Pattern**: DatabaseConnection menggunakan singleton
- **Separation of Concerns**: Model, View, Controller terpisah
- **Prepared Statement**: Mencegah SQL injection
- **Transaction**: Peminjaman menggunakan database transaction (atomic)

---

## 🛠️ DOWNLOAD SQLite JDBC

Download file `sqlite-jdbc-x.x.x.jar` dari:
```
https://github.com/xerial/sqlite-jdbc/releases/latest
```

Kemudian tambahkan ke project NetBeans:
```
Project Properties → Libraries → Add JAR/Folder → Pilih sqlite-jdbc.jar
```

---

## 📝 CATATAN TEKNIS

- Database SQLite dibuat otomatis di `database/perpustakaan.db` saat pertama kali dijalankan
- Format tanggal menggunakan `YYYY-MM-DD` (standar ISO 8601)
- Semua operasi database menggunakan **Prepared Statement** untuk keamanan
- Operasi peminjaman dan pengembalian menggunakan **database transaction**
- Tidak perlu konfigurasi server database

---

## 👥 Tim Pengembang

Tugas Kelompok - Pemrograman Java  
© 2024 Sistem Manajemen Perpustakaan
