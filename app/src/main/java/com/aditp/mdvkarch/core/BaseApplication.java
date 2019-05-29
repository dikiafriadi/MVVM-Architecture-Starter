package com.aditp.mdvkarch.core;

import androidx.multidex.MultiDexApplication;

/**
 * ------------------------------------------------------------------------------------
 *
 * @author : <Aditya Pratama>
 * @since : Mei 2019
 * ------------------------------------------------------------------------------------
 */
public class BaseApplication extends MultiDexApplication {

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
