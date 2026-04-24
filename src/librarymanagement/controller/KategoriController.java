package librarymanagement.controller;

import librarymanagement.model.KategoriModel;
import librarymanagement.view.FormKategori;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * KategoriController - Controller untuk manajemen kategori buku
 */
public class KategoriController {
    
    private FormKategori view;
    private KategoriModel model;
    
    public KategoriController(FormKategori view) {
        this.view  = view;
        this.model = new KategoriModel();
        loadData();
    }
    
    /**
     * Load semua data kategori ke tabel
     */
    public void loadData() {
        List<KategoriModel> list = model.getAllKategori();
        updateTable(list);
    }
    
    /**
     * Load data dengan search
     */
    public void search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            loadData();
            return;
        }
        List<KategoriModel> list = model.searchKategori(keyword.trim());
        updateTable(list);
    }
    
    /**
     * Tambah kategori baru
     */
    public void tambah() {
        String namaKategori = view.getNamaKategori().trim();
        
        // Validasi
        if (namaKategori.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Nama kategori tidak boleh kosong!", 
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (model.isNamaKategoriExists(namaKategori)) {
            JOptionPane.showMessageDialog(view, 
                "Nama kategori '" + namaKategori + "' sudah ada!", 
                "Data Duplikat", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        KategoriModel kategori = new KategoriModel();
        kategori.setNamaKategori(namaKategori);
        
        if (model.addKategori(kategori)) {
            JOptionPane.showMessageDialog(view, 
                "Kategori berhasil ditambahkan!", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal menambahkan kategori!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Update kategori
     */
    public void update(int idKategori) {
        if (idKategori <= 0) {
            JOptionPane.showMessageDialog(view, 
                "Pilih kategori yang akan diupdate!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String namaKategori = view.getNamaKategori().trim();
        
        if (namaKategori.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Nama kategori tidak boleh kosong!", 
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        KategoriModel kategori = new KategoriModel();
        kategori.setIdKategori(idKategori);
        kategori.setNamaKategori(namaKategori);
        
        if (model.updateKategori(kategori)) {
            JOptionPane.showMessageDialog(view, 
                "Kategori berhasil diupdate!", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal mengupdate kategori!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Hapus kategori
     */
    public void hapus(int idKategori) {
        if (idKategori <= 0) {
            JOptionPane.showMessageDialog(view, 
                "Pilih kategori yang akan dihapus!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Apakah Anda yakin ingin menghapus kategori ini?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        if (model.deleteKategori(idKategori)) {
            JOptionPane.showMessageDialog(view, 
                "Kategori berhasil dihapus!", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal menghapus kategori!\nKategori masih digunakan oleh buku.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Update tabel dengan list data
     */
    private void updateTable(List<KategoriModel> list) {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);
        int rowNum = 1;
        for (KategoriModel k : list) {
            tableModel.addRow(new Object[]{
                rowNum++,
                k.getIdKategori(),  // Hidden column for actual ID
                k.getNamaKategori()
            });
        }
    }
}
