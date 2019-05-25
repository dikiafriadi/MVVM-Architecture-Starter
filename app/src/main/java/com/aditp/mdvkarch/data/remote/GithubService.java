package com.aditp.mdvkarch.data.remote;


import com.aditp.mdvkarch.data.remote.api_response.ResponseArray;
import com.aditp.mdvkarch.data.remote.api_response.ResponseObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {
    @GET("users/{username}")
    Call<ResponseObject> getUser(@Path("username") String username);

    @GET("users/{user}/repos")
    Call<List<ResponseArray>> listRepos(@Path("user") String user);

}
