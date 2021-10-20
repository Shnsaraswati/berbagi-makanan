package com.shnsaraswati.berbagimmakanan.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;
import com.shnsaraswati.berbagimmakanan.presenter.ProfileContract;
import com.shnsaraswati.berbagimmakanan.presenter.ProfilePresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGantiKataSandi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGantiKataSandi extends Fragment implements ProfileContract.ViewFragmentGantiKataSandi {

    EditText inputkatasandibaru, inputkonfirmasikatasandibaru, inputkatasandilama;
    Button btnsimpangansandibaru_gantisandi;

    SharedPreference sharedPreference;
    ProfilePresenter profilePresenter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentGantiKataSandi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGantiKataSandi.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGantiKataSandi newInstance(String param1, String param2) {
        FragmentGantiKataSandi fragment = new FragmentGantiKataSandi();
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
        View view = inflater.inflate(R.layout.fragment_ganti_kata_sandi, container, false);

        inputkatasandilama = view.findViewById(R.id.inputkatasandilama);
        inputkatasandibaru = view.findViewById(R.id.inputkatasandibaru);
        inputkonfirmasikatasandibaru = view.findViewById(R.id.inputkonfirmasikatasandibaru);

        btnsimpangansandibaru_gantisandi = view.findViewById(R.id.btnsimpangansandibaru_gantisandi);
        btnsimpangansandibaru_gantisandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = sharedPreference.getProfileID();
                String oldPassword = inputkatasandilama.getText().toString();
                String newPassword = inputkatasandibaru.getText().toString();
                String confirmationPassword = inputkonfirmasikatasandibaru.getText().toString();

                if (newPassword.equals(confirmationPassword)) {
                    profilePresenter.onUpdateProfilePassword(userID, oldPassword, newPassword);
                } else {
                    Toast.makeText(getContext(), "password dan konfirmasi password harus sama" +
                            "", Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }

    @Override
    public void onUpdateProfilePassword() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "password telah diperbarui", Toast.LENGTH_LONG).show();
            }
        });
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