package com.aditp.mdvkarch.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aditp.mdvkarch.data.remote.Endpoint;
import com.aditp.mdvkarch.data.remote.RetrofitClient;
import com.aditp.mdvkarch.data.remote.api_response.ResponseArray;

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

    public GithubRepository() {
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
                // TODO better error handling in part #2 ...
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<ResponseArray> getProjectDetails(String userID, String projectName) {
        final MutableLiveData<ResponseArray> data = new MutableLiveData<>();

        endpoint.getProjectDetails(userID, projectName).enqueue(new Callback<ResponseArray>() {
            @Override
            public void onResponse(Call<ResponseArray> call, Response<ResponseArray> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseArray> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }


}
