package com.aditp.mdvkarch.data.remote;


import com.aditp.mdvkarch.core.CONSTANT;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aditp.mdvkarch.helper.MDVK.MY_UNSAFE_OKHTTP_CLIENT;


public class RetrofitClient {
    private static Retrofit retrofit;

    // ------------------------------------------------------------------------
    // SINGLETON PATTERN + synchronized
    // ------------------------------------------------------------------------
    public static Retrofit getClient() {
        synchronized (RetrofitClient.class) {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(CONSTANT.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(MY_UNSAFE_OKHTTP_CLIENT())
                        .build();
            }
        }
        return retrofit;
    }

    public static Retrofit getClient(String url) {
        synchronized (RetrofitClient.class) {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }


}
