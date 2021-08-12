package com.shnsaraswati.berbagimmakanan.presenter;

import query.UseGetUserByPhoneQuery;

public class ProfileContract {
    public interface ViewFragmentProfilSaya {
        void onUpdateProfil(String id, String name, String phonenumber, String address);
        void onFailure(String message);
    }

    public interface ViewFragmentGantiKataSandi {
        void onUpdateProfilePassword();
        void onFailure(String message);
    }

    interface Presenter {
        void onUpdateProfil(String id, String name, String phonenumber, String address);
        void onUpdateProfilePassword(String id, String oldpassword, String newpassword);
    }
}
