package com.aditp.mdvkarch.core;


import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.utils.Tools;

import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class MyActivity extends AppCompatActivity implements BaseView {
    protected String TAG = "DEBUG";


    // ------------------------------------------------------------------------
    // Inheritance Technique to apply on all class extends AppCompatActivity
    // ------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // change SystemBar UI
        Tools.setSystemBarColor(this, R.color.mdtp_white);
        Tools.setSystemBarLight(this);
    }

    protected void initToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void initToolbar(String title, int toolbarColor) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(toolbarColor), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onActionComponent();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
