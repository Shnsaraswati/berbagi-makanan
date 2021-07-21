package com.shnsaraswati.applikasiberbagimakanan.presenter.home;

import com.shnsaraswati.applikasiberbagimakanan.model.User;
import com.shnsaraswati.applikasiberbagimakanan.presenter.profile.ProfileDataSource;

import java.util.List;

import query.GetAllPostsQuery;
import query.GetProfileQuery;

public interface HomeDataSource {
    void getPosts(LoadDataCallback callback);

    interface LoadDataCallback {
        void onDataLoaded(List<GetAllPostsQuery.Post> posts);

        void onNoDataLoaded();

        void onError(Throwable e);
    }
}
