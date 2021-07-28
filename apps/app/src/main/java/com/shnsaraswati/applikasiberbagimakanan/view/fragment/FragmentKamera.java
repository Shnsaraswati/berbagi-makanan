package com.shnsaraswati.applikasiberbagimakanan.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.shnsaraswati.applikasiberbagimakanan.R;
import com.shnsaraswati.applikasiberbagimakanan.view.dashboard.HalamanEditAkun;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentKamera#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentKamera extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentKamera() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentKamera.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentKamera newInstance(String param1, String param2) {
        FragmentKamera fragment = new FragmentKamera();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kamera, container, false);

        getActivity().setTitle("Upload Makanan");
        ImagePicker.with(getActivity()).start();

        return view;
    }
}