package com.example.danang.bpcamikom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class IsiDataDiriActivity extends AppCompatActivity {

    private EditText edNamaMhs, edNimMhs, edJurusanMhs, edTipeMhs, edTahunLulus;
    private Button btnInputData, btnRNamaMhs, btnRNimMhs, btnRJurusanMhs, btnTahunLulusMhs;
    private ProgressBar pgInputData;
    private RadioGroup rdTipeMhs, rdTipeJurusan;
    private RadioButton rdTI;
    private RadioButton rdSI;
    private RadioButton rdAktif;
    private RadioButton rdAlumni;
    private Spinner spinnerJurusan;

    private FirebaseAuth mAuth;
    private DatabaseReference drDataMhs, drCheckDataMhs;

    private String dataId, nama, nim, jurusan, tipe_mhs = "Alumni", tahun_lulus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_data_diri);

        initialize();

        btnInputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nama = edNamaMhs.getText().toString();
                nim = edNimMhs.getText().toString();
                tahun_lulus = edTahunLulus.getText().toString();
//                rdTipeJurusan.getCheckedRadioButtonId();


//                rdTipeMhs.getCheckedRadioButtonId();

                if (nama.isEmpty() || nim.isEmpty() || tahun_lulus.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Data belum lengkap", Toast.LENGTH_SHORT).show();
                } else {
                    String idUser = mAuth.getCurrentUser().getUid();
                    drCheckDataMhs = FirebaseDatabase.getInstance().getReference("mahasiswa").child(idUser);
                    drCheckDataMhs.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
                                //Jika data diri mahasiswa tersebut sudah ada
//                                Toast.makeText(getApplicationContext(), "Data sudah ada", Toast.LENGTH_SHORT).show();
//                            } else {
                                //Jika data diri mahasiswa tersebut belum ada
                                drDataMhs = FirebaseDatabase.getInstance().getReference("mahasiswa");
                                inputData();
//                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        btnRNamaMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edNamaMhs.setText("");
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

    private void initialize() {
        //Inisialisasi komponen
        edNamaMhs = findViewById(R.id.NamaMhs);
        edNimMhs = findViewById(R.id.NimMhs);
        rdTipeMhs = findViewById(R.id.rdTipeMhs);
        rdAktif = findViewById(R.id.rdAktif);
        rdAlumni = findViewById(R.id.rdAlumni);
        rdTipeJurusan = findViewById(R.id.rdTipeJurusan);
        rdTI = findViewById(R.id.rdTI);
        rdSI = findViewById(R.id.rdSI);
        edTahunLulus = findViewById(R.id.TahunLulusMhs);
        btnInputData = findViewById(R.id.btnIsiDataDiri);
        btnRNamaMhs = findViewById(R.id.btnRNamaMhsReset);
        btnRNimMhs = findViewById(R.id.btnRNimReset);
        btnTahunLulusMhs = findViewById(R.id.btnRTahunLulusReset);
        spinnerJurusan = findViewById(R.id.spinnerJurusan);

        mAuth = FirebaseAuth.getInstance();
        drDataMhs = FirebaseDatabase.getInstance().getReference("mahasiswa");
        if (getIntent().hasExtra("mode") && getIntent().getStringExtra("mode").equalsIgnoreCase("edit")) {
            btnInputData.setText("Update Data");
        }
    }

    public void onRadioButtonClickedTipeMhs(View view) {
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

    public void onRadioButtonClickedTipeJurusan(View view) {

        //Apakah radio button sudah dipilih?
        boolean checked = ((RadioButton) view).isChecked();

        // Cek bagian radio button yang sudah dipilih
        switch (view.getId()) {
            case R.id.rdTI:
                if (checked) {
                    jurusan = "Teknik Informatika";
                    break;
                }
            case R.id.rdSI:
                if (checked) {
                    jurusan = "Sistem Informasi";
                    break;
                }

        }
    }


    public void inputData() {

        nama = edNamaMhs.getText().toString();
        nim = edNimMhs.getText().toString();
        tahun_lulus = edTahunLulus.getText().toString();
//        rdTipeJurusan.getCheckedRadioButtonId();


//        rdTipeMhs.getCheckedRadioButtonId();


        if (nama.isEmpty() || nim.isEmpty() || tahun_lulus.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Data harus lengkap", Toast.LENGTH_SHORT).show();
        } else {
            String idUser = mAuth.getCurrentUser().getUid();
            DataDiriMhs datadiri = new DataDiriMhs(idUser, nama, nim, spinnerJurusan.getSelectedItem().toString(), tipe_mhs, tahun_lulus);

            drDataMhs.child(idUser).setValue(datadiri).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //JIka sukses input data
                        Toast.makeText(IsiDataDiriActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                        if (getIntent().hasExtra("mode") && getIntent().getStringExtra("mode").equalsIgnoreCase("edit")) {
                            finish();
                        } else {
                            Intent i = new Intent(IsiDataDiriActivity.this, DashboardMahasiswa.class);
                            startActivity(i);
                        }
                    } else {
                        //Jika gagal input data
                        Toast.makeText(IsiDataDiriActivity.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnInputData.setEnabled(false);
        }
    }

    public void onBackPressed() {

        if (getIntent().hasExtra("mode") && getIntent().getStringExtra("mode").equalsIgnoreCase("edit")) {
            super.onBackPressed();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(IsiDataDiriActivity.this);
            builder.setTitle("Keluar");
            builder.setMessage("Apakah anda akan keluar?");

            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(getApplicationContext(), "Berhasil keluar", Toast.LENGTH_LONG).show();
                    finish();
                    Intent i = new Intent(IsiDataDiriActivity.this, LoginMhsActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

}
