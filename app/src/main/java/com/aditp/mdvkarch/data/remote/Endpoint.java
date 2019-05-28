package com.aditp.mdvkarch.data.remote;


import com.aditp.mdvkarch.data.remote.api_response.ResponseArray;
import com.aditp.mdvkarch.data.remote.api_response.ResponseObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Endpoint {

    @GET("/users/{user}")
    Call<ResponseObject> getUsers(@Path("user") String user);

    @GET("users/{user}/repos")
    Call<List<ResponseArray>> getProjectList(@Path("user") String user);


}
