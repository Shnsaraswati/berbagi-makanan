package com.shnsaraswati.berbagimmakanan.presenter;

public class UserAuthContract {
    public interface View {
        void onSuccessLogin();
        void onFailureLogin(String message);
    }

    interface Presenter {
        void onLogin(String phonenumber, String password);
    }
}
