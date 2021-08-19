package com.shnsaraswati.berbagimmakanan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shnsaraswati.berbagimmakanan.R;

import java.util.ArrayList;

public class PenggunaTerdekatRecyclerViewAdapter extends RecyclerView.Adapter<PenggunaTerdekatRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> imgakun_terdekat = new ArrayList<>();
    private ArrayList<String> txtnamaakun_terdekat = new ArrayList<>();
    private ArrayList<String> lokasi_terdekat = new ArrayList<>();
    private final Context context;

    public PenggunaTerdekatRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public PenggunaTerdekatRecyclerViewAdapter(ArrayList<String> imgakun_terdekat, ArrayList<String> txtnamaakun_terdekat, ArrayList<String> lokasi_terdekat, Context context) {
        this.imgakun_terdekat = imgakun_terdekat;
        this.txtnamaakun_terdekat = txtnamaakun_terdekat;
        this.lokasi_terdekat = lokasi_terdekat;
        this.context = context;

    }

    @NonNull
    @Override
    public PenggunaTerdekatRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.penggunaterdekat_layout_adapter, parent, false);
        PenggunaTerdekatRecyclerViewAdapter.ViewHolder viewHolder = new PenggunaTerdekatRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PenggunaTerdekatRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgakun_terdekat;
        TextView txtnamaakun_terdekat, lokasi_terdekat;
        ConstraintLayout constraintLayoutTerdekat;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgakun_terdekat = itemView.findViewById(R.id.imgakun_terdekat);
            txtnamaakun_terdekat = itemView.findViewById(R.id.txtnamaakun_terdekat);
            lokasi_terdekat = itemView.findViewById(R.id.lokasi_terdekat);
            constraintLayoutTerdekat = itemView.findViewById(R.id.constraintLayoutTerdekat);


        }
    }
}
