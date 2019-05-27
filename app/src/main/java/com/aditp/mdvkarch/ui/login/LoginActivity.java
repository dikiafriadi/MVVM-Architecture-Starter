package com.aditp.mdvkarch.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseActivity;
import com.aditp.mdvkarch.databinding.ActivityLoginBinding;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.aditp.mdvkarch.ui.main.MainActivity;

import java.util.Objects;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginBL> {
    LoginBL loginBL;

    @Override
    public int LAYOUT() {
        return R.layout.activity_login;
    }

    @Override
    public LoginBL setBLClass() {
        loginBL = new LoginBL(this, binding);
        return loginBL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MDVKHelper.WINDOW_TOOLS.setActivityToFullScreen(this);
        onActionComponent();

    }

    @Override
    public void onActionComponent() {
        binding.btnLogin.setOnClickListener(view -> {
            String username = Objects.requireNonNull(binding.etUsername.getText()).toString();
            if (TextUtils.isEmpty(username)) {
                binding.etUsername.setError("cannot be Empty");
                binding.etUsername.requestFocus();
            } else if (username.length() <= 3) {
                binding.etUsername.setError("The Word min 3 Character");
                binding.etUsername.requestFocus();
            } else {
                loginBL.doLogin(username);
            }
        });

        loginBL.setOnLoginSuccess(() -> {
            Dialog dialog = MDVKHelper.DIALOG_TOOLS.showProgressDialog(this);
            dialog.show();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }, 500);

        });

    }

}
