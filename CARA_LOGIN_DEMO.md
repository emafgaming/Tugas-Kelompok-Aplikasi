# 🎭 Cara Login Akun Demo

## 🔑 Kredensial Login

### Akun Demo (Read-Only)
```
Username: demo
Password: demo123
```

### Akun Admin (Full Access)
```
Username: admin
Password: admin123
```

### Akun Petugas (Limited Access)
```
Username: petugas
Password: petugas123
```

---

## 🎯 Fitur Akun Demo

### ✅ BISA Dilakukan:
- ✅ Melihat semua data (Buku, Anggota, Kategori, Peminjaman)
- ✅ Mencari dan filter data
- ✅ Melihat laporan dan statistik
- ✅ Preview dan cetak kartu anggota
- ✅ Navigasi semua menu
- ✅ **Akses menu "Hubungi Developer"**

### ❌ TIDAK BISA Dilakukan:
- ❌ Tambah data baru
- ❌ Edit/Update data
- ❌ Hapus data
- ❌ Bersihkan form
- ❌ Semua tombol CRUD dinonaktifkan

---

## 📞 Menu Khusus Demo

Saat login sebagai demo, Anda akan melihat menu tambahan:

```
DEMO MODE
├── 📞 Hubungi Developer
```

Menu ini berisi:
- 📧 Email developer
- 📱 WhatsApp
- 💼 LinkedIn
- 🐙 GitHub

Klik button **"Buka"** untuk membuka link kontak!

---

## 🚀 Langkah Login

1. **Jalankan aplikasi**
   ```
   Run Main.java atau LibraryManagement.java
   ```

2. **Masukkan kredensial demo**
   ```
   Username: demo
   Password: demo123
   ```

3. **Klik Login**

4. **Baca dialog informasi**
   - Dialog akan muncul menjelaskan mode demo
   - Klik OK untuk melanjutkan

5. **Mulai eksplorasi!**
   - Dashboard akan terbuka
   - Banner orange menunjukkan mode demo aktif
   - Semua tombol CRUD disabled

---

## 💡 Tips Penggunaan

### Untuk Presentasi/Demo:
1. Login sebagai **demo** untuk showcase read-only
2. Tunjukkan semua fitur view dan laporan
3. Jelaskan bahwa CRUD disabled untuk keamanan
4. Tunjukkan menu "Hubungi Developer"

### Untuk Testing Penuh:
1. Login sebagai **admin** untuk akses penuh
2. Test semua fitur CRUD
3. Test peminjaman dan pengembalian
4. Test laporan dan cetak kartu

---

## 🎨 Visual Indicator Mode Demo

### 1. Badge Role (Orange)
```
┌─────────────┐
│ 👤 DEMO     │
│ [Demo] ← Orange badge
└─────────────┘
```

### 2. Banner Peringatan
```
┌──────────────────────────────────────┐
│ 🎭 MODE DEMO AKTIF                   │
│ Anda dalam mode READ-ONLY            │
└──────────────────────────────────────┘
```

### 3. Tombol Disabled
```
[Tambah]  ← Disabled (abu-abu)
[Update]  ← Disabled (abu-abu)
[Hapus]   ← Disabled (abu-abu)
```

Hover pada tombol disabled akan menampilkan tooltip:
```
🔒 Fitur ini dinonaktifkan dalam mode demo
```

---

## 🔄 Cara Logout

1. Klik tombol **"🚪 Logout"** di sidebar bawah
2. Konfirmasi logout
3. Kembali ke halaman login

---

## ❓ FAQ

**Q: Apakah data demo tersimpan di database?**
A: Tidak. Akun demo adalah hardcoded dan tidak tersimpan di database.

**Q: Bisakah saya mengubah password demo?**
A: Ya, edit file `UserModel.java` di method `login()`.

**Q: Bagaimana cara menambah kontak developer?**
A: Edit file `FormKontakDeveloper.java` di method `initComponents()`.

**Q: Apakah akun demo bisa dinonaktifkan?**
A: Ya, hapus atau comment kode login demo di `UserModel.java`.

**Q: Bisakah saya membuat akun demo dengan role berbeda?**
A: Ya, ubah parameter role saat membuat `UserModel` di login demo.

---

## 🛠️ Troubleshooting

### Masalah: Tombol CRUD masih aktif di mode demo
**Solusi:** 
- Pastikan method `disableCRUDForDemo()` dipanggil di Dashboard
- Check apakah `currentUser.isDemo()` return true

### Masalah: Menu "Hubungi Developer" tidak muncul
**Solusi:**
- Pastikan login sebagai demo (bukan admin/petugas)
- Check kondisi `if (currentUser.isDemo())` di Dashboard

### Masalah: Link kontak tidak terbuka
**Solusi:**
- Pastikan browser default sudah diset di sistem
- Jika gagal, copy link manual dari dialog error

---

## 📝 Customisasi

### Ubah Username/Password Demo
Edit `UserModel.java`:
```java
if ("USERNAME_BARU".equalsIgnoreCase(username) && 
    "PASSWORD_BARU".equals(password)) {
    UserModel demoUser = new UserModel(999, "demo", "demo123", "Demo", true);
    return demoUser;
}
```

### Ubah Kontak Developer
Edit `FormKontakDeveloper.java`:
```java
createContactCard("📧", "Email", 
    "EMAIL_ANDA@example.com", 
    "mailto:EMAIL_ANDA@example.com")
```

---

## ✅ Checklist Sebelum Demo

- [ ] Aplikasi berjalan tanpa error
- [ ] Database terisi data dummy
- [ ] Login demo berhasil
- [ ] Dialog informasi muncul
- [ ] Banner orange terlihat
- [ ] Tombol CRUD disabled
- [ ] Menu "Hubungi Developer" muncul
- [ ] Link kontak bisa dibuka
- [ ] Laporan bisa dilihat
- [ ] Kartu anggota bisa di-preview

---

**Selamat mencoba! 🚀**
