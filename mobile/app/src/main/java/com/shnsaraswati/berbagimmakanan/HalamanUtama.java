package com.shnsaraswati.berbagimmakanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class HalamanUtama extends AppCompatActivity {

    Button btnmasuk,btndaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_halaman_utama);

        btnmasuk = findViewById(R.id.btnmasuk);
        btndaftar = findViewById(R.id.btndaftar);

        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHalamanmasuk();
            }
        });

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHalamandaftar();
            }
        });
    }

    public void openHalamanmasuk(){
        Intent intent = new Intent(this, HalamanMasuk.class);
        startActivity(intent);
    }
    public void openHalamandaftar(){
        Intent intent = new Intent(this, HalamanDaftar.class);
        startActivity(intent);
    }
}