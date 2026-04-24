package librarymanagement.controller;

import librarymanagement.model.UserModel;
import librarymanagement.view.Dashboard;
import librarymanagement.view.LoginForm;
import javax.swing.JOptionPane;

/**
 * LoginController - Controller untuk autentikasi user
 * Menghubungkan LoginForm (View) dengan UserModel (Model)
 */
public class LoginController {
    
    private LoginForm view;
    private UserModel model;
    
    // User yang sedang login (session)
    private static UserModel currentUser;
    
    public LoginController(LoginForm view) {
        this.view  = view;
        this.model = new UserModel();
    }
    
    /**
     * Proses login
     */
    public void prosesLogin() {
        String username = view.getUsername().trim();
        String password = view.getPassword().trim();
        
        // Validasi input tidak boleh kosong
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Username tidak boleh kosong!", 
                "Validasi", JOptionPane.WARNING_MESSAGE);
            view.focusUsername();
            return;
        }
        
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Password tidak boleh kosong!", 
                "Validasi", JOptionPane.WARNING_MESSAGE);
            view.focusPassword();
            return;
        }
        
        try {
            // Autentikasi
            UserModel user = model.login(username, password);
            
            if (user != null) {
                currentUser = user;
                
                // Tampilkan pesan khusus untuk akun demo
                if (user.isDemo()) {
                    JOptionPane.showMessageDialog(view,
                        "🎭 SELAMAT DATANG DI MODE DEMO!\n\n" +
                        "Anda login sebagai pengguna demo dengan akses READ-ONLY.\n\n" +
                        "✅ Yang bisa dilakukan:\n" +
                        "  • Melihat semua data\n" +
                        "  • Mencari dan filter data\n" +
                        "  • Melihat laporan\n\n" +
                        "❌ Yang TIDAK bisa dilakukan:\n" +
                        "  • Tambah, Edit, Hapus data\n" +
                        "  • Semua tombol CRUD dinonaktifkan\n\n" +
                        "💡 Tertarik dengan sistem ini?\n" +
                        "   Klik menu 'Hubungi Developer' untuk info lebih lanjut!",
                        "Mode Demo", JOptionPane.INFORMATION_MESSAGE);
                }
                
                Dashboard dashboard = new Dashboard(user);
                dashboard.setVisible(true);
                view.dispose();
            } else {
                // Cek apakah DB konek atau tidak
                if (librarymanagement.model.DatabaseConnection.getInstance().getConnection() == null) {
                    JOptionPane.showMessageDialog(view,
                        "❌ GAGAL KONEKSI DATABASE!\n\n" +
                        "Kemungkinan penyebab:\n" +
                        "1. Library sqlite-jdbc.jar BELUM ditambahkan ke project\n\n" +
                        "Solusi:\n" +
                        "• Klik kanan 'Dependencies' di panel project\n" +
                        "• Pilih 'Add Dependency'\n" +
                        "• Cari: org.xerial : sqlite-jdbc\n" +
                        "• Atau edit pom.xml secara manual",
                        "Error Database", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "Username atau password salah!\n\n" +
                        "Default login:\n" +
                        "• Admin: admin / admin123\n" +
                        "• Petugas: petugas / petugas123\n" +
                        "• Demo: demo / demo123 (Read-Only)", 
                        "Login Gagal", JOptionPane.ERROR_MESSAGE);
                    view.clearPassword();
                    view.focusPassword();
                }
            }
        } catch (Exception e) {
            System.err.println(">>> Exception di prosesLogin: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(view,
                "❌ ERROR TIDAK TERDUGA!\n\n" +
                "Pesan: " + e.getMessage() + "\n\n" +
                "Lihat Output window NetBeans untuk detail lengkap.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Proses logout
     */
    public static void logout(Dashboard dashboard) {
        int confirm = JOptionPane.showConfirmDialog(dashboard, 
            "Apakah Anda yakin ingin logout?", 
            "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            currentUser = null;
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            dashboard.dispose();
        }
    }
    
    /**
     * Mendapatkan user yang sedang login
     */
    public static UserModel getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Cek apakah user adalah Admin
     */
    public static boolean isAdmin() {
        return currentUser != null && "Admin".equals(currentUser.getRole());
    }
    
    /**
     * Cek apakah user adalah Demo (Read-Only)
     */
    public static boolean isDemo() {
        return currentUser != null && currentUser.isDemo();
    }
}
