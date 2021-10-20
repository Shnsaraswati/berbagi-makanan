package com.shnsaraswati.berbagimmakanan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.presenter.PostPresenter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import query.UseGetAllPostsByUserQuery;

public class StatusMakananRecyclerViewAdapter extends RecyclerView.Adapter<StatusMakananRecyclerViewAdapter.ViewHolder> {
    public static final String TAG = "StatusMakananAdapter";

    private Context context;
    private List<UseGetAllPostsByUserQuery.Post> posts;
    private FragmentTransaction fragmentTransaction;
    PostPresenter postPresenter;

    public StatusMakananRecyclerViewAdapter(Context context, List<UseGetAllPostsByUserQuery.Post> posts, FragmentTransaction fragmentTransaction, PostPresenter postPresenter) {
        this.context = context;
        this.posts = posts;
        this.fragmentTransaction = fragmentTransaction;
        this.postPresenter = postPresenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statusmakanan_layout_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtnamamakanan.setText(posts.get(position).name_food());
        String image = MediaManager.get().url().generate("berbagimakanan/" + posts.get(position).picture());
        Picasso.get().load(image).error(R.drawable.ic_fotoprofil).placeholder(R.drawable.ic_fotomakanan_menu).into(holder.fotomakanan_upload, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess:");
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }
        });
        holder.btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postPresenter.onInactivePost(posts.get(position).id().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtnamamakanan;
        ImageView fotomakanan_upload;
        Button btnhapus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtnamamakanan = itemView.findViewById(R.id.txtnamamakanan);
            fotomakanan_upload = itemView.findViewById(R.id.fotomakanan_upload);
            btnhapus = itemView.findViewById(R.id.btnhapus);
        }
    }
}
