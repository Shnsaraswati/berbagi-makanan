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

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> fotomakanan_menu = new ArrayList<>();
    private ArrayList<String> txtnamapemberi = new ArrayList<>();
    private ArrayList<String> txtnamamakanan_menu = new ArrayList<>();
    private ArrayList<String> txtlinklokasimakanan = new ArrayList<>();
    private ArrayList<String> txtdilihat = new ArrayList<>();
    private Context context;

    public MenuRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public MenuRecyclerViewAdapter(ArrayList<String> fotomakanan_menu, ArrayList<String> txtnamapemberi, ArrayList<String> txtnamamakanan_menu, ArrayList<String> txtlinklokasimakanan, ArrayList<String> txtdilihat, Context context) {
        this.fotomakanan_menu = fotomakanan_menu;
        this.txtnamapemberi = txtnamapemberi;
        this.txtnamamakanan_menu = txtnamamakanan_menu;
        this.txtlinklokasimakanan = txtlinklokasimakanan;
        this.txtdilihat = txtdilihat;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_layout_adapter,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fotomakanan_menu;
        TextView txtnamapemberi,txtnamamakanan_menu,txtlinklokasimakanan,txtdilihat;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fotomakanan_menu = itemView.findViewById(R.id.fotomakanan_menu);
            txtnamapemberi = itemView.findViewById(R.id.txtnamapemberi);
            txtnamamakanan_menu = itemView.findViewById(R.id.txtnamamakanan_menu);
            txtlinklokasimakanan = itemView.findViewById(R.id.txtlinklokasimakanan);
            txtdilihat = itemView.findViewById(R.id.txtdilihat);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

        }
    }
}
