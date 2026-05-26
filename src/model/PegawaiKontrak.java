package model;

public class PegawaiKontrak extends Pegawai {

    private double upahPerBulan;
    private double bonus;
    private int absen;

    @Override
    public double hitungGaji() {

        return (upahPerBulan + bonus)- hitungPotongan(absen);
    }

    public double getUpahPerBulan() {
        return upahPerBulan;
    }

    public void setUpahPerBulan(double upahPerBulan) {
        this.upahPerBulan = upahPerBulan;
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