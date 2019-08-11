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

public class DashboardAdminBPC extends AppCompatActivity {

    private Button btnInputLowonganAdmin, btnLihatLowonganAdmin, btnLogoutAdmin;
    private TextView txtCurrentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin_bpc);

        initialize();
        txtCurrentUser.setText(mAuth.getCurrentUser().getEmail());

        btnInputLowonganAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardAdminBPC.this, InputLowonganBPC.class);
                startActivity(i);
            }
        });

        btnLogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        btnLihatLowonganAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardAdminBPC.this, ListLowongan.class);
                startActivity(i);
            }
        });
    }

    public void initialize() {
        btnInputLowonganAdmin = findViewById(R.id.btnInputLowongan);
        btnLihatLowonganAdmin = findViewById(R.id.btnLihatLowongan);
        btnLogoutAdmin = findViewById(R.id.btnLogout);
        txtCurrentUser = findViewById(R.id.txtCurrentUser);
        mAuth = FirebaseAuth.getInstance();

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent i = new Intent(DashboardAdminBPC.this, LoginBPCActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardAdminBPC.this);
        builder.setTitle("Logout");
        builder.setMessage("Apakah anda akan logout?");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Berhasil keluar", Toast.LENGTH_LONG).show();
                finish();
                Intent i = new Intent(DashboardAdminBPC.this, LoginBPCActivity.class);
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
