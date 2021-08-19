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

public class RiwayatRecyclerViewAdapter extends RecyclerView.Adapter<RiwayatRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> imgakun = new ArrayList<>();
    private ArrayList<String> txtnamaakun_riwayat = new ArrayList<>();
    private ArrayList<String> txtketerangan_riwayat = new ArrayList<>();
    private final Context context;

    public RiwayatRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public RiwayatRecyclerViewAdapter(ArrayList<String> imgakun, ArrayList<String> txtnamaakun_riwayat, ArrayList<String> txtketerangan_riwayat, Context context) {
        this.imgakun = imgakun;
        this.txtnamaakun_riwayat = txtnamaakun_riwayat;
        this.txtketerangan_riwayat = txtketerangan_riwayat;
        this.context = context;

    }

    @NonNull
    @Override
    public RiwayatRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.riwayat_layout_adapter, parent, false);
        RiwayatRecyclerViewAdapter.ViewHolder viewHolder = new RiwayatRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgakun;
        TextView txtnamaakun_riwayat, txtketerangan_riwayat;
        ConstraintLayout constraintLayoutRiwayat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgakun = itemView.findViewById(R.id.imgakun);
            txtnamaakun_riwayat = itemView.findViewById(R.id.txtnamaakun_riwayat);
            txtketerangan_riwayat = itemView.findViewById(R.id.txtketerangan_riwayat);
            constraintLayoutRiwayat = itemView.findViewById(R.id.constraintLayouRiwayat);

        }
    }
}
