package com.shnsaraswati.berbagimmakanan.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shnsaraswati.berbagimmakanan.R;

public class HalamanVerifikasi extends AppCompatActivity {

    private  static final String TAG = "HalamanVerifikasi";

    EditText code1,code2,code3,code4;
    Button btnkirimverifikasi;
    TextView linkkirimulang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_halaman_verifikasi);

        code1 = findViewById(R.id.code1);
        code2 = findViewById(R.id.code2);
        code3 = findViewById(R.id.code3);
        code4 = findViewById(R.id.code4);
        btnkirimverifikasi = findViewById(R.id.btnkirimverifikasi);
        linkkirimulang = findViewById(R.id.linkkirimulang);

        String otp = getIntent().getStringExtra("otp");

        btnkirimverifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = code1.getText().toString() + code2.getText().toString() + code3.getText().toString() + code4.getText().toString();

                if (otp.equals(code)){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Kode Verifikasi Salah !", Toast.LENGTH_LONG).show();
                }
            }
        });

        linkkirimulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}