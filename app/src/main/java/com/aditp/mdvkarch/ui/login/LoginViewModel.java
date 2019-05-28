package com.aditp.mdvkarch.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aditp.mdvkarch.data.remote.api_response.ResponseObject;
import com.aditp.mdvkarch.data.repository.GithubRepository;

public class LoginViewModel extends ViewModel {


    // ------------------------------------------------------------------------
    // Expose the LiveData query so the UI can observe it.
    // ------------------------------------------------------------------------
    public LiveData<ResponseObject> getUserProfileObservable(String owner) {
        return GithubRepository.getInstance().getUserProfile(owner);
    }

}