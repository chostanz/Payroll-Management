package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    Connection conn;

    public UserDAO() {

        conn = Koneksi.getConnection();

    }

    public boolean login(String username,
            String password) {

        try {

            String query =
                    "SELECT * FROM user "
                    + "WHERE username=? AND password=?";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        return false;
    }
}