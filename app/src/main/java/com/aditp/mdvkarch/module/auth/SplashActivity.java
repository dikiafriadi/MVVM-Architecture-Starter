package com.aditp.mdvkarch.module.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseView;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.databinding.ActivitySplashBinding;
import com.aditp.mdvkarch.module.main.MainActivity;
import com.aditp.mdvkarch.utils.Tools;
import com.aditp.mdvkarch.utils.Utility;
import com.aditp.mdvkarch.utils.ViewAnimation;

import static com.aditp.mdvkarch.core.CONSTANT.IS_DEV_MODE;
import static com.aditp.mdvkarch.core.CONSTANT.KEY_USER_TOKEN;
import static com.aditp.mdvkarch.utils.Utility.isOnline;
import static com.aditp.mdvkarch.utils.Utility.showCustomDialog;

public class SplashActivity extends AppCompatActivity implements BaseView {
    ActivitySplashBinding binding;
    int LAYOUT = R.layout.activity_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setActivityToFullScreen(this);

        binding = DataBindingUtil.setContentView(this, LAYOUT);

        onActionComponent();
        ViewAnimation.showIn(binding.icon, 2000);
    }

    @Override
    public void onActionComponent() {
        if (IS_DEV_MODE) {
            // .: DEV MODE :.
            String token = SharedPref.getInstance().getString(KEY_USER_TOKEN, "");
            if (token.isEmpty()) {
                runApps(500, LoginActivity.class);
            } else {
                runApps(2000, MainActivity.class);
            }
        } else {
            // .: PRODUCTION :.
            if (isOnline(this)) {
                runApps(1500, LoginActivity.class);
            } else {
                showCustomDialog(this,
                        getString(R.string.msg_no_connection_title),
                        getString(R.string.msg_no_connection),
                        R.drawable.flag_question, new Utility.ActionDialogListener() {
                            @Override
                            public void executeNo() {
                                // ignore
                            }

                            @Override
                            public void executeYes() {
                                finish();
                            }
                        });
            }
        }
    }

    private void runApps(int milSecond, Class<?> cls) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, cls));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, milSecond);
    }

}
