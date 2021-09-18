package com.shnsaraswati.berbagimmakanan.adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.presenter.NotificationContract;
import com.shnsaraswati.berbagimmakanan.presenter.NotificationPresenter;
import com.shnsaraswati.berbagimmakanan.view.DialogRating;
import com.shnsaraswati.berbagimmakanan.view.FragmentMenuDipilih;
import com.shnsaraswati.berbagimmakanan.view.FragmentNotifikasi;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import query.UseFetchNotificationByUserIDQuery;

public class NotifikasiRecyclerViewAdapter extends RecyclerView.Adapter<NotifikasiRecyclerViewAdapter.ViewHolder> implements NotificationContract.NotificationRecyclerView {
    public static final String TAG = "NotifikasiRecycler";

    private final Context context;
    private List<UseFetchNotificationByUserIDQuery.User> users;
    private FragmentNotifikasi fragmentNotifikasi;
    private final FragmentTransaction fragmentTransaction;
    private final FragmentManager fragmentManager;
    private NotificationPresenter notificationPresenter;

    public NotifikasiRecyclerViewAdapter(Context context, List<UseFetchNotificationByUserIDQuery.User> users, FragmentNotifikasi fragmentNotifikasi, FragmentTransaction fragmentTransaction, FragmentManager fragmentManager) {
        this.context = context;
        this.users = users;
        this.fragmentNotifikasi = fragmentNotifikasi;
        this.fragmentTransaction = fragmentTransaction;
        this.fragmentManager = fragmentManager;
        notificationPresenter = new NotificationPresenter(this);
    }

    @NonNull
    @Override
    public NotifikasiRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifikasi_layout_adapter, parent, false);
        NotifikasiRecyclerViewAdapter.ViewHolder viewHolder = new NotifikasiRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifikasiRecyclerViewAdapter.ViewHolder holder, int position) {
        String user_id = users.get(0).id().toString();
        String to_user_id = users.get(0).notification_users().get(position).user().id().toString();
        String post_id = users.get(0).notification_users().get(position).post_id().toString();
        String notification_id = users.get(0).notification_users().get(position).id().toString();
        String sender_name = users.get(0).notification_users().get(position).user().name();
        String keterangan = users.get(0).notification_users().get(position).keterangan();
        String img_profile = users.get(0).notification_users().get(position).user().img_profile();
        String status = users.get(0).notification_users().get(position).status();
        String image = MediaManager.get().url().generate("berbagimakanan/" + img_profile);
        float rating = ((BigDecimal) users.get(0).notification_users().get(0).user().rating()).floatValue();
        int count_rating = users.get(0).notification_users().get(0).user().count_rating();

        holder.txtnamaakun_notif.setText(sender_name);
        holder.txtketerangan_notif.setText(keterangan);
        Picasso.get().load(image).error(R.drawable.ic_fotoprofil).placeholder(R.drawable.ic_fotoprofil).into(holder.imgakun_notif, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess:");
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }
        });
        if (status.equals("diterima")) {
            holder.btnterima.setText("Beri Rating");
            holder.btndtolak.setText("Lihat");
        }
        holder.btnterima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keterangan_update = "Permintaan Diterima";
                String status_update = "diterima";
                boolean seen_update = false;

                if (holder.btnterima.getText().toString().equals("Terima")) {
                    notificationPresenter.onSendBackNotification(keterangan_update, status_update, seen_update, user_id, to_user_id, post_id, notification_id);
                } else {
                    fragmentNotifikasi.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "beri rating", Toast.LENGTH_LONG).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("user_id", to_user_id);
                            bundle.putFloat("rating", rating);
                            bundle.putString("notification_id", notification_id);
                            bundle.putInt("count_rating", count_rating+1);
                            DialogRating dialogRating = new DialogRating();
                            dialogRating.setArguments(bundle);
                            dialogRating.show(fragmentManager, "berikan rating");
                        }
                    });
                }
            }
        });

        holder.btndtolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btndtolak.getText().toString().equals("Tolak")) {
                    notificationPresenter.onUpdateNotification(notification_id);
                } else {
                    fragmentNotifikasi.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(context, "Lihat makanan", Toast.LENGTH_LONG).show();
                            FragmentMenuDipilih fragmentMenuDipilih = new FragmentMenuDipilih();
                            Bundle bundle = new Bundle();
                            bundle.putString("post_id", post_id);
                            bundle.putString("user_post_id", user_id);
                            bundle.putBoolean("is_detail", false);
                            fragmentMenuDipilih.setArguments(bundle);
                            fragmentTransaction.replace(R.id.fragment, fragmentMenuDipilih, "Berhasil");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.get(0).notification_users().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgakun_notif;
        TextView txtnamaakun_notif, txtketerangan_notif;
        ConstraintLayout constraintLayoutNotif;
        Button btnterima, btndtolak;

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

    @Override
    public void onSuccessAnswerNotification() {
        fragmentNotifikasi.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "berhasil menjawab notifikasi", Toast.LENGTH_LONG).show();
                fragmentNotifikasi.getActivity().getSupportFragmentManager().beginTransaction().detach(fragmentNotifikasi).commitNowAllowingStateLoss();
                fragmentNotifikasi.getActivity().getSupportFragmentManager().beginTransaction().attach(fragmentNotifikasi).commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onFailure(String message) {
        fragmentNotifikasi.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onSuccessUpdateNotification() {
        fragmentNotifikasi.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "berhasil menolak notifikasi", Toast.LENGTH_LONG).show();
                fragmentNotifikasi.getActivity().getSupportFragmentManager().beginTransaction().detach(fragmentNotifikasi).commitNowAllowingStateLoss();
                fragmentNotifikasi.getActivity().getSupportFragmentManager().beginTransaction().attach(fragmentNotifikasi).commitAllowingStateLoss();
            }
        });
    }
}
