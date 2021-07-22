package com.shnsaraswati.applikasiberbagimakanan.presenter.profile;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.applikasiberbagimakanan.model.User;
import com.shnsaraswati.applikasiberbagimakanan.service.Apollo;

import org.jetbrains.annotations.NotNull;

import mutation.UpdateProfileMutation;
import query.GetProfileQuery;

public class ProfileRepository implements ProfileDataSource {

    public static final String TAG = "ProfileRepository";

    ApolloClient apolloClient = Apollo.ConnectApollo();

    @Override
    public void getProfile(User user, LoadDataCallback callback) {
        apolloClient.query(new GetProfileQuery(user.getId())).enqueue(new ApolloCall.Callback<GetProfileQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetProfileQuery.Data> response) {
                if (!response.getData().users().isEmpty()) {
                    callback.onDataLoaded(response.getData().users());
                } else {
                    Log.d(TAG, "onResponse-ProfileRepository: No Data Loaded");
                    callback.onNoDataLoaded();
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d(TAG, "onResponse-ProfileRepository: Error " + e.getMessage());
                callback.onError(e);
            }
        });
    }

    @Override
    public void updateProfile(User user, LoadDataCallbackUpdateProfile callback) {
        apolloClient.mutate(new UpdateProfileMutation(user.getAddress(), user.getBirth_date(), user.getId(), user.getName(), user.getPhone_number(), user.getImg_profile()))
                .enqueue(new ApolloCall.Callback<UpdateProfileMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<UpdateProfileMutation.Data> response) {
                        if (response.getData().update_users().affected_rows() > 0) {
                            callback.onDataLoaded(response.getData().update_users().affected_rows());
                        } else {
                            callback.onNoDataLoaded();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        callback.onError(e);
                    }
                });
    }
}
