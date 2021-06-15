package com.shnsaraswati.applikasiberbagimakanan.view.otentikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shnsaraswati.applikasiberbagimakanan.R;
import com.shnsaraswati.applikasiberbagimakanan.model.User;
import com.shnsaraswati.applikasiberbagimakanan.presenter.otentikasi.OtentikasiDataSource;
import com.shnsaraswati.applikasiberbagimakanan.presenter.otentikasi.OtentikasiRepository;
import com.shnsaraswati.applikasiberbagimakanan.view.MainActivity;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

import query.GetUserQuery;

public class HalamanLogin extends AppCompatActivity {
    private static final String TAG = "HalamanLogin";
    private static final String SHARED_PREF_NAME = "shared_preferences";

    private User user;
    SharedPreferences sharedPreferences;

    Boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_login);

        TextView textView = findViewById(R.id.textView);
        TextView txtlinkdaftar = findViewById(R.id.txtlinkdaftar);
        Button btnlogin = findViewById(R.id.btnlogin);
        EditText nohp = findViewById(R.id.LoginNoHP);
        EditText password = findViewById(R.id.LoginPassword);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);

        if (isLoggedIn) {
            MainActivity();
            finish();
        }

        OtentikasiRepository otentikasiRepository = new OtentikasiRepository();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hp= "+62" + nohp.getText().toString().substring(1);
                user = new User(hp, password.getText().toString());

                otentikasiRepository.login(user, new OtentikasiDataSource.LoadDataCallback() {
                    @Override
                    public void onDataLoaded(List<GetUserQuery.User> users) {
                        // mengatur sharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_id", users.get(0).id().toString());
                        editor.putBoolean("is_logged_in", true);
                        editor.apply();

                        MainActivity();
                    }

                    @Override
                    public void onNoDataLoaded() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "no hp atau password anda salah", Toast.LENGTH_LONG).show(); 
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage() );
                    }
                });
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HalamanLupaKataSandi();
            }
        });

        txtlinkdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HalamanDaftar();
            }
        });

    }

    public  void MainActivity(){
        Intent intentmain = new Intent(this, MainActivity.class);
        startActivity(intentmain);
    }

    public void HalamanLupaKataSandi(){
        Intent intentlupakatasandi = new Intent(this, HalamanLupaKataSandi.class);
        startActivity(intentlupakatasandi);
    }

    public  void HalamanDaftar(){
        Intent intentdaftar = new Intent(this,HalamanDaftar.class);
        startActivity(intentdaftar);
    }
}