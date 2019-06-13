package com.aditp.mdvkarch.ui.clean_example;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aditp.mdvkarch.data.model.ResponseProfileUser;
import com.aditp.mdvkarch.data.repository.RepositoryGithub;

/**
 * @implSpec BusinessLogic Here ...
 */
public class CleanViewModel extends ViewModel {

    public LiveData<ResponseProfileUser> getUserGithub(String username) {
        return RepositoryGithub.getInstance().refreshUserProfile(username);
    }
}
