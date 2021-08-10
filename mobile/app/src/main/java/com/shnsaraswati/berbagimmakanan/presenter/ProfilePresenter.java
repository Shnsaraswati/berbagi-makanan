package com.shnsaraswati.berbagimmakanan.presenter;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.berbagimmakanan.config.Apollo;

import org.jetbrains.annotations.NotNull;

import mutation.UseUpdateProfileMutation;

public class ProfilePresenter implements ProfileContract.Presenter{
    ProfileContract.ViewFragmentProfilSaya viewFragmentProfilSaya;
    Apollo apollo = new Apollo();
    ApolloClient apolloClient = apollo.ConnectApollo();

    public ProfilePresenter(ProfileContract.ViewFragmentProfilSaya viewFragmentProfilSaya) {
        this.viewFragmentProfilSaya = viewFragmentProfilSaya;
    }

    @Override
    public void onUpdateProfil(String id, String name, String phonenumber, String address) {
        apolloClient.mutate(new UseUpdateProfileMutation(id, name, phonenumber, address)).enqueue(new ApolloCall.Callback<UseUpdateProfileMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseUpdateProfileMutation.Data> response) {
                if (response.getData() != null) {
                    viewFragmentProfilSaya.onUpdateProfil(id,name,phonenumber,address);
                }
                else {
                    viewFragmentProfilSaya.onFailure("Gagal melakukan perubahan");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                viewFragmentProfilSaya.onFailure("Terjadi Kesalahan");
            }
        });
    }
}
