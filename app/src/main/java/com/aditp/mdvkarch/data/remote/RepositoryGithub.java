package com.aditp.mdvkarch.data.remote;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aditp.mdvkarch.data.remote.api_response.ResponseProfileUser;
import com.aditp.mdvkarch.data.remote.api_response.ResponseProjectList;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.aditp.mdvkarch.data.remote.Endpoint.BASE_URL;
import static com.aditp.mdvkarch.data.remote.Endpoint.KEY_USERNAME;
import static com.aditp.mdvkarch.data.remote.Endpoint.USER;
import static com.aditp.mdvkarch.helper.MDVKHelper.DIALOG_HELPER.showAlertDialog;


// ------------------------------------------------------------------------
// SINGLETON
// ------------------------------------------------------------------------
public class RepositoryGithub {

    private Gson gson = new Gson();

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
        AndroidNetworking.get(BASE_URL.concat(USER))
                .addPathParameter(KEY_USERNAME, username)
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
                        ResponseProfileUser err = gson.fromJson(anError.getErrorBody(), ResponseProfileUser.class);
                        data.setValue(err);
                        showAlertDialog(context, String.valueOf(anError.getErrorCode()), err.getMessage(), "OK");
                    }
                });

        return data;
    }

    // ------------------------------------------------------------------------
    // GET_USER_REPO_LIST
    // ------------------------------------------------------------------------
    public LiveData<List<ResponseProjectList>> refreshUserProjectList(Context context, String username) {
        final MutableLiveData<List<ResponseProjectList>> data = new MutableLiveData<>();
        AndroidNetworking.get(BASE_URL.concat(USER.concat("/repos")))
                .addPathParameter(KEY_USERNAME, username)
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
                        ResponseProjectList err = gson.fromJson(anError.getErrorBody(), ResponseProjectList.class);
                        data.postValue(null);
                        showAlertDialog(context, String.valueOf(anError.getErrorCode()), err.getMessage(), "OK");
                    }
                });

        return data;
    }

}
