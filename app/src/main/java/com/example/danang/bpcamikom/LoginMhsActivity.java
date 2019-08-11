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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginMhsActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private EditText edEmail, edPassword;
    private ProgressBar pgLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference drDataMhs;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mhs);

        initialize();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMhsActivity.this, RegisterMhsActivity.class);
                startActivity(i);
            }
        });

    }

    private void initialize() {
        //Inisialisasi komponen
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnARegister);
        pgLogin = findViewById(R.id.pgLogin);
        mAuth = FirebaseAuth.getInstance();
        edPassword.setTextSize(18);
        edPassword.setHint("Password");
    }

    protected void onResume() {
        super.onResume();
        edPassword.setText("");
        edEmail.setText("");
    }

    private void showProgressDialogDataDiri() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengecek Data Diri");
        progressDialog.show();
    }

    private void showProgressDialogLogin() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.show();
    }


    private void signin() {
        String email, pass;
        email = edEmail.getText().toString();
        pass = edPassword.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email diperlukan", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password diperlukan", Toast.LENGTH_SHORT).show();
        } else {
            showProgressDialogLogin();
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.cancel();
                    if (task.isSuccessful()) {
                        //Jika berhasil cek apakah email tersebut merupakan email admin BPC atau bukan
                        if (mAuth.getCurrentUser().getEmail().equals("adminbpc@amikompurwokerto.ac.id")) {
                            //Jika email merupakan email admin BPC
                            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!mAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(getApplicationContext(), "Silahkan verifikasi Email Anda Terlebih Dahulu", Toast.LENGTH_LONG).show();
                                mAuth.signOut();
                            } else {
//Cek apakah mahasiswa sudah mengisi data diri atau belum?
                                String idUser = mAuth.getCurrentUser().getUid();
                                drDataMhs = FirebaseDatabase.getInstance().getReference("mahasiswa").child(idUser);
                                drDataMhs.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.exists()) {
                                            //Jika data diri mahasiswa sudah ada
                                            Toast.makeText(getApplicationContext(), "Masuk ke akun berhasil", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(LoginMhsActivity.this, DashboardMahasiswa.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(i);
                                        } else {
                                            //Jika belum maka mahasiswa diharuskan mengisi data diri terlebih dahulu
                                            Intent i = new Intent(LoginMhsActivity.this, IsiDataDiriActivity.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                showProgressDialogDataDiri();
                            }
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
