package com.shnsaraswati.berbagimmakanan.presenter;

import android.util.Base64;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.berbagimmakanan.config.Apollo;
import com.shnsaraswati.berbagimmakanan.config.TwillioAPI;
import com.shnsaraswati.berbagimmakanan.util.RandomNumber;
import com.twilio.Twilio;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import mutation.UserCreateUserMutation;
import query.UseGetUserByPhoneQuery;

public class UserAuthPresenter implements UserAuthContract.Presenter {
    UserAuthContract.ViewHalamanMasuk viewHalamanMasuk;
    UserAuthContract.ViewHalamanDaftar viewHalamanDaftar;
    Apollo apollo = new Apollo();
    ApolloClient apolloClient = apollo.ConnectApollo();
    RandomNumber randomNumber = new RandomNumber();
    TwillioAPI twillioAPI = new TwillioAPI();

    private final static String ACCOUNT_SID = "ACf93c1b6d45840374aef32e6d689f8502";
    private final static String AUTH_TOKEN = "eca44db12bd9b4f1448792d2b49cb71e";

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
                if (response.getData().users().isEmpty()) {
                    viewHalamanMasuk.onFailure("username atau password salah");
                } else {
                    if (BCrypt.checkpw(password, response.getData().users().get(0).password())) {
                        UseGetUserByPhoneQuery.User user = response.getData().users().get(0);
                        viewHalamanMasuk.onSuccessLogin(user);
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
               if (response.getData() != null) {
                   if (response.getData().insert_users().affected_rows() > 0) {
                       String id = response.getData().insert_users().returning().get(0).id().toString();
                       String name = response.getData().insert_users().returning().get(0).name();
                       String phone_number = response.getData().insert_users().returning().get(0).phone_number();
                       String address = response.getData().insert_users().returning().get(0).address();

                       twillioAPI.sendSMSVerification(phonenumber, otp);
                       viewHalamanDaftar.onSuccessRegister(otp, id, name, phone_number, address);
                   } else {
                       viewHalamanDaftar.onFailure("gagal daftar");
                   }
               } else {
                   viewHalamanDaftar.onFailure("kesalahan mendaftar, ada duplikasi data");
               }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                viewHalamanDaftar.onFailure("terjadi kesalahan");
            }
        });
    }
}
