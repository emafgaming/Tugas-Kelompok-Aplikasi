# ✅ Checklist Sebelum Demo/Presentasi

## 🎯 Panduan Cepat

Gunakan checklist ini sebelum melakukan demo atau presentasi sistem perpustakaan.

---

## 📋 Pre-Demo Checklist

### 1. Persiapan Aplikasi

- [ ] ✅ Aplikasi bisa dijalankan tanpa error
- [ ] ✅ Database `perpustakaan.db` sudah ada
- [ ] ✅ Data dummy sudah terisi (buku, anggota, kategori)
- [ ] ✅ Tidak ada error di console saat startup
- [ ] ✅ Window aplikasi muncul dengan benar

### 2. Testing Login

#### Admin:
- [ ] ✅ Login `admin` / `admin123` berhasil
- [ ] ✅ Badge biru muncul
- [ ] ✅ Tidak ada banner demo
- [ ] ✅ Semua menu terlihat (kecuali "Hubungi Developer")

#### Petugas:
- [ ] ✅ Login `petugas` / `petugas123` berhasil
- [ ] ✅ Badge hijau muncul
- [ ] ✅ Tidak ada banner demo

#### Demo:
- [ ] ✅ Login `demo` / `demo123` berhasil
- [ ] ✅ Dialog informasi demo muncul
- [ ] ✅ Badge orange muncul
- [ ] ✅ Banner demo muncul di dashboard
- [ ] ✅ Menu "Hubungi Developer" muncul

### 3. Testing Fitur Admin (Full Access)

#### Manajemen Buku:
- [ ] ✅ Bisa lihat data buku
- [ ] ✅ Tombol Tambah AKTIF (hijau)
- [ ] ✅ Tombol Update AKTIF (orange)
- [ ] ✅ Tombol Hapus AKTIF (merah)
- [ ] ✅ Tombol Bersihkan AKTIF (biru)
- [ ] ✅ Bisa tambah buku baru
- [ ] ✅ Bisa edit buku
- [ ] ✅ Bisa hapus buku
- [ ] ✅ Pencarian berfungsi
- [ ] ✅ Filter kategori berfungsi

#### Manajemen Kategori:
- [ ] ✅ Bisa lihat data kategori
- [ ] ✅ Semua tombol CRUD aktif
- [ ] ✅ Bisa tambah kategori
- [ ] ✅ Bisa edit kategori
- [ ] ✅ Bisa hapus kategori

#### Manajemen Anggota:
- [ ] ✅ Bisa lihat data anggota
- [ ] ✅ Semua tombol CRUD aktif
- [ ] ✅ Bisa tambah anggota
- [ ] ✅ Bisa edit anggota
- [ ] ✅ Bisa hapus anggota

#### Peminjaman:
- [ ] ✅ Bisa lihat data peminjaman
- [ ] ✅ Tombol Pinjam aktif
- [ ] ✅ Tombol Kembalikan aktif
- [ ] ✅ Bisa proses peminjaman
- [ ] ✅ Bisa proses pengembalian
- [ ] ✅ Denda otomatis terhitung

#### Laporan:
- [ ] ✅ Bisa lihat laporan
- [ ] ✅ Statistik terlihat
- [ ] ✅ Chart muncul dengan benar

#### Kartu Anggota:
- [ ] ✅ Bisa pilih anggota
- [ ] ✅ Preview kartu berfungsi
- [ ] ✅ Cetak kartu berfungsi
- [ ] ✅ Simpan gambar berfungsi
- [ ] ✅ Refresh data berfungsi

### 4. Testing Fitur Demo (Read-Only)

#### Visual Indicators:
- [ ] ✅ Badge orange terlihat
- [ ] ✅ Banner demo terlihat di dashboard
- [ ] ✅ Menu "Hubungi Developer" muncul

#### Manajemen Buku:
- [ ] ✅ Bisa lihat data buku
- [ ] ✅ Tombol Tambah DISABLED (abu-abu)
- [ ] ✅ Tombol Update DISABLED (abu-abu)
- [ ] ✅ Tombol Hapus DISABLED (abu-abu)
- [ ] ✅ Tombol Bersihkan DISABLED (abu-abu)
- [ ] ✅ Tooltip muncul saat hover: "🔒 Fitur ini dinonaktifkan dalam mode demo"
- [ ] ✅ Klik tombol tidak ada reaksi
- [ ] ✅ Pencarian berfungsi
- [ ] ✅ Filter kategori berfungsi

#### Manajemen Kategori:
- [ ] ✅ Bisa lihat data kategori
- [ ] ✅ Semua tombol CRUD disabled
- [ ] ✅ Tooltip muncul saat hover

#### Manajemen Anggota:
- [ ] ✅ Bisa lihat data anggota
- [ ] ✅ Semua tombol CRUD disabled
- [ ] ✅ Tooltip muncul saat hover

#### Peminjaman:
- [ ] ✅ Bisa lihat data peminjaman
- [ ] ✅ Tombol Pinjam disabled
- [ ] ✅ Tombol Kembalikan disabled
- [ ] ✅ Tooltip muncul saat hover

#### Laporan:
- [ ] ✅ Bisa lihat laporan
- [ ] ✅ Statistik terlihat
- [ ] ✅ Chart muncul dengan benar

#### Kartu Anggota:
- [ ] ✅ Bisa pilih anggota
- [ ] ✅ Preview kartu berfungsi
- [ ] ✅ Cetak kartu DISABLED
- [ ] ✅ Simpan gambar DISABLED
- [ ] ✅ Refresh data berfungsi
- [ ] ✅ Tooltip muncul pada tombol disabled

#### Form Kontak Developer:
- [ ] ✅ Menu "Hubungi Developer" bisa diklik
- [ ] ✅ Form kontak terbuka
- [ ] ✅ Semua card kontak terlihat (Email, WhatsApp, LinkedIn, GitHub)
- [ ] ✅ Button "Buka" berfungsi
- [ ] ✅ Link terbuka di browser
- [ ] ✅ Hover effect bekerja

### 5. Testing Login Form

- [ ] ✅ Info box terlihat dengan baik
- [ ] ✅ Akun Admin terlihat
- [ ] ✅ Deskripsi Admin (hijau) terlihat: "(Full Access - Bisa menggunakan semua fitur)"
- [ ] ✅ Akun Petugas terlihat
- [ ] ✅ Akun Demo terlihat dengan **bold** dan warna **orange**
- [ ] ✅ Deskripsi Demo (merah) terlihat: "(Read-Only - Hanya bisa melihat, tidak bisa edit/hapus)"
- [ ] ✅ Tidak ada teks terpotong
- [ ] ✅ Layout rapi

### 6. Testing Bug Fix

- [ ] ✅ Kolom Stok menampilkan data stok (bukan kategori)
- [ ] ✅ Kolom Kategori menampilkan data kategori (bukan stok)
- [ ] ✅ Nomor urut muncul di kolom pertama
- [ ] ✅ Data tidak tertukar

---

## 🎬 Skenario Demo

### Skenario 1: Demo ke Klien (15 menit)

**Persiapan:**
- [ ] Aplikasi sudah running
- [ ] Data dummy sudah terisi
- [ ] Browser default sudah diset

**Flow:**
1. **Intro (2 menit)**
   - [ ] Tunjukkan login form
   - [ ] Jelaskan 3 jenis akun
   - [ ] Highlight akun Demo (read-only)

2. **Login Demo (3 menit)**
   - [ ] Login: `demo` / `demo123`
   - [ ] Baca dialog informasi
   - [ ] Tunjukkan badge orange
   - [ ] Tunjukkan banner demo

3. **Eksplorasi Fitur (7 menit)**
   - [ ] Buka menu Buku → Tunjukkan data
   - [ ] Coba pencarian → Berfungsi
   - [ ] Hover tombol Tambah → Tunjukkan tooltip disabled
   - [ ] Buka menu Laporan → Tunjukkan statistik
   - [ ] Buka menu Kartu Anggota → Preview kartu
   - [ ] Tunjukkan tombol Cetak disabled

4. **Form Kontak (2 menit)**
   - [ ] Klik menu "Hubungi Developer"
   - [ ] Tunjukkan semua metode kontak
   - [ ] Klik button "Buka" → Browser terbuka

5. **Closing (1 menit)**
   - [ ] Jelaskan manfaat mode demo
   - [ ] Tanya apakah tertarik
   - [ ] Berikan kontak

### Skenario 2: Presentasi Tugas Akhir (20 menit)

**Persiapan:**
- [ ] Aplikasi sudah running
- [ ] Data dummy sudah terisi
- [ ] Slide presentasi siap

**Flow:**
1. **Intro (3 menit)**
   - [ ] Jelaskan sistem perpustakaan
   - [ ] Tunjukkan arsitektur MVC
   - [ ] Jelaskan teknologi yang digunakan

2. **Demo Admin (8 menit)**
   - [ ] Login: `admin` / `admin123`
   - [ ] Tunjukkan badge biru
   - [ ] Demo CRUD Buku (Tambah, Edit, Hapus)
   - [ ] Demo CRUD Anggota
   - [ ] Demo Peminjaman & Pengembalian
   - [ ] Demo Laporan & Statistik
   - [ ] Demo Cetak Kartu Anggota

3. **Demo Security (5 menit)**
   - [ ] Logout dari admin
   - [ ] Login: `demo` / `demo123`
   - [ ] Tunjukkan dialog informasi
   - [ ] Tunjukkan badge orange
   - [ ] Tunjukkan banner demo
   - [ ] Tunjukkan tombol disabled
   - [ ] Jelaskan security features

4. **Demo Form Kontak (2 menit)**
   - [ ] Klik menu "Hubungi Developer"
   - [ ] Jelaskan fitur untuk showcase
   - [ ] Tunjukkan auto-open browser

5. **Q&A (2 menit)**
   - [ ] Jawab pertanyaan dosen
   - [ ] Tunjukkan kode jika diminta

### Skenario 3: Testing Lengkap (30 menit)

**Persiapan:**
- [ ] Aplikasi sudah running
- [ ] Data dummy sudah terisi
- [ ] Checklist ini dicetak

**Flow:**
1. **Test Admin (10 menit)**
   - [ ] Login admin
   - [ ] Test semua CRUD di semua menu
   - [ ] Test pencarian & filter
   - [ ] Test laporan
   - [ ] Test cetak kartu
   - [ ] Logout

2. **Test Demo (10 menit)**
   - [ ] Login demo
   - [ ] Verify dialog muncul
   - [ ] Verify badge orange
   - [ ] Verify banner demo
   - [ ] Test semua tombol disabled
   - [ ] Test tooltip muncul
   - [ ] Test view data berfungsi
   - [ ] Test pencarian berfungsi
   - [ ] Test preview kartu berfungsi
   - [ ] Test cetak/simpan disabled
   - [ ] Test form kontak
   - [ ] Logout

3. **Test Bug Fix (5 menit)**
   - [ ] Login admin
   - [ ] Buka menu Buku
   - [ ] Verify kolom Stok benar
   - [ ] Verify kolom Kategori benar
   - [ ] Verify nomor urut muncul

4. **Test Login Form (5 menit)**
   - [ ] Logout
   - [ ] Verify info box terlihat
   - [ ] Verify akun Demo terlihat
   - [ ] Verify deskripsi terlihat
   - [ ] Verify warna sesuai

---

## 🐛 Troubleshooting

### Masalah: Aplikasi tidak bisa dijalankan
**Solusi:**
- [ ] Check apakah Java sudah terinstall
- [ ] Check apakah sqlite-jdbc.jar sudah ada
- [ ] Compile ulang project
- [ ] Check console untuk error

### Masalah: Login demo tidak berhasil
**Solusi:**
- [ ] Pastikan username: `demo` (lowercase)
- [ ] Pastikan password: `demo123`
- [ ] Check UserModel.java sudah diupdate

### Masalah: Tombol CRUD masih aktif di demo
**Solusi:**
- [ ] Check Dashboard.java sudah diupdate
- [ ] Check method `disableCRUDForDemo()` dipanggil
- [ ] Compile ulang project

### Masalah: Menu kontak tidak muncul
**Solusi:**
- [ ] Pastikan login sebagai demo (bukan admin)
- [ ] Check Dashboard.java sudah diupdate
- [ ] Check kondisi `if (currentUser.isDemo())`

### Masalah: Link kontak tidak terbuka
**Solusi:**
- [ ] Set browser default di sistem
- [ ] Check format link sudah benar
- [ ] Test manual copy-paste link

### Masalah: Kolom masih tertukar
**Solusi:**
- [ ] Check BukuController.java sudah diupdate
- [ ] Compile ulang project
- [ ] Restart aplikasi

---

## 📝 Notes untuk Presenter

### Tips Presentasi:
- ✅ Siapkan data dummy yang menarik
- ✅ Gunakan nama buku/anggota yang familiar
- ✅ Jelaskan dengan bahasa sederhana
- ✅ Tunjukkan perbedaan admin vs demo dengan jelas
- ✅ Highlight security features
- ✅ Siap jawab pertanyaan teknis

### Yang Perlu Ditekankan:
- ✅ Arsitektur MVC yang rapi
- ✅ Security dengan 3 level akses
- ✅ UI/UX yang modern
- ✅ Fitur lengkap (CRUD, Laporan, Kartu)
- ✅ Mode demo untuk showcase
- ✅ Form kontak untuk project baru

### Yang Perlu Dihindari:
- ❌ Jangan terlalu cepat saat demo
- ❌ Jangan skip penjelasan penting
- ❌ Jangan lupa tunjukkan perbedaan akses
- ❌ Jangan lupa test sebelum presentasi

---

## 🎯 Final Check

### Sebelum Demo:
- [ ] ✅ Semua checklist di atas sudah dicentang
- [ ] ✅ Aplikasi berjalan lancar
- [ ] ✅ Tidak ada error
- [ ] ✅ Data dummy terisi
- [ ] ✅ Kontak developer sudah diupdate (jika perlu)
- [ ] ✅ Dokumentasi sudah dibaca
- [ ] ✅ Skenario demo sudah dipahami
- [ ] ✅ Siap menjawab pertanyaan

### Setelah Demo:
- [ ] ✅ Catat feedback dari audience
- [ ] ✅ Catat pertanyaan yang tidak bisa dijawab
- [ ] ✅ Follow up dengan klien/dosen
- [ ] ✅ Update sistem jika ada saran

---

## 📞 Emergency Contact

Jika ada masalah saat demo:
1. Tetap tenang
2. Restart aplikasi
3. Login ulang
4. Jika masih error, jelaskan ke audience
5. Lanjutkan dengan slide/dokumentasi

---

**Good luck dengan demo/presentasi! 🚀**

**Checklist ini bisa dicetak untuk referensi cepat saat demo.**
