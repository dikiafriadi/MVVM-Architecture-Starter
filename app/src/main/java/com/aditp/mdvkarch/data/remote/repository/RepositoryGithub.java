package com.aditp.mdvkarch.data.remote.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aditp.mdvkarch.data.remote.response.ResponseProfileUser;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;


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


    public LiveData<ResponseProfileUser> getUserProfile(String username) {
        final MutableLiveData<ResponseProfileUser> data = new MutableLiveData<>();
        AndroidNetworking.get(KEY_BASE_URL.concat("users/{username}"))
                .addPathParameter("username", username)
                .setPriority(Priority.LOW).build()
                .getAsParsed(new TypeToken<ResponseProfileUser>() {
                             },
                        new ParsedRequestListener<ResponseProfileUser>() {
                            @Override
                            public void onResponse(ResponseProfileUser response) {
                                data.setValue(response);
                            }

                            @Override
                            public void onError(ANError anError) {

//                                if (anError.getErrorCode() == 400) {
//                                    try {
//                                        JSONObject jsonObj = new JSONObject(anError.getErrorBody());
//                                        String getErrorCodeItem = jsonObj.getJSONArray("errors").getJSONObject(0).getString("code");
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
                            }
                        });

        return data;
    }

}
