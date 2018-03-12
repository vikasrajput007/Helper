package com.example.helper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.helper.utils.Constants.BASE_URL;

/**
 * Created by payal on 2/8/2018.
 */

public class Vendor_Home_API_Client {

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();


    private static final String base_url = "http://helper.org.in/new/apis/vendor/";
    public static Retrofit retrofit_user = null;
    private static Retrofit retrofit = null;
    static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }


    // client for user

    public static Retrofit getUserClient() {

        if (retrofit_user == null) {
            retrofit_user = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }

        return retrofit_user;

    }
}
