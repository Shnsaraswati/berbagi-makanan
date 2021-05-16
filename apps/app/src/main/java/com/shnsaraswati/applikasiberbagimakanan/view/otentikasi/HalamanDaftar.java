package com.shnsaraswati.applikasiberbagimakanan.view.otentikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shnsaraswati.applikasiberbagimakanan.R;
import com.shnsaraswati.applikasiberbagimakanan.model.User;
import com.shnsaraswati.applikasiberbagimakanan.presenter.otentikasi.OtentikasiDataSource;
import com.shnsaraswati.applikasiberbagimakanan.presenter.otentikasi.OtentikasiRepository;
import com.shnsaraswati.applikasiberbagimakanan.view.MainActivity;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HalamanDaftar extends AppCompatActivity {
    private static final String TAG = "HalamanDaftar";

    public static int verification_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar);

        EditText DaftarnNoHP = findViewById(R.id.DaftarnNoHP);
        EditText DaftarNama = findViewById(R.id.DaftarNama);
        EditText DaftarTanggalLahir = findViewById(R.id.DaftarTanggalLahir);
        EditText DaftarAlamat = findViewById(R.id.DaftarAlamat);
        EditText DaftarPassword = findViewById(R.id.DaftarPassword);
        Button btndaftar = findViewById(R.id.btndaftar);

        String URL = "https://api.twilio.com/2010-04-01/Accounts/" + getResources().getString(R.string.ACCOUNT_SID) + "/Messages.json";
        String base64EncodedCredentials = "Basic " + Base64.encodeToString((getResources().getString(R.string.ACCOUNT_SID) + ":" +  getResources().getString(R.string.AUTH_TOKEN)).getBytes(), Base64.NO_WRAP);

        OtentikasiDataSource otentikasiDataSource = new OtentikasiRepository();

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hp= "+62" + DaftarnNoHP.getText().toString().substring(1);
                User user = new User(DaftarNama.getText().toString(), hp,
                        DaftarAlamat.getText().toString(), DaftarTanggalLahir.getText().toString(),
                        DaftarPassword.getText().toString());

                otentikasiDataSource.register(user, new OtentikasiDataSource.LoadDataCallbackRegister() {
                    @Override
                    public void onDataLoaded(int affected_rows) {
                        verification_code = ThreadLocalRandom.current().nextInt(1000, 9998 + 1);

                        String code = String.valueOf(verification_code);
                        Twilio.init(getResources().getString(R.string.ACCOUNT_SID), getResources().getString(R.string.AUTH_TOKEN));
                        if (affected_rows > 0 ){
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost httpPostm = new HttpPost(URL);
                            httpPostm.setHeader("Authorization", base64EncodedCredentials );
                            try {
                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                                nameValuePairs.add(new BasicNameValuePair("MessagingServiceSid",
                                        "MG7b686191d0c16bc1a454fc07f736e609"));
                                nameValuePairs.add(new BasicNameValuePair("To",
                                        hp));
                                nameValuePairs.add(new BasicNameValuePair("Body",
                                        "KODE VERIFIKASI ANDA " + verification_code));

                                httpPostm.setEntity(new UrlEncodedFormEntity(
                                        nameValuePairs));

                                // Execute HTTP Post Request
                                HttpResponse response = httpClient.execute(httpPostm);
                                HttpEntity entity = response.getEntity();
                                System.out.println("Entity post is: "
                                        + EntityUtils.toString(entity));
                                Intent intentmain = new Intent(getApplicationContext(), HalamanVerifikasi.class);
                                startActivity(intentmain);
                            } catch (ClientProtocolException e){

                            }  catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onNoDataLoaded() {
                        Log.d(TAG, "onNoDataLoaded: Gagal" );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+ e.getMessage());
                    }
                });
            }
        });
    }
}