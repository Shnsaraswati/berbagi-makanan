package com.shnsaraswati.berbagimmakanan.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;
import com.shnsaraswati.berbagimmakanan.presenter.PostContract;
import com.shnsaraswati.berbagimmakanan.presenter.PostPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBerbagi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBerbagi extends Fragment implements PostContract.ViewFragmentBerbagi {

    TextView txtlokasi;
    Button btnunggah;
    EditText inputnamamakanan, inputdeskripsinamamakanan;
    ImageView imguploadmakanan;

    PostPresenter postPresenter;
    SharedPreference sharedPreference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentBerbagi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBerbagi.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBerbagi newInstance(String param1, String param2) {
        FragmentBerbagi fragment = new FragmentBerbagi();
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
        postPresenter = new PostPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_berbagi, container, false);
        txtlokasi = view.findViewById(R.id.txtlokasi);
        btnunggah = view.findViewById(R.id.btnunggah);
        inputnamamakanan = view.findViewById(R.id.inputnamamakanan);
        inputdeskripsinamamakanan = view.findViewById(R.id.inputdeskripsinamamakanan);
        imguploadmakanan = view.findViewById(R.id.imguploadmakanan);

        String curUserID = sharedPreference.getProfileID();

        btnunggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namefood = inputnamamakanan.getText().toString();
            }
        });

        txtlokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new FragmentMaps(), "Berhasil");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onSuccessAddPost(String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
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