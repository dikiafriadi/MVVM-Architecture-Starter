package com.aditp.mdvkarch.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aditp.mdvkarch.core.CONSTANT;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.data.repository.GithubRepository;
import com.aditp.mdvkarch.data.response.ResponseProjectList;
import com.aditp.mdvkarch.data.response.ResponseProfileUser;

import java.util.List;

// ------------------------------------------------------------------------
// BUSINESS LOGIC
// ------------------------------------------------------------------------
public class MainViewModel extends ViewModel {
    private LiveData<List<ResponseProjectList>> projectListObservable;
    private LiveData<ResponseProfileUser> userProfileObservable;

    public MainViewModel() {
        // load username from pref
        String owner = SharedPref.getInstance().getString(CONSTANT.KEY_USERNAME, "");
        projectListObservable = GithubRepository.getInstance().getProjectList(owner);
        userProfileObservable = GithubRepository.getInstance().getUserProfile(owner);
    }

    LiveData<List<ResponseProjectList>> getProjectListObservable() {
        return projectListObservable;
    }

    LiveData<ResponseProfileUser> getUserProfileObservable() {
        return userProfileObservable;
    }

}