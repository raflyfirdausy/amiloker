package com.example.danang.bpcamikom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.danang.bpcamikom.ArrayAdapter.DaftarLowonganAdmin;
import com.example.danang.bpcamikom.DataTransfer.Lowongan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListLowongan extends AppCompatActivity {

    static String exUId, exUJudulLowongan, exUNamaPerusahaan, exUKeteranganLowongan, exUEmailPerusahaan,
            exULokasiPerusahaan, exUNoTelpPerusahaan, exUJenisLowongan, exUBatasPendaftaraan, exUKualifikasi, exUGaji;
    static String exAJudulLowongan, exANamaPerusahaan, exAKeteranganLowongan, exAEmailPerusahaan,
            exALokasiPerusahaan, exANoTelpPerusahaan, exAJenisLowongan, exABatasPendaftaran, exAKualifikasi, exAGaji;
    List<Lowongan> daftarLowongan;
    private ListView listViewDaftarLowonganAdmin;
    private DatabaseReference drLowongan;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lowongan);

        initialize();

        listViewDaftarLowonganAdmin.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Lowongan lowongan = daftarLowongan.get(i);

                showOptionDialog(lowongan.getId(), lowongan.getJudul_lowongan(), lowongan.getKeterangan_lowongan(), lowongan.getNama_perusahaan(),
                        lowongan.getEmail_perusahaan(), lowongan.getLokasi_perusahaan(), lowongan.getTelp_perusahaan(),
                        lowongan.getKualifikasi(), lowongan.getJenis_lowongan(), lowongan.getBatas_pendaftaran(), lowongan.getGaji());

                return true;
            }
        });

        listViewDaftarLowonganAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lowongan vacancies = daftarLowongan.get(i);

                showDetail(vacancies.getJudul_lowongan(), vacancies.getKeterangan_lowongan(), vacancies.getNama_perusahaan(),
                        vacancies.getEmail_perusahaan(), vacancies.getLokasi_perusahaan(), vacancies.getTelp_perusahaan(),
                        vacancies.getKualifikasi(), vacancies.getJenis_lowongan(), vacancies.getBatas_pendaftaran(), vacancies.getGaji());

            }
        });
    }

    private void initialize() {
        //Inisialisasi komponen
        listViewDaftarLowonganAdmin = findViewById(R.id.listViewDaftarLowonganAdmin);

        daftarLowongan = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        drLowongan = FirebaseDatabase.getInstance().getReference("lowongan").child(mAuth.getCurrentUser().getUid());
    }

    public void showDetail(String JudulLowongan, final String KeteranganLowongan, String NamaPerusahaan,
                           String EmailPerusahaan, String LokasiPerusahaan, String NoTelpPerusahaan, String Kualifikasi, String JenisLowongan, String BatasPendaftaran, String Gaji) {

        exAJudulLowongan = "JUDUL LOWONGAN";
        exAKeteranganLowongan = "KETERANGAN LOWONGAN";
        exANamaPerusahaan = "NAMA PERUSAHAAN";
        exAEmailPerusahaan = "EMAIL PERUSAHAAN";
        exALokasiPerusahaan = "LOKASI PERUSAHAAN";
        exANoTelpPerusahaan = "NO.TELP PERUSAHAAN";
        exAJenisLowongan = "JENIS LOWONGAN";
        exABatasPendaftaran = "BATAS PENDAFTARAN";
        exAKualifikasi = "KUALIFIKASI";
        exAGaji = "GAJI";

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

        Intent i = new Intent(ListLowongan.this, DetailLowonganAdminActivity.class);
        i.putExtra(exAJudulLowongan, judulLowongan);
        i.putExtra(exAKeteranganLowongan, keteranganLowongan);
        i.putExtra(exANamaPerusahaan, namaPerusahaan);
        i.putExtra(exAEmailPerusahaan, emailPerusahaan);
        i.putExtra(exALokasiPerusahaan, lokasiPerusahaan);
        i.putExtra(exANoTelpPerusahaan, noTelpPerusahaan);
        i.putExtra(exAJenisLowongan, jenisLowongan);
        i.putExtra(exABatasPendaftaran, batasPendaftaran);
        i.putExtra(exAKualifikasi, kualifikasi);
        i.putExtra(exAGaji, gaji);
        startActivity(i);
    }

    private void showOptionDialog(final String idLowongan, final String JudulLowongan, final String KeteranganLowongan, String NamaPerusahaan,
                                  String EmailPerusahaan, String LokasiPerusahaan, String NoTelpPerusahaan, String Kualifikasi, String JenisLowongan, String BatasPendaftaran, String Gaji) {

        final String idLowonganO, JudulLowonganO, NamaPerusahaanO, KeteranganLowonganO, EmailPerusahaanO, LokasiPerusahaanO,
                NoTelpPerusahaanO, JenisLowonganO, BatasPendaftaranO, KualifikasiO, GajiO;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ListLowongan.this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.option_dialog, null);

        dialogBuilder.setView(dialogView);

        exUId = "IDEDIT";
        exUJudulLowongan = "JUDUL LOWONGAN";
        exUKeteranganLowongan = "KETERANGAN LOWONGAN";
        exUNamaPerusahaan = "NAMA PERUSAHAAN";
        exUEmailPerusahaan = "EMAIL PERUSAHAAN";
        exULokasiPerusahaan = "LOKASI PERUSAHAAN";
        exUNoTelpPerusahaan = "NO.TELP PERUSAHAAN";
        exUJenisLowongan = "JENIS LOWONGAN";
        exUBatasPendaftaraan = "BATAS PENDAFTARAN";
        exUKualifikasi = "KUALIFIKASI";
        exUGaji = "GAJI";

        idLowonganO = idLowongan;
        JudulLowonganO = JudulLowongan;
        KeteranganLowonganO = KeteranganLowongan;
        NamaPerusahaanO = NamaPerusahaan;
        EmailPerusahaanO = EmailPerusahaan;
        LokasiPerusahaanO = LokasiPerusahaan;
        NoTelpPerusahaanO = NoTelpPerusahaan;
        JenisLowonganO = JenisLowongan;
        BatasPendaftaranO = BatasPendaftaran;
        KualifikasiO = Kualifikasi;
        GajiO = Gaji;

        final Button btnEdit, btnHapus;

        btnEdit = dialogView.findViewById(R.id.btnAEdit);
        btnHapus = dialogView.findViewById(R.id.btnHapusData);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mengambil data pada list yang diklik, lalu mengirimkan ke activity EditLowonganActivity
                Intent editIntent = new Intent(ListLowongan.this, EditLowonganActivity.class);
                editIntent.putExtra(exUId, idLowonganO);
                editIntent.putExtra(exUJudulLowongan, JudulLowonganO);
                editIntent.putExtra(exUKeteranganLowongan, KeteranganLowonganO);
                editIntent.putExtra(exUNamaPerusahaan, NamaPerusahaanO);
                editIntent.putExtra(exUEmailPerusahaan, EmailPerusahaanO);
                editIntent.putExtra(exULokasiPerusahaan, LokasiPerusahaanO);
                editIntent.putExtra(exUNoTelpPerusahaan, NoTelpPerusahaanO);
                editIntent.putExtra(exUJenisLowongan, JenisLowonganO);
                editIntent.putExtra(exUBatasPendaftaraan, BatasPendaftaranO);
                editIntent.putExtra(exUKualifikasi, KualifikasiO);
                editIntent.putExtra(exUGaji, GajiO);

                startActivity(editIntent);
                alertDialog.dismiss();
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Menghapus data sesuai data yang dipilih dengan cara long press
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ListLowongan.this);
                builder1.setTitle(JudulLowongan);
                builder1.setMessage("Apakah anda yakin ingin menghapus Lowongan ini?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseReference drDataLowongan = FirebaseDatabase.getInstance().getReference("lowongan").child(mAuth.getCurrentUser().getUid()).child(idLowongan);

                                drDataLowongan.removeValue();

                                alertDialog.dismiss();

                                Toast.makeText(getApplicationContext(), "Data telah dihapus", Toast.LENGTH_SHORT).show();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alertDialog.dismiss();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        drLowongan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarLowongan.clear();
                for (DataSnapshot lowonganSnapshot : dataSnapshot.getChildren()) {
                    Lowongan lowongan = lowonganSnapshot.getValue(Lowongan.class);
                    daftarLowongan.add(lowongan);
                }

                DaftarLowonganAdmin adapter = new DaftarLowonganAdmin(ListLowongan.this, daftarLowongan);
                listViewDaftarLowonganAdmin.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
