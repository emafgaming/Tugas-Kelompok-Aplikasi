# UI/UX Redesign Reference

## Sistem Manajemen Perpustakaan

Dokumen ini adalah blueprint redesign total untuk aplikasi desktop "Sistem Manajemen Perpustakaan" berbasis Java Swing. Rekomendasi disusun berdasarkan struktur aplikasi yang sudah ada pada modul:

- `Dashboard`
- `FormBuku`
- `FormKategori`
- `FormAnggota`
- `FormPeminjaman`
- `PanelLaporan`
- `ToastUtil`
- `LoadingUtil`

Tujuan redesign:

- mengurangi kesan form-heavy dan table-heavy
- membuat aplikasi terasa modern, rapi, profesional, dan nyaman dipakai
- mempermudah fokus user saat input data dan transaksi
- menjaga implementasi tetap realistis untuk Java Swing

---

## 1. Arah Visual

### Konsep desain

Gunakan arah visual "Modern Library Workspace":

- bersih, terang, dan tenang
- terasa profesional seperti aplikasi admin modern
- tidak terlalu kaku seperti aplikasi desktop lama
- memakai aksen warna hangat agar identitas perpustakaan terasa lebih hidup

Karakter visual:

- dominan background terang
- panel putih dengan sudut membulat
- sidebar gelap agar navigasi stabil dan mudah dikenali
- aksen amber untuk memberi nuansa akademik dan hangat
- komponen ringkas, tidak terlalu padat

### Prinsip utama

- satu halaman hanya punya satu fokus utama
- form tidak selalu tampil penuh di layar
- tabel dipakai untuk scan cepat, card dipakai untuk detail dan aksi
- informasi penting seperti stok, keterlambatan, dan status harus langsung terbaca lewat warna dan badge

---

## 2. Struktur Layout

## 2.1 Shell aplikasi

Layout utama dibagi menjadi 3 area:

1. Sidebar kiri
2. Header/topbar atas
3. Content area utama

Ukuran yang disarankan:

| Area | Ukuran |
|------|--------|
| Sidebar | 248 px |
| Topbar | 72 px |
| Content margin | 24 px |
| Jarak antar section | 24 px |

### Sidebar

Posisi:

- menempel di sisi kiri
- full height
- fixed, tidak ikut scroll bersama konten

Fungsi:

- navigasi utama
- penanda lokasi halaman aktif
- akses cepat ke laporan, kartu anggota, dan logout
- menampilkan profil user dan role badge

Struktur sidebar:

1. Logo + nama aplikasi
2. Profil singkat user
3. Grup menu utama
4. Grup menu laporan/utility
5. Tombol logout di bagian bawah

Urutan menu yang disarankan:

- Dashboard
- Buku
- Kategori
- Anggota
- Transaksi
- Laporan
- Kartu Anggota
- Pengaturan atau Bantuan
- Logout

Catatan implementasi:

- file `Dashboard.java` saat ini sudah memakai sidebar, jadi fokus redesign adalah memperhalus styling, spacing, dan state aktif
- gunakan icon satu gaya, jangan campur icon emoji dan icon visual lain pada final production

### Header / Topbar

Posisi:

- berada di atas content area
- fixed di area kanan

Fungsi:

- menampilkan judul halaman dan breadcrumb
- global search
- quick action
- profile menu
- optional date / shift status

Struktur topbar:

- kiri: judul halaman + subjudul singkat
- tengah: search field global
- kanan: tombol notifikasi, avatar, nama user, role, dropdown profile

Komponen topbar:

- search bar dengan placeholder seperti `Cari buku, anggota, transaksi...`
- tombol quick add
- profile chip

### Content Area

Fungsi:

- menampung konten dinamis dari setiap halaman
- jadi area scroll utama

Struktur content area:

1. Page intro
2. Stats / quick actions
3. Filter bar
4. Main data section
5. Detail / insight / activity

Aturan layout:

- jangan tampilkan form panjang langsung di samping tabel jika bukan sedang input
- untuk data management, pakai pola browse dulu lalu detail/edit
- untuk transaksi, buat proses terasa bertahap dan jelas

---

## 3. Struktur Layout Per Halaman

## 3.1 Dashboard

### Tujuan

Dashboard harus terasa seperti pusat kendali, bukan hanya halaman sambutan.

### Susunan yang disarankan

1. Welcome hero
2. Stat cards
3. Insight row
4. Activity and alert row

### Struktur detail

#### A. Welcome hero

Posisi paling atas, full width.

Isi:

- sapaan personal ke user
- tanggal hari ini
- 2 quick action
- ringkasan singkat seperti `12 buku hampir habis` atau `5 transaksi harus diproses`

Contoh isi:

- `Selamat datang, Admin`
- `Pantau koleksi, anggota, dan transaksi perpustakaan dari satu tempat`
- tombol `Tambah Buku`
- tombol `Buat Transaksi`

Visual:

- card besar dengan gradient lembut navy ke teal
- teks putih
- ilustrasi abstrak buku di sisi kanan

#### B. Stat cards

Tampilkan 4 kartu utama:

- Total Buku
- Buku Dipinjam
- Anggota Aktif
- Keterlambatan Hari Ini

Setiap card berisi:

- label
- angka utama
- delta kecil atau context
- icon lingkaran di kanan atas

#### C. Insight row

Buat 2 panel sejajar:

- kiri: grafik tren peminjaman 7 atau 30 hari
- kanan: status koleksi

Status koleksi bisa berisi:

- stok hampir habis
- kategori paling populer
- buku paling sering dipinjam

#### D. Activity and alert row

Buat 2 panel:

- aktivitas terbaru
- daftar aksi prioritas

Aktivitas terbaru:

- anggota meminjam buku
- pengembalian selesai
- buku baru ditambahkan

Aksi prioritas:

- transaksi terlambat
- stok buku 0
- anggota nonaktif yang masih punya pinjaman

### Prioritas visual dashboard

1. hero dan quick action
2. stat cards
3. alert penting
4. grafik dan aktivitas

---

## 3.2 Halaman Data Buku

### Tujuan

Halaman buku harus memudahkan 3 hal:

- mencari buku dengan cepat
- memeriksa detail buku tanpa membuka form penuh
- menambah atau edit data dengan fokus

### Pola utama

Gunakan `table + detail card hybrid`, bukan form permanen di sisi kiri.

### Struktur yang disarankan

1. Page header
2. Summary cards mini
3. Toolbar filter dan search
4. Area data 2 kolom

### Detail layout

#### A. Header

Isi:

- judul `Data Buku`
- subjudul singkat
- tombol `Tambah Buku`
- tombol `Import` opsional

#### B. Summary cards mini

Tiga mini card horizontal:

- Total Judul
- Stok Menipis
- Kategori Terbanyak

Card ini kecil, ringkas, dan membantu konteks sebelum user masuk ke tabel.

#### C. Toolbar

Komponen:

- search field besar
- dropdown kategori
- filter stok
- sort dropdown
- toggle view `Table / Grid`

#### D. Main section hybrid

Bagi area utama menjadi 2 kolom:

- kiri 65%: tabel daftar buku
- kanan 35%: detail card buku terpilih

##### Panel kiri: tabel

Isi kolom yang disarankan:

- Judul
- Penulis
- Kategori
- Tahun
- Stok
- Status
- Aksi singkat

Tabel modern:

- tinggi row 44-48 px
- header soft gray
- zebra row sangat tipis
- hover row dengan highlight lembut
- kolom aksi kecil berisi icon view/edit

##### Panel kanan: detail card

Saat user klik satu buku, tampil:

- cover placeholder
- judul buku
- penulis
- penerbit
- kategori
- stok
- status badge
- histori singkat peminjaman
- tombol `Edit`
- tombol `Hapus`

Jika belum ada buku dipilih:

- tampilkan empty state yang mendorong user memilih data

### Mode tambah dan edit

Untuk input, gunakan salah satu dari dua pola berikut:

1. Modal besar terpusat
2. Side panel / drawer dari kanan

Rekomendasi:

- tambah data: modal besar
- edit data: drawer kanan

Alasannya:

- user tetap melihat konteks daftar buku
- form terasa fokus, tidak menyesaki halaman utama

### Status visual stok

Gunakan badge:

- `Tersedia` untuk stok > 10
- `Menipis` untuk stok 1-10
- `Habis` untuk stok 0

Warna:

- hijau
- amber
- merah

---

## 3.3 Halaman Transaksi

### Tujuan

Halaman transaksi harus mengurangi rasa "rumit" saat proses pinjam dan kembali. User harus tahu langkah yang sedang dijalankan, status transaksi, dan dampaknya ke stok.

### Struktur yang disarankan

Alih-alih terasa seperti kumpulan tab biasa, buat halaman transaksi menjadi 3 section utama:

1. Ringkasan transaksi
2. Workspace peminjaman
3. Daftar transaksi aktif dan riwayat

### A. Ringkasan transaksi

Empat stat card:

- Dipinjam Hari Ini
- Pengembalian Hari Ini
- Terlambat
- Total Denda Berjalan

### B. Workspace peminjaman

Gunakan pola step-based form satu panel:

1. Pilih anggota
2. Pilih buku
3. Tentukan jumlah dan tanggal
4. Review
5. Konfirmasi pinjam

#### Susunan panel peminjaman

Bagian kiri:

- form transaksi

Bagian kanan:

- ringkasan anggota
- detail buku
- stok tersedia
- estimasi tanggal kembali
- alert jika stok rendah atau anggota bermasalah

### C. Daftar transaksi aktif

Gunakan segmented control atau tab modern:

- Aktif
- Terlambat
- Selesai

Setiap baris transaksi menampilkan:

- nama anggota
- buku
- tanggal pinjam
- batas kembali
- status
- denda
- tombol aksi

### D. Pengembalian

Saat user klik `Kembalikan`, munculkan modal konfirmasi yang menampilkan:

- nama anggota
- judul buku
- tanggal pinjam
- batas kembali
- tanggal pengembalian aktual
- jumlah hari terlambat
- total denda

Lalu beri 2 aksi:

- `Konfirmasi Pengembalian`
- `Batal`

### E. Laporan transaksi

Pisahkan dari proses utama. Riwayat panjang tetap ada, tetapi jangan jadi fokus pertama di atas.

---

## 4. Design System

## 4.1 Color Palette

Gunakan palet berikut agar terasa modern, profesional, dan tetap cocok dengan konteks perpustakaan:

| Token | Warna | Fungsi |
|------|------|--------|
| Primary 900 | `#16324F` | sidebar, heading kuat |
| Primary 700 | `#24577A` | button utama, active state |
| Primary 500 | `#3B82A0` | highlight lembut, chart |
| Secondary 100 | `#EAF3F6` | background chip, section tint |
| Accent Amber | `#D9A441` | aksen, warning ringan, badge penting |
| Success | `#2E9E6F` | sukses, stok aman |
| Warning | `#E49B2F` | stok menipis, perhatian |
| Danger | `#D85C4A` | hapus, error, keterlambatan |
| Info | `#4A90C2` | info, helper text |
| Surface | `#FFFFFF` | card, modal, table |
| Background | `#F5F7FA` | background utama |
| Border | `#DCE3EA` | border komponen |
| Text Primary | `#1E293B` | teks utama |
| Text Secondary | `#64748B` | teks pendukung |

### Aturan penggunaan warna

- background utama jangan putih polos total, gunakan `#F5F7FA`
- card utama putih agar isi data kontras
- warna status selalu konsisten di semua halaman
- hindari terlalu banyak warna solid kuat dalam satu layar

## 4.2 Typography

### Font utama

Rekomendasi:

- `Plus Jakarta Sans`

Fallback:

- `Segoe UI`

Alasan:

- modern
- bersih
- nyaman untuk dashboard dan tabel
- tetap cocok untuk aplikasi desktop profesional

### Skala ukuran

| Elemen | Ukuran | Weight |
|--------|--------|--------|
| Page title | 28 px | Bold |
| Section title | 20 px | SemiBold |
| Card title | 16 px | SemiBold |
| Body default | 14 px | Regular |
| Small text | 12 px | Regular |
| Caption | 11 px | Medium |
| Stat number | 28-32 px | Bold |
| Button label | 13-14 px | SemiBold |

### Line height

- heading: 120% sampai 130%
- body: 150%
- table cell: minimum 44 px row height

## 4.3 Radius

Gunakan radius konsisten:

| Komponen | Radius |
|----------|--------|
| Card utama | 18 px |
| Card kecil | 14 px |
| Input | 12 px |
| Button | 12 px |
| Modal | 20 px |
| Badge | 999 px |

## 4.4 Shadow

Gunakan shadow halus:

- card default: `0 8 24 rgba(15, 23, 42, 0.06)`
- card hover: `0 12 30 rgba(15, 23, 42, 0.10)`
- modal: `0 24 60 rgba(15, 23, 42, 0.18)`

Dalam Swing, shadow dapat disimulasikan dengan:

- border custom paint
- layer panel
- outer shadow lembut 2-3 tingkat opacity

---

## 5. Komponen UI

## 5.1 Button

### Varian

#### Primary button

Dipakai untuk aksi utama:

- Tambah Buku
- Simpan
- Pinjam Sekarang
- Konfirmasi Pengembalian

Spec:

- tinggi 40 px
- padding horizontal 16-20 px
- background `Primary 700`
- text putih
- radius 12 px

Hover:

- warna sedikit lebih terang
- naik 1-2 px secara visual

#### Secondary button

Dipakai untuk aksi pendamping:

- Batal
- Refresh
- Lihat Detail

Spec:

- background putih
- border `Border`
- text `Text Primary`

#### Danger button

Dipakai untuk:

- Hapus
- Reset transaksi

Spec:

- background `Danger`
- text putih

#### Ghost / icon button

Dipakai untuk:

- search clear
- filter
- detail row action

### State

- default
- hover
- pressed
- disabled
- loading

Disabled state:

- opacity turun
- cursor normal
- tooltip menjelaskan alasan

## 5.2 Card

### Tipe card

1. Stat card
2. Detail card
3. Action card
4. Alert card

### Struktur card

- title
- value atau isi utama
- supporting text
- optional icon atau action

Padding:

- 20 px untuk card biasa
- 16 px untuk mini card

## 5.3 Table

### Style

- header background abu terang
- text header semi bold
- row height 46 px
- border internal minimal
- fokus pada readability, bukan grid tebal

### Behavior

- hover row highlight
- selected row dengan background soft blue
- sticky header jika memungkinkan
- aksi per row berupa icon atau kebab menu

### Badge di tabel

Gunakan badge untuk:

- status buku
- status peminjaman
- status anggota

Badge harus ringkas, rounded, dan punya padding kecil.

## 5.4 Modal

### Fungsi

Dipakai untuk:

- tambah data
- konfirmasi hapus
- konfirmasi pengembalian
- preview detail

### Struktur modal

- title
- subtitle optional
- isi
- footer action

Ukuran:

- small: 420 px
- medium: 640 px
- large: 860 px

Modal konfirmasi wajib ringkas dan jelas.

---

## 6. Visual Hierarchy dan Spacing

## 6.1 Prioritas elemen

Setiap halaman harus mengikuti urutan visual:

1. Judul halaman
2. Aksi utama
3. Search / filter
4. Konten inti
5. Detail sekunder
6. Aksi destruktif

Jangan menaruh terlalu banyak tombol primer dalam satu area.

## 6.2 Sistem spacing

Gunakan grid berbasis 8 px:

| Token | Ukuran |
|------|--------|
| xs | 4 px |
| sm | 8 px |
| md | 12 px |
| lg | 16 px |
| xl | 24 px |
| 2xl | 32 px |
| 3xl | 40 px |

Aturan praktik:

- jarak antar field form: 12 px
- jarak antar group form: 20 px
- padding panel: 20-24 px
- jarak antar section besar: 24-32 px

## 6.3 Kepadatan tampilan

Target redesign:

- lebih banyak white space
- lebih sedikit border keras
- lebih banyak grouping melalui card dan section

Hindari:

- semua elemen menempel
- form terlalu panjang tanpa pemisah
- tabel memenuhi layar tanpa ringkasan

---

## 7. Modern Look and Feel

## 7.1 Rounded corner

Gunakan rounded corner secara konsisten di:

- card
- input
- button
- modal
- badge
- search field

## 7.2 Hover effect

Hover harus terasa ringan, bukan ramai:

- button: warna naik sedikit
- card: shadow naik tipis
- row table: background berubah tipis
- menu sidebar: background aktif dengan strip kiri atau pill highlight

## 7.3 Transition

Durasi transition:

- 120 ms sampai 180 ms

Gunakan untuk:

- hover button
- card selection
- toast muncul
- drawer buka/tutup

## 7.4 Empty state

Setiap halaman utama wajib punya empty state:

- belum ada data buku
- pencarian tidak menemukan hasil
- belum ada transaksi aktif
- belum memilih item pada detail panel

Struktur empty state:

- icon sederhana
- pesan utama
- teks bantuan
- tombol aksi

---

## 8. UX Improvement Wajib

## 8.1 Notifikasi

Aplikasi sudah memiliki `ToastUtil`, jadi ini bisa dipertahankan dan dipoles.

Gunakan 4 tipe notifikasi:

- success
- error
- warning
- info

Aturan:

- sukses: singkat dan meyakinkan
- error: spesifik, jelaskan masalah
- warning: beri solusi atau langkah lanjut
- info: untuk proses non-kritis

Contoh:

- `Buku berhasil ditambahkan`
- `Stok buku tidak mencukupi`
- `Pengembalian berhasil, denda Rp3.000`

## 8.2 Loading state

`LoadingUtil` sudah ada, tetapi UX bisa ditingkatkan:

- loading overlay untuk proses simpan atau transaksi
- skeleton ringan untuk dashboard dan tabel jika data sedang diambil
- tombol yang sedang loading berubah label menjadi `Menyimpan...`

## 8.3 Konfirmasi aksi

Wajib ada konfirmasi untuk:

- hapus data
- edit data penting
- pinjam buku
- pengembalian
- aksi yang mengubah stok

Struktur modal konfirmasi:

- apa aksi yang dilakukan
- objek yang terdampak
- dampak setelah aksi
- opsi lanjut atau batal

## 8.4 Validasi form

Validasi jangan hanya muncul setelah submit.

Pakai:

- inline validation di bawah field
- warna border berbeda saat error
- helper text singkat

Contoh:

- `Tahun harus 4 digit`
- `Jumlah pinjam melebihi stok tersedia`
- `Nama anggota wajib diisi`

---

## 9. UX Flow

## 9.1 Flow Tambah Data

Contoh untuk Buku / Anggota / Kategori.

### Alur ideal

1. User klik tombol `Tambah`
2. Muncul modal atau drawer
3. User isi field wajib lebih dulu
4. Validasi real-time membantu selama pengisian
5. Tombol `Simpan` aktif jika data valid
6. Saat submit, tampil loading state
7. Setelah sukses:
   - modal tertutup
   - toast sukses muncul
   - data baru otomatis terseleksi di daftar
8. Fokus kembali ke daftar data

### Prinsip UX

- jangan reset konteks halaman
- tampilkan hasil aksi dengan jelas
- setelah tambah, user langsung tahu data sudah masuk

## 9.2 Flow Edit Data

### Alur ideal

1. User pilih row pada tabel
2. Detail card di kanan menampilkan informasi lengkap
3. User klik `Edit`
4. Drawer kanan terbuka dengan data terisi otomatis
5. Field yang berubah diberi highlight tipis
6. User klik `Simpan Perubahan`
7. Tampil loading singkat
8. Toast sukses tampil
9. Detail card dan tabel ikut ter-update

### Prinsip UX

- edit harus terasa aman
- user tidak kehilangan konteks daftar
- perubahan terlihat langsung

## 9.3 Flow Proses Peminjaman Buku

### Alur ideal

1. User buka halaman `Transaksi`
2. Di panel peminjaman, user pilih anggota
3. Sistem menampilkan kartu anggota singkat:
   - nama
   - status
   - jumlah pinjaman aktif
   - catatan jika ada tunggakan atau keterlambatan
4. User pilih buku
5. Sistem menampilkan kartu buku:
   - judul
   - kategori
   - stok tersedia
   - status stok
6. User isi jumlah pinjam
7. User pilih tanggal pinjam dan tanggal kembali
8. Sistem menampilkan review transaksi
9. User klik `Pinjam Sekarang`
10. Muncul modal konfirmasi
11. Setelah konfirmasi:
   - tombol loading
   - stok diperbarui
   - toast sukses muncul
   - transaksi masuk ke daftar aktif

### Friction yang harus dikurangi

- jangan biarkan user mengisi form panjang tanpa feedback
- stok tersedia harus langsung terlihat
- denda atau risiko keterlambatan bukan fokus di awal, tapi tetap informatif

## 9.4 Flow Proses Pengembalian

### Alur ideal

1. User buka tab atau segmen `Aktif`
2. User cari anggota atau transaksi
3. User klik row transaksi
4. Panel detail menampilkan:
   - nama anggota
   - buku dipinjam
   - tanggal pinjam
   - batas kembali
   - keterlambatan
   - estimasi denda
5. User klik `Kembalikan`
6. Modal konfirmasi tampil dengan perhitungan final
7. User konfirmasi
8. Sistem memproses:
   - update status
   - update stok
   - hitung denda
9. Toast sukses tampil
10. Data berpindah dari daftar aktif ke selesai

### Prinsip UX

- detail denda harus jelas sebelum final submit
- pengembalian harus terasa final dan aman
- hasil update harus langsung terlihat di daftar

---

## 10. Rekomendasi Implementasi ke Struktur Kode Saat Ini

## 10.1 Dashboard.java

Refactor menjadi shell utama aplikasi:

- sidebar tetap dipertahankan
- tambahkan topbar nyata di area kanan atas
- content page dibungkus dalam panel dengan padding konsisten
- menu aktif menggunakan pill highlight, bukan hanya perubahan warna

## 10.2 FormBuku.java

Saat ini layout masih `form kiri + tabel kanan`.

Redesign:

- ubah jadi `toolbar + tabel kiri + detail card kanan`
- form tambah/edit dipindah ke modal atau drawer
- tambahkan mini summary di bagian atas

## 10.3 FormAnggota.java dan FormKategori.java

Pakai pola yang sama dengan `FormBuku` agar konsisten:

- browse mode
- detail panel
- modal/drawer untuk input

## 10.4 FormPeminjaman.java

Saat ini memakai `JTabbedPane`.

Redesign:

- pertahankan pembagian logika, tetapi tampilkan sebagai segmented tab modern atau tab custom
- tab `Form Peminjaman` diubah jadi workspace step-by-step
- daftar transaksi menonjolkan status dan action
- pengembalian via modal konfirmasi detail

## 10.5 PanelLaporan.java

Tetap relevan, tetapi visual bisa diperbarui:

- stat card lebih ringan dan modern
- chart diletakkan setelah summary
- riwayat dan buku populer dalam panel yang lebih rapi
- tambahkan filter periode

## 10.6 Util baru yang disarankan

Buat utility atau komponen reusable baru:

- `Theme.java` atau `DesignTokens.java`
- `AppSidebar.java`
- `AppTopbar.java`
- `StatCard.java`
- `SectionCard.java`
- `StatusBadge.java`
- `PrimaryButton.java`
- `ConfirmDialog.java`
- `EmptyStatePanel.java`

Tujuannya:

- semua halaman konsisten
- lebih mudah maintenance
- perubahan tema tidak perlu edit banyak file

---

## 11. Wireframe Ringkas

## 11.1 App Shell

```text
+---------------------------------------------------------------+
| Sidebar         | Topbar: Title | Search | Notification | User|
|                 +---------------------------------------------+
| Dashboard       |                                             |
| Buku            | Content Area                                |
| Kategori        |                                             |
| Anggota         |  Page Intro                                 |
| Transaksi       |  Stats                                      |
| Laporan         |  Main Section                               |
| Logout          |                                             |
+---------------------------------------------------------------+
```

## 11.2 Data Buku

```text
+---------------------------------------------------------------+
| Data Buku                         [Tambah Buku]               |
| Mini stats | Mini stats | Mini stats                          |
| Search | Filter Kategori | Filter Stok | Toggle View          |
|-------------------------------+-------------------------------|
| Tabel daftar buku             | Detail buku terpilih          |
|                               | Cover / metadata / status     |
| Row                           | Riwayat singkat               |
| Row                           | [Edit] [Hapus]                |
| Row                           |                               |
+---------------------------------------------------------------+
```

## 11.3 Transaksi

```text
+---------------------------------------------------------------+
| Transaksi                                                     |
| Stat cards                                                    |
|-------------------------------+-------------------------------|
| Form langkah peminjaman       | Ringkasan anggota + buku      |
| Pilih anggota                 | Status anggota                |
| Pilih buku                    | Stok tersedia                 |
| Jumlah & tanggal              | Estimasi kembali              |
| Review                        | Alert                         |
| [Pinjam Sekarang]             |                               |
|-------------------------------+-------------------------------|
| Segmen: Aktif | Terlambat | Selesai                          |
| Tabel transaksi                                               |
+---------------------------------------------------------------+
```

---

## 12. Checklist Hasil Akhir yang Harus Tercapai

- aplikasi terasa modern sejak pertama dibuka
- layout lebih longgar dan tidak melelahkan mata
- user tidak harus melihat form panjang setiap saat
- tabel tetap kuat untuk data, tetapi didampingi card untuk detail
- proses pinjam dan kembali terasa jelas langkah demi langkah
- aksi penting memiliki konfirmasi dan feedback
- semua halaman konsisten secara warna, typography, radius, dan spacing

---

## 13. Prioritas Implementasi Bertahap

Jika ingin dikerjakan bertahap, urutan terbaik:

1. Buat design tokens dan komponen reusable
2. Redesign shell aplikasi: sidebar, topbar, content wrapper
3. Redesign `Dashboard`
4. Redesign `FormBuku`
5. Terapkan pola yang sama ke `FormAnggota` dan `FormKategori`
6. Redesign `FormPeminjaman`
7. Poles `PanelLaporan`
8. Final pass untuk notifikasi, loading, modal, dan empty state

Dengan urutan ini, hasil visual akan cepat terasa berubah meskipun implementasi dilakukan secara bertahap.
