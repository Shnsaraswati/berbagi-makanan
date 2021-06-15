package com.shnsaraswati.applikasiberbagimakanan.presenter.profile;

import com.shnsaraswati.applikasiberbagimakanan.model.User;
import com.shnsaraswati.applikasiberbagimakanan.presenter.otentikasi.OtentikasiDataSource;

import java.util.List;

import query.GetProfileQuery;
import query.GetUserQuery;

public interface ProfileDataSource {
    void getProfile(User user, LoadDataCallback callback);
    void updateProfile(User user, LoadDataCallbackUpdateProfile callback);

    interface LoadDataCallback {
        void onDataLoaded(List<GetProfileQuery.User> profiles);

        void onNoDataLoaded();

        void onError(Throwable e);
    }

    interface LoadDataCallbackUpdateProfile {

        void onDataLoaded(int affected_rows);

        void onNoDataLoaded();

        void onError(Throwable e);
    }
}
