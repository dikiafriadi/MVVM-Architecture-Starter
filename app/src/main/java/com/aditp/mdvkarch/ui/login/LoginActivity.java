package com.aditp.mdvkarch.ui.login;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.lifecycle.ViewModelProviders;

import com.adit.mdvklibrary.MDVKOkHttpClient;
import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseActivity;
import com.aditp.mdvkarch.databinding.ActivityLoginBinding;
import com.aditp.mdvkarch.helper.CONSTANT;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.aditp.mdvkarch.helper.utils.SharedPref;

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
            String username = Objects.requireNonNull(binding.etUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
            if (TextUtils.isEmpty(username)) {
                binding.etUsername.setError("cannot be Empty");
                binding.etUsername.requestFocus();
            } else if (username.length() <= 3) {
                binding.etUsername.setError("The Word min 3 Character");
                binding.etUsername.requestFocus();
            } else {
                Dialog dialog = MDVKHelper.DIALOG_HELPER.showProgressDialog(this);
                dialog.show();
                viewModel().getUserProfileObservable(this, username, password).observe(this, responseLogin -> {
                    if (responseLogin.getErrors().size() == 0) {
                        SharedPref.getInstance().saveString(CONSTANT.KEY_TOKEN, responseLogin.getData().getToken());
                        MDVKHelper.DIALOG_HELPER.showAlertDialog(this,"200",responseLogin.getData().getToken(),"ok");
                    }else {
                        MDVKHelper.DIALOG_HELPER.showAlertDialog(this,"400",responseLogin.getErrors().get(0).getCode(),"ok");
                    }
                });
            }
        });
    }

}
