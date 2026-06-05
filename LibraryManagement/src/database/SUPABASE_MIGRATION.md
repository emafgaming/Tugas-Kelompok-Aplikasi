# Migrasi Database ke Supabase

Panduan ini memindahkan schema SQLite aplikasi perpustakaan ke Supabase PostgreSQL.

## 1. Buat project Supabase

1. Buka Supabase Dashboard.
2. Buat project baru.
3. Masuk ke **SQL Editor**.
4. Jalankan isi file `database/supabase_schema.sql`.

Setelah berhasil, tabel yang dibuat:

- `users`
- `kategori`
- `buku`
- `anggota`
- `peminjaman`
- `detail_peminjaman`

## 2. Cek data

Jalankan query ini di SQL Editor:

```sql
select count(*) as total_buku from public.buku;
select count(*) as total_anggota from public.anggota;
select b.judul, k.nama_kategori
from public.buku b
left join public.kategori k on k.id_kategori = b.id_kategori
order by b.id_buku;
```

## 3. Hubungkan aplikasi Java

Ambil connection string dari Supabase:

**Project Settings > Database > Connection string > JDBC**

Lalu set environment variable di PowerShell:

```powershell
$env:SUPABASE_DB_URL="jdbc:postgresql://db.<PROJECT_REF>.supabase.co:5432/postgres?sslmode=require"
$env:SUPABASE_DB_USER="postgres"
$env:SUPABASE_DB_PASSWORD="<PASSWORD_DATABASE_SUPABASE>"
```

Jalankan aplikasi dari folder Maven:

```powershell
cd LibraryManagement
mvn clean package
mvn exec:java
```

Kalau `SUPABASE_DB_URL` tidak diisi, aplikasi otomatis tetap memakai SQLite lokal.

Koneksi ini dibaca di `DatabaseConnection.java`. Jadi tidak perlu menulis password langsung di source code. Format URL JDBC harus berisi host database saja; username dan password dikirim lewat `SUPABASE_DB_USER` dan `SUPABASE_DB_PASSWORD`.

## Catatan keamanan

`supabase_schema.sql` mengaktifkan Row Level Security pada semua tabel `public`. Ini membuat tabel tidak otomatis terbuka lewat Supabase Data API. Aplikasi Java ini memakai koneksi JDBC langsung, jadi RLS policy tidak dibutuhkan untuk alur tersebut.

Jangan masukkan password database Supabase ke source code atau commit Git. Pakai environment variable seperti contoh di atas.
