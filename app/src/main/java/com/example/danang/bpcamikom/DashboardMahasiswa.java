package com.example.danang.bpcamikom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardMahasiswa extends AppCompatActivity {

    public String dataId, tipe_mhs;
    private Button btnSeacrhLowonganMhs, btnUpdateDataDiri, btnLogout;
    private TextView txtCurrentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference drDataMhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_mahasiswa);

        initialize();

        drDataMhs = FirebaseDatabase.getInstance().getReference("mahasiswa").child(mAuth.getCurrentUser().getUid());
        txtCurrentUser.setText(mAuth.getCurrentUser().getEmail());

        btnUpdateDataDiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(DashboardMahasiswa.this, UpdateDataDiriActivity.class);
                Intent i = new Intent(DashboardMahasiswa.this, IsiDataDiriActivity.class);
                i.putExtra("mode", "edit");
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        btnSeacrhLowonganMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardMahasiswa.this, ListLowonganUser.class);
                startActivity(i);
            }
        });
    }

    public void initialize() {
        btnSeacrhLowonganMhs = findViewById(R.id.btnLihatLowongan);
        btnUpdateDataDiri = findViewById(R.id.btnUpdateDataDiri);
        btnLogout = findViewById(R.id.btnLogout);
        txtCurrentUser = findViewById(R.id.txtCurrentUser);
        mAuth = FirebaseAuth.getInstance();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent i = new Intent(DashboardMahasiswa.this, LoginMhsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardMahasiswa.this);
        builder.setTitle("Logout");
        builder.setMessage("Apakah anda akan logout?");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Berhasil keluar", Toast.LENGTH_LONG).show();
                finish();
                Intent i = new Intent(DashboardMahasiswa.this, LoginMhsActivity.class);
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
