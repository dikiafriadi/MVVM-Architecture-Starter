package com.aditp.mdvkarch.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

/**
 * ------------------------------------------------------------------------------------
 *
 * @param <T> ViewDataBinding
 * @param <V> ViewModel
 *            _______________
 * @author : <Aditya Pratama>
 * @since : Mei 2019
 * ------------------------------------------------------------------------------------
 */
public abstract class BaseFragment<T extends ViewDataBinding, V extends ViewModel> extends Fragment implements BaseImpl {
    private static final String TAG = "BaseFragment";
    protected T binding;
    private View view;

    public abstract @LayoutRes
    int LAYOUT();

    public abstract V viewModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, LAYOUT(), container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void isFullScreen(boolean val) {
        // ignored
    }

    @Override
    public void isChangeSystemBarColor(boolean val) {
        // ignored
    }

    @Override
    public void onStart() {
        super.onStart();
        onActionComponent();
    }
}