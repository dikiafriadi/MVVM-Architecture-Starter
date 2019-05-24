package com.aditp.mdvkarch.ui.main;

import android.content.Context;

import com.aditp.mdvkarch.data.retrofit.GithubService;
import com.aditp.mdvkarch.data.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainBL {
    private Context context;
    private MainModel model;
    private OnSuccess onSuccess;

    // Trigger Technique
    public void setOnSuccess(final OnSuccess onSuccess) {
        this.onSuccess = onSuccess;
    }

    public MainBL(Context context, MainModel model) {
        this.context = context;
        this.model = model;
    }


    public void getUsers(String username) {
        GithubService githubService = RetrofitClient.getClient().create(GithubService.class);
        Call<MainModel> call = githubService.getUser(username);
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                model.setName(response.body().getName());
                model.setBio(response.body().getBio());
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

            }
        });
    }


    // ------------------------------------------------------------------------
    // IFACE
    // ------------------------------------------------------------------------
    public interface OnSuccess {
        void onSuccess();
    }
}
