package com.shnsaraswati.berbagimmakanan.view;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.staticmap.v1.MapboxStaticMap;
import com.mapbox.api.staticmap.v1.StaticMapCriteria;
import com.mapbox.api.staticmap.v1.models.StaticMarkerAnnotation;
import com.mapbox.geojson.Point;
import com.shnsaraswati.berbagimmakanan.R;
import com.shnsaraswati.berbagimmakanan.config.SharedPreference;
import com.shnsaraswati.berbagimmakanan.presenter.PostContract;
import com.shnsaraswati.berbagimmakanan.presenter.PostPresenter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBerbagi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBerbagi extends Fragment implements PostContract.ViewFragmentBerbagi, LocationEngineListener {
    public static final String TAG = "FragmentBerbagi";

    TextView txtlokasi;
    Button btnunggah;
    EditText inputnamamakanan, inputdeskripsinamamakanan;
    ImageView imguploadmakanan, imgmaplokasi;


    PostPresenter postPresenter;
    SharedPreference sharedPreference;

    Uri uri;

    LocationEngine locationEngine;
    public Location originLocation;
    List<StaticMarkerAnnotation> markerStatic;
    StaticMarkerAnnotation staticMarkerAnnotation;
    Geocoder geocoder;
    List<Address> addresses;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentBerbagi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBerbagi.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBerbagi newInstance(String param1, String param2) {
        FragmentBerbagi fragment = new FragmentBerbagi();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPreference = new SharedPreference(requireContext());
        postPresenter = new PostPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_berbagi, container, false);
        initializeLocationEngine();
        txtlokasi = view.findViewById(R.id.txtlokasi);
        btnunggah = view.findViewById(R.id.btnunggah);
        inputnamamakanan = view.findViewById(R.id.inputnamamakanan);
        inputdeskripsinamamakanan = view.findViewById(R.id.inputdeskripsinamamakanan);
        imguploadmakanan = view.findViewById(R.id.imguploadmakanan);
        imgmaplokasi = view.findViewById(R.id.imgmaplokasi);

        String curUserID = sharedPreference.getProfileID();

        staticMarkerAnnotation = StaticMarkerAnnotation.builder()
                .lnglat(Point.fromLngLat(originLocation.getLongitude(), originLocation.getLatitude()))
                .name(StaticMapCriteria.MEDIUM_PIN)
                .build();

        markerStatic = new ArrayList<>();
        markerStatic.add(staticMarkerAnnotation);


        MapboxStaticMap staticMap = MapboxStaticMap.builder()
                .accessToken(getString(R.string.access_token))
                .styleId(StaticMapCriteria.STREET_STYLE)
                .cameraPoint(Point.fromLngLat(originLocation.getLongitude(), originLocation.getLatitude()))
                .staticMarkerAnnotations(markerStatic)
                .cameraZoom(13)
                .width(180)
                .height(180)
                .retina(true)
                .build();

        geocoder = new Geocoder(requireContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(originLocation.getLatitude(), originLocation.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
            onFailure("terjadi kesalahan");
        }


        String imgURL = staticMap.url().toString();
        Picasso.get().load(imgURL).error(R.drawable.ic_map).into(imgmaplokasi);

        imguploadmakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(requireActivity()).crop().start();
            }
        });

        btnunggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namefood = inputnamamakanan.getText().toString();
                String address = addresses.get(0).getAddressLine(0);
                double latitude = originLocation.getLatitude();
                double longitude = originLocation.getLongitude();

                postPresenter.onNewAddPost(namefood, address, curUserID, latitude, longitude, uri);
            }
        });

        txtlokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new FragmentMaps(), "Berhasil");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.getData();
                File file = new File(uri.getPath());
                long fileSizeInKB = file.length() / 1024;
                if (fileSizeInKB > 3072) {
                    Log.d(TAG, "onActivityResult: " + file.length());
                    onFailure("gagal, maksimal ukuran gambar kurang dari 3MB!");
                    uri = null;
                } else {
                    imguploadmakanan.setImageURI(uri);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessAddPost(String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onFailure(String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationEngine() {
        locationEngine = new LocationEngineProvider(getContext()).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            Log.d(TAG, "initializeLocationEngine: last location tidak null");
            originLocation = lastLocation;
        } else {
            Log.d(TAG, "initializeLocationEngine: last location null");
            locationEngine.addLocationEngineListener(this);
        }
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            originLocation = location;
        }
    }
}