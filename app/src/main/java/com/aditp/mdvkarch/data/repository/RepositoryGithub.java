package com.aditp.mdvkarch.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aditp.mdvkarch.data.remote.model_response.ResponseProfileUser;
import com.aditp.mdvkarch.data.remote.model_response.ResponseSearchRepositories;
import com.aditp.mdvkarch.data.remote.model_response.login.ResponseLogin;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static com.aditp.mdvkarch.helper.MDVKHelper.DIALOG_HELPER.showAlertDialog;

/**
 * @author <aditya pratama>
 * @implNote : Kalo dalam 1 repo kebanyakan method di buat partial aja, ini cuma contoh ~
 */
public class RepositoryGithub {

    private Gson gson = new Gson();

    private static RepositoryGithub instance;

    // Singleton
    public synchronized static RepositoryGithub getInstance() {
        if (instance == null) {
            instance = new RepositoryGithub();
        }
        return instance;
    }


    /**
     * @implSpec <NETWORK LAYER>
     * @implNote example methodName :refreshUserProfile
     * @implNote clue -> refresh
     * @implNote refresh means mengambil ulang dari network layer .
     */


    // ------------------------------------------------------------------------
    // GET_USER_PROFILE
    // ------------------------------------------------------------------------
    public LiveData<ResponseProfileUser> refreshUserProfile(Context context, String username) {
        final MutableLiveData<ResponseProfileUser> data = new MutableLiveData<>();
        AndroidNetworking.get(Endpoint.BASE_URL + "users/{username}")
                .addPathParameter(Endpoint.KEY_USERNAME, username)
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
    // test login (post)
    // ------------------------------------------------------------------------
    public LiveData<ResponseLogin> refreshLogin(Context context, String username, String password) {
        final MutableLiveData<ResponseLogin> data = new MutableLiveData<>();
        AndroidNetworking.post("https://trial.gaji.id/gaji/rest/ess/login")
                .addHeaders("username", username)
                .addHeaders("password", password)
                .setPriority(Priority.LOW)
                .build()
                .getAsParsed(new TypeToken<ResponseLogin>() {
                }, new ParsedRequestListener<ResponseLogin>() {
                    @Override
                    public void onResponse(ResponseLogin response) {
                        data.setValue(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        ResponseLogin err = gson.fromJson(anError.getErrorBody(), ResponseLogin.class);
                        data.setValue(err);
                    }
                });

        return data;
    }

    // ------------------------------------------------------------------------
    // SEARCH_REPO
    // ------------------------------------------------------------------------
    public LiveData<ResponseSearchRepositories> refreshSearchRepo(Context context, String query) {
        final MutableLiveData<ResponseSearchRepositories> data = new MutableLiveData<>();
        AndroidNetworking.get(Endpoint.BASE_URL + "search/repositories")
                .addQueryParameter("q", query)
                .addHeaders("User-Agent", "MDVK-ARCH")
                .setPriority(Priority.LOW)
                .build()
                .getAsParsed(new TypeToken<ResponseSearchRepositories>() {
                }, new ParsedRequestListener<ResponseSearchRepositories>() {
                    @Override
                    public void onResponse(ResponseSearchRepositories response) {
                        data.setValue(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        ResponseSearchRepositories err = gson.fromJson(anError.getErrorBody(), ResponseSearchRepositories.class);
                        data.postValue(null);
                        showAlertDialog(context, String.valueOf(anError.getErrorCode()), anError.getErrorBody(), "OK");
                    }
                });

        return data;
    }


}
