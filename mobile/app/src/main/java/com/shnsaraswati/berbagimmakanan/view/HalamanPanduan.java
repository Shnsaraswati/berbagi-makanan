package com.shnsaraswati.berbagimmakanan.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shnsaraswati.berbagimmakanan.R;

public class HalamanPanduan extends AppCompatActivity {

    ViewPager mslideViewpager;
    LinearLayout mDotLayout;
    TextView txtskip,txtnext,txtback;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_panduan);

        txtskip = findViewById(R.id.txtskip);
        txtnext = findViewById(R.id.txtnext);
        txtback = findViewById(R.id.txtback);

        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getitem(0) > 0){
                    mslideViewpager.setCurrentItem(getitem(-1),true);
                }

            }
        });

        txtnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getitem(0) < 6){
                    mslideViewpager.setCurrentItem(getitem(1), true );
                } else {
                    Intent i = new Intent(HalamanPanduan.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        txtskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HalamanPanduan.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        mslideViewpager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicatorlayout);

        viewPagerAdapter = new ViewPagerAdapter(this);
        mslideViewpager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mslideViewpager.addOnPageChangeListener(viewListener);
    }

    public void setUpindicator(int position){
        dots = new TextView[7];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i <dots.length;i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.white,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.white,getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);
            if (position > 0 ){
                txtback.setVisibility(View.VISIBLE);
            } else {
                txtback.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private  int getitem(int i){
        return  mslideViewpager.getCurrentItem() + i;
    }

}