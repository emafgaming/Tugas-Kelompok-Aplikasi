package librarymanagement.controller;

import librarymanagement.model.AnggotaModel;
import librarymanagement.model.PeminjamanModel;
import librarymanagement.util.AnggotaPhotoUtil;
import librarymanagement.view.FormAnggota;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * AnggotaController - Controller untuk manajemen anggota.
 */
public class AnggotaController {

    private final FormAnggota view;
    private final AnggotaModel model;

    public AnggotaController(FormAnggota view) {
        this.view = view;
        this.model = new AnggotaModel();
        loadData();
    }

    public void loadData() {
        List<AnggotaModel> list = model.getAllAnggota();
        updateTable(list);
    }

    public void search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            loadData();
            return;
        }
        updateTable(model.searchAnggota(keyword.trim()));
    }

    public void filterByStatus(String status) {
        if (status == null || "Semua".equals(status)) {
            loadData();
            return;
        }
        updateTable(model.filterByStatus(status));
    }

    public void pilihAnggota(int idAnggota) {
        AnggotaModel anggota = model.getAnggotaById(idAnggota);
        if (anggota == null) {
            JOptionPane.showMessageDialog(view,
                "Data anggota tidak ditemukan.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view.displayAnggota(anggota);
    }

    public void pilihFotoPdf() {
        File selectedFile = view.chooseFotoPdf();
        if (selectedFile == null) {
            return;
        }

        String validationError = AnggotaPhotoUtil.validatePdfFile(selectedFile);
        if (validationError != null) {
            JOptionPane.showMessageDialog(view, validationError, "Validasi Foto PDF", JOptionPane.WARNING_MESSAGE);
            return;
        }

        view.setSelectedFotoFile(selectedFile);
    }

    public void bukaFotoPdf() {
        String source = view.getFotoPdfSource();
        if (source == null || source.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Belum ada file foto PDF yang dapat dibuka.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!AnggotaPhotoUtil.openPdf(source)) {
            JOptionPane.showMessageDialog(view,
                "File PDF tidak dapat dibuka.\nPeriksa apakah file masih tersedia.",
                "Gagal Membuka PDF", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void tambah() {
        if (!validateInput()) {
            return;
        }

        if (model.isNamaExists(view.getNama().trim(), 0)) {
            JOptionPane.showMessageDialog(view,
                "Nama anggota '" + view.getNama().trim() + "' sudah terdaftar.\nGunakan nama yang berbeda.",
                "Data Duplikat", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AnggotaModel anggota = getAnggotaFromForm();
        anggota.setFotoPdfPath(null);

        if (!model.addAnggota(anggota)) {
            JOptionPane.showMessageDialog(view,
                "Gagal menambahkan anggota.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String photoError = persistSelectedPhoto(anggota.getIdAnggota(), null);
        showSuccessMessage("Anggota berhasil ditambahkan.", photoError);
    }

    public void update(int idAnggota) {
        if (idAnggota <= 0) {
            JOptionPane.showMessageDialog(view,
                "Pilih anggota yang akan diupdate.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateInput()) {
            return;
        }

        if (model.isNamaExists(view.getNama().trim(), idAnggota)) {
            JOptionPane.showMessageDialog(view,
                "Nama anggota '" + view.getNama().trim() + "' sudah digunakan anggota lain.",
                "Data Duplikat", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AnggotaModel existing = model.getAnggotaById(idAnggota);
        if (existing == null) {
            JOptionPane.showMessageDialog(view,
                "Data anggota tidak ditemukan.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AnggotaModel anggota = getAnggotaFromForm();
        anggota.setIdAnggota(idAnggota);
        anggota.setFotoPdfPath(existing.getFotoPdfPath());

        if (!model.updateAnggota(anggota)) {
            JOptionPane.showMessageDialog(view,
                "Gagal mengupdate anggota.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String photoError = persistSelectedPhoto(idAnggota, existing.getFotoPdfPath());
        showSuccessMessage("Anggota berhasil diupdate.", photoError);
    }

    public void hapus(int idAnggota) {
        if (idAnggota <= 0) {
            JOptionPane.showMessageDialog(view,
                "Pilih anggota yang akan dihapus.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PeminjamanModel peminjamanModel = new PeminjamanModel();
        if (peminjamanModel.hasPeminjamanAktif(idAnggota)) {
            JOptionPane.showMessageDialog(view,
                "Tidak dapat menghapus anggota.\n\n" +
                "Anggota ini masih memiliki buku yang belum dikembalikan.\n" +
                "Selesaikan semua peminjaman terlebih dahulu.",
                "Gagal Menghapus", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
            "Apakah Anda yakin ingin menghapus anggota ini?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        if (model.deleteAnggota(idAnggota)) {
            JOptionPane.showMessageDialog(view,
                "Anggota berhasil dihapus.",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.clearForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(view,
                "Gagal menghapus anggota.\nAnggota masih memiliki data peminjaman.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateInput() {
        if (view.getNama().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama tidak boleh kosong.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getAlamat().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Alamat tidak boleh kosong.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getNoHp().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "No. HP tidak boleh kosong.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String noHp = view.getNoHp().trim();
        if (!noHp.matches("^[0-9+]{8,15}$")) {
            JOptionPane.showMessageDialog(view,
                "Format No. HP tidak valid. Gunakan 8-15 digit angka.",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String photoValidationError = AnggotaPhotoUtil.validatePdfFile(view.getSelectedFotoFile());
        if (photoValidationError != null) {
            JOptionPane.showMessageDialog(view, photoValidationError, "Validasi Foto PDF", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private AnggotaModel getAnggotaFromForm() {
        AnggotaModel anggota = new AnggotaModel();
        anggota.setNama(view.getNama().trim());
        anggota.setAlamat(view.getAlamat().trim());
        anggota.setNoHp(view.getNoHp().trim());
        anggota.setFotoPdfPath(view.getCurrentFotoPdfPath());
        anggota.setStatus(view.getStatus());
        return anggota;
    }

    private void updateTable(List<AnggotaModel> list) {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);
        int rowNum = 1;
        for (AnggotaModel anggota : list) {
            tableModel.addRow(new Object[]{
                rowNum++,
                anggota.getIdAnggota(),
                anggota.getNama(),
                anggota.getAlamat(),
                anggota.getNoHp(),
                anggota.getStatus(),
                hasPhoto(anggota.getFotoPdfPath()) ? "Ada" : "-"
            });
        }
    }

    private boolean hasPhoto(String fotoPdfPath) {
        return fotoPdfPath != null && !fotoPdfPath.trim().isEmpty();
    }

    private String persistSelectedPhoto(int idAnggota, String oldPath) {
        File selectedPhoto = view.getSelectedFotoFile();
        if (selectedPhoto == null) {
            return null;
        }

        try {
            String storedPath = AnggotaPhotoUtil.savePdfForAnggota(selectedPhoto, idAnggota);
            if (!model.updateFotoPath(idAnggota, storedPath)) {
                return "Foto PDF gagal disimpan ke database.";
            }

            if (oldPath != null && !oldPath.trim().isEmpty() && !oldPath.equals(storedPath)) {
                AnggotaPhotoUtil.deleteStoredPdf(oldPath);
            }

            view.setStoredFotoPdfPath(storedPath);
            return null;
        } catch (IOException e) {
            return "Data anggota tersimpan, tetapi file PDF gagal disimpan: " + e.getMessage();
        }
    }

    private void showSuccessMessage(String mainMessage, String photoError) {
        if (photoError == null) {
            String storedPath = view.getCurrentFotoPdfPath();
            String message = mainMessage;
            if (storedPath != null && !storedPath.trim().isEmpty()) {
                message += "\nStorage foto: " + AnggotaPhotoUtil.getStorageDescription();
            }
            JOptionPane.showMessageDialog(view,
                message,
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view,
                mainMessage + "\n\nCatatan:\n" + photoError,
                "Sukses Dengan Catatan", JOptionPane.WARNING_MESSAGE);
        }

        view.clearForm();
        loadData();
    }
}
