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

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import mutation.UseAddNewPostMutation;
import mutation.UseInactivePostMutation;
import mutation.UseUpdateSeenPostMutation;
import query.UseGetAllPostsByUserQuery;
import query.UseGetAllPostsQuery;
import query.UseGetPostByIdQuery;

public class PostPresenter implements PostContract.Presenter {
    public static final String TAG = "PostPresenter";

    PostContract.ViewFragmentMenu viewFragmentMenu;
    PostContract.ViewFragmentBerbagi viewFragmentBerbagi;
    PostContract.ViewFragmentMenuDipilih viewFragmentMenuDipilih;
    PostContract.MenuRecyclerView menuRecyclerView;
    PostContract.ViewFragmentStatusMenu viewFragmentStatusMenu;
    PostContract.StatusMakananRecyclerView statusMakananRecyclerView;
    Apollo apollo = new Apollo();
    ApolloClient apolloClient = apollo.ConnectApollo();

    public PostPresenter(PostContract.ViewFragmentMenu viewFragmentMenu) {
        this.viewFragmentMenu = viewFragmentMenu;
    }

    public PostPresenter(PostContract.ViewFragmentBerbagi viewFragmentBerbagi) {
        this.viewFragmentBerbagi = viewFragmentBerbagi;
    }

    public PostPresenter(PostContract.MenuRecyclerView menuRecyclerView) {
        this.menuRecyclerView = menuRecyclerView;
    }

    public PostPresenter(PostContract.ViewFragmentMenuDipilih viewFragmentMenuDipilih) {
        this.viewFragmentMenuDipilih = viewFragmentMenuDipilih;
    }

    public PostPresenter(PostContract.ViewFragmentStatusMenu viewFragmentStatusMenu) {
        this.viewFragmentStatusMenu = viewFragmentStatusMenu;
    }

    public PostPresenter(PostContract.StatusMakananRecyclerView statusMakananRecyclerView) {
        this.statusMakananRecyclerView = statusMakananRecyclerView;
    }

    @Override
    public void onGetAllPosts(PostContract.Callback callback) {
        apolloClient.query(new UseGetAllPostsQuery()).enqueue(new ApolloCall.Callback<UseGetAllPostsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseGetAllPostsQuery.Data> response) {
                if (!response.getData().posts().isEmpty()) {
                    List<UseGetAllPostsQuery.Post> posts = response.getData().posts();
                    callback.onResponse(posts);
                } else {
                    viewFragmentMenu.onSetFailure("tidak ada data posts");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                callback.onFailure(e);
            }
        });
    }

    @Override
    public void onGetAllPostsByUser(String user_id, PostContract.Callback callback) {
        apolloClient.query(new UseGetAllPostsByUserQuery(user_id)).enqueue(new ApolloCall.Callback<UseGetAllPostsByUserQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseGetAllPostsByUserQuery.Data> response) {
                if (!response.getData().posts().isEmpty()) {
                    List<UseGetAllPostsByUserQuery.Post> posts = response.getData().posts();
                    callback.onResponse(posts);
                } else {
                    viewFragmentStatusMenu.onSetFailure("tidak ada data posts");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                callback.onFailure(e);
            }
        });
    }

    @Override
    public void onGetPost(String id, PostContract.Callback callback) {
        apolloClient.query(new UseGetPostByIdQuery(id)).enqueue(new ApolloCall.Callback<UseGetPostByIdQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseGetPostByIdQuery.Data> response) {
                if (!response.getData().posts().isEmpty()) {
                    List<UseGetPostByIdQuery.Post> posts = response.getData().posts();
                    callback.onResponse(posts);
                } else {
                    viewFragmentMenuDipilih.onSetFailure("terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                viewFragmentMenuDipilih.onSetFailure("terjadi kesalahan");
            }
        });
    }

    @Override
    public void onNewAddPost(String namefood, String address, String user_id, String description, double latitude, double longitude, Uri uri) {
        String uniqueTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
        String img_public_id = namefood + "_" + uniqueTime;
        String imgFood = namefood + "_" + uniqueTime + ".jpg";

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
                        apolloClient.mutate(new UseAddNewPostMutation(namefood, imgFood, address, description, user_id, latitude, longitude)).enqueue(new ApolloCall.Callback<UseAddNewPostMutation.Data>() {
                            @Override
                            public void onResponse(@NotNull Response<UseAddNewPostMutation.Data> response) {
                                if (response.getData() != null) {
                                    if (response.getData().insert_posts().affected_rows() > 0) {
                                        viewFragmentBerbagi.onSuccessAddPost("berhasil memposting makanan untuk di bagikan");
                                    } else {
                                        viewFragmentBerbagi.onFailure("gagal menambah makanan baru");
                                    }
                                } else {
                                    Log.d(TAG, "onResponse: " + response.errors().get(0).getMessage());
                                    viewFragmentBerbagi.onFailure("gagal menambah makanan baru");
                                }
                            }

                            @Override
                            public void onFailure(@NotNull ApolloException e) {
                                viewFragmentBerbagi.onFailure("terjadi kesalahan");
                            }
                        });
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Log.d(TAG, "onError: " + error.toString());
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        Log.d(TAG, "onReschedule: ");
                    }
                }).dispatch();
    }

    @Override
    public void onUpdateSeenPost(String post_id, String user_id) {
        apolloClient.mutate(new UseUpdateSeenPostMutation(post_id)).enqueue(new ApolloCall.Callback<UseUpdateSeenPostMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseUpdateSeenPostMutation.Data> response) {
                if (response.getData() != null) {
                    if (response.getData().update_posts().affected_rows() > 0) {
                        menuRecyclerView.onSuccessUpdateSeenPost(post_id, user_id);
                    } else {
                        menuRecyclerView.onFailure("Terjadi Kesalahan");
                    }
                } else {
                    menuRecyclerView.onFailure("Terjadi Kesalahan");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                menuRecyclerView.onFailure("Terjadi Kesalahan");
            }
        });
    }

    public void onInactivePost(String post_id) {
        apolloClient.mutate(new UseInactivePostMutation(post_id)).enqueue(new ApolloCall.Callback<UseInactivePostMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<UseInactivePostMutation.Data> response) {
                if (response.getData() != null) {
                    if (response.getData().update_posts().affected_rows() > 0) {
                       viewFragmentStatusMenu.onSetSuccess("Makanan berhasil di hapus");
                    } else {
                        viewFragmentStatusMenu.onSetFailure("gagal menghapus makanan");
                    }
                } else {
                    viewFragmentStatusMenu.onSetFailure("Terjadi Kesalahan graphql");
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                viewFragmentStatusMenu.onSetFailure("Terjadi kesalahan");
            }
        });
    }
}
