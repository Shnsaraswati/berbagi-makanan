package com.shnsaraswati.berbagimmakanan.config;

import android.util.Base64;
import android.util.Log;

import com.twilio.Twilio;

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

public class TwillioAPI {
    private final static String TAG = "TwillioAPI";

    private final static String ACCOUNT_SID = "ACf93c1b6d45840374aef32e6d689f8502";
    private final static String AUTH_TOKEN = "9a6dbe8265aba7f4efd0e99a4e392123";

    public void sendSMSVerification(String phonenumber, String otp) {
        String URL = "https://api.twilio.com/2010-04-01/Accounts/" + ACCOUNT_SID + "/Messages.json";
        String base64EncodedCredentials = "Basic " + Base64.encodeToString((ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP);

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPostm = new HttpPost(URL);
        httpPostm.setHeader("Authorization", base64EncodedCredentials);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("MessagingServiceSid",
                    "MG7b686191d0c16bc1a454fc07f736e609"));
            nameValuePairs.add(new BasicNameValuePair("To",
                    phonenumber));
            nameValuePairs.add(new BasicNameValuePair("Body",
                    "KODE VERIFIKASI ANDA " + otp));

            httpPostm.setEntity(new UrlEncodedFormEntity(
                    nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse responsehttp = httpClient.execute(httpPostm);
            HttpEntity entity = responsehttp.getEntity();
            System.out.println("Entity post is: "
                    + EntityUtils.toString(entity));
            Log.d(TAG, "sendSMSVerification: berhasil");

        } catch (ClientProtocolException e) {
            Log.e(TAG, "sendSMSVerification: " + e.getMessage() );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, "sendSMSVerification: " + e.getMessage() );
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "sendSMSVerification: " + e.getMessage() );
        }
    }
}
