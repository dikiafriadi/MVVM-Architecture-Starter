package com.aditp.mdvkarch.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aditp.mdvkarch.core.CONSTANT;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.data.remote.api_response.ResponseArray;
import com.aditp.mdvkarch.data.remote.api_response.ResponseObject;
import com.aditp.mdvkarch.data.repository.GithubRepository;

import java.util.List;

// ------------------------------------------------------------------------
// BUSINESS LOGIC
// ------------------------------------------------------------------------
public class MainViewModel extends ViewModel {
    private LiveData<List<ResponseArray>> projectListObservable;
    private LiveData<ResponseObject> userProfileObservable;

    public MainViewModel() {
        // load username from pref
        String owner = SharedPref.getInstance().getString(CONSTANT.KEY_USERNAME, "");

        projectListObservable = GithubRepository.getInstance().getProjectList(owner);
        userProfileObservable = GithubRepository.getInstance().getUserProfile(owner);
    }


    // ------------------------------------------------------------------------
    // Expose the LiveData query so the UI can observe it.
    // ------------------------------------------------------------------------
    LiveData<List<ResponseArray>> getProjectListObservable() {
        return projectListObservable;
    }

    LiveData<ResponseObject> getUserProfileObservable() {
        return userProfileObservable;
    }

}