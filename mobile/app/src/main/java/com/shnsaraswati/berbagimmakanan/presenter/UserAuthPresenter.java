package com.shnsaraswati.berbagimmakanan.presenter;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.berbagimmakanan.config.Apollo;

import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import query.UseGetUserByPhoneQuery;

public class UserAuthPresenter implements UserAuthContract.Presenter {
    UserAuthContract.View view;
    Apollo apollo = new Apollo();

    public UserAuthPresenter(UserAuthContract.View view) {
        this.view = view;
    }

    @Override
    public void onLogin(String phonenumber, String password) {
        ApolloClient apolloClient = apollo.ConnectApollo();

        apolloClient.query(new UseGetUserByPhoneQuery(phonenumber)).enqueue(new ApolloCall.Callback<UseGetUserByPhoneQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseGetUserByPhoneQuery.Data> response) {
                if (response.getData().users().isEmpty()){
                    view.onFailureLogin("username atau password salah");
                } else {
                    if (BCrypt.checkpw(password, response.getData().users().get(0).password())){
                        view.onSuccessLogin();
                    } else {
                        view.onFailureLogin("username atau password salah");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                view.onFailureLogin("terjadi kesalahan");
            }
        });
    }
}
