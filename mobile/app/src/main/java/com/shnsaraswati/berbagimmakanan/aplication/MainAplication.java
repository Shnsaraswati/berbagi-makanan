package com.shnsaraswati.berbagimmakanan.aplication;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.cloudinary.android.LogLevel;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.download.glide.GlideDownloadRequestBuilderFactory;
import com.cloudinary.android.policy.GlobalUploadPolicy;
import com.cloudinary.android.policy.UploadPolicy;

public class MainAplication extends Application {
    static MainAplication _instance;
    private Handler mainThreadHandler;

    public static MainAplication get() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainThreadHandler = new Handler(Looper.getMainLooper());
        // This can be called any time regardless of initialization.
        MediaManager.setLogLevel(LogLevel.DEBUG);

        // Mandatory - call a flavor of init. Config can be null if cloudinary_url is provided in the manifest.
        MediaManager.init(this);
        MediaManager.get().setDownloadRequestBuilderFactory(new GlideDownloadRequestBuilderFactory());

        MediaManager.get().setGlobalUploadPolicy(
                new GlobalUploadPolicy.Builder()
                        .maxConcurrentRequests(4)
                        .networkPolicy(UploadPolicy.NetworkType.ANY)
                        .build());

        _instance = this;
    }
    public void runOnMainThread(Runnable runnable) {
        mainThreadHandler.post(runnable);
    }
}
