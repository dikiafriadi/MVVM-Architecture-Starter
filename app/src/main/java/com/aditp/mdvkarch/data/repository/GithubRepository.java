package com.aditp.mdvkarch.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aditp.mdvkarch.data.remote.Endpoint;
import com.aditp.mdvkarch.data.remote.RetrofitClient;
import com.aditp.mdvkarch.data.remote.api_response.ResponseArray;
import com.aditp.mdvkarch.data.remote.api_response.ResponseObject;

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


    public LiveData<List<ResponseArray>> getProjectList(String userId) {
        final MutableLiveData<List<ResponseArray>> data = new MutableLiveData<>();

        endpoint.getProjectList(userId).enqueue(new Callback<List<ResponseArray>>() {
            @Override
            public void onResponse(Call<List<ResponseArray>> call, Response<List<ResponseArray>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ResponseArray>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<ResponseObject> getUserProfile(String userID) {
        final MutableLiveData<ResponseObject> data = new MutableLiveData<>();

        endpoint.getUsers(userID).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }


}
