package com.shnsaraswati.berbagimmakanan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shnsaraswati.berbagimmakanan.R;

import java.util.ArrayList;

public class NotifikasiRecyclerViewAdapter extends RecyclerView.Adapter<NotifikasiRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> imgakun_notif = new ArrayList<>();
    private ArrayList<String> txtnamaakun_notif = new ArrayList<>();
    private ArrayList<String> txtketerangan_notif = new ArrayList<>();
    private ArrayList<String> btndtolak = new ArrayList<>();
    private ArrayList<String> btnterima = new ArrayList<>();
    private Context context;

    public NotifikasiRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public NotifikasiRecyclerViewAdapter(ArrayList<String> imgakun_notif, ArrayList<String> txtnamaakun_notif,ArrayList<String> btnterima,ArrayList<String> btndtolak, ArrayList<String> txtketerangan_notif, Context context) {
        this.imgakun_notif = imgakun_notif;
        this.txtnamaakun_notif = txtnamaakun_notif;
        this.txtketerangan_notif = txtketerangan_notif;
        this.btnterima = btnterima;
        this.btndtolak = btndtolak;
        this.context = context;

    }

    @NonNull
    @Override
    public NotifikasiRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifikasi_layout_adapter,parent,false);
        NotifikasiRecyclerViewAdapter.ViewHolder viewHolder = new NotifikasiRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifikasiRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgakun_notif;
        TextView txtnamaakun_notif, txtketerangan_notif;
        ConstraintLayout constraintLayoutNotif;
        Button btnterima,btndtolak;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgakun_notif = itemView.findViewById(R.id.imgakun_notif);
            txtnamaakun_notif = itemView.findViewById(R.id.txtnamaakun_notif);
            txtketerangan_notif = itemView.findViewById(R.id.txtketerangan_notif);
            constraintLayoutNotif = itemView.findViewById(R.id.constraintLayoutNotif);
            btnterima = itemView.findViewById(R.id.btnterima);
            btndtolak = itemView.findViewById(R.id.btndtolak);

        }
    }
}
