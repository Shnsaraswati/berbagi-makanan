package com.shnsaraswati.applikasiberbagimakanan.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.cloudinary.android.MediaManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.shnsaraswati.applikasiberbagimakanan.R;
import com.shnsaraswati.applikasiberbagimakanan.model.User;
import com.shnsaraswati.applikasiberbagimakanan.presenter.profile.ProfileDataSource;
import com.shnsaraswati.applikasiberbagimakanan.presenter.profile.ProfileRepository;
import com.shnsaraswati.applikasiberbagimakanan.view.fragment.FragmentAkun;
import com.shnsaraswati.applikasiberbagimakanan.view.fragment.FragmentHome;
import com.shnsaraswati.applikasiberbagimakanan.view.fragment.FragmentInformasi;
import com.shnsaraswati.applikasiberbagimakanan.view.fragment.FragmentMakanan;
import com.shnsaraswati.applikasiberbagimakanan.view.fragment.FragmentMessage;
import com.shnsaraswati.applikasiberbagimakanan.view.fragment.FragmentPenggunaTerdekat;
import com.shnsaraswati.applikasiberbagimakanan.view.fragment.FragmentRiwayat;
import com.squareup.picasso.Picasso;

import java.util.List;

import query.GetProfileQuery;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private BottomNavigationView bottomNavigationView;
    private static final String SHARED_PREF_NAME = "shared_preferences";
    private static final String TAG = "MainActivity";


    SharedPreferences sharedPreferences;

    private TextView namaakun,nohpakun;
    private ImageView fotoprofileheader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_vie);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_cole);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentHome()).commit();
            navigationView.setCheckedItem(R.id.nav_panduan);
        }

        namaakun = navigationView.getHeaderView(0).findViewById(R.id.namaakun);
        nohpakun = navigationView.getHeaderView(0).findViewById(R.id.nohpakun);
        fotoprofileheader = navigationView.getHeaderView(0).findViewById(R.id.fotoprofileheader);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("user_id", "");

        User user = new User(id);

        ProfileRepository repository = new ProfileRepository();

        repository.getProfile(user, new ProfileDataSource.LoadDataCallback() {
            @Override
            public void onDataLoaded(List<GetProfileQuery.User> profiles) {
                String nama = profiles.get(0).name();
                namaakun.post(new Runnable() {
                    @Override
                    public void run() {
                        namaakun.setText(nama);
                    }
                });
                nohpakun.post(new Runnable() {
                    @Override
                    public void run() {
                        nohpakun.setText(profiles.get(0).phone_number());
                    }
                });

                String imgprofile = profiles.get(0).img_profile();
                String img = MediaManager.get().url().generate("berbagimakanan/" + imgprofile);
                Handler uiHandler = new Handler(Looper.getMainLooper());
                uiHandler.post(new Runnable(){
                    @Override
                    public void run() {
                        Picasso.get()
                                .load(img)
                                .error(R.mipmap.ic_launcher_round)
                                .fit()
                                .into(fotoprofileheader);
                    }
                });
            }

            @Override
            public void onNoDataLoaded() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }


    private final BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = new FragmentHome();

            switch (item.getItemId()) {
                case R.id.nav_account:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAkun()).commit();
                    break;
                case R.id.nav_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
                    break;
                case R.id.nav_makanan:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMakanan()).commit();
                    break;
                case R.id.nav_message:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMessage()).commit();
                    break;
                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
                    break;
            }

            return true;
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_panduan:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentInformasi()).commit();
                break;
            case R.id.nav_penggunaterdekat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentPenggunaTerdekat()).commit();
                break;
            case R.id.nav_riwayat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentRiwayat()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }


}