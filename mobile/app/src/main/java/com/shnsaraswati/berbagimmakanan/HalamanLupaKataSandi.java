package com.shnsaraswati.berbagimmakanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class HalamanLupaKataSandi extends AppCompatActivity {

    Button btnlupasandi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_halaman_lupa_kata_sandi);

        btnlupasandi = findViewById(R.id.btnselanjutnyalupasandi);
        btnlupasandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHalamanverifikasi();
            }
        });
    }

    public void openHalamanverifikasi(){
        Intent intent = new Intent(this, HalamanVerifikasi.class);
        startActivity(intent);
    }
}