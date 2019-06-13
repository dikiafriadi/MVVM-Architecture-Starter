package com.aditp.mdvkarch.ui.clean_example;

import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseActivity;
import com.aditp.mdvkarch.databinding.ActivityCleanBinding;

/**
 * ------------------------------
 *
 * @author : <Aditya Pratama>
 * @since : Mei 2019
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
        initToolbar("Clean Activity");
    }

    @Override
    public void onActionComponent() {
        this.updateUI();
        binding.tvFullname.setOnClickListener(v -> Toast.makeText(this, "Hello World ...", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void updateUI() {
        viewModel().getUserGithub("abehbatre").observe(this, responseProfileUser -> {
            binding.tvFullname.setText(responseProfileUser.getName());
        });
    }
}
