package com.aditp.mdvkarch.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseView;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.databinding.ActivitySplashBinding;
import com.aditp.mdvkarch.helper.MDVK;
import com.aditp.mdvkarch.ui.login.LoginActivity;
import com.aditp.mdvkarch.ui.main.MainActivity;
import com.aditp.mdvkarch.utils.ViewAnimation;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import static com.aditp.mdvkarch.core.CONSTANT.IS_DEV_MODE;
import static com.aditp.mdvkarch.core.CONSTANT.KEY_USERNAME;

public class SplashActivity extends AppCompatActivity implements BaseView {
    ActivitySplashBinding binding;
    int LAYOUT = R.layout.activity_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MDVK.WINDOW_TOOLS.setActivityToFullScreen(this);

        binding = DataBindingUtil.setContentView(this, LAYOUT);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable textDrawable = TextDrawable.builder().buildRect("", color);
        binding.icon.setImageDrawable(textDrawable);

        onActionComponent();
        ViewAnimation.showIn(binding.icon, 2000);
    }

    @Override
    public void onActionComponent() {
        if (IS_DEV_MODE) {
            // .: DEV MODE :.
            String token = SharedPref.getInstance().getString(KEY_USERNAME, "");
            if (token.isEmpty()) {
                runApps(500, LoginActivity.class);
            } else {
                runApps(700, MainActivity.class);
            }
        } else {
            // .: PRODUCTION :.
            if (MDVK.NETWORK_TOOLS.isOnline(this)) {
                runApps(600, LoginActivity.class);
            } else {
                MDVK.DIALOG_TOOLS.showCustomDialog(this,
                        getString(R.string.msg_no_connection_title),
                        getString(R.string.msg_no_connection),
                        R.drawable.flag_question, new MDVK.ActionDialogListener() {
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
