package com.shnsaraswati.applikasiberbagimakanan.view.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.shnsaraswati.applikasiberbagimakanan.R;
import com.shnsaraswati.applikasiberbagimakanan.model.User;
import com.shnsaraswati.applikasiberbagimakanan.presenter.profile.ProfileDataSource;
import com.shnsaraswati.applikasiberbagimakanan.presenter.profile.ProfileRepository;
import com.shnsaraswati.applikasiberbagimakanan.view.fragment.FragmentAkun;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import query.GetProfileQuery;

public class HalamanEditAkun extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "shared_preferences";
    private static final String TAG = "FRAGMENT_AKUN";

    SharedPreferences sharedPreferences;
    User user;

    ImageView fotoprofile;

    Uri uri;
    String profileimg;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_edit_akun);

        //MediaManager.init(this);

        EditText nohp = findViewById(R.id.editnohp);
        EditText nama = findViewById(R.id.editnama);
        EditText tanggallahir = findViewById(R.id.edittanggallahir);
        EditText alamat = findViewById(R.id.editalamat);
        CircularProgressButton btnsimpan = findViewById(R.id.btnsimpan);
        fotoprofile = findViewById(R.id.editfotoakun);


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
                userid = profile.id().toString();
                String imgprofile = profile.img_profile();
                profileimg = imgprofile;

                String img = MediaManager.get().url().generate("berbagimakanan/" + imgprofile);
                Handler uiHandler = new Handler(Looper.getMainLooper());
                uiHandler.post(new Runnable(){
                    @Override
                    public void run() {
                        Picasso.get()
                                .load(img)
                                .error(R.drawable.ic_account)
                                .fit()
                                .into(fotoprofile);
                    }
                });
                Log.d(TAG, "onDataLoaded: " + img);
            }

            @Override
            public void onNoDataLoaded() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError-Editakun: " + e.getMessage());
            }
        });

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnsimpan.startAnimation();
                user.setName(nama.getText().toString());
                user.setAddress(alamat.getText().toString());
                user.setBirth_date(tanggallahir.getText().toString());
                user.setPhone_number(nohp.getText().toString());
                user.setImg_profile(profileimg);
                if (uri != null){
                    user.setImg_profile(userid + ".jpg");
                    String requestId = MediaManager.get().
                            upload(uri).
                            unsigned("berbagi_preset").
                            option("public_id", userid).
                            callback(new UploadCallback() {
                                @Override
                                public void onStart(String requestId) {

                                }

                                @Override
                                public void onProgress(String requestId, long bytes, long totalBytes) {


                                }

                                @Override
                                public void onSuccess(String requestId, Map resultData) {
                                    Log.d(TAG, "onSuccess: Berhasil");
                                    repository.updateProfile(user, new ProfileDataSource.LoadDataCallbackUpdateProfile() {
                                        @Override
                                        public void onDataLoaded(int affected_rows) {
                                            btnsimpan.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
                                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment_container, new FragmentAkun());
                                            fragmentTransaction.commit();

                                        }

                                        @Override
                                        public void onNoDataLoaded() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.e(TAG, "onError-HalamanAkun: " + e.getMessage());
                                        }
                                    });
                                }

                                @Override
                                public void onError(String requestId, ErrorInfo error) {
                                    Log.d(TAG, "onError: ");
                                }

                                @Override
                                public void onReschedule(String requestId, ErrorInfo error) {
                                    Log.d(TAG, "onReschedule: ");
                                }
                            }).dispatch();
                }else{
                    Log.d(TAG, "onSuccess: Berhasil");
                    repository.updateProfile(user, new ProfileDataSource.LoadDataCallbackUpdateProfile() {
                        @Override
                        public void onDataLoaded(int affected_rows) {
                            btnsimpan.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, new FragmentAkun());
                            fragmentTransaction.commit();
                        }

                        @Override
                        public void onNoDataLoaded() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError-HalamanAkun: " + e.getMessage());
                        }
                    });
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            File file = new File(uri.getPath());
            fotoprofile.setImageURI(uri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public String getPath(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);

        Log.d(TAG, "getPath: " + picturePath);

        return picturePath;
    }
}