package com.shnsaraswati.berbagimmakanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class HalamanDaftar extends AppCompatActivity {

    TextView linkmasuk;
    Button btnverifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_halaman_daftar);

        linkmasuk = findViewById(R.id.txtlinkmasuk);
        linkmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHalamanmasuk();
            }
        });

        btnverifikasi = findViewById(R.id.btndaftarakun);
        btnverifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHalamanverifikasi();
            }
        });
    }
    public void openHalamanmasuk(){
        Intent intent = new Intent(this, HalamanMasuk.class);
        startActivity(intent);
    }
    public void openHalamanverifikasi(){
        Intent intent = new Intent(this, HalamanVerifikasi.class);
        startActivity(intent);
    }
}