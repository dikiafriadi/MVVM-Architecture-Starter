package com.aditp.mdvkarch.ui.main;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aditp.mdvkarch.core.CONSTANT;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.data.remote.RepositoryGithub;
import com.aditp.mdvkarch.data.remote.api_response.ResponseProfileUser;
import com.aditp.mdvkarch.data.remote.api_response.ResponseProjectList;

import java.util.List;

// ------------------------------------------------------------------------
// BUSINESS LOGIC
// ------------------------------------------------------------------------
public class MainViewModel extends ViewModel {
    private String getUsernameSK = SharedPref.getInstance().getString(CONSTANT.KEY_USERNAME, "");


    public LiveData<ResponseProfileUser> getUserProfileObservable(Context context) {
        return RepositoryGithub.getInstance().refreshUserProfile(context, this.getUsernameSK);
    }

    public LiveData<List<ResponseProjectList>> getUserProjectListObservable(Context context) {
        return RepositoryGithub.getInstance().refreshUserProjectList(context, this.getUsernameSK);
    }


}