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
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;
import com.shnsaraswati.berbagimmakanan.config.TwillioAPI;

public class HalamanVerifikasi extends AppCompatActivity {

    private  static final String TAG = "HalamanVerifikasi";

    private EditText code1,code2,code3,code4;
    private Button btnkirimverifikasi;
    private TextView linkkirimulang;

    private SharedPreference sharedPreference;
    private TwillioAPI twillioAPI;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_halaman_verifikasi);

        sharedPreference = new SharedPreference(this);
        twillioAPI = new TwillioAPI();

        code1 = findViewById(R.id.code1);
        code2 = findViewById(R.id.code2);
        code3 = findViewById(R.id.code3);
        code4 = findViewById(R.id.code4);
        btnkirimverifikasi = findViewById(R.id.btnkirimverifikasi);
        linkkirimulang = findViewById(R.id.linkkirimulang);

        String otp = getIntent().getStringExtra("otp");
        String userid = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String phonenumber = getIntent().getStringExtra("phonenumber");
        String address = getIntent().getStringExtra("address");

        btnkirimverifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = code1.getText().toString() + code2.getText().toString() + code3.getText().toString() + code4.getText().toString();

                if (otp.equals(code)){
                    // melakukan set preference profile dan is logged in agar tersimpan di memory lokal android
                    sharedPreference.setProfileSharedPreference(userid, name, phonenumber, address);
                    sharedPreference.setIsLoggedIn(true);

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
                Toast.makeText(getApplicationContext(), "melakukan proses mengirim ulang kode verifikasi", Toast.LENGTH_LONG).show();
               thread = new Thread(new Runnable() {
                   @Override
                   public void run() {
                       twillioAPI.sendSMSVerification(phonenumber, otp);
                   }
               });
               thread.start();
            }
        });


    }
}