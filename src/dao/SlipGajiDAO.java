package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.SlipGaji;

public class SlipGajiDAO {
    Connection conn;
    public SlipGajiDAO() {
        conn = Koneksi.getConnection();
    }

    public void insertSlip(SlipGaji s) {
        try {
            String query =
                    "INSERT INTO slip_gaji "
                    + "(id_penggajian,no_slip)"
                    + "VALUES(?,?)";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, s.getIdPenggajian());
            ps.setString(2, s.getNoSlip());
            ps.executeUpdate();
            System.out.println("Slip berhasil dibuat");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}