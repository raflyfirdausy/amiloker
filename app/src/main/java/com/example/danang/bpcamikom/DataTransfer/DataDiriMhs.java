package com.example.danang.bpcamikom.DataTransfer;

public class DataDiriMhs {

    private String id, nama, nim, jurusan, tipe_mhs, tahun_lulus;

    public DataDiriMhs() {

    }

    public DataDiriMhs(String id, String nama, String nim, String jurusan, String tipe_mhs, String tahun_lulus) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.jurusan = jurusan;
        this.tipe_mhs = tipe_mhs;
        this.tahun_lulus = tahun_lulus;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setTahunLulus(String tahun_lulus) {
        this.tahun_lulus = tahun_lulus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNIM() {
        return nim;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getTipeMhs() {
        return tipe_mhs;
    }

    public void setTipeMhs(String tipe_mhs) {
        this.tipe_mhs = tipe_mhs;
    }

    public String getTahun_lulus() {
        return tahun_lulus;
    }

}
