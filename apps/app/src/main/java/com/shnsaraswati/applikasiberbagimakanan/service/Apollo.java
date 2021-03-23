package com.shnsaraswati.applikasiberbagimakanan.service;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Apollo {
    static public ApolloClient ConnectApollo() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.header("x-hasura-admin-secret",  "shaniasaraswati");
                    return chain.proceed(builder.build());
                })
                .build();

        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl("https://berbagi-makanan.herokuapp.com/v1/graphql")
                .okHttpClient(okHttpClient)
                .build();

        return apolloClient;
    }
}
