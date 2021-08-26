package com.shnsaraswati.berbagimmakanan.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.apollographql.apollo.exception.ApolloException;
import com.cloudinary.android.MediaManager;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;
import com.shnsaraswati.berbagimmakanan.presenter.ProfileContract;
import com.shnsaraswati.berbagimmakanan.presenter.ProfilePresenter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import query.UseGetProfileByIDQuery;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfilSaya#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfilSaya extends Fragment implements ProfileContract.ViewFragmentProfilSaya {
    public static final String TAG = "FragmentProfilSaya";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText editnama, editnohp, editalamat;
    SharedPreference sharedPreference;
    Button btnsimpanprofil;
    TextView linkeditfotoprofil, txtnamaprofilhalamanedit;
    CircleImageView fotoprofiledit;

    Uri uri;

    ProfilePresenter profilePresenter;

    String img_profile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProfilSaya() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfilSaya.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfilSaya newInstance(String param1, String param2) {
        FragmentProfilSaya fragment = new FragmentProfilSaya();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        sharedPreference = new SharedPreference(getContext());
        profilePresenter = new ProfilePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil_saya, container, false);

        editnama = view.findViewById(R.id.editnama);
        editnohp = view.findViewById(R.id.editnohp);
        editalamat = view.findViewById(R.id.editalamat);
        btnsimpanprofil = view.findViewById(R.id.btnsimpanprofil);
        linkeditfotoprofil = view.findViewById(R.id.linkeditfotoprofil);
        txtnamaprofilhalamanedit = view.findViewById(R.id.txtnamaprofilhalamanedit);
        fotoprofiledit = view.findViewById(R.id.fotoprofiledit);

        String curName = sharedPreference.getProfileName();
        String userID = sharedPreference.getProfileID();
        String curPhonenumber = sharedPreference.getProfilePhonenumber();
        String curAddress = sharedPreference.getProfileAddress();

        editnama.setText(curName);
        editnohp.setText(curPhonenumber);
        editalamat.setText(curAddress);
        txtnamaprofilhalamanedit.setText(curName);

        profilePresenter.onGetProfile(userID, new ProfileContract.Callback() {
            @Override
            public void onResponse(UseGetProfileByIDQuery.User user) {
                if (user != null) {
                    img_profile = user.img_profile();
                    String image = MediaManager.get().url().generate("berbagimakanan/" + img_profile);
                    String imagehttp = "";

                    if (image.contains("http://")) {
                        String[] images = image.split("http://");

                        imagehttp = images[1];
                    } else if (image.contains("https://")) {
                        String[] images = image.split("https://");

                        imagehttp = images[1];
                    }

                    onSetPhotoProfile(imagehttp);
                } else {
                    onSetFailure("terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                onSetFailure("terjadi kesalahan");
            }
        });

        btnsimpanprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editnama.getText().toString();
                String phonenumber = editnohp.getText().toString();
                String alamat = editalamat.getText().toString();
                profilePresenter.onUpdateProfil(userID, name, phonenumber, alamat, img_profile, uri);
            }
        });

        linkeditfotoprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(requireActivity()).crop().start();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.getData();
                File file = new File(uri.getPath());
                long fileSizeInKB = file.length() / 1024;
                if (fileSizeInKB > 3072) {
                    Log.d(TAG, "onActivityResult: " + file.length());
                    onSetFailure("gagal, maksimal ukuran gambar kurang dari 3MB!");
                    uri = null;
                } else {
                    fotoprofiledit.setImageURI(uri);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpdateProfil(String id, String name, String phonenumber, String address) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "profil telah diperbarui", Toast.LENGTH_LONG).show();
            }
        });
        sharedPreference.setProfileSharedPreference(id, name, phonenumber, address);
        getParentFragmentManager().popBackStack();
    }

    @Override
    public void onSetFailure(String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onSetPhotoProfile(String images) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String image = "https://" + images;
                Picasso.get().load(image).error(R.drawable.ic_fotoprofil).placeholder(R.drawable.ic_fotoprofil).into(fotoprofiledit, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess:");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                });
            }
        });
    }
}