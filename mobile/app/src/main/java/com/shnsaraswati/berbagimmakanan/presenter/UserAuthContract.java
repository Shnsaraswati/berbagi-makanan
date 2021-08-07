package com.shnsaraswati.berbagimmakanan.presenter;

public class UserAuthContract {
    public interface ViewHalamanMasuk {
        void onSuccessLogin();
        void onFailure(String message);
    }

    public  interface ViewHalamanDaftar {
        void onSuccessRegister();
        void onFailure(String message);
    }

    interface Presenter {
        void onLogin(String phonenumber, String password);
        void onRegister(String phonenumber, String password, String name);
    }
}
