package com.shnsaraswati.berbagimmakanan.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;
import com.shnsaraswati.berbagimmakanan.presenter.UserAuthContract;
import com.shnsaraswati.berbagimmakanan.presenter.UserAuthPresenter;

import query.UseGetUserByPhoneQuery;

public class HalamanMasuk extends AppCompatActivity implements UserAuthContract.ViewHalamanMasuk {

    private TextView linkdaftar, linklupakatasandi;
    private Button btnmasuk;
    private EditText inputnohp, inputkatasandi;

    private UserAuthPresenter userAuthPresenter;

    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_halaman_masuk);

        sharedPreference = new SharedPreference(this);

        userAuthPresenter = new UserAuthPresenter(this);

        inputnohp = findViewById(R.id.inputnohp);
        inputkatasandi = findViewById(R.id.inputkatasandi);
        btnmasuk = findViewById(R.id.btnmasukapp);

        inputnohp.setSelection(inputnohp.getText().length());
        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nohp = inputnohp.getText().toString();
                String katasandi = inputkatasandi.getText().toString();
                userAuthPresenter.onLogin(nohp, katasandi);
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

    public void openHalamandaftar() {
        Intent intent = new Intent(this, HalamanDaftar.class);
        startActivity(intent);
    }

    public void openHalamanlupakatasandi() {
        Intent intent = new Intent(this, HalamanLupaKataSandi.class);
        startActivity(intent);
    }

    public void openMainactivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccessLogin(UseGetUserByPhoneQuery.User user) {
        // melakukan set preference profile dan is logged in agar tersimpan di memory lokal android
        String userid = user.id().toString();
        String name = user.name();
        String phone_number = user.phone_number();
        String address = user.address();
        sharedPreference.setProfileSharedPreference(userid, name, phone_number, address);
        sharedPreference.setIsLoggedIn(true);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}