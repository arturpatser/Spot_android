package com.gridyn.potspot.service;

import com.gridyn.potspot.Constant;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by dmytro_vodnik on 8/25/16.
 * working on potspot project
 */
public class ServerApiUtil {

    private static SpotService instanceSpot;
    private static UserService instanceUser;

    public static SpotService initSpot() {

        if (instanceSpot == null) {

            instanceSpot = getRetrofitClient().create(SpotService.class);
        }

        return instanceSpot;
    }

    public static UserService initUser() {

        if (instanceUser == null) {

            instanceUser = getRetrofitClient().create(UserService.class);
        }

        return instanceUser;
    }

    private static Retrofit getRetrofitClient() {

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .client(getOkClientForRetrofit())
                .build();
    }

    private static OkHttpClient getOkClientForRetrofit() {

        OkHttpClient client = new OkHttpClient();

        HttpLoggingInterceptor httpLog = new HttpLoggingInterceptor();

        httpLog.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.interceptors().add(httpLog);

        return client;
    }
}
