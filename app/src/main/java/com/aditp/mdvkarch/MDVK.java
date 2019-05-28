package com.aditp.mdvkarch;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.aditp.mdvkarch.helper.MDVKHelper.ActionDialogListener;
import com.aditp.mdvkarch.ui.login.LoginActivity;
import com.aditp.mdvkarch.ui.main.MainActivity;

import static com.aditp.mdvkarch.core.CONSTANT.KEY_USERNAME;
import static com.aditp.mdvkarch.helper.MDVKHelper.DIALOG_HELPER.showCustomDialog;
import static com.aditp.mdvkarch.helper.MDVKHelper.NETWORK_HELPER.isOnline;

public class MDVK extends AppCompatActivity {

    // ------------------------------------------------------------------------
    // Splash Screens the Right Way :
    // https://www.bignerdranch.com/blog/splash-screens-the-right-way/
    // ------------------------------------------------------------------------
    protected void startApp(Class<?> cls) {
        startActivity(new Intent(MDVK.this, cls));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MDVKHelper.WINDOW_HELPER.setActivityToFullScreen(this);

        // check device is online ? -> coz online require
        if (isOnline(this)) {

            // check user login session
            String token = SharedPref.getInstance().getString(KEY_USERNAME, "");
            if (token.isEmpty()) {
                startApp(LoginActivity.class);
            } else {
                startApp(MainActivity.class);
            }

        } else {
            showCustomDialog(
                    this,
                    getResources().getString(R.string.msg_no_connection_title),
                    getResources().getString(R.string.msg_no_connection),
                    R.drawable.flag_question,
                    new ActionDialogListener() {
                        @Override
                        public void executeNo() {
                            // ignored
                        }

                        @Override
                        public void executeYes() {
                            finish();
                        }
                    });
        }

    }


}
