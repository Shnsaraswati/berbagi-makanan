package com.shnsaraswati.berbagimmakanan.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;
import com.shnsaraswati.berbagimmakanan.presenter.ProfileContract;
import com.shnsaraswati.berbagimmakanan.presenter.ProfilePresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfilSaya#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfilSaya extends Fragment implements ProfileContract.ViewFragmentProfilSaya {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText editnama, editnohp, editalamat;
    SharedPreference sharedPreference;
    Button btnsimpanprofil;
    TextView linkeditfotoprofil;
    ProfilePresenter profilePresenter;

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
        profilePresenter =  new ProfilePresenter(this);
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

        String curName = sharedPreference.getProfileName();
        String userID = sharedPreference.getProfileID();
        String curPhonenumber = sharedPreference.getProfilePhonenumber();
        String curAddress = sharedPreference.getProfileAddress();

        editnama.setText(curName);
        editnohp.setText(curPhonenumber);
        editalamat.setText(curAddress);

        btnsimpanprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editnama.getText().toString();
                String phonenumber = editnohp.getText().toString();
                String alamat = editalamat.getText().toString();
                profilePresenter.onUpdateProfil(userID, name,phonenumber,alamat);
            }
        });

        return view;
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
    public void onFailure(String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}