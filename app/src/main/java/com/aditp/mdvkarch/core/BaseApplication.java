package com.aditp.mdvkarch.core;

import android.app.Application;

public class BaseApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Ubuntu-Light.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );

        SharedPref.init(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
