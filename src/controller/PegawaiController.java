package controller;

import dao.PegawaiDAO;
import javax.swing.JTable;
import model.Pegawai;

public class PegawaiController {
    PegawaiDAO dao;
    public PegawaiController() {
        dao = new PegawaiDAO();
    }

    // INSERT
    public void insertPegawai(Pegawai p) {
        dao.insertPegawai(p);
    }

    // TAMPIL DATA
    public void tampilData(JTable table) {
        dao.tampilData(table);
    }

    // UPDATE
    public void updatePegawai(Pegawai p) {
        dao.updatePegawai(p);
    }

    // DELETE
    public void deletePegawai(int id) {
        dao.deletePegawai(id);
    }

}