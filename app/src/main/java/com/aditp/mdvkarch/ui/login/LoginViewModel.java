package com.aditp.mdvkarch.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aditp.mdvkarch.data.remote.repository.RepositoryGithub;
import com.aditp.mdvkarch.data.remote.response.ResponseProfileUser;

public class LoginViewModel extends ViewModel {

    public LiveData<ResponseProfileUser> getUserProfileObservable(String username) {
        return RepositoryGithub.getInstance().getUserProfile(username);
    }

}