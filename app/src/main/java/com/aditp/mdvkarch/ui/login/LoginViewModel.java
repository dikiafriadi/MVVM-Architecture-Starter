package com.aditp.mdvkarch.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aditp.mdvkarch.data.repository.GithubRepository;
import com.aditp.mdvkarch.data.response.ResponseProfileUser;

public class LoginViewModel extends ViewModel {

    public LiveData<ResponseProfileUser> getUserProfileObservable(String owner) {
        return GithubRepository.getInstance().getUserProfile(owner);
    }

}