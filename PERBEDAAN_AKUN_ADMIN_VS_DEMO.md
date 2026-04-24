# 🔐 Perbedaan Akun Admin vs Demo

## 📋 Ringkasan

Sistem sekarang memiliki **3 jenis akun** dengan level akses berbeda:

| Akun | Username | Password | Level Akses | Warna Badge |
|------|----------|----------|-------------|-------------|
| **Admin** | `admin` | `admin123` | Full Access ✅ | 🔵 Blue |
| **Petugas** | `petugas` | `petugas123` | Limited Access ⚠️ | 🟢 Green |
| **Demo** | `demo` | `demo123` | Read-Only 👁️ | 🟠 Orange |

---

## 🎯 Perbandingan Fitur

### ✅ ADMIN (Full Access)

**Bisa melakukan SEMUA fitur:**
- ✅ Lihat semua data
- ✅ Cari & filter data
- ✅ **Tambah data baru**
- ✅ **Edit/Update data**
- ✅ **Hapus data**
- ✅ **Bersihkan form**
- ✅ Lihat laporan
- ✅ **Cetak kartu anggota**
- ✅ **Simpan kartu sebagai gambar**
- ✅ Semua tombol AKTIF

**Visual:**
```
┌─────────────────────────────┐
│ 👤  A                       │
│     ADMIN                   │
│     [Admin] ← Blue Badge    │
└─────────────────────────────┘

Form Buku:
[➕ Tambah]    ← AKTIF (hijau)
[✏️ Update]    ← AKTIF (orange)
[🗑️ Hapus]     ← AKTIF (merah)
[🔄 Bersihkan] ← AKTIF (biru)

Kartu Anggota:
[👁️ Preview]   ← AKTIF
[🖨️ Cetak]     ← AKTIF
[💾 Simpan]    ← AKTIF
[⟳ Refresh]   ← AKTIF
```

---

### 👁️ DEMO (Read-Only)

**Hanya bisa VIEW, TIDAK bisa edit:**
- ✅ Lihat semua data
- ✅ Cari & filter data
- ❌ **Tambah data baru** (DISABLED)
- ❌ **Edit/Update data** (DISABLED)
- ❌ **Hapus data** (DISABLED)
- ❌ **Bersihkan form** (DISABLED)
- ✅ Lihat laporan
- ✅ Preview kartu anggota
- ❌ **Cetak kartu** (DISABLED)
- ❌ **Simpan kartu** (DISABLED)
- ✅ **Akses menu "Hubungi Developer"**

**Visual:**
```
┌─────────────────────────────┐
│ 👤  D                       │
│     DEMO                    │
│     [Demo] ← Orange Badge   │
└─────────────────────────────┘

Banner Peringatan:
┌──────────────────────────────────────┐
│ 🎭 MODE DEMO AKTIF                   │
│ Anda dalam mode READ-ONLY            │
└──────────────────────────────────────┘

Form Buku:
[➕ Tambah]    ← DISABLED (abu-abu) 🔒
[✏️ Update]    ← DISABLED (abu-abu) 🔒
[🗑️ Hapus]     ← DISABLED (abu-abu) 🔒
[🔄 Bersihkan] ← DISABLED (abu-abu) 🔒

Kartu Anggota:
[👁️ Preview]   ← AKTIF (bisa preview)
[🖨️ Cetak]     ← DISABLED 🔒
[💾 Simpan]    ← DISABLED 🔒
[⟳ Refresh]   ← AKTIF (bisa refresh)

Menu Khusus:
📞 Hubungi Developer ← Hanya untuk DEMO
```

---

## 🖼️ Tampilan Login Form

### ❌ SEBELUM (Tidak ada info Demo)
```
┌─────────────────────────────────────┐
│ ℹ  Akun Default:                    │
│                                     │
│ Admin: username = admin             │
│        password = admin123          │
│                                     │
│ Petugas: username = petugas         │
│          password = petugas123      │
└─────────────────────────────────────┘
```

### ✅ SESUDAH (Ada info Demo dengan penjelasan)
```
┌─────────────────────────────────────────────────────────┐
│ ℹ  Akun Default:                                        │
│                                                         │
│ Admin: username = admin  |  password = admin123        │
│        (Full Access - Bisa menggunakan semua fitur)    │
│                                                         │
│ Petugas: username = petugas  |  password = petugas123  │
│                                                         │
│ Demo: username = demo  |  password = demo123           │
│       (Read-Only - Hanya bisa melihat, tidak bisa      │
│        edit/hapus)                                      │
└─────────────────────────────────────────────────────────┘
```

**Warna:**
- Admin: Teks biru normal
- Admin desc: Hijau (menunjukkan full access)
- Demo: **Teks orange bold** (menonjol)
- Demo desc: Merah (peringatan read-only)

---

## 📊 Tabel Perbandingan Detail

```
┌──────────────────────────┬────────┬──────────┬──────┐
│ Fitur                    │ Admin  │ Petugas  │ Demo │
├──────────────────────────┼────────┼──────────┼──────┤
│ VIEWING                  │        │          │      │
│ • Lihat Data Buku        │   ✅   │    ✅    │  ✅  │
│ • Lihat Data Anggota     │   ✅   │    ✅    │  ✅  │
│ • Lihat Data Kategori    │   ✅   │    ✅    │  ✅  │
│ • Lihat Peminjaman       │   ✅   │    ✅    │  ✅  │
│ • Cari & Filter          │   ✅   │    ✅    │  ✅  │
├──────────────────────────┼────────┼──────────┼──────┤
│ CRUD BUKU                │        │          │      │
│ • Tambah Buku            │   ✅   │    ✅    │  ❌  │
│ • Edit Buku              │   ✅   │    ✅    │  ❌  │
│ • Hapus Buku             │   ✅   │    ✅    │  ❌  │
│ • Bersihkan Form         │   ✅   │    ✅    │  ❌  │
├──────────────────────────┼────────┼──────────┼──────┤
│ CRUD KATEGORI            │        │          │      │
│ • Tambah Kategori        │   ✅   │    ✅    │  ❌  │
│ • Edit Kategori          │   ✅   │    ✅    │  ❌  │
│ • Hapus Kategori         │   ✅   │    ✅    │  ❌  │
│ • Bersihkan Form         │   ✅   │    ✅    │  ❌  │
├──────────────────────────┼────────┼──────────┼──────┤
│ CRUD ANGGOTA             │        │          │      │
│ • Tambah Anggota         │   ✅   │    ✅    │  ❌  │
│ • Edit Anggota           │   ✅   │    ✅    │  ❌  │
│ • Hapus Anggota          │   ✅   │    ✅    │  ❌  │
│ • Bersihkan Form         │   ✅   │    ✅    │  ❌  │
├──────────────────────────┼────────┼──────────┼──────┤
│ PEMINJAMAN               │        │          │      │
│ • Proses Peminjaman      │   ✅   │    ✅    │  ❌  │
│ • Proses Pengembalian    │   ✅   │    ✅    │  ❌  │
│ • Bersihkan Form         │   ✅   │    ✅    │  ❌  │
├──────────────────────────┼────────┼──────────┼──────┤
│ LAPORAN                  │        │          │      │
│ • Lihat Laporan          │   ✅   │    ✅    │  ✅  │
│ • Lihat Statistik        │   ✅   │    ✅    │  ✅  │
│ • Lihat Chart            │   ✅   │    ✅    │  ✅  │
├──────────────────────────┼────────┼──────────┼──────┤
│ KARTU ANGGOTA            │        │          │      │
│ • Preview Kartu          │   ✅   │    ✅    │  ✅  │
│ • Cetak Kartu            │   ✅   │    ✅    │  ❌  │
│ • Simpan Gambar          │   ✅   │    ✅    │  ❌  │
│ • Refresh Data           │   ✅   │    ✅    │  ✅  │
├──────────────────────────┼────────┼──────────┼──────┤
│ KHUSUS                   │        │          │      │
│ • Hubungi Developer      │   ❌   │    ❌    │  ✅  │
│ • Banner Demo            │   ❌   │    ❌    │  ✅  │
└──────────────────────────┴────────┴──────────┴──────┘
```

---

## 🎨 Visual Indicator

### 1. Badge Role di Sidebar

**Admin:**
```
┌─────────────┐
│ 👤  A       │
│     ADMIN   │
│     [Admin] │ ← Background: Blue (#3498db)
└─────────────┘
```

**Demo:**
```
┌─────────────┐
│ 👤  D       │
│     DEMO    │
│     [Demo]  │ ← Background: Orange (#e67e22)
└─────────────┘
```

### 2. Banner Peringatan (Hanya Demo)

```
┌──────────────────────────────────────────────────┐
│ 🎭 MODE DEMO AKTIF                               │
│ Anda dalam mode READ-ONLY. Semua fitur CRUD      │
│ dinonaktifkan. Tertarik? Klik menu 'Hubungi      │
│ Developer' untuk info lebih lanjut!              │
└──────────────────────────────────────────────────┘
↑ Background: Orange gradient
```

### 3. Tombol Disabled

**Admin (Aktif):**
```
[➕ Tambah]  ← Background: Green, Cursor: Hand
```

**Demo (Disabled):**
```
[➕ Tambah]  ← Background: Gray, Cursor: Default
              Tooltip: "🔒 Fitur ini dinonaktifkan dalam mode demo"
```

---

## 🔄 User Flow

### Flow Admin (Full Access)
```
[Login: admin/admin123]
         ↓
[Dashboard] (Tanpa banner)
         ↓
[Pilih Menu: Buku]
         ↓
[Form Buku]
         ↓
Semua tombol AKTIF ✅
         ↓
[Klik Tambah] → Berhasil ✅
[Klik Update] → Berhasil ✅
[Klik Hapus]  → Berhasil ✅
```

### Flow Demo (Read-Only)
```
[Login: demo/demo123]
         ↓
[Dialog Info Demo] ← Penjelasan mode demo
         ↓
User klik OK
         ↓
[Dashboard] (Dengan banner orange)
         ↓
[Pilih Menu: Buku]
         ↓
[Form Buku]
         ↓
Semua tombol DISABLED ❌
         ↓
[Hover Tambah] → Tooltip: "🔒 Fitur dinonaktifkan"
[Klik Tambah]  → Tidak ada reaksi (disabled)
         ↓
[Bisa lihat data] ✅
[Bisa cari data]  ✅
[Bisa filter]     ✅
         ↓
Tertarik?
         ↓
[Klik Menu: Hubungi Developer]
         ↓
[Form Kontak] → Hubungi developer
```

---

## 💡 Use Case

### 1. Showcase ke Klien (Gunakan Demo)
```
Skenario: Demo sistem ke calon klien

1. Login: demo / demo123
2. Tunjukkan:
   ✅ Lihat data buku lengkap
   ✅ Fitur pencarian & filter
   ✅ Laporan & statistik
   ✅ Preview kartu anggota
3. Jelaskan:
   ❌ Tombol CRUD disabled untuk keamanan
   ❌ Data tidak bisa diubah/dihapus
4. Jika tertarik:
   ✅ Klik menu "Hubungi Developer"
   ✅ Pilih metode kontak
```

### 2. Presentasi Tugas Akhir (Gunakan Admin)
```
Skenario: Presentasi di depan dosen

1. Login: admin / admin123
2. Tunjukkan:
   ✅ Semua fitur CRUD berfungsi
   ✅ Tambah data buku
   ✅ Edit data anggota
   ✅ Proses peminjaman
   ✅ Cetak kartu anggota
3. Kemudian demo mode read-only:
   - Logout
   - Login: demo / demo123
   - Tunjukkan perbedaan akses
```

### 3. Testing Sistem (Gunakan Keduanya)
```
Skenario: Test security & restrictions

1. Test Admin:
   ✅ Semua fitur harus berfungsi
   ✅ CRUD berhasil
   ✅ Cetak/simpan berhasil

2. Test Demo:
   ✅ View data berhasil
   ✅ Cari/filter berhasil
   ❌ CRUD harus disabled
   ❌ Cetak/simpan harus disabled
   ✅ Menu kontak muncul
```

---

## 🛡️ Security Features

### Validasi di Level Controller

**Contoh di BukuController:**
```java
public void tambah() {
    // Cek apakah user demo
    if (LoginController.isDemo()) {
        JOptionPane.showMessageDialog(view,
            "Fitur ini tidak tersedia dalam mode demo!",
            "Demo Mode", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Lanjutkan proses tambah...
}
```

### Disable di Level View

**Contoh di Dashboard:**
```java
if (currentUser.isDemo()) {
    disableCRUDForDemo();
}

private void disableCRUDForDemo() {
    disableButtonsInPanel(formBuku, "Tambah", "Update", "Hapus", "Bersihkan");
    disableButtonsInPanel(formKategori, "Tambah", "Update", "Hapus", "Bersihkan");
    disableButtonsInPanel(formAnggota, "Tambah", "Update", "Hapus", "Bersihkan");
    disableButtonsInPanel(formPeminjaman, "Pinjam", "Kembalikan", "Bersihkan");
}
```

### Disable di KartuAnggotaPanel

**Baru ditambahkan:**
```java
// Disable tombol untuk akun demo (kecuali Preview dan Refresh)
if (LoginController.isDemo()) {
    btnCetak.setEnabled(false);
    btnCetak.setToolTipText("🔒 Fitur ini dinonaktifkan dalam mode demo");
    btnSimpan.setEnabled(false);
    btnSimpan.setToolTipText("🔒 Fitur ini dinonaktifkan dalam mode demo");
}
```

---

## 📝 Checklist Testing

### Test Admin:
- [ ] Login berhasil
- [ ] Badge biru muncul
- [ ] Tidak ada banner demo
- [ ] Tombol Tambah aktif
- [ ] Tombol Update aktif
- [ ] Tombol Hapus aktif
- [ ] Tombol Bersihkan aktif
- [ ] Cetak kartu berhasil
- [ ] Simpan kartu berhasil
- [ ] Menu "Hubungi Developer" TIDAK muncul

### Test Demo:
- [ ] Login berhasil
- [ ] Dialog info demo muncul
- [ ] Badge orange muncul
- [ ] Banner demo muncul
- [ ] Tombol Tambah disabled
- [ ] Tombol Update disabled
- [ ] Tombol Hapus disabled
- [ ] Tombol Bersihkan disabled
- [ ] Tooltip muncul saat hover
- [ ] Preview kartu berhasil
- [ ] Cetak kartu disabled
- [ ] Simpan kartu disabled
- [ ] Menu "Hubungi Developer" muncul
- [ ] Bisa lihat semua data
- [ ] Bisa cari & filter

---

## 🎯 Kesimpulan

### Perbedaan Utama:

| Aspek | Admin | Demo |
|-------|-------|------|
| **Akses** | Full Access | Read-Only |
| **CRUD** | ✅ Semua aktif | ❌ Semua disabled |
| **Badge** | 🔵 Blue | 🟠 Orange |
| **Banner** | ❌ Tidak ada | ✅ Ada peringatan |
| **Cetak/Simpan** | ✅ Bisa | ❌ Tidak bisa |
| **Menu Kontak** | ❌ Tidak ada | ✅ Ada |
| **Tooltip** | - | 🔒 Disabled info |

### Manfaat:

**Untuk Admin:**
- ✅ Kontrol penuh sistem
- ✅ Bisa kelola semua data
- ✅ Tidak ada batasan

**Untuk Demo:**
- ✅ Showcase aman
- ✅ Data tidak bisa rusak
- ✅ Channel untuk project baru
- ✅ Presentasi profesional

---

**Sistem siap untuk demo dan production! 🚀**
