package com.shnsaraswati.berbagimmakanan.presenter;

import android.net.Uri;

import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import query.UseGetProfileByIDQuery;

public class ProfileContract {
    public interface ViewFragmentProfil {
        void onSetRatingBar(float rating);

        void onSetFailure(String message);
    }

    public interface ViewFragmentProfilSaya {
        void onUpdateProfil(String id, String name, String phonenumber, String address);

        void onSetFailure(String message);
    }

    public interface ViewFragmentGantiKataSandi {
        void onUpdateProfilePassword();

        void onFailure(String message);
    }

    public interface Callback {
        void onResponse(UseGetProfileByIDQuery.User user);

        void onFailure(@NotNull ApolloException e);
    }

    interface Presenter {
        void onGetProfile(String id, Callback callback);

        void onUpdateProfil(String id, String name, String phonenumber, String address, String img_profile, Uri uri);

        void onUpdateProfilePassword(String id, String oldpassword, String newpassword);
    }
}
