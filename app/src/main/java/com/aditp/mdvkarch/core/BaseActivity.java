package com.aditp.mdvkarch.core;


import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.helper.MDVKHelper;

import java.util.Objects;


/**
 * ----------------------------
 * A D I T Y A   P R A T A M A
 * MEI 2019
 * ----------------------------
 *
 * @param <T> ViewDataBinding
 * @param <L> Business Logic
 */
public abstract class BaseActivity<T extends ViewDataBinding, L> extends AppCompatActivity implements BaseImpl {
    protected T binding;
    protected L bl;

    public abstract @LayoutRes
    int LAYOUT();

    public abstract L setBLClass();

    // ------------------------------------------------------------------------
    // Inheritance Technique to apply on all class extends AppCompatActivity
    // ------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // change SystemBar UI
        MDVKHelper.WINDOW_TOOLS.setSystemBarColor(this, R.color.mdvk_white);
        MDVKHelper.WINDOW_TOOLS.setSystemBarLight(this);
        performDataBinding();
        setBLClass();
    }


    private void performDataBinding() {
        binding = DataBindingUtil.setContentView(this, LAYOUT());
    }


    @Override
    protected void onStart() {
        super.onStart();
        onActionComponent();
    }


    // ------------------------------------------------------------------------
    // BORING METHOD -__-
    // ------------------------------------------------------------------------
    protected void initToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.grey_60));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    protected void initToolbar(String title, int drawable) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(drawable);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.grey_60));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }


}
