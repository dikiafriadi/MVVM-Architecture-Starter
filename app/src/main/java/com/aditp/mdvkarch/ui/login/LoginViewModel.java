package com.aditp.mdvkarch.ui.login;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aditp.mdvkarch.data.model.login.ResponseLogin;
import com.aditp.mdvkarch.data.repository.RepositoryGithub;

public class LoginViewModel extends ViewModel {

    public LiveData<ResponseLogin> getUserProfileObservable(Context context, String username, String password) {
        return RepositoryGithub.getInstance().refreshLogin(context, username, password);
    }

}