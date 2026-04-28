package librarymanagement.util;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Utility untuk validasi, penyimpanan, dan render foto anggota dari file PDF.
 */
public final class AnggotaPhotoUtil {

    public static final long MAX_FILE_SIZE_BYTES = 2L * 1024 * 1024;

    private static final Path APP_BASE_DIR = Paths.get(System.getProperty("user.home"), "perpustakaan_db");
    private static final Path FOTO_DIR = APP_BASE_DIR.resolve(Paths.get("assets", "foto"));

    private AnggotaPhotoUtil() {
    }

    public static String validatePdfFile(File file) {
        if (file == null) {
            return null;
        }
        if (!file.exists() || !file.isFile()) {
            return "File foto tidak ditemukan.";
        }
        if (!file.getName().toLowerCase(Locale.ROOT).endsWith(".pdf")) {
            return "Foto anggota harus berupa file PDF.";
        }
        if (file.length() > MAX_FILE_SIZE_BYTES) {
            return "Ukuran file PDF maksimal 2 MB.";
        }

        try (InputStream input = Files.newInputStream(file.toPath())) {
            byte[] header = new byte[4];
            int read = input.read(header);
            String signature = read >= 4 ? new String(header, StandardCharsets.US_ASCII) : "";
            if (!"%PDF".equals(signature)) {
                return "File yang dipilih bukan PDF yang valid.";
            }
        } catch (IOException e) {
            return "Gagal membaca file PDF: " + e.getMessage();
        }

        return null;
    }

    public static String savePdfForAnggota(File sourceFile, int idAnggota) throws IOException {
        Files.createDirectories(FOTO_DIR);
        String storedFileName = String.format("anggota_%04d.pdf", idAnggota);
        Path target = FOTO_DIR.resolve(storedFileName);
        Files.copy(sourceFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
        return Paths.get("assets", "foto", storedFileName).toString().replace('\\', '/');
    }

    public static void deleteStoredPdf(String storedPath) {
        File file = resolvePdfFile(storedPath);
        if (file == null || !file.exists()) {
            return;
        }

        try {
            Path normalizedBase = FOTO_DIR.toAbsolutePath().normalize();
            Path normalizedFile = file.toPath().toAbsolutePath().normalize();
            if (normalizedFile.startsWith(normalizedBase)) {
                Files.deleteIfExists(normalizedFile);
            }
        } catch (IOException e) {
            System.err.println("Warning deleteStoredPdf: " + e.getMessage());
        }
    }

    public static File resolvePdfFile(String storedPath) {
        if (storedPath == null || storedPath.trim().isEmpty()) {
            return null;
        }

        Path rawPath = Paths.get(storedPath);
        Path resolvedPath = rawPath.isAbsolute() ? rawPath : APP_BASE_DIR.resolve(rawPath);
        return resolvedPath.normalize().toFile();
    }

    public static BufferedImage renderPdfFirstPage(String storedPath, int targetWidth, int targetHeight) {
        File pdfFile = resolvePdfFile(storedPath);
        if (pdfFile == null || !pdfFile.exists()) {
            return null;
        }

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage rendered = renderer.renderImageWithDPI(0, 160, ImageType.RGB);
            return scaleToFit(rendered, targetWidth, targetHeight);
        } catch (IOException e) {
            System.err.println("Warning renderPdfFirstPage: " + e.getMessage());
            return null;
        }
    }

    public static boolean openPdf(String storedPath) {
        File pdfFile = resolvePdfFile(storedPath);
        if (pdfFile == null || !pdfFile.exists() || !Desktop.isDesktopSupported()) {
            return false;
        }

        try {
            Desktop.getDesktop().open(pdfFile);
            return true;
        } catch (IOException e) {
            System.err.println("Warning openPdf: " + e.getMessage());
            return false;
        }
    }

    public static String getDisplayName(String storedPath) {
        File pdfFile = resolvePdfFile(storedPath);
        return pdfFile != null ? pdfFile.getName() : "-";
    }

    public static String getStorageDescription() {
        return FOTO_DIR.toAbsolutePath().toString();
    }

    private static BufferedImage scaleToFit(BufferedImage source, int targetWidth, int targetHeight) {
        BufferedImage output = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, targetWidth, targetHeight);

        double scale = Math.min(
            (double) targetWidth / Math.max(1, source.getWidth()),
            (double) targetHeight / Math.max(1, source.getHeight())
        );

        int drawWidth = Math.max(1, (int) Math.round(source.getWidth() * scale));
        int drawHeight = Math.max(1, (int) Math.round(source.getHeight() * scale));
        int drawX = (targetWidth - drawWidth) / 2;
        int drawY = (targetHeight - drawHeight) / 2;

        g2.drawImage(source, drawX, drawY, drawWidth, drawHeight, null);
        g2.dispose();
        return output;
    }
}
