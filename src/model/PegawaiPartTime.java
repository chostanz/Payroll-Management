package model;

public class PegawaiPartTime extends Pegawai {

    private double upahPerJam;
    private double jamKerja;
    private double bonus;
    private int terlambat;

    @Override
    public double hitungGaji() {

        return (upahPerJam * jamKerja + bonus) - hitungPotongan(terlambat);
    }

    public double getUpahPerJam() {
        return upahPerJam;
    }

    public void setUpahPerJam(double upahPerJam) {
        this.upahPerJam = upahPerJam;
    }

    public double getJamKerja() {
        return jamKerja;
    }

    public void setJamKerja(double jamKerja) {
        this.jamKerja = jamKerja;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public int getTerlambat() {
        return terlambat;
    }

    public void setTerlambat(int terlambat) {
        this.terlambat = terlambat;
    }
}