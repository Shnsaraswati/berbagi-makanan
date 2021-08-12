package com.shnsaraswati.berbagimmakanan.presenter;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.berbagimmakanan.config.Apollo;

import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import mutation.UseUpdateProfileMutation;
import mutation.UseUpdateProfilePasswordMutation;
import query.UseGetProfileByIDQuery;

public class ProfilePresenter implements ProfileContract.Presenter{
    ProfileContract.ViewFragmentProfilSaya viewFragmentProfilSaya;
    ProfileContract.ViewFragmentGantiKataSandi viewFragmentGantiKataSandi;
    Apollo apollo = new Apollo();
    ApolloClient apolloClient = apollo.ConnectApollo();

    public ProfilePresenter(ProfileContract.ViewFragmentProfilSaya viewFragmentProfilSaya) {
        this.viewFragmentProfilSaya = viewFragmentProfilSaya;
    }

    public ProfilePresenter(ProfileContract.ViewFragmentGantiKataSandi viewFragmentGantiKataSandi) {
        this.viewFragmentGantiKataSandi = viewFragmentGantiKataSandi;
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

    @Override
    public void onUpdateProfilePassword(String id, String oldpassword, String newpassword) {
        apolloClient.query(new UseGetProfileByIDQuery(id)).enqueue(new ApolloCall.Callback<UseGetProfileByIDQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseGetProfileByIDQuery.Data> response) {
                if (!response.getData().users().isEmpty()) {
                    if (BCrypt.checkpw(oldpassword, response.getData().users().get(0).password())) {
                        String hashPassword = BCrypt.hashpw(newpassword, BCrypt.gensalt(12));
                        apolloClient.mutate(new UseUpdateProfilePasswordMutation(id, hashPassword)).enqueue(new ApolloCall.Callback<UseUpdateProfilePasswordMutation.Data>() {
                            @Override
                            public void onResponse(@NotNull Response<UseUpdateProfilePasswordMutation.Data> response) {
                                if (response.getData() != null ) {
                                    viewFragmentGantiKataSandi.onUpdateProfilePassword();
                                } else {
                                    viewFragmentGantiKataSandi.onFailure("gagal melakukan perubahan password");
                                }
                            }

                            @Override
                            public void onFailure(@NotNull ApolloException e) {
                                viewFragmentGantiKataSandi.onFailure("terjadi kesalahan");
                            }
                        });
                    } else {
                        viewFragmentGantiKataSandi.onFailure("password lama anda salah");
                    }
                } else {
                    viewFragmentGantiKataSandi.onFailure("password lama anda salah");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                viewFragmentGantiKataSandi.onFailure("terjadi kesalahan");
            }
        });
    }
}
