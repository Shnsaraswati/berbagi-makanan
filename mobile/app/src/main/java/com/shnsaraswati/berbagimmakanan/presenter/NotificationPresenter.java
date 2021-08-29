package com.shnsaraswati.berbagimmakanan.presenter;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.berbagimmakanan.config.Apollo;

import org.jetbrains.annotations.NotNull;

import mutation.UseAddNoticiationMutation;
import mutation.UseUpdateNotificationMutation;
import query.UseFetchNotificationByUserIDQuery;

public class NotificationPresenter implements NotificationContract.Presenter {
    Apollo apollo = new Apollo();
    ApolloClient apolloClient = apollo.ConnectApollo();

    PostContract.ViewFragmentMenuDipilih viewFragmentMenuDipilih;
    NotificationContract.ViewFragmentNotifikasi viewFragmentNotifikasi;
    NotificationContract.NotificationRecyclerView notificationRecyclerView;


    public NotificationPresenter(PostContract.ViewFragmentMenuDipilih viewFragmentMenuDipilih) {
        this.viewFragmentMenuDipilih = viewFragmentMenuDipilih;
    }

    public NotificationPresenter(NotificationContract.ViewFragmentNotifikasi viewFragmentNotifikasi) {
        this.viewFragmentNotifikasi = viewFragmentNotifikasi;
    }

    public NotificationPresenter(NotificationContract.NotificationRecyclerView notificationRecyclerView) {
        this.notificationRecyclerView = notificationRecyclerView;
    }

    @Override
    public void onGetNotification(String id, NotificationContract.Callback callback) {
        apolloClient.query(new UseFetchNotificationByUserIDQuery(id)).enqueue(new ApolloCall.Callback<UseFetchNotificationByUserIDQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseFetchNotificationByUserIDQuery.Data> response) {
                if (!response.getData().users().isEmpty()) {
                    callback.onResponse(response.getData().users());
                } else {
                    viewFragmentNotifikasi.onSetFailure("tidak ada notifikasi");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                callback.onFailure(e);
            }
        });
    }

    @Override
    public void onSendNotification(String keterangan, String status, boolean seen, String user_id, String to_user_id, String post_id) {
        apolloClient.mutate(new UseAddNoticiationMutation(keterangan, status, seen, user_id, to_user_id, post_id)).enqueue(new ApolloCall.Callback<UseAddNoticiationMutation.Data>() {
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

    @Override
    public void onSendBackNotification(String keterangan, String status, boolean seen, String user_id, String to_user_id, String post_id, String notification_id) {
        apolloClient.mutate(new UseUpdateNotificationMutation(notification_id)).enqueue(new ApolloCall.Callback<UseUpdateNotificationMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseUpdateNotificationMutation.Data> response) {
                if (response.getData() != null && response.getData().update_notification().affected_rows() > 0) {
                    apolloClient.mutate(new UseAddNoticiationMutation(keterangan, status, seen, user_id, to_user_id, post_id)).enqueue(new ApolloCall.Callback<UseAddNoticiationMutation.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<UseAddNoticiationMutation.Data> response) {
                            if (response.getData() != null) {
                                if (response.getData().insert_notification().affected_rows() > 0) {
                                    notificationRecyclerView.onSuccessAnswerNotification();
                                } else {
                                    notificationRecyclerView.onFailure("gagal mengirim notifikasi ke pemilik");
                                }
                            } else {
                                notificationRecyclerView.onFailure("gagal mengirim notifikasi ke pemilik");
                            }
                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                            notificationRecyclerView.onFailure("terjadi kesalahan");
                        }
                    });
                } else {
                    notificationRecyclerView.onFailure("gagal melakukan update notifikasi");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                notificationRecyclerView.onFailure("terjadi kesalahan update notifikasi");
            }
        });
    }

    @Override
    public void onUpdateNotification(String notification_id) {
        apolloClient.mutate(new UseUpdateNotificationMutation(notification_id)).enqueue(new ApolloCall.Callback<UseUpdateNotificationMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseUpdateNotificationMutation.Data> response) {
                if (response.getData() != null && response.getData().update_notification().affected_rows() > 0) {
                    notificationRecyclerView.onSuccessUpdateNotification();
                } else {
                    notificationRecyclerView.onFailure("gagal melakukan update notifikasi");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                notificationRecyclerView.onFailure("terjadi kesalahan update notifikasi");
            }
        });
    }
}
