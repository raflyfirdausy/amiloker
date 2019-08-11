package com.example.danang.bpcamikom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.danang.bpcamikom.DataTransfer.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisterMhsActivity extends AppCompatActivity {

    private EditText edRName, edREmail, edRPass, edRRepass;
    private Button btnRName, btnREmail, btnRpass, btnRRepass, btnRegister;
    private ProgressBar pgRegister;
    private Spinner spinnerJurusan;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private List<User> users;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mhs);

        initialize();

        btnRName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edRName.setText("");
            }
        });

        btnREmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edREmail.setText("");
            }
        });

        btnRpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edRPass.setText("");
            }
        });

        btnRRepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edRRepass.setText("");
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void initialize() {
        //Inisialisasi komponen
        edRName = findViewById(R.id.edRName);
        edREmail = findViewById(R.id.edREmail);
        edRPass = findViewById(R.id.edRPassword);
        edRRepass = findViewById(R.id.edRRePassword);
        btnRName = findViewById(R.id.btnRUsernameReset);
        btnREmail = findViewById(R.id.btnREmailReset);
        btnRpass = findViewById(R.id.btnRPasswordReset);
        btnRRepass = findViewById(R.id.btnRRePasswordReset);
        spinnerJurusan = findViewById(R.id.spinnerJurusan);
        btnRegister = findViewById(R.id.btnRegister);
        pgRegister = findViewById(R.id.pgRegister);
        edRPass.setHint("Password");
        edRPass.setTextSize(18);
        edRRepass.setHint("Re-type Password");
        edRRepass.setTextSize(18);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        users = new ArrayList<User>();
    }

    private void resetForm() {
        edRName.setText("");
        edREmail.setText("");
        edRPass.setText("");
        edRRepass.setText("");
    }

    private void registerUser() {
        final String nama, email, pass, repass;
        nama = edRName.getText().toString();
        email = edREmail.getText().toString();
        pass = edRPass.getText().toString();
        repass = edRRepass.getText().toString();

        if (nama.isEmpty() || email.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Data harus lengkap", Toast.LENGTH_SHORT).show();
        } else if (pass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
        } else if (!pass.equals(repass)) {
            Toast.makeText(getApplicationContext(), "Password tidak cocok", Toast.LENGTH_SHORT).show();
        } else {
            pgRegister.setVisibility(View.VISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            firebaseAuth.getCurrentUser().sendEmailVerification()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            pgRegister.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "Pendaftaran berhasil, silahkan cek email anda untuk aktivasi akun", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pgRegister.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pgRegister.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}
