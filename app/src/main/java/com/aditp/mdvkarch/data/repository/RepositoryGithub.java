package com.aditp.mdvkarch.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aditp.mdvkarch.data.model.ResponseProfileUser;
import com.aditp.mdvkarch.data.model.ResponseSearchRepositories;
import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;

import static com.aditp.mdvkarch.data.repository.Endpoint.BASE_URL;

/**
 * @author <aditya pratama>
 * @implNote : Kalo dalam 1 repo kebanyakan method di buat partial aja, ini cuma contoh ~
 * @see Endpoint to edit baseUrl
 */
public class RepositoryGithub {
    private Gson gson = new Gson();
    private static RepositoryGithub instance;


    /**
     * @return instance
     */
    // Singleton
    public synchronized static RepositoryGithub getInstance() {
        if (instance == null) instance = new RepositoryGithub();
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
    public LiveData<ResponseProfileUser> refreshUserProfile(String username) {
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
    public LiveData<ResponseSearchRepositories> refreshSearchRepo(String query) {
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
                        if (anError.getErrorCode() == 403) {
                            Log.d("DEBUX", "onError: 403");
                        } else if (anError.getErrorCode() == 404) {
                            Log.d("DEBUX", "onError: 404");

                        }
                        ResponseSearchRepositories err = gson.fromJson(anError.getErrorBody(), ResponseSearchRepositories.class);
                        data.setValue(err);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


        return data;
    }
}
