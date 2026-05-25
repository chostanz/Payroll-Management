package model;

import java.util.Date;

public class SlipGaji {

    private int idSlip;
    private int idPenggajian;
    private String noSlip;
    private Date tanggalCetak;

    public int getIdSlip() {
        return idSlip;
    }

    public void setIdSlip(int idSlip) {
        this.idSlip = idSlip;
    }

    public int getIdPenggajian() {
        return idPenggajian;
    }

    public void setIdPenggajian(int idPenggajian) {
        this.idPenggajian = idPenggajian;
    }

    public String getNoSlip() {
        return noSlip;
    }

    public void setNoSlip(String noSlip) {
        this.noSlip = noSlip;
    }

    public Date getTanggalCetak() {
        return tanggalCetak;
    }

    public void setTanggalCetak(Date tanggalCetak) {
        this.tanggalCetak = tanggalCetak;
    }
}