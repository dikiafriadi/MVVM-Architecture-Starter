package com.aditp.mdvkarch.data.remote;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aditp.mdvkarch.data.remote.api_response.ResponseProfileUser;
import com.aditp.mdvkarch.data.remote.api_response.ResponseProjectList;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


// ------------------------------------------------------------------------
// SINGLETON
// ------------------------------------------------------------------------
public class RepositoryGithub {
    // BASE_URL
    private static final String KEY_BASE_URL = "https://api.github.com/";
    private static RepositoryGithub repositoryGithub;

    // @Singleton
    public synchronized static RepositoryGithub getInstance() {
        if (repositoryGithub == null) {
            repositoryGithub = new RepositoryGithub();
        }
        return repositoryGithub;
    }


    // ------------------------------------------------------------------------
    // GET_USER_PROFILE
    // ------------------------------------------------------------------------
    public LiveData<ResponseProfileUser> refreshUserProfile(Context context, String username) {
        final MutableLiveData<ResponseProfileUser> data = new MutableLiveData<>();
        AndroidNetworking.get(KEY_BASE_URL.concat("users/{username}"))
                .addPathParameter("username", username)
                .setPriority(Priority.LOW)
                .build()
                .getAsParsed(new TypeToken<ResponseProfileUser>() {
                }, new ParsedRequestListener<ResponseProfileUser>() {
                    @Override
                    public void onResponse(ResponseProfileUser response) {
                        data.setValue(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson g = new Gson();
                        ResponseProfileUser err = g.fromJson(anError.getErrorBody(), ResponseProfileUser.class);
                        data.setValue(err);
                        MDVKHelper.DIALOG_HELPER.showAlertDialog(context, String.valueOf(anError.getErrorCode()), err.getMessage(), "OK");
                    }
                });

        return data;
    }

    // ------------------------------------------------------------------------
    // GET_USER_REPO_LIST
    // ------------------------------------------------------------------------
    public LiveData<List<ResponseProjectList>> refreshUserProjectList(Context context, String username) {
        final MutableLiveData<List<ResponseProjectList>> data = new MutableLiveData<>();
        AndroidNetworking.get(KEY_BASE_URL.concat("users/{username}/repos"))
                .addPathParameter("username", username)
                .setPriority(Priority.LOW)
                .build()
                .getAsParsed(new TypeToken<List<ResponseProjectList>>() {
                }, new ParsedRequestListener<List<ResponseProjectList>>() {
                    @Override
                    public void onResponse(List<ResponseProjectList> response) {
                        data.setValue(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Gson g = new Gson();
                        ResponseProjectList err = g.fromJson(anError.getErrorBody(), ResponseProjectList.class);
                        data.postValue(null);
                        MDVKHelper.DIALOG_HELPER.showAlertDialog(context, String.valueOf(anError.getErrorCode()), err.getMessage(), "OK");
                    }
                });

        return data;
    }

}
