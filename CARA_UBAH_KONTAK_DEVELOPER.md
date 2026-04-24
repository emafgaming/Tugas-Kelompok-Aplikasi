# 📝 Cara Mengubah Kontak Developer

## 🎯 Tujuan
Panduan ini menjelaskan cara mengubah informasi kontak developer yang ditampilkan di menu "Hubungi Developer" untuk akun demo.

---

## 📁 File yang Perlu Diubah

**File:** `LibraryManagement/src/main/java/librarymanagement/view/FormKontakDeveloper.java`

---

## 🔧 Langkah-Langkah

### 1. Buka File FormKontakDeveloper.java

Lokasi file:
```
LibraryManagement/
└── src/
    └── main/
        └── java/
            └── librarymanagement/
                └── view/
                    └── FormKontakDeveloper.java
```

### 2. Cari Method `initComponents()`

Scroll ke bagian yang membuat contact cards (sekitar baris 60-70):

```java
// Contact cards
centerPanel.add(createContactCard("📧", "Email", "your.email@example.com", "mailto:your.email@example.com"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("📱", "WhatsApp", "+62 812-3456-7890", "https://wa.me/6281234567890"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("💼", "LinkedIn", "linkedin.com/in/yourprofile", "https://linkedin.com/in/yourprofile"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("🐙", "GitHub", "github.com/yourusername", "https://github.com/yourusername"));
```

### 3. Ubah Informasi Kontak

#### A. Ubah Email

**Sebelum:**
```java
centerPanel.add(createContactCard("📧", "Email", 
    "your.email@example.com", 
    "mailto:your.email@example.com"));
```

**Sesudah:**
```java
centerPanel.add(createContactCard("📧", "Email", 
    "nama.anda@gmail.com",           // ← Ubah email di sini
    "mailto:nama.anda@gmail.com"));  // ← Ubah email di sini juga
```

#### B. Ubah WhatsApp

**Sebelum:**
```java
centerPanel.add(createContactCard("📱", "WhatsApp", 
    "+62 812-3456-7890", 
    "https://wa.me/6281234567890"));
```

**Sesudah:**
```java
centerPanel.add(createContactCard("📱", "WhatsApp", 
    "+62 821-9876-5432",                    // ← Nomor untuk ditampilkan
    "https://wa.me/6282198765432"));        // ← Nomor tanpa tanda hubung/spasi
```

**⚠️ Penting untuk WhatsApp:**
- Tampilan: Gunakan format `+62 XXX-XXXX-XXXX` (dengan tanda hubung)
- Link: Gunakan format `https://wa.me/62XXXXXXXXXXX` (tanpa tanda hubung, tanpa +)

#### C. Ubah LinkedIn

**Sebelum:**
```java
centerPanel.add(createContactCard("💼", "LinkedIn", 
    "linkedin.com/in/yourprofile", 
    "https://linkedin.com/in/yourprofile"));
```

**Sesudah:**
```java
centerPanel.add(createContactCard("💼", "LinkedIn", 
    "linkedin.com/in/johndoe",              // ← Username LinkedIn Anda
    "https://linkedin.com/in/johndoe"));    // ← URL lengkap
```

#### D. Ubah GitHub

**Sebelum:**
```java
centerPanel.add(createContactCard("🐙", "GitHub", 
    "github.com/yourusername", 
    "https://github.com/yourusername"));
```

**Sesudah:**
```java
centerPanel.add(createContactCard("🐙", "GitHub", 
    "github.com/johndoe",                   // ← Username GitHub Anda
    "https://github.com/johndoe"));         // ← URL lengkap
```

---

## 📝 Contoh Lengkap

### Sebelum (Default):
```java
// Contact cards
centerPanel.add(createContactCard("📧", "Email", "your.email@example.com", "mailto:your.email@example.com"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("📱", "WhatsApp", "+62 812-3456-7890", "https://wa.me/6281234567890"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("💼", "LinkedIn", "linkedin.com/in/yourprofile", "https://linkedin.com/in/yourprofile"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("🐙", "GitHub", "github.com/yourusername", "https://github.com/yourusername"));
```

### Sesudah (Contoh dengan data John Doe):
```java
// Contact cards
centerPanel.add(createContactCard("📧", "Email", "john.doe@gmail.com", "mailto:john.doe@gmail.com"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("📱", "WhatsApp", "+62 821-9876-5432", "https://wa.me/6282198765432"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("💼", "LinkedIn", "linkedin.com/in/johndoe", "https://linkedin.com/in/johndoe"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("🐙", "GitHub", "github.com/johndoe", "https://github.com/johndoe"));
```

---

## 🎨 Menambah/Menghapus Kontak

### Menambah Kontak Baru (Contoh: Telegram)

Tambahkan setelah kontak terakhir:

```java
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("✈️", "Telegram", "@johndoe", "https://t.me/johndoe"));
```

### Menghapus Kontak (Contoh: Hapus GitHub)

Hapus atau comment 2 baris ini:

```java
// centerPanel.add(Box.createVerticalStrut(15));
// centerPanel.add(createContactCard("🐙", "GitHub", "github.com/yourusername", "https://github.com/yourusername"));
```

---

## 🔍 Format Link yang Benar

### Email
```java
"mailto:email@example.com"
```

### WhatsApp
```java
"https://wa.me/6281234567890"  // Tanpa +, tanpa spasi, tanpa tanda hubung
```

### LinkedIn
```java
"https://linkedin.com/in/username"
```

### GitHub
```java
"https://github.com/username"
```

### Telegram
```java
"https://t.me/username"
```

### Instagram
```java
"https://instagram.com/username"
```

### Twitter/X
```java
"https://twitter.com/username"
```

### Website
```java
"https://www.yourwebsite.com"
```

---

## 💡 Tips Format Nomor WhatsApp

### Nomor Indonesia:
```
Nomor HP: 0821-9876-5432
Format WA: 6282198765432

Cara konversi:
1. Hilangkan angka 0 di depan: 821-9876-5432
2. Tambahkan kode negara 62: 6282198765432
3. Hilangkan semua tanda hubung/spasi
```

### Contoh Negara Lain:

**Malaysia (+60):**
```
Nomor: 012-345-6789
Format: 60123456789
```

**Singapura (+65):**
```
Nomor: 9123-4567
Format: 6591234567
```

**Amerika (+1):**
```
Nomor: (555) 123-4567
Format: 15551234567
```

---

## 🧪 Testing

### 1. Compile Project
```bash
# Jika menggunakan Maven
mvn clean compile

# Jika menggunakan IDE
Build > Rebuild Project
```

### 2. Jalankan Aplikasi
```bash
Run Main.java
```

### 3. Test Kontak
1. Login sebagai demo (username: `demo`, password: `demo123`)
2. Klik menu "📞 Hubungi Developer"
3. Klik button "Buka" pada setiap kontak
4. Pastikan link terbuka dengan benar

---

## ❌ Kesalahan Umum

### 1. Link WhatsApp Tidak Terbuka

**Salah:**
```java
"https://wa.me/+62-821-9876-5432"  // ❌ Ada + dan tanda hubung
```

**Benar:**
```java
"https://wa.me/6282198765432"      // ✅ Tanpa + dan tanda hubung
```

### 2. Email Tidak Terbuka

**Salah:**
```java
"email@example.com"                // ❌ Tanpa mailto:
```

**Benar:**
```java
"mailto:email@example.com"         // ✅ Dengan mailto:
```

### 3. Tampilan dan Link Tidak Sama

**Salah:**
```java
createContactCard("📱", "WhatsApp", 
    "+62 821-9876-5432",           // Tampilan
    "https://wa.me/+62-821-9876-5432")  // ❌ Link salah format
```

**Benar:**
```java
createContactCard("📱", "WhatsApp", 
    "+62 821-9876-5432",           // Tampilan (boleh pakai tanda hubung)
    "https://wa.me/6282198765432") // ✅ Link tanpa tanda hubung
```

---

## 📊 Checklist Setelah Ubah

- [ ] Email sudah diubah
- [ ] Nomor WhatsApp sudah diubah (format benar)
- [ ] LinkedIn username sudah diubah
- [ ] GitHub username sudah diubah
- [ ] Project berhasil di-compile
- [ ] Aplikasi berjalan tanpa error
- [ ] Login demo berhasil
- [ ] Menu "Hubungi Developer" muncul
- [ ] Semua link bisa dibuka
- [ ] Email client terbuka saat klik Email
- [ ] WhatsApp Web terbuka saat klik WhatsApp
- [ ] LinkedIn terbuka saat klik LinkedIn
- [ ] GitHub terbuka saat klik GitHub

---

## 🎯 Contoh Kasus Nyata

### Contoh 1: Developer Freelance

```java
centerPanel.add(createContactCard("📧", "Email", 
    "freelance.dev@gmail.com", 
    "mailto:freelance.dev@gmail.com"));

centerPanel.add(createContactCard("📱", "WhatsApp", 
    "+62 812-3456-7890", 
    "https://wa.me/6281234567890"));

centerPanel.add(createContactCard("💼", "LinkedIn", 
    "linkedin.com/in/freelancedev", 
    "https://linkedin.com/in/freelancedev"));

centerPanel.add(createContactCard("🌐", "Portfolio", 
    "www.freelancedev.com", 
    "https://www.freelancedev.com"));
```

### Contoh 2: Mahasiswa (Tugas Akhir)

```java
centerPanel.add(createContactCard("📧", "Email", 
    "mahasiswa@student.ac.id", 
    "mailto:mahasiswa@student.ac.id"));

centerPanel.add(createContactCard("📱", "WhatsApp", 
    "+62 856-7890-1234", 
    "https://wa.me/6285678901234"));

centerPanel.add(createContactCard("🐙", "GitHub", 
    "github.com/mahasiswa123", 
    "https://github.com/mahasiswa123"));

centerPanel.add(createContactCard("📄", "LinkedIn", 
    "linkedin.com/in/mahasiswa", 
    "https://linkedin.com/in/mahasiswa"));
```

### Contoh 3: Software House

```java
centerPanel.add(createContactCard("📧", "Email Bisnis", 
    "info@softwarehouse.com", 
    "mailto:info@softwarehouse.com"));

centerPanel.add(createContactCard("📱", "WhatsApp Bisnis", 
    "+62 21-1234-5678", 
    "https://wa.me/622112345678"));

centerPanel.add(createContactCard("🌐", "Website", 
    "www.softwarehouse.com", 
    "https://www.softwarehouse.com"));

centerPanel.add(createContactCard("📍", "Alamat", 
    "Jakarta, Indonesia", 
    "https://maps.google.com/?q=Jakarta"));
```

---

## 🔄 Rollback (Kembalikan ke Default)

Jika ingin kembali ke kontak default:

```java
centerPanel.add(createContactCard("📧", "Email", "your.email@example.com", "mailto:your.email@example.com"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("📱", "WhatsApp", "+62 812-3456-7890", "https://wa.me/6281234567890"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("💼", "LinkedIn", "linkedin.com/in/yourprofile", "https://linkedin.com/in/yourprofile"));
centerPanel.add(Box.createVerticalStrut(15));
centerPanel.add(createContactCard("🐙", "GitHub", "github.com/yourusername", "https://github.com/yourusername"));
```

---

## 📞 Bantuan

Jika masih bingung atau ada error:
1. Pastikan format link sudah benar
2. Check typo di email/username
3. Test link di browser dulu sebelum masukkan ke kode
4. Compile ulang setelah ubah kode

---

**Selamat mencoba! 🚀**
