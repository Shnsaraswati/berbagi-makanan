package com.shnsaraswati.berbagimmakanan.presenter;

import android.net.Uri;

import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import query.UseGetAllPostsQuery;

public class PostContract {
    public interface ViewFragmentMenu {
        void onSetFailure(String message);
    }

    public interface ViewFragmentBerbagi {
        void onSuccessAddPost(String message);

        void onFailure(String message);
    }

    public interface ViewFragmentMenuDipilih {
        void onSuccessSendNotification();
        void onSetFailure(String message);
    }

    public interface ViewFragmentStatusMenu {
        void onSetSuccess(String message);
        void onSetFailure(String message);
    }

    public interface MenuRecyclerView {
        void onSuccessUpdateSeenPost(String id, String user_id);

        void onFailure(String message);
    }

    public interface StatusMakananRecyclerView {
        void OnSuccess();
        void onFailure();
    }

    public interface Callback<T> {
        void onResponse(List<T> posts);

        void onFailure(@NotNull ApolloException e);
    }

    interface Presenter {
        void onGetAllPosts(Callback callback);

        void onGetAllPostsByUser(String user_id, Callback callback);

        void onGetPost(String id, Callback callback);

        void onNewAddPost(String namefood, String address, String user_id, String description, double latitude, double longitude, Uri uri);

        void onUpdateSeenPost(String post_id, String user_id);
    }
}
