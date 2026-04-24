# 📚 CONTOH IMPLEMENTASI LENGKAP - SEMUA UTILITY

## 🎯 PANDUAN LENGKAP PENGGUNAAN UTILITY

Dokumen ini berisi contoh lengkap cara menggunakan semua utility yang sudah dibuat.

---

## 1️⃣ VALIDATION UTIL

### Import:
```java
import librarymanagement.util.ValidationUtil;
```

### Contoh Penggunaan di Controller:

```java
public class AnggotaController {
    
    private FormAnggota view;
    private AnggotaModel model;
    
    public void tambah() {
        // 1. Validasi field tidak boleh kosong
        if (!ValidationUtil.isNotEmpty(view.getNama(), "Nama", view)) {
            return;
        }
        
        if (!ValidationUtil.isNotEmpty(view.getAlamat(), "Alamat", view)) {
            return;
        }
        
        // 2. Validasi nomor HP
        if (!ValidationUtil.isValidPhone(view.getNoHp(), view)) {
            return;
        }
        
        // 3. Proses tambah data
        AnggotaModel anggota = new AnggotaModel();
        anggota.setNama(view.getNama());
        anggota.setAlamat(view.getAlamat());
        anggota.setNoHp(view.getNoHp());
        
        if (model.addAnggota(anggota)) {
            // 4. Tampilkan notifikasi sukses
            ValidationUtil.showSuccess(view, 
                "Anggota berhasil ditambahkan!\n\n" +
                "Nama: " + anggota.getNama() + "\n" +
                "No HP: " + anggota.getNoHp());
            view.clearForm();
            loadData();
        } else {
            // 5. Tampilkan notifikasi error
            ValidationUtil.showError(view, 
                "Gagal menambahkan anggota!\nSilakan coba lagi.");
        }
    }
    
    public void hapus(int idAnggota) {
        if (idAnggota <= 0) {
            ValidationUtil.showWarning(view, 
                "Pilih anggota yang akan dihapus dari tabel!");
            return;
        }
        
        // Ambil data untuk konfirmasi
        AnggotaModel anggota = getAnggotaById(idAnggota);
        
        // Konfirmasi hapus dengan detail
        if (!ValidationUtil.confirmDelete(view, 
            "Anggota", 
            "Nama: " + anggota.getNama() + "\n" +
            "No HP: " + anggota.getNoHp())) {
            return;
        }
        
        // Proses hapus
        if (model.deleteAnggota(idAnggota)) {
            ValidationUtil.showSuccess(view, "Anggota berhasil dihapus!");
            view.clearForm();
            loadData();
        } else {
            ValidationUtil.showError(view, "Gagal menghapus anggota!");
        }
    }
}
```

---

## 2️⃣ TABLE UTIL

### Import:
```java
import librarymanagement.util.TableUtil;
```

### Contoh Penggunaan di View:

```java
public class FormAnggota extends JPanel {
    
    private JTable tblAnggota;
    private DefaultTableModel tableModel;
    
    private void initComponents() {
        // ... setup table ...
        
        // 1. Apply modern style dengan hover effect
        TableUtil.applyModernStyle(tblAnggota);
        
        // 2. Make table sortable (klik header untuk sort)
        TableUtil.makeSortable(tblAnggota);
        
        // 3. Auto-resize columns
        TableUtil.autoResizeColumns(tblAnggota);
        
        // 4. Highlight search results
        JTextField txtSearch = new JTextField();
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearch.getText();
                controller.search(keyword);
                TableUtil.highlightSearchResults(tblAnggota, keyword);
            }
        });
        
        // 5. Export to CSV button
        JButton btnExport = new JButton("Export CSV");
        btnExport.addActionListener(e -> {
            try {
                TableUtil.exportToCSV(tblAnggota, "data_anggota.csv");
                JOptionPane.showMessageDialog(this, 
                    "Data berhasil di-export ke data_anggota.csv");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Gagal export: " + ex.getMessage());
            }
        });
    }
}
```

---

## 3️⃣ KEYBOARD SHORTCUT UTIL

### Import:
```java
import librarymanagement.util.KeyboardShortcutUtil;
```

### Contoh Penggunaan di View:

```java
public class FormAnggota extends JPanel {
    
    private JButton btnTambah, btnUpdate, btnHapus, btnBersihkan;
    private JTextField txtSearch;
    
    private void initComponents() {
        // ... setup components ...
        
        // 1. Setup CRUD shortcuts
        KeyboardShortcutUtil.setupCRUDShortcuts(
            this,           // parent component
            btnTambah,      // save button (Ctrl+S)
            btnBersihkan,   // new button (Ctrl+N)
            btnHapus        // delete button (Delete)
        );
        
        // 2. Setup search shortcut (Ctrl+F)
        KeyboardShortcutUtil.setupSearchShortcut(this, txtSearch);
        
        // 3. Setup ESC to clear selection
        KeyboardShortcutUtil.setupEscapeToClear(this, () -> {
            clearForm();
            tblAnggota.clearSelection();
        });
        
        // 4. Setup Enter to edit (di tabel)
        KeyboardShortcutUtil.setupEnterToEdit(tblAnggota, () -> {
            int row = tblAnggota.getSelectedRow();
            if (row >= 0) {
                // Fill form dengan data dari tabel
                fillFormFromTable(row);
            }
        });
        
        // 5. Custom shortcut (Ctrl+P untuk print)
        KeyboardShortcutUtil.addCustomShortcut(
            this,
            KeyEvent.VK_P,
            KeyEvent.CTRL_DOWN_MASK,
            () -> {
                System.out.println("Print triggered!");
                // Implementasi print
            }
        );
    }
}
```

---

## 4️⃣ LOADING UTIL

### Import:
```java
import librarymanagement.util.LoadingUtil;
```

### Contoh Penggunaan di Controller:

```java
public class PeminjamanController {
    
    private FormPeminjaman view;
    private PeminjamanModel model;
    
    // Contoh 1: Simple loading
    public void loadData() {
        LoadingUtil.showLoading(view, "Memuat data peminjaman...");
        
        try {
            List<PeminjamanModel> list = model.getAllPeminjaman();
            updateTable(list);
        } finally {
            LoadingUtil.hideLoading();
        }
    }
    
    // Contoh 2: Loading dengan SwingWorker
    public void generateLaporan() {
        LoadingUtil.runWithLoading(
            view,
            "Generating laporan...",
            // Task yang akan dijalankan
            () -> {
                try {
                    Thread.sleep(2000); // Simulasi proses lama
                    // Generate laporan
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },
            // Callback setelah selesai
            () -> {
                JOptionPane.showMessageDialog(view, "Laporan berhasil dibuat!");
            }
        );
    }
    
    // Contoh 3: Progress loading
    public void importData() {
        LoadingUtil.ProgressLoading progress = 
            new LoadingUtil.ProgressLoading(view, "Import Data");
        
        progress.show();
        
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<String> data = readCSV();
                int total = data.size();
                
                for (int i = 0; i < total; i++) {
                    // Process data
                    processRow(data.get(i));
                    
                    // Update progress
                    int percent = (i + 1) * 100 / total;
                    publish(percent);
                    
                    Thread.sleep(100); // Simulasi
                }
                return null;
            }
            
            @Override
            protected void process(List<Integer> chunks) {
                int percent = chunks.get(chunks.size() - 1);
                progress.setProgress(percent);
                progress.setMessage("Processing... " + percent + "%");
            }
            
            @Override
            protected void done() {
                progress.close();
                JOptionPane.showMessageDialog(view, "Import selesai!");
            }
        };
        
        worker.execute();
    }
}
```

---

## 5️⃣ TOAST UTIL

### Import:
```java
import librarymanagement.util.ToastUtil;
```

### Contoh Penggunaan di Controller:

```java
public class BukuController {
    
    private FormBuku view;
    private BukuModel model;
    
    public void tambah() {
        if (!validateInput()) return;
        
        BukuModel buku = getBukuFromForm();
        
        if (model.addBuku(buku)) {
            // Toast notification (tidak blocking)
            ToastUtil.showSuccess(view, "Buku berhasil ditambahkan!");
            view.clearForm();
            loadData();
        } else {
            ToastUtil.showError(view, "Gagal menambahkan buku!");
        }
    }
    
    public void update(int idBuku) {
        if (idBuku <= 0) {
            ToastUtil.showWarning(view, "Pilih buku yang akan diupdate!");
            return;
        }
        
        if (!validateInput()) return;
        
        BukuModel buku = getBukuFromForm();
        buku.setIdBuku(idBuku);
        
        if (model.updateBuku(buku)) {
            ToastUtil.showSuccess(view, "Buku berhasil diupdate!");
            view.clearForm();
            loadData();
        } else {
            ToastUtil.showError(view, "Gagal mengupdate buku!");
        }
    }
    
    public void search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            loadData();
            ToastUtil.showInfo(view, "Menampilkan semua data");
            return;
        }
        
        List<BukuModel> list = model.searchBuku(keyword.trim());
        updateTable(list);
        
        if (list.isEmpty()) {
            ToastUtil.showWarning(view, "Tidak ada data yang ditemukan");
        } else {
            ToastUtil.showInfo(view, "Ditemukan " + list.size() + " buku");
        }
    }
}
```

---

## 6️⃣ TABLE DOUBLE CLICK UTIL

### Import:
```java
import librarymanagement.util.TableDoubleClickUtil;
```

### Contoh Penggunaan di View:

```java
public class FormAnggota extends JPanel {
    
    private JTable tblAnggota;
    private JTextField txtNama, txtAlamat, txtNoHp;
    private JComboBox<String> cmbJenisKelamin;
    
    private void initComponents() {
        // ... setup components ...
        
        // 1. Setup double-click auto-fill untuk text fields
        JTextField[] fields = {txtNama, txtAlamat, txtNoHp};
        TableDoubleClickUtil.setupDoubleClickAutoFill(
            tblAnggota,
            fields,
            1,  // Start dari kolom 1 (skip ID di kolom 0)
            () -> {
                // Callback setelah auto-fill
                int row = tblAnggota.getSelectedRow();
                selectedId = (int) tableModel.getValueAt(row, 0);
                
                // Set combo box
                String jenisKelamin = tableModel.getValueAt(row, 4).toString();
                cmbJenisKelamin.setSelectedItem(jenisKelamin);
                
                // Show toast
                ToastUtil.showInfo(this, "Data siap untuk diedit");
            }
        );
        
        // 2. Setup double-click dengan custom handler
        TableDoubleClickUtil.setupDoubleClickHandler(tblAnggota, () -> {
            int row = tblAnggota.getSelectedRow();
            if (row >= 0) {
                // Custom logic
                int id = (int) tableModel.getValueAt(row, 0);
                String nama = tableModel.getValueAt(row, 1).toString();
                
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Edit data: " + nama + "?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    fillFormFromTable(row);
                }
            }
        });
    }
}
```

---

## 7️⃣ REAL TIME VALIDATION UTIL

### Import:
```java
import librarymanagement.util.RealTimeValidationUtil;
```

### Contoh Penggunaan di View:

```java
public class FormAnggota extends JPanel {
    
    private JTextField txtNama, txtAlamat, txtNoHp, txtTahunLahir;
    private JLabel lblErrorNama, lblErrorNoHp, lblErrorTahun;
    
    private void initComponents() {
        // ... setup components ...
        
        // 1. Validasi field tidak boleh kosong
        RealTimeValidationUtil.setupNotEmptyValidation(txtNama, lblErrorNama);
        RealTimeValidationUtil.setupNotEmptyValidation(txtAlamat, null); // Tanpa label error
        
        // 2. Validasi nomor HP
        RealTimeValidationUtil.setupPhoneValidation(txtNoHp, lblErrorNoHp);
        
        // 3. Validasi tahun
        RealTimeValidationUtil.setupYearValidation(txtTahunLahir, lblErrorTahun);
        
        // 4. Validasi angka positif
        JTextField txtStok = new JTextField();
        JLabel lblErrorStok = new JLabel();
        RealTimeValidationUtil.setupPositiveNumberValidation(txtStok, lblErrorStok);
        
        // 5. Validasi tanggal (YYYY-MM-DD)
        JTextField txtTanggal = new JTextField();
        JLabel lblErrorTanggal = new JLabel();
        RealTimeValidationUtil.setupDateValidation(txtTanggal, lblErrorTanggal);
        
        // 6. Custom validation
        RealTimeValidationUtil.setupCustomValidation(
            txtNama,
            lblErrorNama,
            value -> {
                // Custom logic: nama harus minimal 3 karakter
                if (value.length() < 3) {
                    return "Nama minimal 3 karakter";
                }
                // Nama tidak boleh mengandung angka
                if (value.matches(".*\\d.*")) {
                    return "Nama tidak boleh mengandung angka";
                }
                return null; // Valid
            }
        );
    }
}
```

---

## 🎯 CONTOH IMPLEMENTASI LENGKAP DI FORM ANGGOTA

### FormAnggota.java (Complete Example):

```java
package librarymanagement.view;

import librarymanagement.controller.AnggotaController;
import librarymanagement.util.*;
import javax.swing.*;
import java.awt.*;

public class FormAnggota extends JPanel {
    
    // Components
    private JTextField txtNama, txtAlamat, txtNoHp, txtSearch;
    private JComboBox<String> cmbJenisKelamin;
    private JButton btnTambah, btnUpdate, btnHapus, btnBersihkan, btnExport;
    private JTable tblAnggota;
    private DefaultTableModel tableModel;
    private JLabel lblErrorNama, lblErrorNoHp;
    
    private AnggotaController controller;
    private int selectedId = 0;
    
    public FormAnggota() {
        initComponents();
        controller = new AnggotaController(this);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        
        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        // Nama
        formPanel.add(new JLabel("Nama:"));
        JPanel namaPanel = new JPanel(new BorderLayout());
        txtNama = new JTextField();
        lblErrorNama = new JLabel();
        lblErrorNama.setForeground(Color.RED);
        lblErrorNama.setFont(new Font("Arial", Font.PLAIN, 10));
        namaPanel.add(txtNama, BorderLayout.CENTER);
        namaPanel.add(lblErrorNama, BorderLayout.SOUTH);
        formPanel.add(namaPanel);
        
        // Alamat
        formPanel.add(new JLabel("Alamat:"));
        txtAlamat = new JTextField();
        formPanel.add(txtAlamat);
        
        // No HP
        formPanel.add(new JLabel("No HP:"));
        JPanel hpPanel = new JPanel(new BorderLayout());
        txtNoHp = new JTextField();
        lblErrorNoHp = new JLabel();
        lblErrorNoHp.setForeground(Color.RED);
        lblErrorNoHp.setFont(new Font("Arial", Font.PLAIN, 10));
        hpPanel.add(txtNoHp, BorderLayout.CENTER);
        hpPanel.add(lblErrorNoHp, BorderLayout.SOUTH);
        formPanel.add(hpPanel);
        
        // Jenis Kelamin
        formPanel.add(new JLabel("Jenis Kelamin:"));
        cmbJenisKelamin = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
        formPanel.add(cmbJenisKelamin);
        
        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTambah = new JButton("Tambah");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnBersihkan = new JButton("Bersihkan");
        btnExport = new JButton("Export CSV");
        
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnBersihkan);
        buttonPanel.add(btnExport);
        
        // ===== TABLE PANEL =====
        String[] columns = {"ID", "Nama", "Alamat", "No HP", "Jenis Kelamin"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblAnggota = new JTable(tableModel);
        
        // ===== SEARCH PANEL =====
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.add(new JLabel("Search:"), BorderLayout.WEST);
        txtSearch = new JTextField();
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        
        // ===== APPLY UTILITIES =====
        
        // 1. Real-time validation
        RealTimeValidationUtil.setupNotEmptyValidation(txtNama, lblErrorNama);
        RealTimeValidationUtil.setupPhoneValidation(txtNoHp, lblErrorNoHp);
        
        // 2. Table utilities
        TableUtil.applyModernStyle(tblAnggota);
        TableUtil.makeSortable(tblAnggota);
        TableUtil.autoResizeColumns(tblAnggota);
        
        // 3. Keyboard shortcuts
        KeyboardShortcutUtil.setupCRUDShortcuts(this, btnTambah, btnBersihkan, btnHapus);
        KeyboardShortcutUtil.setupSearchShortcut(this, txtSearch);
        KeyboardShortcutUtil.setupEscapeToClear(this, () -> clearForm());
        KeyboardShortcutUtil.setupEnterToEdit(tblAnggota, () -> fillFormFromTable());
        
        // 4. Double-click to edit
        JTextField[] fields = {txtNama, txtAlamat, txtNoHp};
        TableDoubleClickUtil.setupDoubleClickAutoFill(tblAnggota, fields, 1, () -> {
            int row = tblAnggota.getSelectedRow();
            selectedId = (int) tableModel.getValueAt(row, 0);
            String jk = tableModel.getValueAt(row, 4).toString();
            cmbJenisKelamin.setSelectedItem(jk);
            ToastUtil.showInfo(this, "Data siap untuk diedit");
        });
        
        // ===== EVENT LISTENERS =====
        
        btnTambah.addActionListener(e -> controller.tambah());
        btnUpdate.addActionListener(e -> controller.update(selectedId));
        btnHapus.addActionListener(e -> controller.hapus(selectedId));
        btnBersihkan.addActionListener(e -> clearForm());
        
        btnExport.addActionListener(e -> {
            try {
                TableUtil.exportToCSV(tblAnggota, "data_anggota.csv");
                ToastUtil.showSuccess(this, "Data berhasil di-export!");
            } catch (Exception ex) {
                ToastUtil.showError(this, "Gagal export: " + ex.getMessage());
            }
        });
        
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String keyword = txtSearch.getText();
                controller.search(keyword);
                TableUtil.highlightSearchResults(tblAnggota, keyword);
            }
        });
        
        // ===== LAYOUT =====
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(tblAnggota), BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }
    
    // ===== GETTER METHODS =====
    public String getNama() { return txtNama.getText(); }
    public String getAlamat() { return txtAlamat.getText(); }
    public String getNoHp() { return txtNoHp.getText(); }
    public String getJenisKelamin() { return (String) cmbJenisKelamin.getSelectedItem(); }
    public DefaultTableModel getTableModel() { return tableModel; }
    
    // ===== HELPER METHODS =====
    public void clearForm() {
        txtNama.setText("");
        txtAlamat.setText("");
        txtNoHp.setText("");
        txtSearch.setText("");
        cmbJenisKelamin.setSelectedIndex(0);
        selectedId = 0;
        tblAnggota.clearSelection();
        lblErrorNama.setText("");
        lblErrorNoHp.setText("");
    }
    
    private void fillFormFromTable() {
        int row = tblAnggota.getSelectedRow();
        if (row >= 0) {
            selectedId = (int) tableModel.getValueAt(row, 0);
            txtNama.setText(tableModel.getValueAt(row, 1).toString());
            txtAlamat.setText(tableModel.getValueAt(row, 2).toString());
            txtNoHp.setText(tableModel.getValueAt(row, 3).toString());
            cmbJenisKelamin.setSelectedItem(tableModel.getValueAt(row, 4).toString());
        }
    }
}
```

---

## 🎉 KESIMPULAN

Dengan menggunakan semua utility ini, Anda bisa:

✅ **Validasi input yang lengkap dan konsisten**
✅ **Notifikasi yang jelas dan tidak blocking**
✅ **Table yang modern dengan hover dan sort**
✅ **Keyboard shortcuts untuk efisiensi**
✅ **Loading indicator untuk feedback**
✅ **Toast notifications yang elegan**
✅ **Double-click to edit untuk kemudahan**
✅ **Real-time validation untuk UX yang baik**

**APLIKASI ANDA SEKARANG SANGAT PROFESIONAL!** 🚀

---

## 📚 DOKUMENTASI LAINNYA:

- **RINGKASAN_SEMUA_PERBAIKAN.md** - Ringkasan lengkap semua perbaikan
- **FITUR_TAMBAHAN_LENGKAP.md** - Dokumentasi detail setiap utility
- **CARA_MELIHAT_PERUBAHAN.md** - Cara fix NetBeans cache
- **FIX_ERROR_BERHASIL.md** - Penjelasan error dan solusi
- **FIX_ERROR_COMPILE.md** - Panduan fix compile error

**SELAMAT CODING!** 🎉
