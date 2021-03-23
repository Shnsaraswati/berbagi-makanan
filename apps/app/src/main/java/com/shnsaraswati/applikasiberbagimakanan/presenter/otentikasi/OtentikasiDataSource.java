package com.shnsaraswati.applikasiberbagimakanan.presenter.otentikasi;

import com.shnsaraswati.applikasiberbagimakanan.model.User;

import java.util.List;

import query.GetUserQuery;

public interface OtentikasiDataSource {
    void login(User user, LoadDataCallback callback);
    void register(User user, LoadDataCallbackRegister callback);

    interface LoadDataCallback {
        void onDataLoaded(List<GetUserQuery.User> users);

        void onNoDataLoaded();

        void onError(Throwable e);
    }

    interface LoadDataCallbackRegister {

        void onDataLoaded(int affected_rows);

        void onNoDataLoaded();

        void onError(Throwable e);
    }
}

