package com.shnsaraswati.berbagimmakanan.presenter;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.berbagimmakanan.config.Apollo;

import org.jetbrains.annotations.NotNull;

import mutation.UseAddNoticiationMutation;

public class NotificationPresenter implements NotificationContract.Presenter {
    Apollo apollo = new Apollo();
    ApolloClient apolloClient = apollo.ConnectApollo();

    PostContract.ViewFragmentMenuDipilih viewFragmentMenuDipilih;

    public NotificationPresenter(PostContract.ViewFragmentMenuDipilih viewFragmentMenuDipilih) {
        this.viewFragmentMenuDipilih = viewFragmentMenuDipilih;
    }

    @Override
    public void onGetNotification() {

    }

    @Override
    public void onSendNotification(String keterangan, String status, String user_id, String to_user_id, String post_id) {
        apolloClient.mutate(new UseAddNoticiationMutation(keterangan, status, user_id, to_user_id, post_id)).enqueue(new ApolloCall.Callback<UseAddNoticiationMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseAddNoticiationMutation.Data> response) {
                if (response.getData() != null) {
                    if (response.getData().insert_notification().affected_rows() > 0) {
                        viewFragmentMenuDipilih.onSuccessSendNotification();
                    } else {
                        viewFragmentMenuDipilih.onSetFailure("gagal mengirim notifikasi ke pemilik");
                    }
                } else {
                    viewFragmentMenuDipilih.onSetFailure("gagal mengirim notifikasi ke pemilik");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                viewFragmentMenuDipilih.onSetFailure("terjadi kesalahan");
            }
        });
    }
}
