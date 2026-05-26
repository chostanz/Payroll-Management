package model;

public class PegawaiTetap extends Pegawai {

    private double gajiPokok;
    private double tunjangan;
    private double bonus;
    private int absen;

    @Override
    public double hitungGaji() {

        return (gajiPokok + tunjangan + bonus) - hitungPotongan(absen);
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

    public int getAbsen() {
        return absen;
    }

    public void setAbsen(int absen) {
        this.absen = absen;
    }
}