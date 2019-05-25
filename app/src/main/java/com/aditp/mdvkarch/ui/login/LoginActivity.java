package com.aditp.mdvkarch.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseView;
import com.aditp.mdvkarch.databinding.ActivityLoginBinding;
import com.aditp.mdvkarch.ui.main.MainActivity;
import com.aditp.mdvkarch.helper.MDVK;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements BaseView {
    ActivityLoginBinding binding;
    LoginBL loginBL;
    int LAYOUT = R.layout.activity_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MDVK.WINDOW_TOOLS.setActivityToFullScreen(this);
        binding = DataBindingUtil.setContentView(this, LAYOUT);
        loginBL = new LoginBL(this, binding);
        onActionComponent();

    }

    @Override
    public void onActionComponent() {
        binding.btnLogin.setOnClickListener(view -> {
            String username = Objects.requireNonNull(binding.etUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
            if (TextUtils.isEmpty(username)) {
                binding.etUsername.setError("this still empty -_-");
                binding.etUsername.requestFocus();
            } else if (username.length() < 5) {
                binding.etUsername.setError("this < 5 -_-");
                binding.etUsername.requestFocus();
            } else if (password.isEmpty()) {
                binding.etPassword.setError("this still empty -_-");
                binding.etPassword.requestFocus();
            } else {
                loginBL.doLogin(username, password);
            }
        });

        loginBL.setOnLoginSuccess(() -> {
            Dialog dialog = MDVK.DIALOG_TOOLS.showProgressDialog(this);
            dialog.show();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }, 2500);

        });

    }

}
