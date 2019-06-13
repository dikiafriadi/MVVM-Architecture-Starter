package com.aditp.mdvkarch.core;

import androidx.multidex.MultiDexApplication;

import com.adit.mdvklibrary.MDVKPref;
import com.aditp.mdvkarch.MDVK;
import com.aditp.mdvkarch.R;
import com.squareup.leakcanary.LeakCanary;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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


        if (MDVK.IS_DEV_MODE) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }

        // CalligraphyConfig
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/samsung.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        // SharedPreference
        MDVKPref.init(getApplicationContext());

    }
}
