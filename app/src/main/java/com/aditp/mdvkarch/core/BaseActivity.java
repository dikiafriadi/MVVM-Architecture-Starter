package com.aditp.mdvkarch.core;


import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import com.adit.mdvklibrary.MDVKHelper;
import com.adit.mdvklibrary.MDVKOkHttpClient;
import com.aditp.mdvkarch.MDVK;
import com.aditp.mdvkarch.R;
import com.androidnetworking.AndroidNetworking;

import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * ------------------------------------------------------------------------------------
 *
 * @param <VDB> ViewDataBinding
 * @param <VM>  ViewModel
 *              _______________
 * @author : <Aditya Pratama>
 * @since : Mei 2019
 * ------------------------------------------------------------------------------------
 */
public abstract class BaseActivity<VDB extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity implements BaseImpl {
    protected String TAG = BaseActivity.class.getSimpleName();
    protected VDB binding;
    private Dialog dialog;

    public abstract @LayoutRes
    int LAYOUT();

    public abstract VM viewModel();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getApplicationContext(), MDVKOkHttpClient.CLIENT());
        if (MDVK.IS_DEV_MODE) AndroidNetworking.enableLogging();

        isChangeSystemBarColor(true);
        performDataBinding();
        onActionComponent();


    }

    private void performDataBinding() {
        binding = DataBindingUtil.setContentView(this, LAYOUT());
    }

    @Override
    public void isFullScreen(boolean val) {
        if (val) MDVKHelper.WINDOW_HELPER.setActivityToFullScreen(this);
    }

    @Override
    public void isChangeSystemBarColor(boolean val) {
        if (val) {
            MDVKHelper.WINDOW_HELPER.setSystemBarColor(this, R.color.mdvk_white);
            MDVKHelper.WINDOW_HELPER.setSystemBarLight(this);
        }
    }

    // ------------------------------------------------------------------------
    // BORING METHOD -__-
    // ------------------------------------------------------------------------
    public void initToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.grey_60));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    public void initToolbar(String title, int drawable) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(drawable);
        toolbar.setTitle(title);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        toolbar.setTitleTextColor(getResources().getColor(R.color.grey_60));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    protected void showLoading(Context context, boolean isCancelable) {
        dialog = MDVKHelper.DIALOG_HELPER.showProgressDialog(context);
        dialog.show();
        dialog.setCancelable(isCancelable);
    }

    protected void hideLoading() {
        dialog.dismiss();
    }

}
