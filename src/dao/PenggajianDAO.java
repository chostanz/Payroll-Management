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

    // INSERT
    public void insertPenggajian(
            Penggajian p) {

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
                    + "tanggal_proses)"
                    + "VALUES"
                    + "(?,?,?,?,?,?,?,?,NOW())";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ps.setInt(1,
                    p.getIdPegawai());

            ps.setInt(2,
                    p.getBulan());

            ps.setInt(3,
                    p.getTahun());

            ps.setDouble(4,
                    p.getBonus());

            ps.setInt(5,
                    p.getJumlahTerlambat());

            ps.setDouble(6,
                    p.getTotalPotongan());

            ps.setDouble(7,
                    p.getGajiKotor());

            ps.setDouble(8,
                    p.getGajiBersih());

            ps.executeUpdate();

            System.out.println(
                    "Penggajian berhasil");

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

            String query =
                    "SELECT * FROM penggajian";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ResultSet rs =
                    ps.executeQuery();

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