package com.shnsaraswati.berbagimmakanan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.view.FragmentMenuDipilih;
import com.squareup.picasso.Picasso;

import java.util.List;

import query.UseGetAllPostsQuery;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolder> {
    public static final String TAG = "MenuRecyclerViewAdapter";

    private Context context;
    private FragmentTransaction fragmentTransaction;
    private List<UseGetAllPostsQuery.Post> posts;

    public MenuRecyclerViewAdapter(Context context, FragmentTransaction fragmentTransaction, List<UseGetAllPostsQuery.Post> posts) {
        Log.d(TAG, "MenuRecyclerViewAdapter Posts: " + posts);
        this.context = context;
        this.fragmentTransaction = fragmentTransaction;
        this.posts = posts;
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
        String seen = String.valueOf(posts.get(position).seen());

        holder.txtnamapemberi.setText(posts.get(position).user().name());
        holder.txtnamamakanan_menu.setText(posts.get(position).name_food());
        holder.txtdilihat.setText(seen);
        holder.txtlinklokasimakanan.setText(posts.get(position).address());
        Picasso.get().load(posts.get(position).picture()).error(R.drawable.ic_fotomakanan_menu).into(holder.fotomakanan_menu);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragment, new FragmentMenuDipilih(), "Berhasil");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fotomakanan_menu;
        TextView txtnamapemberi,txtnamamakanan_menu,txtlinklokasimakanan,txtdilihat;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fotomakanan_menu = itemView.findViewById(R.id.fotomakanan_menushimmer);
            txtnamapemberi = itemView.findViewById(R.id.txtnamapemberishimmer);
            txtnamamakanan_menu = itemView.findViewById(R.id.txtnamamakanan_menushimmer);
            txtlinklokasimakanan = itemView.findViewById(R.id.txtlinklokasimakananshimmer);
            txtdilihat = itemView.findViewById(R.id.txtdilihatshimmer);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

        }
    }
}
