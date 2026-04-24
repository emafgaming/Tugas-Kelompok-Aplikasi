package librarymanagement;

import librarymanagement.model.DatabaseConnection;
import librarymanagement.view.LoginForm;
import javax.swing.*;

/**
 * Main - Entry point aplikasi Sistem Manajemen Perpustakaan
 * 
 * CARA MENJALANKAN:
 * 1. Pastikan sqlite-jdbc.jar sudah ditambahkan ke Libraries project NetBeans
 * 2. Jalankan file ini sebagai Main Class
 * 
 * AKUN DEFAULT:
 * - Admin : username=admin, password=admin123 (Full Access - Semua fitur)
 * - Petugas: username=petugas, password=petugas123 (Limited Access)
 * - Demo  : username=demo, password=demo123 (Read-Only - Hanya view)
 * 
 * DATABASE:
 * - SQLite database akan otomatis dibuat di folder 'database/perpustakaan.db'
 */
public class Main {
    
    public static void main(String[] args) {
        // Tampilkan di Event Dispatch Thread untuk thread safety Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Set tema Look & Feel
                // Gunakan Nimbus untuk tampilan lebih modern
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
                
                // Konfigurasi warna Nimbus agar sesuai tema
                UIManager.put("nimbusBase", new java.awt.Color(26, 82, 118));
                UIManager.put("nimbusBlueGrey", new java.awt.Color(26, 82, 118));
                UIManager.put("control", new java.awt.Color(240, 244, 248));
                
            } catch (Exception e) {
                // Jika Nimbus tidak tersedia, gunakan system default
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    System.err.println("Tidak dapat mengatur Look & Feel: " + ex.getMessage());
                }
            }
            
            // Inisialisasi database (singleton akan membuat tabel dan data default)
            System.out.println("Menginisialisasi database...");
            DatabaseConnection.getInstance();
            
            // Tampilkan Login Form
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            
            System.out.println("Aplikasi Perpustakaan berhasil dijalankan.");
        });

        // Shutdown hook untuk menutup koneksi database saat aplikasi ditutup
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DatabaseConnection.getInstance().closeConnection();
            System.out.println("Koneksi database ditutup. Aplikasi selesai.");
        }));
    }
}
