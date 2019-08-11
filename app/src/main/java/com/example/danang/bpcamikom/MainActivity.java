package com.example.danang.bpcamikom;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnLoginMhs, btnLoginBPC;
    private TextView txtAboutBPC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check_internet();
        initialize();

        btnLoginMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginMhsActivity.class);
                startActivity(i);
            }
        });

        btnLoginBPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginBPCActivity.class);
                startActivity(i);
            }
        });

        txtAboutBPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TentangBPCActivity.class);
                startActivity(i);
            }
        });

    }

    private void initialize() {
        //Inisialisasi komponen
        btnLoginMhs = findViewById(R.id.btnLoginMHS);
        btnLoginBPC = findViewById(R.id.btnLoginBPC);
        txtAboutBPC = findViewById(R.id.txtAboutBPC);
    }

    private void check_internet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        android.net.NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            //Jika konektifitas internet tersedia, do nothing.
        } else {
            //Jika konektifitas internet tidak ada maka akan diarahkan untuk mengaktifkan internet.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_signal_wifi_off_black_24dp);
            builder.setTitle("NO INTERNET");
            builder.setMessage("Please check your internet connection.");
            builder.setPositiveButton("ENABLE INTERNET", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                    startActivity(intent);

                }
            });
            builder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    System.exit(0);
                }
            });
            builder.show();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        check_internet();

    }

}
