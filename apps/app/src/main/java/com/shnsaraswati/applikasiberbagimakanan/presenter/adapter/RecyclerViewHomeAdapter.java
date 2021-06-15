package com.shnsaraswati.applikasiberbagimakanan.presenter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shnsaraswati.applikasiberbagimakanan.R;
import com.shnsaraswati.applikasiberbagimakanan.model.Model;

import java.util.ArrayList;

public class RecyclerViewHomeAdapter extends RecyclerView.Adapter<RecyclerViewHomeAdapter.ViewHolder> {
    public static final String TAG = "RecyclerViewHomeAdapter";

    private ArrayList<Model> models = new ArrayList<>();
    private final Context context;

    public RecyclerViewHomeAdapter(ArrayList<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called ");

        holder.txtNamaAkun.setText(models.get(position).getNamaAkun());
        holder.txtLokasi.setText(models.get(position).getLokasi());
        holder.txtNamaMakanan.setText(models.get(position).getNamaMakanan());
        holder.txtDilihat.setText(String.valueOf(models.get(position).getDilihat()));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtNamaAkun, txtNamaMakanan, txtDilihat, txtLokasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.fotoupload);
            txtNamaAkun = itemView.findViewById(R.id.namaakunpost);
            txtNamaMakanan = itemView.findViewById(R.id.namamakananpost);
            txtDilihat = itemView.findViewById(R.id.dilihat);
            txtLokasi = itemView.findViewById(R.id.lokasipost);
        }
    }
}
