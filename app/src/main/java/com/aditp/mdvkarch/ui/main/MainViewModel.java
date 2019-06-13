package com.aditp.mdvkarch.ui.main;

import android.content.Context;

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

    public LiveData<ResponseProfileUser> getUserProfileObservable(Context context, String query) {
        return RepositoryGithub.getInstance().refreshUserProfile(context, query);
    }

    public LiveData<ResponseSearchRepositories> getSearchRepoObservable(Context context, String query) {
        return RepositoryGithub.getInstance().refreshSearchRepo(context, query);
    }

    public LiveData<ResponseProfileUser> loadDataProfileLocal() {
        ResponseProfileUser responseProfileUser;
        responseProfileUser = MDVKPref.getInstance().getObject("TES", ResponseProfileUser.class);
        final MutableLiveData<ResponseProfileUser> data = new MutableLiveData<>();
        data.setValue(responseProfileUser);
        return data;
    }
}