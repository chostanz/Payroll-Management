package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RekapPenggajianDAO {

    Connection conn;

    public RekapPenggajianDAO() {
        conn = Koneksi.getConnection();
    }

    // Tampil rekap bulanan (dari view_rekap_penggajian_bulanan)
    public void tampilRekap(JTable table) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Bulan");
        model.addColumn("Tahun");
        model.addColumn("Jml Pegawai");
        model.addColumn("Tetap");
        model.addColumn("Kontrak");
        model.addColumn("Part Time");
        model.addColumn("Total Gaji Kotor");
        model.addColumn("Total Potongan");
        model.addColumn("Total Gaji Bersih");

        try {
            String query = "SELECT * FROM view_rekap_penggajian_bulanan";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("bulan"),
                    rs.getInt("tahun"),
                    rs.getInt("jumlah_pegawai"),
                    rs.getInt("jumlah_tetap"),
                    rs.getInt("jumlah_kontrak"),
                    rs.getInt("jumlah_parttime"),
                    "Rp " + String.format("%,.0f", rs.getDouble("total_gaji_kotor")),
                    "Rp " + String.format("%,.0f", rs.getDouble("total_potongan")),
                    "Rp " + String.format("%,.0f", rs.getDouble("total_gaji_bersih"))
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Tampil detail per bulan & tahun (dari view_penggajian_lengkap)
    public void tampilDetailBulan(JTable table, int bulan, int tahun) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("NIK");
        model.addColumn("Nama");
        model.addColumn("Jabatan");
        model.addColumn("Jenis");
        model.addColumn("Gaji Pokok");
        model.addColumn("Bonus");
        model.addColumn("Total Potongan");
        model.addColumn("Gaji Bersih");

        try {
            String query =
                "SELECT * FROM view_penggajian_lengkap "
                + "WHERE bulan = ? AND tahun = ? "
                + "ORDER BY nama";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, bulan);
            ps.setInt(2, tahun);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nik"),
                    rs.getString("nama"),
                    rs.getString("jabatan"),
                    rs.getString("jenis_pegawai"),
                    "Rp " + String.format("%,.0f", rs.getDouble("gaji_kotor")),
                    "Rp " + String.format("%,.0f", rs.getDouble("bonus")),
                    "Rp " + String.format("%,.0f", rs.getDouble("total_potongan")),
                    "Rp " + String.format("%,.0f", rs.getDouble("gaji_bersih"))
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}