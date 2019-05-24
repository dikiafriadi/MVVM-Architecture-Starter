package com.aditp.mdvkarch.data.retrofit;


import com.aditp.mdvkarch.ui.main.MainModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {
    @GET("users/{username}")
    Call<MainModel> getUser(@Path("username") String username);

}
