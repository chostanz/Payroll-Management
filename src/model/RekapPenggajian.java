package model;

public class RekapPenggajian {
    private int    bulan;
    private int    tahun;
    private int    jumlahPegawai;
    private int    jumlahTetap;
    private int    jumlahKontrak;
    private int    jumlahParttime;
    private double totalGajiKotor;
    private double totalPotongan;
    private double totalGajiBersih;

    public RekapPenggajian() {}

    public int getBulan() { 
        return bulan; 
    }
    public void setBulan(int bulan){
        this.bulan = bulan; 
    }

    public int getTahun(){
        return tahun; 
    }
    public void setTahun(int tahun){
        this.tahun = tahun; 
    }

    public int getJumlahPegawai(){ 
        return jumlahPegawai; 
    }
    public void setJumlahPegawai(int jumlahPegawai) {
        this.jumlahPegawai = jumlahPegawai; 
    }

    public int getJumlahTetap(){ 
        return jumlahTetap; 
    }
    public void setJumlahTetap(int jumlahTetap) {
        this.jumlahTetap = jumlahTetap; 
    }

    public int getJumlahKontrak() { 
        return jumlahKontrak; 
    }
    public void setJumlahKontrak(int jumlahKontrak) { 
        this.jumlahKontrak = jumlahKontrak; 
    }

    public int getJumlahParttime(){ 
        return jumlahParttime; 
    }
    public void setJumlahParttime(int jumlahParttime) { 
        this.jumlahParttime = jumlahParttime; 
    }

    public double getTotalGajiKotor(){ 
        return totalGajiKotor; 
    }
    public void setTotalGajiKotor(double totalGajiKotor)  {
        this.totalGajiKotor = totalGajiKotor; 
    }

    public double getTotalPotongan(){ 
        return totalPotongan; 
    }
    public void setTotalPotongan(double totalPotongan)  {
        this.totalPotongan = totalPotongan; 
    }

    public double getTotalGajiBersih() { 
        return totalGajiBersih; 
    }
    public void setTotalGajiBersih(double totalGajiBersih)  {
        this.totalGajiBersih = totalGajiBersih; 
    }
}
