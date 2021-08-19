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

    public interface Callback {
        void onResponse(List<UseGetAllPostsQuery.Post> posts);
        void onFailure(@NotNull ApolloException e);
    }

    interface Presenter {
        void onGetAllPosts(Callback callback);
        void onNewAddPost(String namefood, String address, String user_id, float latitude, float longitude, Uri uri);
    }
}
