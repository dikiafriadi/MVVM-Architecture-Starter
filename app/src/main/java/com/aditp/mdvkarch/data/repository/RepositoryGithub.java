package com.aditp.mdvkarch.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aditp.mdvkarch.data.model.ResponseProfileUser;
import com.aditp.mdvkarch.data.model.ResponseSearchRepositories;
import com.aditp.mdvkarch.data.model.login.ResponseLogin;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;

import static com.adit.mdvklibrary.MDVKHelper.DIALOG_HELPER.showAlertDialog;
import static com.aditp.mdvkarch.data.repository.Endpoint.BASE_URL;

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
        Rx2AndroidNetworking.get(BASE_URL.concat("users/{username}"))
                .addPathParameter("username", username)
                .build()
                .getObjectObservable(ResponseProfileUser.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseProfileUser>() {
                    @Override
                    public void onNext(ResponseProfileUser responseProfileUser) {
                        data.setValue(responseProfileUser);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ANError anError = (ANError) e;
                        ResponseProfileUser err = gson.fromJson(anError.getErrorBody(), ResponseProfileUser.class);
                        data.setValue(err);
                        showAlertDialog(context, String.valueOf(anError.getErrorCode()), err.getMessage(), "ok");
                    }

                    @Override
                    public void onComplete() {

                    }
                });


        return data;
    }

    // ------------------------------------------------------------------------
    // SEARCH_REPO
    // ------------------------------------------------------------------------
    public LiveData<ResponseSearchRepositories> refreshSearchRepo(Context context, String query) {
        final MutableLiveData<ResponseSearchRepositories> data = new MutableLiveData<>();
        Rx2AndroidNetworking.get(BASE_URL.concat("search/repositories"))
                .addQueryParameter("q", query)
                .addHeaders("User-Agent", "MDVK")
                .build()
                .getObjectObservable(ResponseSearchRepositories.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseSearchRepositories>() {
                    @Override
                    public void onNext(ResponseSearchRepositories responseSearchRepositories) {
                        data.setValue(responseSearchRepositories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ANError anError = (ANError) e;
                        ResponseSearchRepositories err = gson.fromJson(anError.getErrorBody(), ResponseSearchRepositories.class);
                        data.setValue(err);
                        showAlertDialog(context, String.valueOf(anError.getErrorCode()), err.getMessage(), "ok");
                    }

                    @Override
                    public void onComplete() {

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

}