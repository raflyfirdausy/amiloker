package com.example.danang.bpcamikom;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class EditLowonganActivity extends AppCompatActivity {


    EditText edJudulLowongan, edKeteranganLowongan, edNamaPerusahaan, edEmailPerusahaan,
            edLokasiPerusahaan, edTeleponPerusahaan, edKualifikasi, edBatasPendaftaran, edGaji;

    String eId, eJudulLowongan, eKeteranganLowongan, eNamaPerusahaan, eEmailPerusahaan,
            eLokasiPerusahaan, eTeleponPerusahaan, eKualifikasi, eJenisLowongan, eBatasPendaftaran, eGaji;

    String getId, getJudulLowongan, getKeteranganLowongan, getNamaPerusahaan, getEmailPerusahaan,
            getLokasiPerusahaan, getTeleponPerusahaan, getKualifikasi, getJenisLowongan, getBatasPendaftaran, getGaji;

    private Button btnEditLowongan, btnRNamaPerusahaan, btnREmailPerusahaan, btnRLokasiPerusahaan,
            btnRNoTelpPerusahaan, btnRKualifikasiPerusahaan, btnPilihBatasPendaftaran;

    private RadioGroup rdUTipeLowongan;
    private RadioButton rdUFullTime;
    private RadioButton rdUFreelance;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private FirebaseAuth mAuth;
    private DatabaseReference drLowongan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lowongan);

        initialize();

        Intent iE = getIntent();

        //Ambil data dari activity ListLowongan
        getId = iE.getStringExtra(ListLowongan.exUId);
        getJudulLowongan = iE.getStringExtra(ListLowongan.exUJudulLowongan);
        getKeteranganLowongan = iE.getStringExtra(ListLowongan.exUKeteranganLowongan);
        getNamaPerusahaan = iE.getStringExtra(ListLowongan.exUNamaPerusahaan);
        getEmailPerusahaan = iE.getStringExtra(ListLowongan.exUEmailPerusahaan);
        getLokasiPerusahaan = iE.getStringExtra(ListLowongan.exULokasiPerusahaan);
        getTeleponPerusahaan = iE.getStringExtra(ListLowongan.exUNoTelpPerusahaan);
        getKualifikasi = iE.getStringExtra(ListLowongan.exUKualifikasi);
        getJenisLowongan = iE.getStringExtra(ListLowongan.exUJenisLowongan);
        getBatasPendaftaran = iE.getStringExtra(ListLowongan.exUBatasPendaftaraan);

        if (getJenisLowongan.equals("Full Time")) {
            rdUFullTime.setChecked(true);
        } else if (getJenisLowongan.equals("Freelance")) {
            rdUFreelance.setChecked(true);
        }

        getGaji = iE.getStringExtra(ListLowongan.exUGaji);

        getSupportActionBar().setTitle(getJudulLowongan);

        //Set data yang telah diambil
        eId = getId;
        edJudulLowongan.setText(getJudulLowongan);
        edKeteranganLowongan.setText(getKeteranganLowongan);
        edNamaPerusahaan.setText(getNamaPerusahaan);
        edEmailPerusahaan.setText(getEmailPerusahaan);
        edLokasiPerusahaan.setText(getLokasiPerusahaan);
        edTeleponPerusahaan.setText(getTeleponPerusahaan);
        edKualifikasi.setText(getKualifikasi);
        edGaji.setText(getGaji);
        eJenisLowongan = getJenisLowongan;
        edBatasPendaftaran.setText(getBatasPendaftaran);

        btnEditLowongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateDataLowongan();
            }
        });

        btnPilihBatasPendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
    }

    public void initialize() {
        //Inisialisasi komponen
        edJudulLowongan = findViewById(R.id.edUJudulLowongan);
        edKeteranganLowongan = findViewById(R.id.edUKeteranganLowongan);
        edNamaPerusahaan = findViewById(R.id.edUNamaPerusahaan);
        edEmailPerusahaan = findViewById(R.id.edUEmailPerusahaan);
        edLokasiPerusahaan = findViewById(R.id.edULokasiPerusahaan);
        edTeleponPerusahaan = findViewById(R.id.edUNoTelpPerusahaan);
        edKualifikasi = findViewById(R.id.edUKualifikasi);
        edBatasPendaftaran = findViewById(R.id.edUBatasPendaftaran);
        edGaji = findViewById(R.id.edUGaji);
        rdUTipeLowongan = findViewById(R.id.rdUTipeLowongan);
        rdUFullTime = findViewById(R.id.rdUFullTime);
        rdUFreelance = findViewById(R.id.rdUFreelance);
        btnEditLowongan = findViewById(R.id.btnEditLowongan);
        btnRNamaPerusahaan = findViewById(R.id.btnRNamaPerusahaanReset);
        btnREmailPerusahaan = findViewById(R.id.btnREmailPerusahaanReset);
        btnRLokasiPerusahaan = findViewById(R.id.btnRLokasiPerusahaanReset);
        btnRNoTelpPerusahaan = findViewById(R.id.btnRNoTelpPerusahaan);
        btnRKualifikasiPerusahaan = findViewById(R.id.btnRKualifikasiPerusahaan);
        btnPilihBatasPendaftaran = findViewById(R.id.btnPilihBatasTanggalPendaftaran);

        mAuth = FirebaseAuth.getInstance();
        drLowongan = FirebaseDatabase.getInstance().getReference("lowongan");
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    }

    private void showDateDialog() {

        //Calendar untuk mendapatkan tanggal sekarang
        Calendar newCalendar = Calendar.getInstance();

        //Inisialisasi DatePicker dialog
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                //Method ini dipanggil saat kita selesai memilih tanggal di DatePicker

                //Set Calendar untuk menampung tanggal yang dipilih
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                //Update TextView dengan tanggal yang kita pilih
                edBatasPendaftaran.setText(dateFormatter.format(newDate.getTime()));

                edBatasPendaftaran.setEnabled(false);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        //Tampilkan DatePicker dialog
        datePickerDialog.show();
    }

    public void onRadioButtonClicked(View view) {
        //Apakah radio button sudah dipilih?
        boolean checked = ((RadioButton) view).isChecked();

        // Cek bagian radio button yang sudah dipilih
        switch (view.getId()) {
            case R.id.rdUFullTime:
                if (checked) {
                    eJenisLowongan = "Full Time";
                    break;
                }
            case R.id.rdUFreelance:
                if (checked) {
                    eJenisLowongan = "Freelance";
                    break;
                }
        }
    }

    public void UpdateDataLowongan() {
        eJudulLowongan = edJudulLowongan.getText().toString();
        eKeteranganLowongan = edKeteranganLowongan.getText().toString();
        eNamaPerusahaan = edNamaPerusahaan.getText().toString();
        eEmailPerusahaan = edEmailPerusahaan.getText().toString();
        eLokasiPerusahaan = edLokasiPerusahaan.getText().toString();
        eTeleponPerusahaan = edTeleponPerusahaan.getText().toString();
        eKualifikasi = edKualifikasi.getText().toString();
        eBatasPendaftaran = edBatasPendaftaran.getText().toString();
        eGaji = edGaji.getText().toString();
        rdUTipeLowongan.getCheckedRadioButtonId();

        if (TextUtils.isEmpty(eId) || TextUtils.isEmpty(eJudulLowongan) || TextUtils.isEmpty(eKeteranganLowongan) ||
                TextUtils.isEmpty(eNamaPerusahaan) || TextUtils.isEmpty(eEmailPerusahaan) || TextUtils.isEmpty(eLokasiPerusahaan) ||
                TextUtils.isEmpty(eTeleponPerusahaan) || TextUtils.isEmpty(eKualifikasi) || TextUtils.isEmpty(eJenisLowongan) ||
                TextUtils.isEmpty(eBatasPendaftaran) || TextUtils.isEmpty(eGaji)
        ) {
            Toast.makeText(getApplicationContext(), "Data harus lengkap", Toast.LENGTH_SHORT).show();
        } else {
            drLowongan = FirebaseDatabase.getInstance().getReference("lowongan").child(mAuth.getCurrentUser().getUid()).child(eId);

            Lowongan lowonganUpdate = new Lowongan(eId, eJudulLowongan, eKeteranganLowongan, eNamaPerusahaan, eEmailPerusahaan,
                    eLokasiPerusahaan, eTeleponPerusahaan, eKualifikasi, eJenisLowongan, eBatasPendaftaran, eGaji);

            drLowongan.setValue(lowonganUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //Jika sukses input data
                        Toast.makeText(getApplicationContext(), "Data berhasi diupdate", Toast.LENGTH_SHORT).show();
                    } else {
                        //Jika Gagal input data
                        Toast.makeText(getApplicationContext(), "Data gagal diupdate", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


}
