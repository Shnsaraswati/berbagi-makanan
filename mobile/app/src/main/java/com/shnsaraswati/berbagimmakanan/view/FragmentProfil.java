package com.shnsaraswati.berbagimmakanan.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.apollographql.apollo.exception.ApolloException;
import com.cloudinary.android.MediaManager;
import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;
import com.shnsaraswati.berbagimmakanan.presenter.ProfileContract;
import com.shnsaraswati.berbagimmakanan.presenter.ProfilePresenter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import de.hdodenhof.circleimageview.CircleImageView;
import query.UseGetProfileByIDQuery;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfil extends Fragment implements ProfileContract.ViewFragmentProfil {
    public static final String TAG = "FragmentProfile";

    TextView linkeditprofil, linkgantikatasandi, linkpenggunaterdekat, linktentangaplikasi, linktpanduan, txtnamaprofil;
    Button btnkeluarapp;
    RatingBar ratingBar;
    CircleImageView fotoprofil;

    SharedPreference sharedPreference;
    ProfilePresenter profilePresenter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfil.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfil newInstance(String param1, String param2) {
        FragmentProfil fragment = new FragmentProfil();
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
        sharedPreference = new SharedPreference(requireContext());
        profilePresenter = new ProfilePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profil, container, false);
        linkeditprofil = view.findViewById(R.id.linkeditprofil);
        linkgantikatasandi = view.findViewById(R.id.linkgantikatasandi);
        linkpenggunaterdekat = view.findViewById(R.id.linkpenggunaterdekat);
        linktentangaplikasi = view.findViewById(R.id.linktentangaplikasi);
        linktpanduan = view.findViewById(R.id.linktpanduan);
        btnkeluarapp = view.findViewById(R.id.btnkeluarapp);
        txtnamaprofil = view.findViewById(R.id.txtnamaprofil);
        ratingBar = view.findViewById(R.id.ratingBar);
        fotoprofil = view.findViewById(R.id.fotoprofil);

        String name = sharedPreference.getProfileName();
        String userID = sharedPreference.getProfileID();

        txtnamaprofil.setText(name);

        profilePresenter.onGetProfile(userID, new ProfileContract.Callback() {
            @Override
            public void onResponse(UseGetProfileByIDQuery.User user) {
                if (user != null) {
                    float rating = ((BigDecimal) user.rating()).floatValue();
                    String img_profile = user.img_profile();
                    String image = MediaManager.get().url().generate("berbagimakanan/" + img_profile);
                    String imagehttp = "";

                    if (image.contains("http://")) {
                        String[] images = image.split("http://");

                        imagehttp = images[1];
                    } else if (image.contains("https://")) {
                        String[] images = image.split("https://");

                        imagehttp = images[1];
                    }

                    onSetRatingBar(rating);
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

        btnkeluarapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreference.clearSharedPreference();
                Intent intent = new Intent(getContext(), HalamanUtama.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        linkgantikatasandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new FragmentGantiKataSandi(), "Berhasil");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        linkpenggunaterdekat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new FragmentPenggunaTerdekat(), "Berhasil");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        linktentangaplikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new FragmentTentangAplikasi(), "Berhasil");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        linktpanduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HalamanPanduan.class);
                startActivity(intent);
            }
        });

        linkeditprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new FragmentProfilSaya(), "Berhasil");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return view;

    }

    @Override
    public void onSetRatingBar(float rating) {
        ratingBar.setRating(rating);
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
                Picasso.get().load(image).error(R.drawable.ic_fotoprofil).into(fotoprofil, new Callback() {
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