package model;

public class PegawaiPartTime extends Pegawai {

    private double upahPerJam;
    private double jamKerja;
    private double bonus;
    private int absen;

    @Override
    public double hitungGaji() {

        return (upahPerJam * jamKerja + bonus) - hitungPotongan(absen);
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

    public int getAbsen() {
        return absen;
    }

    public void setAbsen(int absen) {
        this.absen = absen;
    }
}