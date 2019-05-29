package com.aditp.mdvkarch.ui.login;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aditp.mdvkarch.data.remote.RepositoryGithub;
import com.aditp.mdvkarch.data.remote.api_response.ResponseProfileUser;

public class LoginViewModel extends ViewModel {

    public LiveData<ResponseProfileUser> getUserProfileObservable(Context context, String username) {
        return RepositoryGithub.getInstance().refreshUserProfile(context, username);
    }

}