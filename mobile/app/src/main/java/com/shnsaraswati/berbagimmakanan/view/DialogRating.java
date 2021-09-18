package com.shnsaraswati.berbagimmakanan.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.presenter.NotificationContract;
import com.shnsaraswati.berbagimmakanan.presenter.NotificationPresenter;

import org.jetbrains.annotations.NotNull;

import mutation.UseUpdateRatingMutation;

public class DialogRating extends AppCompatDialogFragment implements NotificationContract.DialogRatingView {
    public static final String TAG = "DialogRating";

    RatingBar ratingBar;

    NotificationPresenter presenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.ratting, null);

        presenter = new NotificationPresenter(this);

        Bundle bundle = this.getArguments();
        String user_id = bundle.getString("user_id");
        String notification_id = bundle.getString("notification_id");
        float cur_rating = bundle.getFloat("rating");
        int cur_count_rating = bundle.getInt("count_rating");

        Log.d(TAG, "onCreateDialog: " + user_id);

        builder.setView(view)
                .setTitle("Berikan Rating")
                .setPositiveButton("Berikan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float givenRating = ratingBar.getRating();
                        float totalRating = (givenRating + cur_rating) / cur_count_rating;

                        Toast.makeText(getContext(), "Berhasil memberikan rating", Toast.LENGTH_SHORT).show();

                        presenter.onSendRating(user_id, totalRating, notification_id);
                    }
                });

        ratingBar = view.findViewById(R.id.ratingBar2);

        return builder.create();
    }

    @Override
    public void onSuccessRating() {

    }

    @Override
    public void onFailure(String message) {
        ((MainActivity) getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
