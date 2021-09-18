package com.shnsaraswati.berbagimmakanan.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.adapter.NotifikasiRecyclerViewAdapter;
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;
import com.shnsaraswati.berbagimmakanan.presenter.NotificationContract;
import com.shnsaraswati.berbagimmakanan.presenter.NotificationPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import query.UseFetchNotificationByUserIDQuery;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNotifikasi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNotifikasi extends Fragment implements NotificationContract.ViewFragmentNotifikasi {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "FragmentNotifikasi";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<UseFetchNotificationByUserIDQuery.User> users;

    RecyclerView recyclerView;

    NotificationPresenter notificationPresenter;
    SharedPreference sharedPreference;


    public FragmentNotifikasi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNotifikasi.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNotifikasi newInstance(String param1, String param2) {
        FragmentNotifikasi fragment = new FragmentNotifikasi();
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
        notificationPresenter = new NotificationPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifikasi, container, false);

        recyclerView = view.findViewById(R.id.recyeclerviewNotif);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        FragmentManager fragmentManager = getParentFragmentManager();


        String cur_id = sharedPreference.getProfileID();

        notificationPresenter.onGetNotification(cur_id, new NotificationContract.Callback() {
            @Override
            public void onResponse(List results) {
                NotifikasiRecyclerViewAdapter adapter = new NotifikasiRecyclerViewAdapter(getContext(), results, FragmentNotifikasi.this, fragmentTransaction, fragmentManager);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                onSetFailure("terjadi kesalahan");
            }
        });

        return view;
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
}