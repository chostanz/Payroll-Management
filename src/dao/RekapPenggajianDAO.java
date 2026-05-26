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

    // Rekap bulanan
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

    // Detail per bulan + kolom Status (Lunas/Belum)
    public void tampilDetailBulan(JTable table, int bulan, int tahun) {
        DefaultTableModel model = new DefaultTableModel() {
            // Kolom tidak bisa diedit langsung dari tabel
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        model.addColumn("ID");
        model.addColumn("NIK");
        model.addColumn("Nama");
        model.addColumn("Jabatan");
        model.addColumn("Jenis");
        model.addColumn("Gaji Pokok");
        model.addColumn("Tunjangan");
        model.addColumn("Potongan");
        model.addColumn("Total Gaji");
        model.addColumn("Status");
        model.addColumn("Aksi");

        try {
            // LEFT JOIN: semua pegawai aktif, penggajian bulan ini kalau ada
            String query =
                "SELECT pg.id_penggajian, p.nik, p.nama, p.jabatan, p.jenis_pegawai, " +
                "  COALESCE(pt.gaji_pokok, pk.upah_per_bulan, 0) AS gaji_pokok, " +
                "  COALESCE(pt.tunjangan, 0) AS tunjangan, " +
                "  pg.total_potongan, pg.gaji_bersih, " +
                "  COALESCE(pg.keterangan, 'Belum') AS status " +   // ← pakai keterangan
                "FROM pegawai p " +
                "LEFT JOIN pegawai_tetap pt ON p.id_pegawai = pt.id_pegawai " +
                "LEFT JOIN pegawai_kontrak pk ON p.id_pegawai = pk.id_pegawai " +
                "LEFT JOIN penggajian pg ON p.id_pegawai = pg.id_pegawai " +
                "  AND pg.bulan = ? AND pg.tahun = ? " +
                "WHERE p.status = 'Aktif' " +
                "ORDER BY p.nama";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, bulan);
            ps.setInt(2, tahun);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            String status    = rs.getString("status");
            double gajiPokok = rs.getDouble("gaji_pokok");
            double tunjangan = rs.getDouble("tunjangan");
            double potongan  = rs.getDouble("total_potongan");
            double totalGaji = rs.getDouble("gaji_bersih");
            int idPenggajian = rs.getInt("id_penggajian"); // 0 kalau belum ada

            model.addRow(new Object[]{
                idPenggajian,   // kolom 0 — disembunyikan, dipakai untuk update
                rs.getString("nik"),
                rs.getString("nama"),
                rs.getString("jabatan"),
                rs.getString("jenis_pegawai"),
                "Rp " + String.format("%,.0f", gajiPokok),
                "Rp " + String.format("%,.0f", tunjangan),
                idPenggajian > 0 ? "Rp " + String.format("%,.0f", potongan) : "-",
                idPenggajian > 0 ? "Rp " + String.format("%,.0f", totalGaji) : "-",
                status,
                "⋮"
            });
        }
            table.setModel(model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Summary cards untuk bulan & tahun tertentu
    public int[] getSummary(int bulan, int tahun) {
        // return: [totalPegawai, sudahDibayar, belumDibayar, totalGaji]
        int[] result = {0, 0, 0, 0};
        try {
            // Total pegawai aktif
            PreparedStatement ps1 = conn.prepareStatement(
                "SELECT COUNT(*) FROM pegawai WHERE status = 'Aktif'"
            );
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) result[0] = rs1.getInt(1);

            // Sudah dibayar bulan ini
            PreparedStatement ps2 = conn.prepareStatement(
                "SELECT COUNT(*) FROM penggajian WHERE bulan = ? AND tahun = ?"
            );
            ps2.setInt(1, bulan);
            ps2.setInt(2, tahun);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) result[1] = rs2.getInt(1);

            result[2] = result[0] - result[1]; // belum dibayar

            // Total gaji bersih bulan ini
            PreparedStatement ps3 = conn.prepareStatement(
                "SELECT COALESCE(SUM(gaji_bersih), 0) FROM penggajian WHERE bulan = ? AND tahun = ?"
            );
            ps3.setInt(1, bulan);
            ps3.setInt(2, tahun);
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) result[3] = (int) rs3.getDouble(1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    // Update status pembayaran (keterangan)
    public void updateStatusBayar(int idPenggajian, String status) {
        try {
            String query = "UPDATE penggajian SET keterangan=? WHERE id_penggajian=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, idPenggajian);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateStatusBayar: " + e.getMessage());
        }
    }

    // Update potongan & gaji bersih
    public void updatePenggajian(int idPenggajian, double gajiKotor, double totalPotongan, double gajiBersih) {
        try {
            String query = "UPDATE penggajian SET gaji_kotor=?, total_potongan=?, gaji_bersih=? " +
                           "WHERE id_penggajian=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, gajiKotor);
            ps.setDouble(2, totalPotongan);
            ps.setDouble(3, gajiBersih);
            ps.setInt(4, idPenggajian);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updatePenggajian: " + e.getMessage());
        }
    }

}