package com.aditp.mdvkarch.ui.login;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.aditp.mdvkarch.core.CONSTANT;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.databinding.ActivityLoginBinding;

public class LoginBL extends ViewModel {
    private Context context;
    private ActivityLoginBinding binding;
    private OnLoginSuccess onLoginSuccess;


    public LoginBL(Context context, ActivityLoginBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    // Trigger Technique
    public void setOnLoginSuccess(final OnLoginSuccess onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    public void doLogin(String username, String password) {
        binding.pb1.setVisibility(View.VISIBLE);
        binding.btnLogin.setVisibility(View.GONE);
        SharedPref.getInstance().saveString(CONSTANT.KEY_USER_TOKEN, username);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            binding.pb1.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.VISIBLE);

            if (onLoginSuccess != null) {
                onLoginSuccess.onSuccess();
            }

        }, 1000);

    }

    public interface OnLoginSuccess {
        void onSuccess();
    }

}