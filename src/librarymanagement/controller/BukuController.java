package librarymanagement.controller;

import librarymanagement.model.BukuModel;
import librarymanagement.model.KategoriModel;
import librarymanagement.view.FormBuku;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * BukuController - Controller untuk manajemen buku
 */
public class BukuController {
    
    private FormBuku view;
    private BukuModel model;
    private KategoriModel kategoriModel;
    
    public BukuController(FormBuku view) {
        this.view          = view;
        this.model         = new BukuModel();
        this.kategoriModel = new KategoriModel();
        loadData();
        loadKategoriCombo();
    }
    
    /**
     * Load semua buku ke tabel
     */
    public void loadData() {
        List<BukuModel> list = model.getAllBuku();
        updateTable(list);
    }
    
    /**
     * Load kategori ke ComboBox
     */
    public void loadKategoriCombo() {
        List<KategoriModel> list = kategoriModel.getAllKategori();
        view.loadKategoriCombo(list);
    }
    
    /**
     * Search buku
     */
    public void search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            loadData();
            return;
        }
        List<BukuModel> list = model.searchBuku(keyword.trim());
        updateTable(list);
    }
    
    /**
     * Filter berdasarkan kategori
     */
    public void filterByKategori(int idKategori) {
        if (idKategori <= 0) {
            loadData();
            return;
        }
        List<BukuModel> list = model.filterByKategori(idKategori);
        updateTable(list);
    }
    
    /**
     * Tambah buku baru
     */
    public void tambah() {
        if (!validateInput()) return;
        
        // Validasi duplikasi judul buku
        if (model.isJudulExists(view.getJudul().trim(), 0)) {
            JOptionPane.showMessageDialog(view, 
                "Buku dengan judul '" + view.getJudul().trim() + "' sudah ada!\nGunakan judul yang berbeda.", 
                "Data Duplikat", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        BukuModel buku = getBukuFromForm();
        
        if (model.addBuku(buku)) {
            JOptionPane.showMessageDialog(view, 
                "Buku berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal menambahkan buku!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Update buku
     */
    public void update(int idBuku) {
        if (idBuku <= 0) {
            JOptionPane.showMessageDialog(view, 
                "Pilih buku yang akan diupdate!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateInput()) return;
        
        // Validasi duplikasi judul (kecuali buku ini sendiri)
        if (model.isJudulExists(view.getJudul().trim(), idBuku)) {
            JOptionPane.showMessageDialog(view, 
                "Buku dengan judul '" + view.getJudul().trim() + "' sudah ada!", 
                "Data Duplikat", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        BukuModel buku = getBukuFromForm();
        buku.setIdBuku(idBuku);
        
        if (model.updateBuku(buku)) {
            JOptionPane.showMessageDialog(view, 
                "Buku berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal mengupdate buku!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Hapus buku
     */
    public void hapus(int idBuku) {
        if (idBuku <= 0) {
            JOptionPane.showMessageDialog(view, 
                "Pilih buku yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Apakah Anda yakin ingin menghapus buku ini?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        if (model.deleteBuku(idBuku)) {
            JOptionPane.showMessageDialog(view, 
                "Buku berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal menghapus buku!\nBuku masih dipinjam atau error lainnya.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Validasi input form
     */
    private boolean validateInput() {
        if (view.getJudul().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Judul tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getPenulis().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Penulis tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getPenerbit().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Penerbit tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getTahun().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tahun tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            int tahun = Integer.parseInt(view.getTahun().trim());
            if (tahun < 1000 || tahun > 2100) {
                JOptionPane.showMessageDialog(view, "Tahun tidak valid!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Tahun harus berupa angka!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getStok().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Stok tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            int stok = Integer.parseInt(view.getStok().trim());
            if (stok < 0) {
                JOptionPane.showMessageDialog(view, "Stok tidak boleh negatif!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Stok harus berupa angka!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getSelectedKategoriId() <= 0) {
            JOptionPane.showMessageDialog(view, "Pilih kategori!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * Ambil data dari form dan buat BukuModel
     */
    private BukuModel getBukuFromForm() {
        BukuModel buku = new BukuModel();
        buku.setJudul(view.getJudul().trim());
        buku.setPenulis(view.getPenulis().trim());
        buku.setPenerbit(view.getPenerbit().trim());
        buku.setTahun(Integer.parseInt(view.getTahun().trim()));
        buku.setStok(Integer.parseInt(view.getStok().trim()));
        buku.setIdKategori(view.getSelectedKategoriId());
        return buku;
    }
    
    /**
     * Update tabel dengan data
     */
    private void updateTable(List<BukuModel> list) {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);
        int rowNum = 1;
        for (BukuModel b : list) {
            tableModel.addRow(new Object[]{
                rowNum++,
                b.getIdBuku(),  // Hidden column for actual ID
                b.getJudul(),
                b.getPenulis(),
                b.getPenerbit(),
                b.getTahun(),
                b.getStok(),
                b.getNamaKategori()
            });
        }
    }
}
