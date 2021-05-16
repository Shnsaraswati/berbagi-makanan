package com.shnsaraswati.applikasiberbagimakanan.presenter.otentikasi;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.applikasiberbagimakanan.model.User;
import com.shnsaraswati.applikasiberbagimakanan.service.Apollo;
import org.mindrot.jbcrypt.BCrypt;

import org.jetbrains.annotations.NotNull;

import mutation.RegisterUserMutation;
import query.GetUserQuery;

public class OtentikasiRepository implements OtentikasiDataSource {
    private static final String TAG = "OtentikasiRepository";
    
    ApolloClient apolloClient = Apollo.ConnectApollo();

    @Override
    public void login(User user, LoadDataCallback callback) {
        
//        apolloClient.query(new GetUserQuery(user.getPhone_number()))
//                .enqueue(new ApolloCall.Callback<GetUserQuery.Data>() {
//                    @Override
//                    public void onResponse(@NotNull Response<GetUserQuery.Data> response) {
//                        if (!response.getData().users().isEmpty()) {
//                            if (BCrypt.checkpw(user.getPassword(), response.getData().users().get(0).password())) {
//                                callback.onDataLoaded(response.getData().users());
//                            } else {
//                                callback.onNoDataLoaded();
//                            }
//                        } else {
//                            callback.onNoDataLoaded();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull ApolloException e) {
//                       callback.onError(e);
//                    }
//                });


        Log.d(TAG, "No Hp: " + user.getPhone_number());

        apolloClient.query(new GetUserQuery(user.getPhone_number())).enqueue(new ApolloCall.Callback<GetUserQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetUserQuery.Data> response) {
                if (!response.getData().users().isEmpty()){
                    if (BCrypt.checkpw(user.getPassword(), response.getData().users().get(0).password())){
                        callback.onDataLoaded(response.getData().users());
                    }
                    else {
                        callback.onNoDataLoaded();
                    }
                }
                else {
                    callback.onNoDataLoaded();
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d(TAG, "Error");
                callback.onError(e);
            }
        });

    }

    @Override
    public void register(User user, LoadDataCallbackRegister callback) {
        String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));

        apolloClient.mutate(new RegisterUserMutation(user.getAddress(),user.getBirth_date(),user.getName(),password,user.getPhone_number()))
                .enqueue(new ApolloCall.Callback<RegisterUserMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<RegisterUserMutation.Data> response) {

                        if (response.getData().insert_users().affected_rows() > 0) {
                            callback.onDataLoaded(response.getData().insert_users().affected_rows());
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
