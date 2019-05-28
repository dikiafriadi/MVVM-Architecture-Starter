package com.aditp.mdvkarch.data.remote;


import com.aditp.mdvkarch.data.response.ResponseProjectList;
import com.aditp.mdvkarch.data.response.ResponseProfileUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Endpoint {

    @GET("/users/{user}")
    Call<ResponseProfileUser> getUsers(@Path("user") String user);

    @GET("users/{user}/repos")
    Call<List<ResponseProjectList>> getProjectList(@Path("user") String user);


}
