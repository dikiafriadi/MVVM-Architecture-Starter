package com.aditp.mdvkarch.core;

import androidx.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;

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
        // LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        // SharedPreference
        SharedPref.init(getApplicationContext());

    }
}
