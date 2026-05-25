package model;

public abstract class Pegawai {

    protected int idPegawai;
    protected String nik;
    protected String nama;
    protected String jabatan;
    protected String jenisPegawai;

    public abstract double hitungGaji();

    public double hitungPotongan(int terlambat) {
        return 100000 + (terlambat * 20000);
    }

    public int getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(int idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getJenisPegawai() {
        return jenisPegawai;
    }

    public void setJenisPegawai(String jenisPegawai) {
        this.jenisPegawai = jenisPegawai;
    }
}