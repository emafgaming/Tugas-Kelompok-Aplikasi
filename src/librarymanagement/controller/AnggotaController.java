package librarymanagement.controller;

import librarymanagement.model.AnggotaModel;
import librarymanagement.model.PeminjamanModel;
import librarymanagement.view.FormAnggota;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * AnggotaController - Controller untuk manajemen anggota
 */
public class AnggotaController {
    
    private FormAnggota view;
    private AnggotaModel model;
    
    public AnggotaController(FormAnggota view) {
        this.view  = view;
        this.model = new AnggotaModel();
        loadData();
    }
    
    /**
     * Load semua anggota ke tabel
     */
    public void loadData() {
        List<AnggotaModel> list = model.getAllAnggota();
        updateTable(list);
    }
    
    /**
     * Search anggota
     */
    public void search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            loadData();
            return;
        }
        List<AnggotaModel> list = model.searchAnggota(keyword.trim());
        updateTable(list);
    }
    
    /**
     * Filter berdasarkan status
     */
    public void filterByStatus(String status) {
        if (status == null || status.equals("Semua")) {
            loadData();
            return;
        }
        List<AnggotaModel> list = model.filterByStatus(status);
        updateTable(list);
    }
    
    /**
     * Tambah anggota
     */
    public void tambah() {
        if (!validateInput()) return;
        
        // Validasi duplikasi nama
        if (model.isNamaExists(view.getNama().trim(), 0)) {
            JOptionPane.showMessageDialog(view, 
                "Nama anggota '" + view.getNama().trim() + "' sudah terdaftar!\nGunakan nama yang berbeda.", 
                "Data Duplikat", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        AnggotaModel anggota = getAnggotaFromForm();
        
        if (model.addAnggota(anggota)) {
            JOptionPane.showMessageDialog(view, 
                "Anggota berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal menambahkan anggota!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Update anggota
     */
    public void update(int idAnggota) {
        if (idAnggota <= 0) {
            JOptionPane.showMessageDialog(view, 
                "Pilih anggota yang akan diupdate!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateInput()) return;
        
        // Validasi duplikasi nama (kecuali nama milik sendiri)
        if (model.isNamaExists(view.getNama().trim(), idAnggota)) {
            JOptionPane.showMessageDialog(view, 
                "Nama anggota '" + view.getNama().trim() + "' sudah digunakan anggota lain!", 
                "Data Duplikat", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        AnggotaModel anggota = getAnggotaFromForm();
        anggota.setIdAnggota(idAnggota);
        
        if (model.updateAnggota(anggota)) {
            JOptionPane.showMessageDialog(view, 
                "Anggota berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal mengupdate anggota!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Hapus anggota
     */
    public void hapus(int idAnggota) {
        if (idAnggota <= 0) {
            JOptionPane.showMessageDialog(view, 
                "Pilih anggota yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Cek apakah anggota masih memiliki peminjaman aktif
        PeminjamanModel peminjamanModel = new PeminjamanModel();
        if (peminjamanModel.hasPeminjamanAktif(idAnggota)) {
            JOptionPane.showMessageDialog(view, 
                "Tidak dapat menghapus anggota!\n\n" +
                "Anggota ini masih memiliki buku yang belum dikembalikan.\n" +
                "Harap selesaikan semua peminjaman terlebih dahulu.", 
                "Gagal Menghapus", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Apakah Anda yakin ingin menghapus anggota ini?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        if (model.deleteAnggota(idAnggota)) {
            JOptionPane.showMessageDialog(view, 
                "Anggota berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal menghapus anggota!\nAnggota masih memiliki data peminjaman.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Validasi input
     */
    private boolean validateInput() {
        if (view.getNama().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getAlamat().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Alamat tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getNoHp().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "No. HP tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // Validasi nomor HP hanya angka
        String noHp = view.getNoHp().trim();
        if (!noHp.matches("^[0-9+]{8,15}$")) {
            JOptionPane.showMessageDialog(view, "Format No. HP tidak valid! (8-15 digit angka)", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * Ambil data dari form
     */
    private AnggotaModel getAnggotaFromForm() {
        AnggotaModel anggota = new AnggotaModel();
        anggota.setNama(view.getNama().trim());
        anggota.setAlamat(view.getAlamat().trim());
        anggota.setNoHp(view.getNoHp().trim());
        anggota.setStatus(view.getStatus());
        return anggota;
    }
    
    /**
     * Update tabel
     */
    private void updateTable(List<AnggotaModel> list) {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);
        int rowNum = 1;
        for (AnggotaModel a : list) {
            tableModel.addRow(new Object[]{
                rowNum++,
                a.getIdAnggota(),  // Hidden column for actual ID
                a.getNama(),
                a.getAlamat(),
                a.getNoHp(),
                a.getStatus()
            });
        }
    }
}
