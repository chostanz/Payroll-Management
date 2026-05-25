package model;

import java.util.Date;

public class Penggajian {

    private int idPenggajian;
    private int idPegawai;
    private int bulan;
    private int tahun;

    private double bonus;
    private int jumlahTerlambat;

    private double totalPotongan;
    private double gajiKotor;
    private double gajiBersih;

    private Date tanggalProses;

    public Penggajian() {
    }

    public int getIdPenggajian() {
        return idPenggajian;
    }

    public void setIdPenggajian(int idPenggajian) {
        this.idPenggajian = idPenggajian;
    }

    public int getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(int idPegawai) {
        this.idPegawai = idPegawai;
    }

    public int getBulan() {
        return bulan;
    }

    public void setBulan(int bulan) {
        this.bulan = bulan;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public int getJumlahTerlambat() {
        return jumlahTerlambat;
    }

    public void setJumlahTerlambat(int jumlahTerlambat) {
        this.jumlahTerlambat = jumlahTerlambat;
    }

    public double getTotalPotongan() {
        return totalPotongan;
    }

    public void setTotalPotongan(double totalPotongan) {
        this.totalPotongan = totalPotongan;
    }

    public double getGajiKotor() {
        return gajiKotor;
    }

    public void setGajiKotor(double gajiKotor) {
        this.gajiKotor = gajiKotor;
    }

    public double getGajiBersih() {
        return gajiBersih;
    }

    public void setGajiBersih(double gajiBersih) {
        this.gajiBersih = gajiBersih;
    }

    public Date getTanggalProses() {
        return tanggalProses;
    }

    public void setTanggalProses(Date tanggalProses) {
        this.tanggalProses = tanggalProses;
    }
}