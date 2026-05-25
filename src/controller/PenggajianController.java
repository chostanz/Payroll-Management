package controller;

import dao.PenggajianDAO;
import javax.swing.JTable;
import model.Penggajian;

public class PenggajianController {

    PenggajianDAO dao;

    public PenggajianController() {

        dao = new PenggajianDAO();

    }

    public void insertPenggajian(Penggajian p) {

        dao.insertPenggajian(p);

    }

    public void tampilData(JTable table) {

        dao.tampilData(table);

    }
}