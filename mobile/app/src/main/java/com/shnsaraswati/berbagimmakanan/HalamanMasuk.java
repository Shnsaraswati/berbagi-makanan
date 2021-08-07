package com.shnsaraswati.berbagimmakanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class HalamanMasuk extends AppCompatActivity {

    TextView linkdaftar,linklupakatasandi;
    Button btnmasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_halaman_masuk);

        btnmasuk = findViewById(R.id.btnmasukapp);
        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainactivity();
            }
        });

        linkdaftar = findViewById(R.id.linkdaftar);
        linkdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHalamandaftar();
            }
        });

        linklupakatasandi = findViewById(R.id.txtlupakatasandi);
        linklupakatasandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHalamanlupakatasandi();
            }
        });
    }

    public void openHalamandaftar(){
        Intent intent = new Intent(this, HalamanDaftar.class);
        startActivity(intent);
    }
    public void openHalamanlupakatasandi(){
        Intent intent = new Intent(this, HalamanLupaKataSandi.class);
        startActivity(intent);
    }
    public void openMainactivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}