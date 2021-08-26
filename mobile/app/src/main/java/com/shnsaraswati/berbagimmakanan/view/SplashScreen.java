package com.shnsaraswati.berbagimmakanan.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;

public class SplashScreen extends AppCompatActivity {

    public static final int LOCATION_PERMISSION_CODE = 110;

    Animation topAnim, botomAnim;
    ImageView logoimage;
    TextView nameapp, sloganapp;

    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        sharedPreference = new SharedPreference(this);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        botomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logoimage = findViewById(R.id.logo);
        nameapp = findViewById(R.id.namaapp);
        sloganapp = findViewById(R.id.selogan);

        logoimage.setAnimation(topAnim);
        nameapp.setAnimation(botomAnim);
        sloganapp.setAnimation(botomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE);
            }
        }, 5000);
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(SplashScreen.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{permission}, requestCode);
        } else {
            checkStatusUser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkStatusUser();
            } else {
                Toast.makeText(this, "anda harus mengaktifkan izin lokasi", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void checkStatusUser() {
        if (sharedPreference.getIsLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashScreen.this, HalamanUtama.class);
            startActivity(intent);
            finish();
        }
    }
}