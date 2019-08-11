package com.example.danang.bpcamikom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginBPCActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText edEmail, edPassword;
    private ProgressBar pgLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference drDataMhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bpc);

        initialize();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });


    }

    private void initialize() {
        //Inisialisasi komponen
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        pgLogin = findViewById(R.id.pgLogin);
        mAuth = FirebaseAuth.getInstance();
        edPassword.setTextSize(18);
        edPassword.setHint("Password");
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu sebentar");
        progressDialog.show();
    }

    protected void onResume() {
        super.onResume();
        edPassword.setText("");
        edEmail.setText("");
    }

    private void signin() {
        String email, pass;
        email = edEmail.getText().toString();
        pass = edPassword.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email Salah", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password diperlukan", Toast.LENGTH_SHORT).show();
        } else {

            showProgressDialog();

            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.cancel();
                    if (task.isSuccessful()) {
                        //Jika berhasil cek apakah email tersebut merupakan email admin BPC atau bukan
                        if (mAuth.getCurrentUser().getEmail().equals("adminbpc@amikompurwokerto.ac.id")) {
                            //Jika email merupakan email admin BPC
                            Intent i = new Intent(LoginBPCActivity.this, DashboardAdminBPC.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(), "Masuk ke akun berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            //Jika gagal
                            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
