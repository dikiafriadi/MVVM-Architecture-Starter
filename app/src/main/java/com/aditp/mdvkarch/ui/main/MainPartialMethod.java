package com.aditp.mdvkarch.ui.main;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainPartialMethod {
    private MainActivity activity;
    private ActivityMainBinding binding;

    public MainPartialMethod(MainActivity activity, ActivityMainBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    // ------------------------------------------------------------------------
    // initNavigationMenu
    // ------------------------------------------------------------------------
    public void initNavigationMenu() {
        binding.navView.setVisibility(View.VISIBLE);
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(activity,
                binding.drawerLayout,
                binding.toolbarContainer.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_menu, activity.getTheme());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Objects.requireNonNull(drawable).setTint(activity.getColor(R.color.mdvk_black));
        }
        toggle.setToolbarNavigationClickListener(v -> binding.drawerLayout.openDrawer(GravityCompat.START));
        //noinspection deprecation
        binding.toolbarContainer.toolbar.setNavigationIcon(drawable);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    break;
                case R.id.nav_leave:
                    break;
                case R.id.nav_absen:
                    break;
                case R.id.nav_calender:
                    break;
                case R.id.nav_logout:
                    break;

            }
            binding.drawerLayout.closeDrawers();
            return true;
        });
    }
}
