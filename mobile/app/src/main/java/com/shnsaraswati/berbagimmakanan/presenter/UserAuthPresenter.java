package com.shnsaraswati.berbagimmakanan.presenter;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.berbagimmakanan.config.Apollo;
import com.shnsaraswati.berbagimmakanan.util.RandomNumber;

import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import mutation.UserCreateUserMutation;
import query.UseGetUserByPhoneQuery;

public class UserAuthPresenter implements UserAuthContract.Presenter {
    UserAuthContract.ViewHalamanMasuk viewHalamanMasuk;
    UserAuthContract.ViewHalamanDaftar viewHalamanDaftar;
    Apollo apollo = new Apollo();
    ApolloClient apolloClient = apollo.ConnectApollo();
    RandomNumber randomNumber = new RandomNumber();

    public UserAuthPresenter(UserAuthContract.ViewHalamanMasuk view) {
        this.viewHalamanMasuk = view;
    }

    public UserAuthPresenter(UserAuthContract.ViewHalamanDaftar viewHalamanDaftar) {
        this.viewHalamanDaftar = viewHalamanDaftar;
    }

    @Override
    public void onLogin(String phonenumber, String password) {
        apolloClient.query(new UseGetUserByPhoneQuery(phonenumber)).enqueue(new ApolloCall.Callback<UseGetUserByPhoneQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseGetUserByPhoneQuery.Data> response) {
                if (response.getData().users().isEmpty()){
                    viewHalamanMasuk.onFailure("username atau password salah");
                } else {
                    if (BCrypt.checkpw(password, response.getData().users().get(0).password())){
                        viewHalamanMasuk.onSuccessLogin();
                    } else {
                        viewHalamanMasuk.onFailure("username atau password salah");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                viewHalamanMasuk.onFailure("terjadi kesalahan");
            }
        });
    }

    @Override
    public void onRegister(String phonenumber, String password, String name) {
        String otp = randomNumber.generateOTP();
        apolloClient.mutate(new UserCreateUserMutation(name, password, phonenumber, otp)).enqueue(new ApolloCall.Callback<UserCreateUserMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<UserCreateUserMutation.Data> response) {
                if (response.getData().insert_users().affected_rows() > 0) {
                    viewHalamanDaftar.onSuccessRegister();
                }
                else {
                    viewHalamanDaftar.onFailure("gagal daftar");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                viewHalamanDaftar.onFailure("terjadi kesalahan");
            }
        });
    }
}
