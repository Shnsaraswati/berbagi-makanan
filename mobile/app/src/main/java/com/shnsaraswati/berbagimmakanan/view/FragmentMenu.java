package com.shnsaraswati.berbagimmakanan.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apollographql.apollo.exception.ApolloException;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.adapter.MenuRecyclerViewAdapter;
import com.shnsaraswati.berbagimmakanan.presenter.PostContract;
import com.shnsaraswati.berbagimmakanan.presenter.PostPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

import query.UseGetAllPostsQuery;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMenu extends Fragment implements PostContract.ViewFragmentMenu {
    private static final String TAG = "FragmentMenu";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ShimmerFrameLayout menuShimmerFrame;
    RecyclerView recyclerView;
    List<UseGetAllPostsQuery.Post> posts;
    PostPresenter postPresenter;

    public FragmentMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMenu newInstance(String param1, String param2) {
        FragmentMenu fragment = new FragmentMenu();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        recyclerView = view.findViewById(R.id.recyeclerview);
        menuShimmerFrame = view.findViewById(R.id.menuShimmerFrame);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

        // mengambil data semua posts
        postPresenter.onGetAllPosts(new PostContract.Callback() {
            @Override
            public void onResponse(List<UseGetAllPostsQuery.Post> posts) {
                MenuRecyclerViewAdapter adapter = new MenuRecyclerViewAdapter( getContext(), fragmentTransaction, posts);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        menuShimmerFrame.stopShimmer();
                        menuShimmerFrame.setVisibility(View.GONE);
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