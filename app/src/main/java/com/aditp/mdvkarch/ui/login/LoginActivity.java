package com.aditp.mdvkarch.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.ViewModelProviders;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseActivity;
import com.aditp.mdvkarch.core.CONSTANT;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.databinding.ActivityLoginBinding;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.aditp.mdvkarch.ui.main.MainActivity;

import java.util.Objects;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {
    @Override
    public int LAYOUT() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel viewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isFullScreen(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActionComponent() {
        binding.btnLogin.setOnClickListener(view -> {
            String owner = Objects.requireNonNull(binding.etUsername.getText()).toString();
            if (TextUtils.isEmpty(owner)) {
                binding.etUsername.setError("cannot be Empty");
                binding.etUsername.requestFocus();
            } else if (owner.length() <= 3) {
                binding.etUsername.setError("The Word min 3 Character");
                binding.etUsername.requestFocus();
            } else {
                viewModel().getUserProfileObservable(owner).observe(this, responseObject -> {
                    Log.d("KONTOL", "onActionComponent: " + responseObject);
                    if (responseObject == null) {
                        MDVKHelper.DIALOG_TOOLS.showAlertDialog(
                                LoginActivity.this,
                                "UPS",
                                "Not Found",
                                "ok");
                    } else {
                        SharedPref.getInstance().saveString(CONSTANT.KEY_USERNAME, owner);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });
            }
        });


    }

}
