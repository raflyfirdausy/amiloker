package com.example.danang.bpcamikom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.danang.bpcamikom.DataTransfer.DataDiriMhs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateDataDiriActivity extends AppCompatActivity {

    private EditText edNamaMhs, edNimMhs, edJurusanMhs, edTipeMhs, edTahunLulus;
    private Button btnUpdateDaraDiri, btnRNamaMhs, btnRNimMhs, btnRJurusanMhs, btnTahunLulusMhs;
    private ProgressBar pgInputData;
    private RadioGroup rdUpdateTipeMhs;
    private RadioButton rdAktif;
    private RadioButton rdAlumni;
    private RadioGroup rdTipeJurusan;
    private RadioButton rdUpdateTI;
    private RadioButton rdUpdateSI;

    private FirebaseAuth mAuth;
    private DatabaseReference drDataMhs;


    private String dataId, nama, nim, jurusan, tipe_mhs, tahun_lulus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_diri);

        initialize();

        drDataMhs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Ambil data diri dari database firebase
                for (DataSnapshot drmhsSnapshot : dataSnapshot.getChildren()) {
                    DataDiriMhs datamhs = drmhsSnapshot.getValue(DataDiriMhs.class);
                    //Set data ke komponen
                    dataId = datamhs.getId();
                    edNamaMhs.setText(datamhs.getNama());
                    edNimMhs.setText(datamhs.getNIM());
                    jurusan = datamhs.getJurusan();
                    tipe_mhs = datamhs.getTipeMhs();
                    edTahunLulus.setText(datamhs.getTahun_lulus());

                    if (datamhs.getJurusan().equals("Teknik Informatika")) {
                        rdUpdateTI.setChecked(true);
                    } else {
                        rdUpdateSI.setChecked(true);
                    }


                    if (datamhs.getTipeMhs().equals("Aktif")) {
                        rdAktif.setChecked(true);
                    } else {
                        rdAlumni.setChecked(true);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnUpdateDaraDiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataDiri();
            }
        });

        btnRNamaMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edNimMhs.setText("");
            }
        });

        btnRNimMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edNimMhs.setText("");
            }
        });

        btnTahunLulusMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edTahunLulus.setText("");
            }
        });
    }

    private void updateDataDiri() {
        nama = edNamaMhs.getText().toString().trim();
        nim = edNimMhs.getText().toString().trim();
        rdTipeJurusan.getCheckedRadioButtonId();
        rdUpdateTipeMhs.getCheckedRadioButtonId();
        tahun_lulus = edTahunLulus.getText().toString().trim();

        drDataMhs = FirebaseDatabase.getInstance().getReference("mahasiswa").child(mAuth.getCurrentUser().getUid()).child(dataId);

        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(nim) || TextUtils.isEmpty(jurusan) || TextUtils.isEmpty(tipe_mhs) || TextUtils.isEmpty(tahun_lulus)) {
            Toast.makeText(getApplicationContext(), "Data harus lengkap", Toast.LENGTH_SHORT).show();
        } else {
            drDataMhs = FirebaseDatabase.getInstance().getReference("mahasiswa").child(mAuth.getCurrentUser().getUid()).child(dataId);

            DataDiriMhs datadirimhs1 = new DataDiriMhs(dataId, nama, nim, jurusan, tipe_mhs, tahun_lulus);

            drDataMhs.setValue(datadirimhs1).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //Jika data diri berhasil diupdate
                        Toast.makeText(getApplicationContext(), "Data berhasi diupdate", Toast.LENGTH_SHORT).show();
                    } else {
                        //Jika data diri gagal di update
                        Toast.makeText(getApplicationContext(), "Gagal update data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initialize() {
        //Inisialisasi komponen
        edNamaMhs = findViewById(R.id.NamaMhs);
        edNimMhs = findViewById(R.id.NimMhs);
        rdUpdateTipeMhs = findViewById(R.id.rdUpdateTipeMhs);
        rdAktif = findViewById(R.id.rdAktif);
        rdAlumni = findViewById(R.id.rdAlumni);
        edTahunLulus = findViewById(R.id.TahunLulusMhs);
        rdTipeJurusan = findViewById(R.id.rdUpdateTipeJurusan);
        rdUpdateTI = findViewById(R.id.rdUpdateTI);
        rdUpdateSI = findViewById(R.id.rdUpdateSI);
        edTahunLulus = findViewById(R.id.TahunLulusMhs);
        btnUpdateDaraDiri = findViewById(R.id.btnUpdateDaraDiri);
        btnRNamaMhs = findViewById(R.id.btnRNamaMhsReset);
        btnRNimMhs = findViewById(R.id.btnRNimReset);
        btnTahunLulusMhs = findViewById(R.id.btnRTahunLulusReset);

        mAuth = FirebaseAuth.getInstance();
        drDataMhs = FirebaseDatabase.getInstance().getReference("mahasiswa").child(mAuth.getCurrentUser().getUid());
    }

    public void TipeMhs(View view) {
        //Apakah radio button sudah dipilih?
        boolean checked = ((RadioButton) view).isChecked();

        // Cek bagian radio button yang sudah dipilih
        switch (view.getId()) {
            case R.id.rdAktif:
                if (checked) {
                    tipe_mhs = "Aktif";
                    edTahunLulus.setText("Belum Lulus");
                    break;
                }
            case R.id.rdAlumni:
                if (checked)
                    tipe_mhs = "Alumni";
                edTahunLulus.setText("");
                edTahunLulus.setHint("Tahun Lulus");
                break;
        }
    }

    public void onRadioButtonClickedUpdateTipeJurusan(View view) {

        //Apakah radio button sudah dipilih?
        boolean checked = ((RadioButton) view).isChecked();

        // Cek bagian radio button yang sudah dipilih
        switch (view.getId()) {
            case R.id.rdUpdateTI:
                if (checked) {
                    jurusan = "Teknik Informatika";
                    break;
                }
            case R.id.rdUpdateSI:
                if (checked)
                    jurusan = "Sistem Informasi";
                break;
        }
    }
}
