package com.aditp.mdvkarch.data.retrofit;


import com.aditp.mdvkarch.core.CONSTANT;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// ------------------------------------------------------------------------
// SINGLETON PATTERN
// ------------------------------------------------------------------------
public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(CONSTANT.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient(String url) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
