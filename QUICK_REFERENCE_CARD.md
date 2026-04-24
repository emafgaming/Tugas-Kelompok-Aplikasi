# 🎯 Quick Reference Card - Update Sistem Perpustakaan

## 🔑 Login Credentials

| Role | Username | Password | Akses |
|------|----------|----------|-------|
| **Admin** | `admin` | `admin123` | Full Access ✅ |
| **Petugas** | `petugas` | `petugas123` | Limited Access ⚠️ |
| **Demo** | `demo` | `demo123` | Read-Only 👁️ |

---

## 🎭 Fitur Akun Demo

### ✅ Bisa:
- Lihat semua data
- Cari & filter
- Lihat laporan
- Cetak kartu
- **Akses menu "Hubungi Developer"**

### ❌ Tidak Bisa:
- Tambah data
- Edit data
- Hapus data
- Bersihkan form

---

## 📞 Kontak Developer (Menu Demo)

| Platform | Info |
|----------|------|
| 📧 Email | `your.email@example.com` |
| 📱 WhatsApp | `+62 812-3456-7890` |
| 💼 LinkedIn | `linkedin.com/in/yourprofile` |
| 🐙 GitHub | `github.com/yourusername` |

**Cara Ubah:** Edit `FormKontakDeveloper.java`

---

## 🔧 Bug Fixes

### ✅ Kolom Stok & Kategori
**File:** `BukuController.java`
- Kolom stok sekarang di posisi yang benar
- Kolom kategori sekarang di posisi yang benar
- Tambah nomor urut di kolom pertama

---

## 🪪 Layout Kartu Anggota

### Sebelum:
```
[Label] [Combo] [Btn1] [Btn2] [Btn3] [Btn4 TERTUTUP]
```

### Sesudah:
```
Baris 1: [Label] [ComboBox]
Baris 2: [Btn1] [Btn2] [Btn3] [Btn4] ✅
```

---

## 🎨 Visual Indicators

### Badge Role:
- 🔵 **Admin** - Blue
- 🟢 **Petugas** - Green
- 🟠 **Demo** - Orange (NEW!)

### Banner Demo:
```
🎭 MODE DEMO AKTIF
Anda dalam mode READ-ONLY
```

### Tombol Disabled:
```
[Tambah]  ← Abu-abu + Tooltip
🔒 Fitur ini dinonaktifkan dalam mode demo
```

---

## 📁 Files Modified

| File | Status | Changes |
|------|--------|---------|
| `BukuController.java` | ✏️ Modified | Fix kolom tabel |
| `UserModel.java` | ✏️ Modified | Add `isDemo` field |
| `LoginController.java` | ✏️ Modified | Add demo login |
| `Dashboard.java` | ✏️ Modified | Add demo features |
| `KartuAnggotaPanel.java` | ✏️ Modified | Fix layout |
| `FormKontakDeveloper.java` | ✨ New | Contact form |

---

## 📚 Documentation

| File | Purpose |
|------|---------|
| `FITUR_DEMO_DAN_KONTAK.md` | Dokumentasi teknis lengkap |
| `CARA_LOGIN_DEMO.md` | Panduan login demo |
| `UPDATE_TERBARU.md` | Ringkasan update |
| `CARA_UBAH_KONTAK_DEVELOPER.md` | Cara customisasi kontak |
| `RINGKASAN_VISUAL_UPDATE.md` | Perbandingan visual |
| `QUICK_REFERENCE_CARD.md` | Quick reference (this) |

---

## 🚀 Quick Start

### 1. Jalankan Aplikasi
```bash
Run Main.java
```

### 2. Login Demo
```
Username: demo
Password: demo123
```

### 3. Eksplorasi
- Lihat data buku, anggota, kategori
- Coba fitur pencarian
- Lihat laporan
- Klik menu "Hubungi Developer"

---

## 🔄 Customisasi Cepat

### Ubah Kontak Developer
**File:** `FormKontakDeveloper.java`

```java
// Email
createContactCard("📧", "Email", 
    "EMAIL_ANDA@example.com", 
    "mailto:EMAIL_ANDA@example.com")

// WhatsApp (tanpa + dan tanda hubung di link)
createContactCard("📱", "WhatsApp", 
    "+62 XXX-XXXX-XXXX", 
    "https://wa.me/62XXXXXXXXXXX")
```

### Ubah Password Demo
**File:** `UserModel.java` → method `login()`

```java
if ("demo".equalsIgnoreCase(username) && 
    "PASSWORD_BARU".equals(password)) {
    // ...
}
```

---

## ⚡ Keyboard Shortcuts

| Shortcut | Action |
|----------|--------|
| `Tab` | Next field |
| `Shift+Tab` | Previous field |
| `Enter` | Submit form |
| `Esc` | Close dialog |
| `Ctrl+F` | Focus search (if available) |

---

## 🐛 Troubleshooting

### Login demo tidak berhasil
✅ Pastikan username: `demo` (lowercase)
✅ Pastikan password: `demo123`

### Menu kontak tidak muncul
✅ Pastikan login sebagai demo
✅ Bukan admin atau petugas

### Link kontak tidak terbuka
✅ Set browser default di sistem
✅ Check format link (lihat dokumentasi)

### Tombol CRUD masih aktif
✅ Pastikan login sebagai demo
✅ Check method `disableCRUDForDemo()`

### Kolom masih tertukar
✅ Pastikan file `BukuController.java` sudah diupdate
✅ Compile ulang project

---

## 📊 Feature Matrix

```
┌─────────────────┬───────┬─────────┬──────┐
│ Feature         │ Admin │ Petugas │ Demo │
├─────────────────┼───────┼─────────┼──────┤
│ View Data       │  ✅   │   ✅    │  ✅  │
│ Search/Filter   │  ✅   │   ✅    │  ✅  │
│ Add Data        │  ✅   │   ✅    │  ❌  │
│ Edit Data       │  ✅   │   ✅    │  ❌  │
│ Delete Data     │  ✅   │   ✅    │  ❌  │
│ Reports         │  ✅   │   ✅    │  ✅  │
│ Print Card      │  ✅   │   ✅    │  ✅  │
│ Contact Dev     │  ❌   │   ❌    │  ✅  │
└─────────────────┴───────┴─────────┴──────┘
```

---

## 🎯 Use Cases

### 1. Demo ke Klien
```
1. Login: demo / demo123
2. Tunjukkan fitur view & laporan
3. Jelaskan CRUD disabled untuk keamanan
4. Tunjukkan menu kontak
```

### 2. Presentasi Tugas Akhir
```
1. Login: admin / admin123 (untuk demo CRUD)
2. Tunjukkan semua fitur
3. Login: demo / demo123 (untuk demo read-only)
4. Jelaskan security features
```

### 3. Testing
```
1. Login: admin (test full features)
2. Login: demo (test restrictions)
3. Verify CRUD disabled
4. Test contact form
```

---

## 📝 Checklist Sebelum Demo

- [ ] Aplikasi berjalan tanpa error
- [ ] Database terisi data dummy
- [ ] Login demo berhasil
- [ ] Dialog informasi muncul
- [ ] Banner orange terlihat
- [ ] Tombol CRUD disabled
- [ ] Menu kontak muncul
- [ ] Link kontak bisa dibuka
- [ ] Kolom tabel sudah benar
- [ ] Layout kartu rapi

---

## 🔐 Security Notes

### Akun Demo:
- ✅ Hardcoded (tidak di database)
- ✅ Tidak bisa ubah data
- ✅ Session terpisah
- ✅ Validasi di setiap operasi

### Best Practices:
- Jangan share password admin
- Gunakan demo untuk showcase
- Monitor session demo
- Update password secara berkala

---

## 📞 Support

### Dokumentasi:
1. `FITUR_DEMO_DAN_KONTAK.md` - Detail teknis
2. `CARA_LOGIN_DEMO.md` - Panduan lengkap
3. `CARA_UBAH_KONTAK_DEVELOPER.md` - Customisasi

### Contact:
- Login sebagai demo
- Klik menu "Hubungi Developer"
- Pilih metode kontak
- Klik "Buka"

---

## 🎉 Summary

### What's New:
- ✅ Bug fix: Kolom stok & kategori
- ✅ Akun demo read-only
- ✅ Form kontak developer
- ✅ Layout kartu anggota improved

### Benefits:
- ✅ Demo aman tanpa risiko
- ✅ Channel untuk project baru
- ✅ UI lebih profesional
- ✅ Ready untuk presentasi

---

**Version:** 2.0.0  
**Date:** 24 April 2026  
**Status:** ✅ Production Ready

---

**Print this card for quick reference! 📄**
