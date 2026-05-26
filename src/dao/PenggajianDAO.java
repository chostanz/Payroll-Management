package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Penggajian;

public class PenggajianDAO {
    Connection conn;
    public PenggajianDAO() {
        conn = Koneksi.getConnection();
    }

    // Ambil gaji pokok/tunjangan/upah sesuai jenis pegawai
    public double[] getGajiDasar(int idPegawai, String jenisPegawai) {
    // return [gajiPokok/upah, tunjangan]
    double[] result = {0, 0};
    try {
        String query = "";
        if ("Tetap".equals(jenisPegawai)) {
            query = "SELECT gaji_pokok, tunjangan FROM pegawai_tetap WHERE id_pegawai = ?";
        } else if ("Kontrak".equals(jenisPegawai)) {
            query = "SELECT upah_per_bulan AS gaji_pokok, 0 AS tunjangan FROM pegawai_kontrak WHERE id_pegawai = ?";
        } else if ("PartTime".equals(jenisPegawai)) {
            query = "SELECT upah_per_jam AS gaji_pokok, 0 AS tunjangan FROM pegawai_parttime WHERE id_pegawai = ?";
        }
        if (query.isEmpty()) return result;

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, idPegawai);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            result[0] = rs.getDouble("gaji_pokok");
            result[1] = rs.getDouble("tunjangan");
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    return result;
}
    
    // Simpan/update gaji dasar ke tabel detail sesuai jenis
    public void simpanGajiDasar(int idPegawai, String jenisPegawai, double gajiPokok, double tunjangan) {
        try {
            String queryCheck = "";
            String queryInsert = "";
            String queryUpdate = "";

            if ("Tetap".equals(jenisPegawai)) {
                queryCheck  = "SELECT COUNT(*) FROM pegawai_tetap WHERE id_pegawai = ?";
                queryInsert = "INSERT INTO pegawai_tetap (id_pegawai, gaji_pokok, tunjangan) VALUES (?,?,?)";
                queryUpdate = "UPDATE pegawai_tetap SET gaji_pokok=?, tunjangan=? WHERE id_pegawai=?";
            } else if ("Kontrak".equals(jenisPegawai)) {
                queryCheck  = "SELECT COUNT(*) FROM pegawai_kontrak WHERE id_pegawai = ?";
                queryInsert = "INSERT INTO pegawai_kontrak (id_pegawai, upah_per_bulan) VALUES (?,?)";
                queryUpdate = "UPDATE pegawai_kontrak SET upah_per_bulan=? WHERE id_pegawai=?";
            } else if ("PartTime".equals(jenisPegawai)) {
                queryCheck  = "SELECT COUNT(*) FROM pegawai_parttime WHERE id_pegawai = ?";
                queryInsert = "INSERT INTO pegawai_parttime (id_pegawai, upah_per_jam) VALUES (?,?)";
                queryUpdate = "UPDATE pegawai_parttime SET upah_per_jam=? WHERE id_pegawai=?";
            } else {
                return;
            }

            // Cek apakah sudah ada row
            PreparedStatement psCheck = conn.prepareStatement(queryCheck);
            psCheck.setInt(1, idPegawai);
            ResultSet rs = psCheck.executeQuery();
            rs.next();
            boolean sudahAda = rs.getInt(1) > 0;

            if (sudahAda) {
                // UPDATE
                PreparedStatement psUp = conn.prepareStatement(queryUpdate);
                if ("Tetap".equals(jenisPegawai)) {
                    psUp.setDouble(1, gajiPokok);
                    psUp.setDouble(2, tunjangan);
                    psUp.setInt(3, idPegawai);
                } else {
                    psUp.setDouble(1, gajiPokok); // upah_per_bulan atau upah_per_jam
                    psUp.setInt(2, idPegawai);
                }
                psUp.executeUpdate();
            } else {
                // INSERT
                PreparedStatement psIn = conn.prepareStatement(queryInsert);
                if ("Tetap".equals(jenisPegawai)) {
                    psIn.setInt(1, idPegawai);
                    psIn.setDouble(2, gajiPokok);
                    psIn.setDouble(3, tunjangan);
                } else {
                    psIn.setInt(1, idPegawai);
                    psIn.setDouble(2, gajiPokok);
                }
                psIn.executeUpdate();
            }
            System.out.println("Gaji dasar berhasil disimpan");
        } catch (Exception e) {
            System.out.println("simpanGajiDasar: " + e.getMessage());
        }
}
    // INSERT
    public void insertPenggajian(Penggajian p) {
        try {
            String query =
                    "INSERT INTO penggajian "
                    + "(id_pegawai,"
                    + "bulan,"
                    + "tahun,"
                    + "bonus,"
                    + "jumlah_terlambat,"
                    + "total_potongan,"
                    + "gaji_kotor,"
                    + "gaji_bersih,"
                    + "tanggal_proses,"
                    +"keterangan"
                    + ")"
                    + "VALUES"
                    + "(?,?,?,?,?,?,?,?,NOW(), ?)";

            PreparedStatement ps =conn.prepareStatement(query);
            ps.setInt(1,p.getIdPegawai());
            ps.setInt(2,p.getBulan());
            ps.setInt(3,p.getTahun());
            ps.setDouble(4,p.getBonus());
            ps.setInt(5,p.getJumlahTerlambat());
            ps.setDouble(6,p.getTotalPotongan());
            ps.setDouble(7,p.getGajiKotor());
            ps.setDouble(8,p.getGajiBersih());
            ps.setString(9, "Belum"); // default belum lunas
            ps.executeUpdate();
            System.out.println("Penggajian berhasil");
        } catch (Exception e) {
            System.out.println(
                    e.getMessage());
        }

    }

    // TAMPIL DATA
    public void tampilData(JTable table) {
        DefaultTableModel model =
                new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("ID Pegawai");
        model.addColumn("Bulan");
        model.addColumn("Tahun");
        model.addColumn("Gaji Bersih");
        try {
            String query = "SELECT * FROM penggajian";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_penggajian"),
                    rs.getInt("id_pegawai"),
                    rs.getInt("bulan"),
                    rs.getInt("tahun"),
                    rs.getDouble("gaji_bersih")
                });
            }
            table.setModel(model);
        } catch (Exception e) {

            System.out.println(
                    e.getMessage());
        }
    }
}