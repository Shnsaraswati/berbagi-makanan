package com.shnsaraswati.berbagimmakanan.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.shnsaraswati.berbagimmakanan.R;

public class SplashScreen extends AppCompatActivity {

    Animation topAnim,botomAnim;
    ImageView logoimage;
    TextView nameapp,sloganapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        botomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logoimage = findViewById(R.id.logo);
        nameapp = findViewById(R.id.namaapp);
        sloganapp = findViewById(R.id.selogan);

        logoimage.setAnimation(topAnim);
        nameapp.setAnimation(botomAnim);
        sloganapp.setAnimation(botomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, HalamanUtama.class);
                startActivity(intent);
                finish();
            }
        }, 5000);


    }
}