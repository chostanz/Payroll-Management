package dao;

import connection.Koneksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Pegawai;

public class PegawaiDAO {
    Connection conn;
    public PegawaiDAO() {
        conn = Koneksi.getConnection();
    }

    // INSERT
    public void insertPegawai(Pegawai p) {
        try {
            String query =
                    "INSERT INTO pegawai "
                    + "(nik,nama,jenis_kelamin,"
                    + "jabatan,jenis_pegawai)"
                    + "VALUES(?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, p.getNik());
            ps.setString(2, p.getNama());
            // default sementara
            ps.setString(3, "Laki-laki");
            ps.setString(4, p.getJabatan());
            ps.setString(5, p.getJenisPegawai());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // TAMPIL DATA
    public void tampilData(JTable table) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("NIK");
        model.addColumn("Nama");
        model.addColumn("Jabatan");
        model.addColumn("Jenis");

        try {
            String query = "SELECT * FROM pegawai";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_pegawai"),
                    rs.getString("nik"),
                    rs.getString("nama"),
                    rs.getString("jabatan"),
                    rs.getString("jenis_pegawai")
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // UPDATE
    public void updatePegawai(Pegawai p) {
        try {
            String query =
                    "UPDATE pegawai SET "
                    + "nik=?,"
                    + "nama=?,"
                    + "jabatan=?,"
                    + "jenis_pegawai=? "
                    + "WHERE id_pegawai=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, p.getNik());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getJabatan());
            ps.setString(4, p.getJenisPegawai());
            ps.setInt(5, p.getIdPegawai());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // DELETE
    public void deletePegawai(int id) {
    try {
        String q1 = "DELETE FROM penggajian "
                + "WHERE id_pegawai=?";
        PreparedStatement ps1 = conn.prepareStatement(q1);

        ps1.setInt(1, id);
        ps1.executeUpdate();

        // HAPUS PEGAWAI
        String q2 = "DELETE FROM pegawai "
                + "WHERE id_pegawai=?";

        PreparedStatement ps2 = conn.prepareStatement(q2);
        ps2.setInt(1, id);
        ps2.executeUpdate();
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}
}
