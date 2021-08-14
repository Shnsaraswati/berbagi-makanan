package com.shnsaraswati.berbagimmakanan.presenter;

import android.net.Uri;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.shnsaraswati.berbagimmakanan.config.Apollo;

import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Calendar;
import java.util.Map;

import mutation.UseUpdateProfileMutation;
import mutation.UseUpdateProfilePasswordMutation;
import query.UseGetProfileByIDQuery;

public class ProfilePresenter implements ProfileContract.Presenter {
    public static final String TAG = "ProfilePresenter";

    ProfileContract.ViewFragmentProfilSaya viewFragmentProfilSaya;
    ProfileContract.ViewFragmentGantiKataSandi viewFragmentGantiKataSandi;
    ProfileContract.ViewFragmentProfil viewFragmentProfil;
    Apollo apollo = new Apollo();
    ApolloClient apolloClient = apollo.ConnectApollo();

    public ProfilePresenter(ProfileContract.ViewFragmentProfilSaya viewFragmentProfilSaya) {
        this.viewFragmentProfilSaya = viewFragmentProfilSaya;
    }

    public ProfilePresenter(ProfileContract.ViewFragmentGantiKataSandi viewFragmentGantiKataSandi) {
        this.viewFragmentGantiKataSandi = viewFragmentGantiKataSandi;
    }

    public ProfilePresenter(ProfileContract.ViewFragmentProfil viewFragmentProfil) {
        this.viewFragmentProfil = viewFragmentProfil;
    }

    @Override
    public void onUpdateProfil(String id, String name, String phonenumber, String address, String img_profile, Uri uri) {
        if (uri != null) {
            Log.d(TAG, "onUpdateProfil: Kesini");
            String uniqueTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
            String img_public_id = name + "_" + uniqueTime;
            String imgProfile = name + "_" + uniqueTime + ".jpg";

            MediaManager.get().upload(uri)
                    .unsigned("berbagi_preset")
                    .option("public_id", img_public_id)
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            Log.d(TAG, "onStart: " + requestId);
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            Log.d(TAG, "onProgress: " + bytes + " " + totalBytes);
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            apolloClient.mutate(new UseUpdateProfileMutation(id, name, phonenumber, address, imgProfile)).enqueue(new ApolloCall.Callback<UseUpdateProfileMutation.Data>() {
                                @Override
                                public void onResponse(@NotNull Response<UseUpdateProfileMutation.Data> response) {
                                    if (response.getData() != null) {
                                        viewFragmentProfilSaya.onUpdateProfil(id, name, phonenumber, address);
                                    } else {
                                        viewFragmentProfilSaya.onSetFailure("Gagal melakukan perubahan");
                                    }
                                }

                                @Override
                                public void onFailure(@NotNull ApolloException e) {
                                    viewFragmentProfilSaya.onSetFailure("Terjadi Kesalahan");
                                }
                            });
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            Log.d(TAG, "onError: " + error.toString());
                            viewFragmentProfilSaya.onSetFailure("Terjadi Kesalahan saat upload foto");
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            Log.d(TAG, "onReschedule: ");
                        }
                    }).dispatch();
        } else {
            apolloClient.mutate(new UseUpdateProfileMutation(id, name, phonenumber, address, img_profile)).enqueue(new ApolloCall.Callback<UseUpdateProfileMutation.Data>() {
                @Override
                public void onResponse(@NotNull Response<UseUpdateProfileMutation.Data> response) {
                    if (response.getData() != null) {
                        viewFragmentProfilSaya.onUpdateProfil(id, name, phonenumber, address);
                    } else {
                        viewFragmentProfilSaya.onSetFailure("Gagal melakukan perubahan");
                    }
                }

                @Override
                public void onFailure(@NotNull ApolloException e) {
                    viewFragmentProfilSaya.onSetFailure("Terjadi Kesalahan");
                }
            });
        }
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
                                if (response.getData() != null) {
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

    @Override
    public void onGetProfile(String id, ProfileContract.Callback callback) {
        apolloClient.query(new UseGetProfileByIDQuery(id)).enqueue(new ApolloCall.Callback<UseGetProfileByIDQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseGetProfileByIDQuery.Data> response) {
                if (!response.getData().users().isEmpty()) {
//                    float rating = ((BigDecimal) response.getData().users().get(0).rating()).floatValue();
//                    viewFragmentProfil.onSetRatingBar(rating);
                    UseGetProfileByIDQuery.User user = response.getData().users().get(0);
                    callback.onResponse(user);
                } else {
//                    viewFragmentProfil.onFailure("terjadi kesalahan");
                    callback.onResponse(null);
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
//                viewFragmentProfil.onFailure("terjadi kesalahan");
                callback.onFailure(e);
            }
        });
    }
}
