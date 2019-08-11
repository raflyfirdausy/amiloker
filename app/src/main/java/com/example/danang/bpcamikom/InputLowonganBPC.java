package com.example.danang.bpcamikom;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.danang.bpcamikom.DataTransfer.Lowongan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InputLowonganBPC extends AppCompatActivity {

    private EditText edJudulLowongan, edKeteranganLowongan, edNamaPerusahaan, edEmailPerusahaan,
            edLokasiPerusahaan, edTeleponPerusahaan, edKualifikasi, edBatasTglPendaftaraan, edGaji;
    private Button btnInputLowongan, btnRNamaPerusahaan, btnREmailPerusahaan, btnRLokasiPerusahaan,
            btnRNoTelpPerusahaan, btnRKualifikasiPerusahaan, btnPilihBatasTanggalPendaftaran;
    private ProgressBar pgInputData;
    private RadioGroup rdTipeLowongan;
    private RadioButton rdFullTime;
    private RadioButton rdFreelance;
    private ProgressDialog progressDialog;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private FirebaseAuth mAuth;
    private DatabaseReference drLowongan;

    private String LowonganId, JudulLowongan, KeteranganLowongan, NamaPerusahaan, EmailPerusahaan,
            LokasiPerusahaan, TeleponPerusahaan, Kualifikasi, JenisLowongan, BatasPendaftaran, Gaji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_lowongan_bpc);

        initialize();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        btnInputLowongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });

        btnRNamaPerusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edNamaPerusahaan.setText("");
            }
        });

        btnREmailPerusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edEmailPerusahaan.setText("");
            }
        });

        btnRLokasiPerusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edLokasiPerusahaan.setText("");
            }
        });

        btnRNoTelpPerusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edTeleponPerusahaan.setText("");
            }
        });

        btnRKualifikasiPerusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edKualifikasi.setText("");
            }
        });

        btnPilihBatasTanggalPendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edBatasTglPendaftaraan.setEnabled(true);
                showDateDialog();
            }
        });
    }

    public void initialize() {
        //Inisialisasi komponen
        edJudulLowongan = findViewById(R.id.JudulLowongan);
        edKeteranganLowongan = findViewById(R.id.KeteranganLowongan);
        edNamaPerusahaan = findViewById(R.id.NamaPerusahaan);
        edEmailPerusahaan = findViewById(R.id.EmailPerusahaan);
        edLokasiPerusahaan = findViewById(R.id.LokasiPerusahaan);
        edTeleponPerusahaan = findViewById(R.id.NoTelpPerusahaan);
        edKualifikasi = findViewById(R.id.Kualifikasi);
        edBatasTglPendaftaraan = findViewById(R.id.BatasTanggalPendaftaran);
        edGaji = findViewById(R.id.Gaji);
        rdTipeLowongan = findViewById(R.id.rdTipeLowongan);
        rdFullTime = findViewById(R.id.rdFullTime);
        rdFreelance = findViewById(R.id.rdFreelance);
        btnInputLowongan = findViewById(R.id.btnInputLowongan);
        btnPilihBatasTanggalPendaftaran = findViewById(R.id.btnPilihBatasTanggalPendaftaran);
        btnRNamaPerusahaan = findViewById(R.id.btnRNamaPerusahaanReset);
        btnREmailPerusahaan = findViewById(R.id.btnREmailPerusahaanReset);
        btnRLokasiPerusahaan = findViewById(R.id.btnRLokasiPerusahaanReset);
        btnRNoTelpPerusahaan = findViewById(R.id.btnRNoTelpPerusahaan);
        btnRKualifikasiPerusahaan = findViewById(R.id.btnRKualifikasiPerusahaan);

        mAuth = FirebaseAuth.getInstance();
        drLowongan = FirebaseDatabase.getInstance().getReference("lowongan");

    }

    public void onRadioButtonClicked(View view) {
        //Apakah radio button sudah dipilih?
        boolean checked = ((RadioButton) view).isChecked();

        // Cek bagian radio button yang sudah dipilih
        switch (view.getId()) {
            case R.id.rdFullTime:
                if (checked) {
                    JenisLowongan = "Full Time";
                    break;
                }
            case R.id.rdFreelance:
                if (checked)
                    JenisLowongan = "Freelance";
                break;
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.show();
    }

    private void showDateDialog() {

        //Calendar untuk mendapatkan tanggal sekarang
        Calendar newCalendar = Calendar.getInstance();

        //Initiate DatePicker dialog
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                //Method ini dipanggil saat kita selesai memilih tanggal di DatePicker

                //Set Calendar untuk menampung tanggal yang dipilih
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                //Update TextView dengan tanggal yang kita pilih
                edBatasTglPendaftaraan.setText(dateFormatter.format(newDate.getTime()));

                edBatasTglPendaftaraan.setEnabled(false);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        //Tampilkan DatePicker dialog
        datePickerDialog.show();
    }

    public void inputData() {
        JudulLowongan = edJudulLowongan.getText().toString();
        KeteranganLowongan = edKeteranganLowongan.getText().toString();
        NamaPerusahaan = edNamaPerusahaan.getText().toString();
        EmailPerusahaan = edEmailPerusahaan.getText().toString();
        LokasiPerusahaan = edLokasiPerusahaan.getText().toString();
        TeleponPerusahaan = edTeleponPerusahaan.getText().toString();
        Kualifikasi = edKualifikasi.getText().toString();
        BatasPendaftaran = edBatasTglPendaftaraan.getText().toString();
        Gaji = edGaji.getText().toString();
        rdTipeLowongan.getCheckedRadioButtonId();

        if (JudulLowongan.isEmpty() || KeteranganLowongan.isEmpty() || NamaPerusahaan.isEmpty() ||
                EmailPerusahaan.isEmpty() || LokasiPerusahaan.isEmpty() || TeleponPerusahaan.isEmpty() || Kualifikasi.isEmpty() ||
                Gaji.isEmpty() || JenisLowongan.isEmpty() || BatasPendaftaran.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Data lowongan harus lengkap", Toast.LENGTH_SHORT).show();
        } else if (TeleponPerusahaan.length() < 10) {
            Toast.makeText(getApplicationContext(), "Nomor Telepon Salah", Toast.LENGTH_SHORT).show();
        } else {
            showProgressDialog();
            String idUser = mAuth.getCurrentUser().getUid();
            String id = drLowongan.push().getKey();
            Lowongan lowongan = new Lowongan(id, JudulLowongan, KeteranganLowongan, NamaPerusahaan,
                    EmailPerusahaan, LokasiPerusahaan, TeleponPerusahaan, Kualifikasi, JenisLowongan, BatasPendaftaran, Gaji);
            drLowongan.child(idUser).child(id).setValue(lowongan).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.cancel();
                    if (task.isSuccessful()) {
                        //Jika sukses input data
                        Toast.makeText(InputLowonganBPC.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();

                    } else {
                        //Jika gagal input data
                        Toast.makeText(InputLowonganBPC.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}
