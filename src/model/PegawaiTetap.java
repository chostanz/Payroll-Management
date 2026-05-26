package model;

public class PegawaiTetap extends Pegawai {

    private double gajiPokok;
    private double tunjangan;
    private double bonus;
    private int terlambat;

    @Override
    public double hitungGaji() {

        return (gajiPokok + tunjangan + bonus) - hitungPotongan(terlambat);
    }

    public double getGajiPokok() {
        return gajiPokok;
    }

    public void setGajiPokok(double gajiPokok) {
        this.gajiPokok = gajiPokok;
    }

    public double getTunjangan() {
        return tunjangan;
    }

    public void setTunjangan(double tunjangan) {
        this.tunjangan = tunjangan;
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