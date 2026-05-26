package controller;

import dao.RekapPenggajianDAO;
import javax.swing.JTable;

public class RekapPenggajianController {

    RekapPenggajianDAO dao;

    public RekapPenggajianController() {
        dao = new RekapPenggajianDAO();
    }

    // Tampil semua rekap bulanan
    public void tampilRekap(JTable table) {
        dao.tampilRekap(table);
    }

    // Tampil detail pegawai per bulan & tahun (dengan kolom Status)
    public void tampilDetailBulan(JTable table, int bulan, int tahun) {
        dao.tampilDetailBulan(table, bulan, tahun);
    }

    // Ambil data summary: [totalPegawai, sudahDibayar, belumDibayar, totalGaji]
    public int[] getSummary(int bulan, int tahun) {
        return dao.getSummary(bulan, tahun);
    }
}