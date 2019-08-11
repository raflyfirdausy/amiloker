package com.example.danang.bpcamikom.DataTransfer;

public class Lowongan {

    private String id;
    private String judul_lowongan;
    private String keterangan_lowongan;
    private String nama_perusahaan;
    private String email_perusahaan;
    private String lokasi_perusahaan;
    private String telp_perusahaan;
    private String kualifikasi;
    private String jenis_lowongan;
    private String batas_pendaftaran;
    private String gaji;
    private String jurusan;

    public Lowongan(String id, String judul_lowongan, String keterangan_lowongan, String nama_perusahaan,
                    String email_perusahaan, String lokasi_perusahaan, String telp_perusahaan,
                    String kualifikasi, String jenis_lowongan, String batas_pendaftaran, String gaji, String jurusan) {
        this.id = id;
        this.judul_lowongan = judul_lowongan;
        this.keterangan_lowongan = keterangan_lowongan;
        this.nama_perusahaan = nama_perusahaan;
        this.email_perusahaan = email_perusahaan;
        this.lokasi_perusahaan = lokasi_perusahaan;
        this.telp_perusahaan = telp_perusahaan;
        this.kualifikasi = kualifikasi;
        this.jenis_lowongan = jenis_lowongan;
        this.gaji = gaji;
        this.batas_pendaftaran = batas_pendaftaran;
        this.jurusan = jurusan;
    }

    public void setJudul_lowongan(String judul_lowongan) {
        this.judul_lowongan = judul_lowongan;
    }

    public void setKeterangan_lowongan(String keterangan_lowongan) {
        this.keterangan_lowongan = keterangan_lowongan;
    }

    public void setNama_perusahaan(String nama_perusahaan) {
        this.nama_perusahaan = nama_perusahaan;
    }

    public void setEmail_perusahaan(String email_perusahaan) {
        this.email_perusahaan = email_perusahaan;
    }

    public void setLokasi_perusahaan(String lokasi_perusahaan) {
        this.lokasi_perusahaan = lokasi_perusahaan;
    }

    public void setTelp_perusahaan(String telp_perusahaan) {
        this.telp_perusahaan = telp_perusahaan;
    }

    public void setJenis_lowongan(String jenis_lowongan) {
        this.jenis_lowongan = jenis_lowongan;
    }

    public String getJurusan() {
        return jurusan;
    }

    public Lowongan() {

    }

    public Lowongan(String id, String judul_lowongan, String keterangan_lowongan, String nama_perusahaan,
                    String email_perusahaan, String lokasi_perusahaan, String telp_perusahaan,
                    String kualifikasi, String jenis_lowongan, String batas_pendaftaran, String gaji) {
        this.id = id;
        this.judul_lowongan = judul_lowongan;
        this.keterangan_lowongan = keterangan_lowongan;
        this.nama_perusahaan = nama_perusahaan;
        this.email_perusahaan = email_perusahaan;
        this.lokasi_perusahaan = lokasi_perusahaan;
        this.telp_perusahaan = telp_perusahaan;
        this.kualifikasi = kualifikasi;
        this.jenis_lowongan = jenis_lowongan;
        this.gaji = gaji;
        this.batas_pendaftaran = batas_pendaftaran;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public void setJudulLowongan(String judul_lowongan) {
        this.judul_lowongan = judul_lowongan;
    }

    public void setKeteranganLowongan(String keterangan_lowongan) {
        this.keterangan_lowongan = keterangan_lowongan;
    }

    public void setNamaPerusahaan(String nama_perusahaan) {
        this.nama_perusahaan = nama_perusahaan;
    }

    public void setEmailPerusahaan(String email_perusahaan) {
        this.email_perusahaan = email_perusahaan;
    }

    public void setLokasiPerusahaan(String lokasi_perusahaan) {
        this.lokasi_perusahaan = lokasi_perusahaan;
    }

    public void setTelpPerusahaan(String telp_perusahaan) {
        this.telp_perusahaan = telp_perusahaan;
    }

    public void setJenisLowongan(String jenis_lowongan) {
        this.jenis_lowongan = jenis_lowongan;
    }

    //Getter
    public String getId() {
        return id;
    }

    //Setter
    public void setId(String id) {
        this.id = id;
    }

    public String getJudul_lowongan() {
        return judul_lowongan;
    }

    public String getKeterangan_lowongan() {
        return keterangan_lowongan;
    }

    public String getNama_perusahaan() {
        return nama_perusahaan;
    }

    public String getEmail_perusahaan() {
        return email_perusahaan;
    }

    public String getLokasi_perusahaan() {
        return lokasi_perusahaan;
    }

    public String getTelp_perusahaan() {
        return telp_perusahaan;
    }

    public String getKualifikasi() {
        return kualifikasi;
    }

    public void setKualifikasi(String kualifikasi) {
        this.kualifikasi = kualifikasi;
    }

    public String getJenis_lowongan() {
        return jenis_lowongan;
    }

    public String getBatas_pendaftaran() {
        return batas_pendaftaran;
    }

    public void setBatas_pendaftaran(String batas_pendaftaran) {
        this.batas_pendaftaran = batas_pendaftaran;
    }

    public String getGaji() {
        return gaji;
    }

    public void setGaji(String gaji) {
        this.gaji = gaji;
    }
}


