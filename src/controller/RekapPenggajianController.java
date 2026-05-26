package controller;

import dao.RekapPenggajianDAO;
import javax.swing.JTable;

public class RekapPenggajianController {
    RekapPenggajianDAO dao = new RekapPenggajianDAO();

    public int[] getSummary(int bulan, int tahun) {
        return dao.getSummary(bulan, tahun);
    }

    public void tampilDetailBulan(JTable table, int bulan, int tahun) {
        dao.tampilDetailBulan(table, bulan, tahun);
    }

    public void updateStatusBayar(int idPenggajian, String status) {
        dao.updateStatusBayar(idPenggajian, status);
    }

    public void updatePenggajian(int idPenggajian, double gajiKotor, double totalPotongan, double gajiBersih) {
        dao.updatePenggajian(idPenggajian, gajiKotor, totalPotongan, gajiBersih);
    }
}