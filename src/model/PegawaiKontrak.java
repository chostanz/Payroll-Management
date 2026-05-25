package model;

public class PegawaiKontrak extends Pegawai {

    private double upahPerBulan;
    private double bonus;
    private int terlambat;

    @Override
    public double hitungGaji() {

        return (upahPerBulan + bonus)
                - hitungPotongan(terlambat);
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

    public int getTerlambat() {
        return terlambat;
    }

    public void setTerlambat(int terlambat) {
        this.terlambat = terlambat;
    }
}