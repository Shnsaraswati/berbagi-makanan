package com.shnsaraswati.applikasiberbagimakanan.view.otentikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shnsaraswati.applikasiberbagimakanan.R;

public class HalamanLupaKataSandi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_lupa_kata_sandi);

        TextView txtlinkdaftar3 = findViewById(R.id.txtlinkdaftar3);
        txtlinkdaftar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HalamanDaftar();
            }
        });
    }

    public void HalamanDaftar(){
        Intent intent = new Intent(this,HalamanDaftar.class);
        startActivity(intent);
    }
}