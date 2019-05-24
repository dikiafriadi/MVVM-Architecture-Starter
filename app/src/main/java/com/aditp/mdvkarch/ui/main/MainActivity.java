package com.aditp.mdvkarch.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.MyActivity;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.databinding.ActivityMainBinding;
import com.aditp.mdvkarch.ui.login.LoginActivity;
import com.aditp.mdvkarch.utils.Utility;

import static com.aditp.mdvkarch.utils.Utility.showCustomDialog;

public class MainActivity extends MyActivity {
    ActivityMainBinding binding;
    MainBL mainBL;
    MainModel model;
    int LAYOUT = R.layout.activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, LAYOUT);
        mainBL = new MainBL(this, model);

        initToolbar("Main Menu");
        initNavigationMenu();

    }


    private void initNavigationMenu() {
        binding.navView.setVisibility(View.VISIBLE);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        //noinspection deprecation
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    break;
                case R.id.nav_logout:
                    showCustomDialog(MainActivity.this,
                            "Logout",
                            "Would you really like to logout?",
                            new Utility.ActionDialogListener() {
                                @Override
                                public void executeNo() {
                                    // ignore
                                }

                                @Override
                                public void executeYes() {
                                    SharedPref.getInstance().clearSession();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    finish();
                                }
                            });
                    break;
            }
            binding.drawerLayout.closeDrawers();
            return true;
        });
    }

    @Override
    public void onActionComponent() {
        binding.btnFab.setOnClickListener(v -> Utility.showAboutDialog(this));
        mainBL.getUsers("abehbatre");
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            mainBL.getUsers("abehbatre");
        });
    }
}
