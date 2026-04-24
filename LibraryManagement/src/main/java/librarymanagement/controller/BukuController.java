package librarymanagement.controller;

import librarymanagement.model.BukuModel;
import librarymanagement.model.KategoriModel;
import librarymanagement.view.FormBuku;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BukuController {
    
    private FormBuku view;
    private BukuModel model;
    private KategoriModel kategoriModel;
    
    public BukuController(FormBuku view) {
        this.view = view;
        this.model = new BukuModel();
        this.kategoriModel = new KategoriModel();
        loadData();
        loadKategoriCombo();
    }
    
    public void loadData() {
        List<BukuModel> list = model.getAllBuku();
        updateTable(list);
    }
    
    public void loadKategoriCombo() {
        List<KategoriModel> list = kategoriModel.getAllKategori();
        view.loadKategoriCombo(list);
    }
    
    public void search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            loadData();
            return;
        }
        List<BukuModel> list = model.searchBuku(keyword.trim());
        updateTable(list);
    }
    
    public void filterByKategori(int idKategori) {
        if (idKategori <= 0) {
            loadData();
            return;
        }
        List<BukuModel> list = model.filterByKategori(idKategori);
        updateTable(list);
    }
    
    public void tambah() {
        if (!validateInput()) return;
        
        BukuModel buku = getBukuFromForm();
        
        if (model.addBuku(buku)) {
            JOptionPane.showMessageDialog(view, 
                "Buku berhasil ditambahkan!\n\nJudul: " + buku.getJudul() + "\nPenulis: " + buku.getPenulis(),
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal menambahkan buku!\nSilakan coba lagi.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void update(int idBuku) {
        if (idBuku <= 0) {
            JOptionPane.showMessageDialog(view, 
                "Pilih buku yang akan diupdate dari tabel!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateInput()) return;
        
        BukuModel buku = getBukuFromForm();
        buku.setIdBuku(idBuku);
        
        if (model.updateBuku(buku)) {
            JOptionPane.showMessageDialog(view, 
                "Buku berhasil diupdate!\n\nJudul: " + buku.getJudul(),
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal mengupdate buku!\nSilakan coba lagi.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void hapus(int idBuku) {
        if (idBuku <= 0) {
            JOptionPane.showMessageDialog(view, 
                "Pilih buku yang akan dihapus dari tabel!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        BukuModel buku = getBukuById(idBuku);
        if (buku == null) {
            JOptionPane.showMessageDialog(view, 
                "Data buku tidak ditemukan!",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String message = "Apakah Anda yakin ingin menghapus data ini?\n\n" +
                        "Judul: " + buku.getJudul() + "\n" +
                        "Penulis: " + buku.getPenulis() + "\n" +
                        "Stok: " + buku.getStok() + " buku\n\n" +
                        "Data yang sudah dihapus tidak dapat dikembalikan!";
        
        int confirm = JOptionPane.showConfirmDialog(view, message, "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        if (model.deleteBuku(idBuku)) {
            JOptionPane.showMessageDialog(view, 
                "Buku berhasil dihapus!\n\nJudul: " + buku.getJudul(),
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal menghapus buku!\n\nKemungkinan:\n- Buku masih dipinjam\n- Error database",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateInput() {
        if (view.getJudul() == null || view.getJudul().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Judul tidak boleh kosong!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (view.getPenulis() == null || view.getPenulis().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Penulis tidak boleh kosong!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (view.getPenerbit() == null || view.getPenerbit().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Penerbit tidak boleh kosong!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (view.getTahun() == null || view.getTahun().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tahun tidak boleh kosong!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String tahun = view.getTahun().trim();
        if (!tahun.matches("^(19|20)\\d{2}$")) {
            JOptionPane.showMessageDialog(view, "Tahun tidak valid!\nHarus format 4 digit (contoh: 2024)", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (view.getStok() == null || view.getStok().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Stok tidak boleh kosong!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String stok = view.getStok().trim();
        if (!stok.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(view, "Stok harus berisi angka!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int stokNum = Integer.parseInt(stok);
            if (stokNum <= 0) {
                JOptionPane.showMessageDialog(view, "Stok harus lebih dari 0!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Stok tidak valid!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (view.getSelectedKategoriId() <= 0) {
            JOptionPane.showMessageDialog(view, "Pilih kategori buku!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
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
    
    private BukuModel getBukuById(int idBuku) {
        List<BukuModel> list = model.getAllBuku();
        for (BukuModel b : list) {
            if (b.getIdBuku() == idBuku) {
                return b;
            }
        }
        return null;
    }
    
    private void updateTable(List<BukuModel> list) {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);
        int no = 1;
        for (BukuModel b : list) {
            tableModel.addRow(new Object[]{
                no++,              // No urut
                b.getIdBuku(),     // ID Asli (hidden)
                b.getJudul(),
                b.getPenulis(),
                b.getPenerbit(),
                b.getTahun(),
                b.getStok(),       // Stok di kolom 6
                b.getNamaKategori() // Kategori di kolom 7
            });
        }
    }
}
