package controller;

import dao.PenggajianDAO;
import javax.swing.JTable;
import model.Penggajian;

public class PenggajianController {
    PenggajianDAO dao;

    public PenggajianController() {
        dao = new PenggajianDAO();
    }
public void simpanGajiDasar(int idPegawai, String jenisPegawai, double gajiPokok, double tunjangan) {
    dao.simpanGajiDasar(idPegawai, jenisPegawai, gajiPokok, tunjangan);
}

    public void insertPenggajian(Penggajian p) {
        dao.insertPenggajian(p);
    }

    public void tampilData(JTable table) {
        dao.tampilData(table);
    }
    
    public double[] getGajiDasar(int idPegawai, String jenisPegawai) {
         return dao.getGajiDasar(idPegawai, jenisPegawai);
}
}
