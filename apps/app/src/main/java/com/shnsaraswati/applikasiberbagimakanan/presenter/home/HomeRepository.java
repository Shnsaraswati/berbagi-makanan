package com.shnsaraswati.applikasiberbagimakanan.presenter.home;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.applikasiberbagimakanan.service.Apollo;

import org.jetbrains.annotations.NotNull;

import query.GetAllPostsQuery;

public class HomeRepository implements HomeDataSource {
    public static final String TAG = "HomeRepository";

    ApolloClient apolloClient = Apollo.ConnectApollo();

    @Override
    public void getPosts(LoadDataCallback callback) {
        apolloClient.query(new GetAllPostsQuery()).enqueue(new ApolloCall.Callback<GetAllPostsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetAllPostsQuery.Data> response) {
                if (!response.getData().posts().isEmpty()){
                    callback.onDataLoaded(response.getData().posts());
                }
                else {
                    Log.d(TAG, "onResponse-ProfileRepository: No Data Loaded");
                    callback.onNoDataLoaded();
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, "onFailure: " + e.getMessage() );
                callback.onError(e);
            }
        });
    }
}
