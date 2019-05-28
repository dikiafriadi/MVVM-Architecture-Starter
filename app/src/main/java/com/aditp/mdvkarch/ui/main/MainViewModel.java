package com.aditp.mdvkarch.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aditp.mdvkarch.core.CONSTANT;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.data.repository.GithubRepository;
import com.aditp.mdvkarch.data.remote.api_response.ResponseArray;

import java.util.List;

// ------------------------------------------------------------------------
// BUSINESS LOGIC
// ------------------------------------------------------------------------
public class MainViewModel extends AndroidViewModel {
    private final LiveData<List<ResponseArray>> projectListObservable;
    private String getUsernameFromSharedPref;


    public MainViewModel(@NonNull Application application) {
        super(application);
        // If any transformation is needed, this can be simply done by Transformations class ...
        getUsernameFromSharedPref = SharedPref.getInstance().getString(CONSTANT.KEY_USERNAME, "");
        projectListObservable = GithubRepository.getInstance().getProjectList(getUsernameFromSharedPref);
    }


    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<List<ResponseArray>> getProjectListObservable() {
        return projectListObservable;
    }

}
