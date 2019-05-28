package com.aditp.mdvkarch.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aditp.mdvkarch.data.remote.Endpoint;
import com.aditp.mdvkarch.data.remote.RetrofitClient;
import com.aditp.mdvkarch.data.response.ResponseProjectList;
import com.aditp.mdvkarch.data.response.ResponseProfileUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// ------------------------------------------------------------------------
// SINGLETON
// ------------------------------------------------------------------------
public class GithubRepository {
    private Endpoint endpoint;

    // @Singleton
    private static GithubRepository githubRepository;

    private GithubRepository() {
        endpoint = RetrofitClient.getClient().create(Endpoint.class);
    }

    public synchronized static GithubRepository getInstance() {
        if (githubRepository == null) {
            githubRepository = new GithubRepository();
        }
        return githubRepository;
    }


    public LiveData<List<ResponseProjectList>> getProjectList(String userId) {
        final MutableLiveData<List<ResponseProjectList>> data = new MutableLiveData<>();

        endpoint.getProjectList(userId).enqueue(new Callback<List<ResponseProjectList>>() {
            @Override
            public void onResponse(Call<List<ResponseProjectList>> call, Response<List<ResponseProjectList>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ResponseProjectList>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<ResponseProfileUser> getUserProfile(String userID) {
        final MutableLiveData<ResponseProfileUser> data = new MutableLiveData<>();

        endpoint.getUsers(userID).enqueue(new Callback<ResponseProfileUser>() {
            @Override
            public void onResponse(Call<ResponseProfileUser> call, Response<ResponseProfileUser> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseProfileUser> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }


}
