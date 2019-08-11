package com.example.danang.bpcamikom.ArrayAdapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.danang.bpcamikom.DataTransfer.Lowongan;
import com.example.danang.bpcamikom.R;

import java.util.List;

public class DaftarLowonganAdmin extends ArrayAdapter {
    private Activity context;
    private List<Lowongan> daftarLowongan;

    public DaftarLowonganAdmin(Activity context, List<Lowongan> daftarLowongan) {
        super(context, R.layout.daftar_lowongan_admin, daftarLowongan);

        this.context = context;
        this.daftarLowongan = daftarLowongan;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflaterAdmin = context.getLayoutInflater();

        View listViewItemAdmin = inflaterAdmin.inflate(R.layout.daftar_lowongan_admin, null, true);

        TextView txtDJudulLowongan, txtDNamaPerusahaan, txtDKeteranganLowongan, txtDGaji;

        //Inisialisasi komponen
        txtDJudulLowongan = listViewItemAdmin.findViewById(R.id.txtDJudulLowongan);
        txtDNamaPerusahaan = listViewItemAdmin.findViewById(R.id.txtDNamaPerusahaan);
        txtDKeteranganLowongan = listViewItemAdmin.findViewById(R.id.txtDKeteranganLowongan);
        txtDGaji = listViewItemAdmin.findViewById(R.id.txtDGaji);

        Lowongan lowongan = daftarLowongan.get(position);

        txtDJudulLowongan.setText(lowongan.getJudul_lowongan());
        txtDNamaPerusahaan.setText(lowongan.getNama_perusahaan());
        txtDKeteranganLowongan.setText(lowongan.getKeterangan_lowongan());
        txtDGaji.setText("Gaji Rp. " + lowongan.getGaji() + " / Bulan");

        return listViewItemAdmin;

    }
}

