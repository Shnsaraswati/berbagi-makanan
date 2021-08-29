package com.shnsaraswati.berbagimmakanan.presenter;

import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotificationContract {
    public interface Callback<T> {
        void onResponse(List<T> posts);

        void onFailure(@NotNull ApolloException e);
    }

    interface Presenter{
        void onGetNotification();
        void onSendNotification(String keterangan, String status, String user_id, String to_user_id, String post_id);
    }
}
