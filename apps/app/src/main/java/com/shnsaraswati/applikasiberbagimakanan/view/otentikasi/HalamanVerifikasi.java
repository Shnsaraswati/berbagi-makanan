package com.shnsaraswati.applikasiberbagimakanan.view.otentikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shnsaraswati.applikasiberbagimakanan.R;
import com.shnsaraswati.applikasiberbagimakanan.view.MainActivity;

public class HalamanVerifikasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_verifikasi);

        EditText code1 = findViewById(R.id.code1);
        EditText code2 = findViewById(R.id.code2);
        EditText code3 = findViewById(R.id.code3);
        EditText code4 = findViewById(R.id.code4);
        Button btnverifikasi = findViewById(R.id.btnverifikasi);
        Button btnkirimulangverifikasi = findViewById(R.id.btnkirimulangverifikasi);

        btnverifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = code1.getText().toString() + code2.getText().toString() + code3.getText().toString() + code4.getText().toString();

                int verification = Integer.parseInt(code);

                if (verification == HalamanDaftar.verification_code) {
                    Intent intentmain = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentmain);
                }
            }
        });


    }
}