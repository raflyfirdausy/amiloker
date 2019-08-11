package com.example.danang.bpcamikom;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailLowonganAdminActivity extends AppCompatActivity {

    private final static int PERMISSION_PHONE_REQUEST = 92;
    TextView txtJudulLowongan, txtKeteranganLowongan, txtJenisPekerjaan, txtNamaPerusahaan, txtEmailPerusahaan,
            txtLokasiPerusahaan, txtKualifikasi, txtBatasPendaftaran, txtGaji;
    Button btnTelepon, btnCekAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lowongan_admin);

        initialize();

        final String sUJudulLowongan, sUKeteranganLowongan, sUNamaPerusahaan, sUEmailPerusahaan, sUNoTelpPerusahaan,
                sULokasiPerusahaan, sUKualifikasi, sUJenisPekerjaan, sUBatasPendaftaran, sUGaji;

        Intent iU = getIntent();

        //Mengambil data dari activity ListLowongan
        sUJudulLowongan = iU.getStringExtra(ListLowongan.exAJudulLowongan);
        sUKeteranganLowongan = iU.getStringExtra(ListLowongan.exAKeteranganLowongan);
        sUNamaPerusahaan = iU.getStringExtra(ListLowongan.exANamaPerusahaan);
        sUEmailPerusahaan = iU.getStringExtra(ListLowongan.exAEmailPerusahaan);
        sUNoTelpPerusahaan = iU.getStringExtra(ListLowongan.exANoTelpPerusahaan);
        sULokasiPerusahaan = iU.getStringExtra(ListLowongan.exALokasiPerusahaan);
        sUKualifikasi = iU.getStringExtra(ListLowongan.exAKualifikasi);
        sUJenisPekerjaan = iU.getStringExtra(ListLowongan.exAJenisLowongan);
        sUBatasPendaftaran = iU.getStringExtra(ListLowongan.exABatasPendaftaran);
        sUGaji = iU.getStringExtra(ListLowongan.exAGaji);

        //Set data ke dalam komponen
        txtKeteranganLowongan.setText(sUKeteranganLowongan);
        txtNamaPerusahaan.setText(sUNamaPerusahaan);
        txtEmailPerusahaan.setText(sUEmailPerusahaan);
        txtLokasiPerusahaan.setText(sULokasiPerusahaan);
        txtKualifikasi.setText(sUKualifikasi);
        txtJenisPekerjaan.setText(sUJenisPekerjaan);
        txtBatasPendaftaran.setText(sUBatasPendaftaran);
        txtGaji.setText("Rp " + sUGaji);

        getSupportActionBar().setTitle(sUJudulLowongan);

        btnTelepon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(DetailLowonganAdminActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetailLowonganAdminActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            PERMISSION_PHONE_REQUEST);
                    return;
                } else {
                    Intent iTelp = new Intent(Intent.ACTION_CALL);
                    iTelp.setData(Uri.parse("tel:" + sUNoTelpPerusahaan));

                    startActivity(iTelp);
                }
            }
        });

        btnCekAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String address = sULokasiPerusahaan;
                    address = address.replace(" ", "+");
                    Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address)); // Prepare intent
                    startActivity(geoIntent);
                } catch (Exception e) {

                }
            }
        });

    }

    public void initialize() {
        txtKeteranganLowongan = findViewById(R.id.txtSKeteranganLowongan);
        txtNamaPerusahaan = findViewById(R.id.txtSNamaPerusahaan);
        txtEmailPerusahaan = findViewById(R.id.txtSEmailPerusahaan);
        txtLokasiPerusahaan = findViewById(R.id.txtSLokasiPerusahaan);
        txtKualifikasi = findViewById(R.id.txtSKualifikasi);
        txtJenisPekerjaan = findViewById(R.id.txtSJenisPekerjaan);
        txtGaji = findViewById(R.id.txtSGaji);
        txtBatasPendaftaran = findViewById(R.id.txtSBatasPendaftaran);
        btnCekAlamat = findViewById(R.id.btnCekAlamat);
        btnTelepon = findViewById(R.id.btnTelepon);
    }
}
