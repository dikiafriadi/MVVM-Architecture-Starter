package com.aditp.mdvkarch.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aditp.mdvkarch.data.remote.api_response.ResponseObject;
import com.aditp.mdvkarch.data.repository.GithubRepository;

public class LoginViewModel extends ViewModel {

    private LiveData<ResponseObject> userProfileObservable;


    public LiveData<ResponseObject> getUserProfileObservable(String owner) {
        userProfileObservable = GithubRepository.getInstance().getUserProfile(owner);

        return userProfileObservable;
    }

}