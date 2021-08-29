package com.shnsaraswati.berbagimmakanan.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apollographql.apollo.exception.ApolloException;
import com.cloudinary.android.MediaManager;
import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;
import com.shnsaraswati.berbagimmakanan.presenter.NotificationPresenter;
import com.shnsaraswati.berbagimmakanan.presenter.PostContract;
import com.shnsaraswati.berbagimmakanan.presenter.PostPresenter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import query.UseGetPostByIdQuery;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMenuDipilih#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMenuDipilih extends Fragment implements PostContract.ViewFragmentMenuDipilih {

    public static final String TAG = "FragmentMenuDipilih";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView txtnamaakun_menudipilih, txtnamamakanan_menudipilih, txtxdeskripsimakanan_menudipilih, txtlokasimakanan_menudipilih, txtdilihat_menudipilih;
    ImageView imgmakanandipilih;
    Button btnmintamakanan;

    PostPresenter postPresenter;
    NotificationPresenter notificationPresenter;

    SharedPreference sharedPreference;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMenuDipilih() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMenuDipilih.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMenuDipilih newInstance(String param1, String param2) {
        FragmentMenuDipilih fragment = new FragmentMenuDipilih();
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
        notificationPresenter = new NotificationPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_dipilih, container, false);

        Bundle bundle = this.getArguments();
        String post_id = bundle.getString("post_id");
        String user_post_id = bundle.getString("user_post_id");

        txtnamaakun_menudipilih = view.findViewById(R.id.txtnamaakun_menudipilih);
        txtnamamakanan_menudipilih = view.findViewById(R.id.txtnamamakanan_menudipilih);
        txtxdeskripsimakanan_menudipilih = view.findViewById(R.id.txtxdeskripsimakanan_menudipilih);
        txtlokasimakanan_menudipilih = view.findViewById(R.id.txtlokasimakanan_menudipilih);
        txtdilihat_menudipilih = view.findViewById(R.id.txtdilihat_menudipilih);
        imgmakanandipilih = view.findViewById(R.id.imgmakanandipilih);
        btnmintamakanan = view.findViewById(R.id.btnmintamakanan);

        String user_id = sharedPreference.getProfileID();

        btnmintamakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keterangan = "permintaan makanan";
                String status = "permintaan;";
                boolean seen = false;
                notificationPresenter.onSendNotification(keterangan, status, seen, user_id, user_post_id, post_id);
            }
        });

        postPresenter.onGetPost(post_id, new PostContract.Callback() {
            @Override
            public void onResponse(List posts) {
                getActivity().runOnUiThread(new Runnable() {
                    UseGetPostByIdQuery.Post post = (UseGetPostByIdQuery.Post) posts.get(0);
                    String seen = String.valueOf(post.seen());
                    @Override
                    public void run() {
                        txtnamaakun_menudipilih.setText(post.user().name());
                        txtnamamakanan_menudipilih.setText(post.name_food());
                        txtlokasimakanan_menudipilih.setText(post.address());
                        txtdilihat_menudipilih.setText(seen);
                        txtxdeskripsimakanan_menudipilih.setText(post.description() == null ? "Tidak ada deskripsi": post.description());
                        String image = MediaManager.get().url().generate("berbagimakanan/" + post.picture());
                        Picasso.get().load(image).error(R.drawable.ic_fotoprofil).placeholder(R.drawable.ic_fotomakanan_menu).into(imgmakanandipilih, new Callback() {
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

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });

        return view;
    }

    @Override
    public void onSuccessSendNotification() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "Berhsil mengirim notifikasi ke pemilik", Toast.LENGTH_LONG).show();
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