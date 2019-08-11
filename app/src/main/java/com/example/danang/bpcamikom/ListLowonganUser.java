package com.example.danang.bpcamikom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.danang.bpcamikom.ArrayAdapter.DaftarLowonganAdmin;
import com.example.danang.bpcamikom.DataTransfer.Lowongan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListLowonganUser extends AppCompatActivity {

    static String exUJudulLowongan, exUNamaPerusahaan, exUKeteranganLowongan, exUEmailPerusahaan,
            exULokasiPerusahaan, exUNoTelpPerusahaan, exUJenisLowongan, exUBatasPendaftaran, exUKualifikasi, exUGaji;
    private ListView lvShowLowongan;
    private DatabaseReference drLowongan;
    private List<Lowongan> daftarLowongan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lowongan_user);

        initialize();

        drLowongan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userKeySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot itemKeysnapshoot : userKeySnapshot.getChildren()) {
                        Lowongan lowongan = itemKeysnapshoot.getValue(Lowongan.class);
                        daftarLowongan.add(lowongan);
                    }
                }

                DaftarLowonganAdmin adapter = new DaftarLowonganAdmin(ListLowonganUser.this, daftarLowongan);
                lvShowLowongan.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lvShowLowongan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lowongan lowongan = daftarLowongan.get(i);

                showDetailLowonganUser(lowongan.getJudul_lowongan(), lowongan.getNama_perusahaan(), lowongan.getKeterangan_lowongan(), lowongan.getEmail_perusahaan(),
                        lowongan.getLokasi_perusahaan(), lowongan.getTelp_perusahaan(), lowongan.getJenis_lowongan(), lowongan.getBatas_pendaftaran(), lowongan.getKualifikasi(), lowongan.getGaji());
            }
        });
    }

    private void initialize() {
        //Inisialisasi komponen
        lvShowLowongan = findViewById(R.id.lvDaftarLowonganUser);
        drLowongan = FirebaseDatabase.getInstance().getReference("lowongan");
        daftarLowongan = new ArrayList<>();
    }

    private void showDetailLowonganUser(String JudulLowongan, String NamaPerusahaan, String KeteranganLowongan,
                                        String EmailPerusahaan, String LokasiPerusahaan, String NoTelpPerusahaan, String JenisLowongan, String BatasPendaftaran, String Kualifikasi, String Gaji) {

        exUJudulLowongan = "JUDUL LOWONGAN";
        exUKeteranganLowongan = "KETERANGAN LOWONGAN";
        exUNamaPerusahaan = "NAMA PERUSAHAAN";
        exUEmailPerusahaan = "EMAIL PERUSAHAAN";
        exULokasiPerusahaan = "LOKASI PERUSAHAAN";
        exUNoTelpPerusahaan = "NO.TELP PERUSAHAAN";
        exUJenisLowongan = "JENIS LOWONGAN";
        exUBatasPendaftaran = "BATAS PENDAFTARAN";
        exUKualifikasi = "KUALIFIKASI";
        exUGaji = "GAJI";

        String judulLowongan, keteranganLowongan, namaPerusahaan, emailPerusahaan, lokasiPerusahaan,
                noTelpPerusahaan, jenisLowongan, batasPendaftaran, kualifikasi, gaji;

        judulLowongan = JudulLowongan;
        keteranganLowongan = KeteranganLowongan;
        namaPerusahaan = NamaPerusahaan;
        emailPerusahaan = EmailPerusahaan;
        lokasiPerusahaan = LokasiPerusahaan;
        noTelpPerusahaan = NoTelpPerusahaan;
        jenisLowongan = JenisLowongan;
        batasPendaftaran = BatasPendaftaran;
        kualifikasi = Kualifikasi;
        gaji = Gaji;

        //Mengambil data pada list yang diklik, lalu mengirimkan ke activity DetailLowonganActivity
        Intent i = new Intent(ListLowonganUser.this, DetailLowonganUserActivity.class);
        i.putExtra(exUJudulLowongan, judulLowongan);
        i.putExtra(exUKeteranganLowongan, keteranganLowongan);
        i.putExtra(exUNamaPerusahaan, namaPerusahaan);
        i.putExtra(exUEmailPerusahaan, emailPerusahaan);
        i.putExtra(exULokasiPerusahaan, lokasiPerusahaan);
        i.putExtra(exUNoTelpPerusahaan, noTelpPerusahaan);
        i.putExtra(exUJenisLowongan, jenisLowongan);
        i.putExtra(exUKualifikasi, kualifikasi);
        i.putExtra(exUBatasPendaftaran, batasPendaftaran);
        i.putExtra(exUGaji, gaji);

        startActivity(i);

    }
}
