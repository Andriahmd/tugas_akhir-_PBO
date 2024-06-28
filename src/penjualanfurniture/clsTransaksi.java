package penjualanfurniture;

import javax.swing.JOptionPane;
import java.sql.*;

public class clsTransaksi extends clskoneksi {
    String no_transaksi;
    String tanggal;
    String nama_furniture;
    int jumlah;
    int total;
    int harga;

    public void tampildata() {
        sql = "SELECT * FROM transaksi";
    }

    public void simpan() {
        try {
            sql = "INSERT INTO transaksi (tanggal, nama_furniture, jumlah, harga, total_harga) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tanggal);
            ps.setString(2, nama_furniture);
            ps.setInt(3, jumlah);
            ps.setInt(4, harga);
            ps.setInt(5, total);
            ps.executeUpdate();
            System.out.println("Data transaksi berhasil disimpan.");

            updateStok();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan / Koneksi error");
            System.out.println(e.getMessage());
        }
    }

    public void edit() {
        try {
            sql = "UPDATE transaksi SET tanggal = ?, nama_furniture = ?, jumlah = ?, harga = ?, total_harga = ? WHERE id_transaksi = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tanggal);
            ps.setString(2, nama_furniture);
            ps.setInt(3, jumlah);
            ps.setInt(4, harga);
            ps.setInt(5, total);
            ps.setString(6, no_transaksi);
            ps.executeUpdate();
            System.out.println("Data transaksi berhasil diedit.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan / Koneksi error");
            System.out.println(e.getMessage());
        }
    }

    public void hapus() {
        try {
            sql = "DELETE FROM transaksi WHERE id_transaksi = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, no_transaksi);
            ps.executeUpdate();
            System.out.println("Data transaksi berhasil dihapus.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan");
            System.out.println(e.getMessage());
        }
    }

    public void setNoTransaksi(String no_transaksi) {
        this.no_transaksi = no_transaksi;
    }

    // Metode untuk memperbarui stok
    public void updateStok() {
        try {
            sql = "UPDATE furniture SET stok = stok - ? WHERE nama_furniture = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, jumlah);
            ps.setString(2, nama_furniture);
            ps.executeUpdate();
            System.out.println("Stok furniture " + nama_furniture + " berhasil diperbarui, berkurang sebesar: " + jumlah);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memperbarui stok");
            System.out.println(e.getMessage());
        }
    }
}
