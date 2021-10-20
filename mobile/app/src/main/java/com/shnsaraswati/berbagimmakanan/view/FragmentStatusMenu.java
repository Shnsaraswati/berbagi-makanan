package com.shnsaraswati.berbagimmakanan.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.adapter.StatusMakananRecyclerViewAdapter;
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;
import com.shnsaraswati.berbagimmakanan.presenter.PostContract;
import com.shnsaraswati.berbagimmakanan.presenter.PostPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentStatusMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentStatusMenu extends Fragment implements PostContract.ViewFragmentStatusMenu {
    public static final String TAG = "FragmentStatusMenu";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;

    SharedPreference sharedPreference;
    PostPresenter postPresenter;

    public FragmentStatusMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentStatusMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentStatusMenu newInstance(String param1, String param2) {
        FragmentStatusMenu fragment = new FragmentStatusMenu();
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
        postPresenter = new PostPresenter(this);
        sharedPreference = new SharedPreference(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_menu, container, false);

        recyclerView = view.findViewById(R.id.recyeclerviewStatusMenu);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

        String user_id = sharedPreference.getProfileID();

        postPresenter.onGetAllPostsByUser(user_id, new PostContract.Callback() {
            @Override
            public void onResponse(List posts) {
                StatusMakananRecyclerViewAdapter adapter = new StatusMakananRecyclerViewAdapter(requireContext(), posts, fragmentTransaction, postPresenter);

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
    public void onSetSuccess(String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction().detach(FragmentStatusMenu.this).commitNowAllowingStateLoss();
                getActivity().getSupportFragmentManager().beginTransaction().attach(FragmentStatusMenu.this).commitAllowingStateLoss();
            }
        });
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