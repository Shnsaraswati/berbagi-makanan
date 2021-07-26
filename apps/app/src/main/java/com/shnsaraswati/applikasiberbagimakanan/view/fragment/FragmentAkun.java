package com.shnsaraswati.applikasiberbagimakanan.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudinary.android.MediaManager;
import com.shnsaraswati.applikasiberbagimakanan.R;
import com.shnsaraswati.applikasiberbagimakanan.model.User;
import com.shnsaraswati.applikasiberbagimakanan.presenter.profile.ProfileDataSource;
import com.shnsaraswati.applikasiberbagimakanan.presenter.profile.ProfileRepository;
import com.shnsaraswati.applikasiberbagimakanan.view.dashboard.HalamanEditAkun;
import com.squareup.picasso.Picasso;

import java.util.List;

import query.GetProfileQuery;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAkun#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAkun extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String SHARED_PREF_NAME = "shared_preferences";
    private static final String TAG = "FRAGMENT_AKUN";

    SharedPreferences sharedPreferences;

    User user;

    TextView txtNamaAkun;
    TextView txtLokasi;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAkun() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAkun.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAkun newInstance(String param1, String param2) {
        FragmentAkun fragment = new FragmentAkun();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MediaManager.init(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_akun,
                container, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_akun, container, false);

        // cara merubah action bar nya
        getActivity().setTitle("Profile");

        txtNamaAkun = view.findViewById(R.id.txtnamaakun);
        txtLokasi = view.findViewById(R.id.textView9);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ImageView foto = view.findViewById(R.id.imageView8);

        String id = sharedPreferences.getString("user_id", "");

        user = new User(id);

        ProfileRepository repository = new ProfileRepository();

        repository.getProfile(user, new ProfileDataSource.LoadDataCallback() {
            @Override
            public void onDataLoaded(List<GetProfileQuery.User> profiles) {
                txtNamaAkun.setText(profiles.get(0).name());
                txtLokasi.post(new Runnable() {
                    @Override
                    public void run() {
                        txtLokasi.setText(profiles.get(0).address());
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
                                .error(R.drawable.ic_account)
                                .fit()
                                .into(foto);
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


        Button button = view.findViewById(R.id.btneditakun);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), HalamanEditAkun.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

