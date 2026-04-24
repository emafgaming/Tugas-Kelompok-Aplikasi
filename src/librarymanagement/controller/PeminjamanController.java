package librarymanagement.controller;

import librarymanagement.model.AnggotaModel;
import librarymanagement.model.BukuModel;
import librarymanagement.model.PeminjamanModel;
import librarymanagement.view.FormPeminjaman;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * PeminjamanController - Controller untuk manajemen peminjaman buku
 * Menangani peminjaman, pengembalian, denda, dan laporan
 */
public class PeminjamanController {
    
    private FormPeminjaman view;
    private PeminjamanModel model;
    private AnggotaModel anggotaModel;
    private BukuModel bukuModel;
    
    public PeminjamanController(FormPeminjaman view) {
        this.view         = view;
        this.model        = new PeminjamanModel();
        this.anggotaModel = new AnggotaModel();
        this.bukuModel    = new BukuModel();
        
        loadData();
        loadAnggotaCombo();
        loadBukuCombo();
    }
    
    /**
     * Load semua data peminjaman ke tabel
     */
    public void loadData() {
        List<PeminjamanModel> list = model.getAllPeminjaman();
        updateTable(list);
    }
    
    /**
     * Load hanya peminjaman aktif
     */
    public void loadPeminjamanAktif() {
        List<PeminjamanModel> list = model.getPeminjamanAktif();
        updateTable(list);
    }
    
    /**
     * Load anggota ke ComboBox
     */
    public void loadAnggotaCombo() {
        List<AnggotaModel> list = anggotaModel.getAnggotaAktif();
        view.loadAnggotaCombo(list);
    }
    
    /**
     * Load buku ke ComboBox
     */
    public void loadBukuCombo() {
        List<BukuModel> list = bukuModel.getAllBuku();
        view.loadBukuCombo(list);
    }
    
    /**
     * Tampilkan info stok buku yang dipilih
     */
    public void tampilkanInfoBuku(int idBuku) {
        if (idBuku <= 0) return;
        int stok = bukuModel.getStokBuku(idBuku);
        view.setInfoStok("Stok tersedia: " + stok);
    }
    
    /**
     * Proses peminjaman buku
     */
    public void pinjam() {
        // Validasi
        if (view.getSelectedAnggotaId() <= 0) {
            JOptionPane.showMessageDialog(view, "Pilih anggota!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (view.getSelectedBukuId() <= 0) {
            JOptionPane.showMessageDialog(view, "Pilih buku!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (view.getTanggalPinjam().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tanggal pinjam tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (view.getTanggalKembali().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tanggal kembali tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validasi jumlah
        int jumlah;
        try {
            jumlah = Integer.parseInt(view.getJumlah().trim());
            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(view, "Jumlah harus lebih dari 0!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Jumlah harus berupa angka!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Cek stok buku
        int idBuku = view.getSelectedBukuId();
        int stok   = bukuModel.getStokBuku(idBuku);
        if (stok < jumlah) {
            JOptionPane.showMessageDialog(view, 
                "Stok buku tidak cukup!\nStok tersedia: " + stok, 
                "Stok Tidak Cukup", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Buat object peminjaman
        PeminjamanModel peminjaman = new PeminjamanModel();
        peminjaman.setIdAnggota(view.getSelectedAnggotaId());
        peminjaman.setTanggalPinjam(view.getTanggalPinjam().trim());
        peminjaman.setTanggalKembali(view.getTanggalKembali().trim());
        
        // Konfirmasi
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Konfirmasi peminjaman buku?\n" +
            "Jumlah: " + jumlah + "\n" +
            "Stok setelah peminjaman: " + (stok - jumlah),
            "Konfirmasi Peminjaman", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        // Proses peminjaman
        if (model.addPeminjaman(peminjaman, idBuku, jumlah)) {
            JOptionPane.showMessageDialog(view, 
                "Peminjaman berhasil dicatat!\nStok buku telah dikurangi.", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
            loadBukuCombo(); // Refresh combo untuk update stok
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal mencatat peminjaman!\nStok buku tidak mencukupi.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Proses pengembalian buku
     */
    public void kembalikan(int idPinjam) {
        if (idPinjam <= 0) {
            JOptionPane.showMessageDialog(view, 
                "Pilih data peminjaman yang akan dikembalikan!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Cek status peminjaman
        PeminjamanModel pinjam = model.getPeminjamanById(idPinjam);
        if (pinjam == null) {
            JOptionPane.showMessageDialog(view, "Data peminjaman tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if ("Kembali".equals(pinjam.getStatus())) {
            JOptionPane.showMessageDialog(view, "Buku sudah dikembalikan!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String tanggalHariIni = PeminjamanModel.getTanggalHariIni();
        
        // Hitung preview denda
        double dendaPreview = PeminjamanModel.hitungDenda(pinjam.getTanggalKembali(), tanggalHariIni);
        
        String pesanKonfirmasi = "Konfirmasi pengembalian buku?\n" +
                                 "Anggota: " + pinjam.getNamaAnggota() + "\n" +
                                 "Batas kembali: " + pinjam.getTanggalKembali() + "\n" +
                                 "Tanggal kembali: " + tanggalHariIni + "\n" +
                                 (dendaPreview > 0 ? "DENDA: " + formatRupiah(dendaPreview) : "Tepat waktu - Tidak ada denda");
        
        int confirm = JOptionPane.showConfirmDialog(view, pesanKonfirmasi, 
            "Konfirmasi Pengembalian", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        // Proses pengembalian
        double denda = model.prosesPengembalian(idPinjam, tanggalHariIni);
        
        if (denda >= 0) {
            String pesan = "Pengembalian berhasil!\n";
            if (denda > 0) {
                pesan += "Denda keterlambatan: " + formatRupiah(denda);
            } else {
                pesan += "Buku dikembalikan tepat waktu.";
            }
            JOptionPane.showMessageDialog(view, pesan, "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            loadBukuCombo();
        } else {
            JOptionPane.showMessageDialog(view, "Gagal memproses pengembalian!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Search peminjaman
     */
    public void search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            loadData();
            return;
        }
        List<PeminjamanModel> list = model.searchPeminjaman(keyword.trim());
        updateTable(list);
    }
    
    /**
     * Proses lapor buku hilang
     */
    public void laporHilang(int idPinjam) {
        if (idPinjam <= 0) {
            JOptionPane.showMessageDialog(view, 
                "Pilih data peminjaman yang akan dilaporkan hilang!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        PeminjamanModel pinjam = model.getPeminjamanById(idPinjam);
        if (pinjam == null) {
            JOptionPane.showMessageDialog(view, "Data peminjaman tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!"Dipinjam".equals(pinjam.getStatus())) {
            JOptionPane.showMessageDialog(view, 
                "Hanya peminjaman berstatus 'Dipinjam' yang bisa dilaporkan hilang!\nStatus saat ini: " + pinjam.getStatus(), 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view,
            "⚠️ LAPOR BUKU HILANG\n\n" +
            "Anggota: " + pinjam.getNamaAnggota() + "\n" +
            "Denda per buku hilang: " + formatRupiah(PeminjamanModel.DENDA_BUKU_HILANG) + "\n\n" +
            "Stok buku TIDAK akan dikembalikan.\n" +
            "Apakah Anda yakin?",
            "Konfirmasi Lapor Hilang", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        double dendaHilang = model.prosesLaporHilang(idPinjam);
        
        if (dendaHilang >= 0) {
            JOptionPane.showMessageDialog(view, 
                "Buku berhasil dilaporkan hilang!\n" +
                "Total denda: " + formatRupiah(dendaHilang) + "\n\n" +
                "Catatan: Anggota dapat mengganti buku sebagai pengganti denda.",
                "Lapor Hilang Berhasil", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            loadBukuCombo();
        } else {
            JOptionPane.showMessageDialog(view, "Gagal memproses lapor hilang!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Load laporan ke tabel laporan
     */
    public void loadLaporan() {
        List<Object[]> list = model.getLaporanPeminjaman();
        DefaultTableModel tableModel = view.getLaporanTableModel();
        tableModel.setRowCount(0);
        int rowNum = 1;
        for (Object[] row : list) {
            // Format denda sebagai Rupiah
            double denda = (Double) row[8];
            // Buat row baru dengan No di depan: {No, ID, Nama, Judul, Jml, TglPinjam, TglKembali, TglDikembalikan, Status, Denda}
            tableModel.addRow(new Object[]{
                rowNum++,
                row[0],  // ID (hidden)
                row[1],  // Nama Anggota
                row[2],  // Judul Buku
                row[3],  // Jumlah
                row[4],  // Tgl Pinjam
                row[5],  // Batas Kembali
                row[6],  // Tgl Dikembalikan
                row[7],  // Status
                formatRupiah(denda)
            });
        }
    }
    
    /**
     * Format angka ke format Rupiah
     */
    public static String formatRupiah(double amount) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return nf.format(amount);
    }
    
    /**
     * Update tabel peminjaman
     */
    private void updateTable(List<PeminjamanModel> list) {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);
        int rowNum = 1;
        String hariIni = PeminjamanModel.getTanggalHariIni();
        
        for (PeminjamanModel p : list) {
            // Hitung denda real-time untuk peminjaman aktif yang terlambat
            double dendaTampil = p.getDenda();
            String statusTampil = p.getStatus();
            
            if ("Dipinjam".equals(p.getStatus())) {
                double dendaPreview = PeminjamanModel.hitungDenda(p.getTanggalKembali(), hariIni);
                if (dendaPreview > 0) {
                    dendaTampil = dendaPreview;
                    statusTampil = "Terlambat!";
                }
            }
            
            tableModel.addRow(new Object[]{
                rowNum++,
                p.getIdPinjam(),  // Hidden column for actual ID
                p.getNamaAnggota(),
                p.getTanggalPinjam(),
                p.getTanggalKembali(),
                p.getTanggalDikembalikan() != null ? p.getTanggalDikembalikan() : "-",
                statusTampil,
                formatRupiah(dendaTampil)
            });
        }
    }
}
