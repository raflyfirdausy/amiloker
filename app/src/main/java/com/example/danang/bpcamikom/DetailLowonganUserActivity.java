package com.example.danang.bpcamikom;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailLowonganUserActivity extends AppCompatActivity {

    private final static int PERMISSION_PHONE_REQUEST = 92;
    TextView txtJudulLowongan, txtKeteranganLowongan, txtJenisPekerjaan, txtNamaPerusahaan, txtEmailPerusahaan,
            txtLokasiPerusahaan, txtKualifikasi, txtBatasPendaftaran, txtGaji;
    Button btnApply, btnTelepon, btnCekAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lowongan_user);

        initialize();

        final String sUJudulLowongan, sUKeteranganLowongan, sUNamaPerusahaan, sUEmailPerusahaan, sUNoTelpPerusahaan,
                sULokasiPerusahaan, sUKualifikasi, sUJenisPekerjaan, sUBatasPendaftaran, sUGaji;

        Intent iU = getIntent();

        //Mengambil data dari activity ListLowongan
        sUJudulLowongan = iU.getStringExtra(ListLowonganUser.exUJudulLowongan);
        sUKeteranganLowongan = iU.getStringExtra(ListLowonganUser.exUKeteranganLowongan);
        sUNamaPerusahaan = iU.getStringExtra(ListLowonganUser.exUNamaPerusahaan);
        sUEmailPerusahaan = iU.getStringExtra(ListLowonganUser.exUEmailPerusahaan);
        sUNoTelpPerusahaan = iU.getStringExtra(ListLowonganUser.exUNoTelpPerusahaan);
        sULokasiPerusahaan = iU.getStringExtra(ListLowonganUser.exULokasiPerusahaan);
        sUKualifikasi = iU.getStringExtra(ListLowonganUser.exUKualifikasi);
        sUJenisPekerjaan = iU.getStringExtra(ListLowonganUser.exUJenisLowongan);
        sUBatasPendaftaran = iU.getStringExtra(ListLowonganUser.exUBatasPendaftaran);
        sUGaji = iU.getStringExtra(ListLowonganUser.exUGaji);

        //Set data ke dalam komponen
        txtKeteranganLowongan.setText(sUKeteranganLowongan);
        txtNamaPerusahaan.setText(sUNamaPerusahaan);
        txtEmailPerusahaan.setText(sUEmailPerusahaan);
        txtLokasiPerusahaan.setText(sULokasiPerusahaan);
        txtKualifikasi.setText(sUKualifikasi);
        txtJenisPekerjaan.setText(sUJenisPekerjaan);
        txtBatasPendaftaran.setText(sUBatasPendaftaran);
        txtGaji.setText(sUGaji);

        getSupportActionBar().setTitle(sUJudulLowongan);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri emailReceive = Uri.parse(sUEmailPerusahaan);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailReceive));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, sUJudulLowongan);
                startActivity(emailIntent);
            }
        });

        btnTelepon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(DetailLowonganUserActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetailLowonganUserActivity.this,
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
        //Inisialisasi komponen
        txtKeteranganLowongan = findViewById(R.id.txtSKeteranganLowongan);
        txtNamaPerusahaan = findViewById(R.id.txtSNamaPerusahaan);
        txtEmailPerusahaan = findViewById(R.id.txtSEmailPerusahaan);
        txtLokasiPerusahaan = findViewById(R.id.txtSLokasiPerusahaan);
        txtKualifikasi = findViewById(R.id.txtSKualifikasi);
        txtJenisPekerjaan = findViewById(R.id.txtSJenisPekerjaan);
        txtBatasPendaftaran = findViewById(R.id.txtSBatasPendaftaran);
        txtGaji = findViewById(R.id.txtSGaji);
        btnApply = findViewById(R.id.btnApply);
        btnTelepon = findViewById(R.id.btnTelepon);
        btnCekAlamat = findViewById(R.id.btnCekAlamat);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_PHONE_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Anda tidak mengijinkan menggunakan telepon", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
