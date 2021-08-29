package com.shnsaraswati.berbagimmakanan.presenter;

import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotificationContract {
    public interface NotificationRecyclerView {
        void onSuccessAnswerNotification();
        void onSuccessUpdateNotification();
        void onFailure(String message);
    }

    public interface ViewFragmentNotifikasi {
        void onSetFailure(String message);
    }

    public interface Callback<T> {
        void onResponse(List<T> results);

        void onFailure(@NotNull ApolloException e);
    }

    interface Presenter{
        void onGetNotification(String id, Callback callback);
        void onUpdateNotification(String notification_id);
        void onSendNotification(String keterangan, String status, boolean seen, String user_id, String to_user_id, String post_id);
        void onSendBackNotification(String keterangan, String status, boolean seen, String user_id, String to_user_id, String post_id, String notification_id);
    }
}
