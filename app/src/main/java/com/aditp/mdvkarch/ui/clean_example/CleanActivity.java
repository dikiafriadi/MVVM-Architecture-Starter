package com.aditp.mdvkarch.ui.clean_example;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseActivity;
import com.aditp.mdvkarch.databinding.ActivityCleanBinding;

/**
 * ------------------------------
 *
 * @author : <Aditya Pratama>
 * @since  : Mei 2019
 * ------------------------------
 */
public class CleanActivity extends BaseActivity<ActivityCleanBinding, CleanViewModel> {

    @Override
    public int LAYOUT() {
        return R.layout.activity_clean;
    }

    @Override
    public CleanViewModel viewModel() {
        return ViewModelProviders.of(this).get(CleanViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActionComponent() {
        viewModel().logicName(this);
    }
}
