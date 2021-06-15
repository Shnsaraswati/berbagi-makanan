package com.shnsaraswati.applikasiberbagimakanan.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shnsaraswati.applikasiberbagimakanan.R;
import com.shnsaraswati.applikasiberbagimakanan.model.Model;
import com.shnsaraswati.applikasiberbagimakanan.presenter.adapter.RecyclerViewHomeAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {
    public static final String TAG = "FragmentHome";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // cara merubah action bar nya
        getActivity().setTitle("Berbagi Makanan");

        ArrayList<Model> models = getMylist();

        Log.d(TAG, "onCreateView: " + models);

        recyclerView = view.findViewById(R.id.recyeclerview);
        RecyclerViewHomeAdapter adapter = new RecyclerViewHomeAdapter(models, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
    private ArrayList<Model> getMylist(){
        ArrayList<Model> models = new ArrayList<>();
        Model m =new Model();
        m.setNamaAkun("Nama");
        m.setNamaMakanan("Nama Makanan");
        m.setLokasi("lokasi");
        m.setImg(R.drawable.ic_fotomakanan);
        m.setDilihat(0);
        models.add(m);

        m =new Model();
        m.setNamaAkun("Nama2");
        m.setNamaMakanan("Nama Makanan2");
        m.setLokasi("lokasi2");
        m.setImg(R.drawable.ic_fotomakanan);
        m.setDilihat(0);
        models.add(m);

        m =new Model();
        m.setNamaAkun("Nama3");
        m.setNamaMakanan("Nama Makanan3");
        m.setLokasi("lokasi3");
        m.setImg(R.drawable.ic_fotomakanan);
        m.setDilihat(0);
        models.add(m);

        m =new Model();
        m.setNamaAkun("Nama4");
        m.setNamaMakanan("Nama Makanan4");
        m.setLokasi("lokasi4");
        m.setImg(R.drawable.ic_fotomakanan);
        m.setDilihat(0);
        models.add(m);

        m =new Model();
        m.setNamaAkun("Nama5");
        m.setNamaMakanan("Nama Makanan5");
        m.setLokasi("lokasi5");
        m.setImg(R.drawable.ic_fotomakanan);
        m.setDilihat(0);
        models.add(m);

        m =new Model();
        m.setNamaAkun("Nama6");
        m.setNamaMakanan("Nama Makanan6");
        m.setLokasi("lokasi6");
        m.setImg(R.drawable.ic_fotomakanan);
        m.setDilihat(0);
        models.add(m);

        m =new Model();
        m.setNamaAkun("Nama7");
        m.setNamaMakanan("Nama Makanan7");
        m.setLokasi("lokasi7");
        m.setImg(R.drawable.ic_fotomakanan);
        m.setDilihat(0);
        models.add(m);

        m =new Model();
        m.setNamaAkun("Nama8");
        m.setNamaMakanan("Nama Makanan8");
        m.setLokasi("lokasi8");
        m.setImg(R.drawable.ic_fotomakanan);
        m.setDilihat(0);
        models.add(m);

        m =new Model();
        m.setNamaAkun("Nama9");
        m.setNamaMakanan("Nama Makanan9");
        m.setLokasi("lokasi9");
        m.setImg(R.drawable.ic_fotomakanan);
        m.setDilihat(0);
        models.add(m);

        return models;
    }

    /* @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.sorting){
            sortDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortDialog() {
        String[] options = ("Ascending","Descending");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Sort by");
        builder.setIcon(R.drawable.ic_sort);

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0){

                }
                if (which == 1){

                }

            }
        });
        builder.create().show();


    } */
}