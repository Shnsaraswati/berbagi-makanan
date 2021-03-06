package com.shnsaraswati.berbagimmakanan.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.presenter.UserAuthContract;
import com.shnsaraswati.berbagimmakanan.presenter.UserAuthPresenter;

import org.mindrot.jbcrypt.BCrypt;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class HalamanDaftar extends AppCompatActivity implements UserAuthContract.ViewHalamanDaftar {

    private TextView linkmasuk;
    private CircularProgressButton btnverifikasi;
    private EditText daftarnama, daftarnohp, daftarkatasandi;

    private UserAuthPresenter userAuthPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_halaman_daftar);

        userAuthPresenter = new UserAuthPresenter(this);

        daftarnama = findViewById(R.id.daftarnama);
        daftarnohp = findViewById(R.id.daftarnohp);
        daftarkatasandi = findViewById(R.id.daftarkatasandi);
        linkmasuk = findViewById(R.id.txtlinkmasuk);

        daftarnohp.setSelection(daftarnohp.getText().length());
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
                btnverifikasi.startAnimation();

                String nama = daftarnama.getText().toString();
                String hp = daftarnohp.getText().toString();
                String sandi = BCrypt.hashpw(daftarkatasandi.getText().toString(), BCrypt.gensalt(12));


                userAuthPresenter.onRegister(hp, sandi, nama);
            }
        });
    }

    public void openHalamanmasuk() {
        Intent intent = new Intent(this, HalamanMasuk.class);
        startActivity(intent);
    }

    public void openHalamanverifikasi() {
        Intent intent = new Intent(this, HalamanVerifikasi.class);
        startActivity(intent);
    }

    @Override
    public void onSuccessRegister(String otp, String id, String name, String phonenumber, String address) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnverifikasi.revertAnimation();
                btnverifikasi.setBackgroundResource(R.drawable.button_oren);
            }
        });

        Intent intent = new Intent(this, HalamanVerifikasi.class);
        intent.putExtra("otp", otp);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("phonenumber", phonenumber);
        intent.putExtra("address", address);
        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnverifikasi.revertAnimation();
                btnverifikasi.setBackgroundResource(R.drawable.button_oren);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}