package com.shnsaraswati.applikasiberbagimakanan.view.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.shnsaraswati.applikasiberbagimakanan.R;
import com.shnsaraswati.applikasiberbagimakanan.model.User;
import com.shnsaraswati.applikasiberbagimakanan.presenter.profile.ProfileDataSource;
import com.shnsaraswati.applikasiberbagimakanan.presenter.profile.ProfileRepository;
import com.shnsaraswati.applikasiberbagimakanan.view.MainActivity;
import com.shnsaraswati.applikasiberbagimakanan.view.fragment.FragmentAkun;

import java.util.List;

import query.GetProfileQuery;

public class HalamanEditAkun extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "shared_preferences";
    private static final String TAG = "FRAGMENT_AKUN";

    SharedPreferences sharedPreferences;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_edit_akun);

        EditText nohp = findViewById(R.id.editnohp);
        EditText nama = findViewById(R.id.editnama);
        EditText tanggallahir = findViewById(R.id.edittanggallahir);
        EditText alamat = findViewById(R.id.editalamat);
        Button btnsimpan =findViewById(R.id.btnsimpan);
        ImageView fotoprofile = findViewById(R.id.editfotoakun);

        fotoprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(HalamanEditAkun.this).start();
            }
        });

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String id = sharedPreferences.getString("user_id", "");

        user = new User(id);

        ProfileRepository repository = new ProfileRepository();
        repository.getProfile(user, new ProfileDataSource.LoadDataCallback() {
            @Override
            public void onDataLoaded(List<GetProfileQuery.User> profiles) {
                GetProfileQuery.User profile = profiles.get(0);

                nama.setText(profile.name());
                alamat.setText(profile.address());
                nohp.setText(profile.phone_number());
                tanggallahir.setText(profile.birth_date().toString());
            }

            @Override
            public void onNoDataLoaded() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError-Editakun: " + e.getMessage() );
            }
        });

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(nama.getText().toString());
                user.setAddress(alamat.getText().toString());
                user.setBirth_date(tanggallahir.getText().toString());
                user.setPhone_number(nohp.getText().toString());

                repository.updateProfile(user, new ProfileDataSource.LoadDataCallbackUpdateProfile() {
                    @Override
                    public void onDataLoaded(int affected_rows) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new FragmentAkun());
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onNoDataLoaded() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError-HalamanAkun: " + e.getMessage() );
                    }
                });
            }
        });

    }
}