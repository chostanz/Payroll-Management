package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static Connection koneksi;

    public static Connection getConnection() {

        try {

            String url = "jdbc:mysql://localhost:3306/penggajian_pegawai";
            String user = "root";
            String password = "";

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            koneksi = DriverManager.getConnection(url, user, password);

            System.out.println("Koneksi Berhasil");

        } catch (SQLException e) {

            System.out.println("Koneksi Gagal");
            System.out.println(e.getMessage());

        }

        return koneksi;
    }
}