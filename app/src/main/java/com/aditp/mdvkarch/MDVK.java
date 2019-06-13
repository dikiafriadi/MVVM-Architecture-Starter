package com.aditp.mdvkarch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.adit.mdvklibrary.MDVKHelper;
import com.adit.mdvklibrary.MDVKPref;
import com.aditp.mdvkarch.ui.main.MainActivity;

import static com.aditp.mdvkarch.helper.constant.K.KEY_USERNAME;


public class MDVK extends AppCompatActivity {

    public static final boolean IS_DEV_MODE = true;

    // ------------------------------------------------------------------------
    // Splash Screens the Right Way :
    // https://www.bignerdranch.com/blog/splash-screens-the-right-way/
    // ------------------------------------------------------------------------
    protected void startApp(Class<?> cls) {
        startActivity(new Intent(MDVK.this, cls));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MDVKHelper.WINDOW_HELPER.setActivityToFullScreen(this);

        // isDevMode?
        if (IS_DEV_MODE) {
            Toast.makeText(this, "DEV_MODE IS TRUE, DONT FORGET TO DISABLE WHEN RELEASE ..", Toast.LENGTH_SHORT).show();
        }

        // isOnline?
        if (MDVKHelper.NETWORK_HELPER.isOnline(this)) {
            // check user login session
            String token = MDVKPref.getInstance().getString(KEY_USERNAME, "");
            if (token.isEmpty()) {
                startApp(MainActivity.class);
            } else {
                startApp(MainActivity.class);
            }

        } else {
            MDVKHelper.DIALOG_HELPER.showCustomDialog(
                    this,
                    getResources().getString(R.string.msg_no_connection_title),
                    getResources().getString(R.string.msg_no_connection),
                    R.drawable.wg_sharp_blues,
                    new MDVKHelper.ActionDialogListener() {
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
