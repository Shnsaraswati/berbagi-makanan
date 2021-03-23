package com.shnsaraswati.applikasiberbagimakanan.view.otentikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.shnsaraswati.applikasiberbagimakanan.R;
import com.shnsaraswati.applikasiberbagimakanan.model.User;
import com.shnsaraswati.applikasiberbagimakanan.presenter.otentikasi.OtentikasiDataSource;
import com.shnsaraswati.applikasiberbagimakanan.presenter.otentikasi.OtentikasiRepository;

import java.util.List;

import query.GetUserQuery;

public class HalamanLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_login);
    }
}