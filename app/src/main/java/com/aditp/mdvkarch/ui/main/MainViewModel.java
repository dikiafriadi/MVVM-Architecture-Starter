package com.aditp.mdvkarch.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adit.mdvklibrary.MDVKPref;
import com.aditp.mdvkarch.data.model.ResponseProfileUser;
import com.aditp.mdvkarch.data.model.ResponseSearchRepositories;
import com.aditp.mdvkarch.data.repository.RepositoryGithub;

// ------------------------------------------------------------------------
// BUSINESS LOGIC
// ------------------------------------------------------------------------
public class MainViewModel extends ViewModel {

    public LiveData<ResponseProfileUser> getUserProfileObservable(String query) {
        return RepositoryGithub.getInstance().refreshUserProfile(query);
    }

    public LiveData<ResponseSearchRepositories> getSearchRepoObservable(String query) {
        return RepositoryGithub.getInstance().refreshSearchRepo(query);
    }

    public LiveData<ResponseProfileUser> loadDataProfileLocal() {
        ResponseProfileUser responseProfileUser;
        responseProfileUser = MDVKPref.getInstance().getObject("TES", ResponseProfileUser.class);
        final MutableLiveData<ResponseProfileUser> data = new MutableLiveData<>();
        data.setValue(responseProfileUser);
        return data;
    }
}