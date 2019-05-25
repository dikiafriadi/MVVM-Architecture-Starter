package com.aditp.mdvkarch.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.CONSTANT;
import com.aditp.mdvkarch.core.MyActivity;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.databinding.ActivityMainBinding;
import com.aditp.mdvkarch.helper.MDVK;
import com.aditp.mdvkarch.ui.login.LoginActivity;
import com.aditp.mdvkarch.utils.SpacesItemDecoration;


public class MainActivity extends MyActivity {
    ActivityMainBinding binding;
    int LAYOUT = R.layout.activity_main;
    MainBL mainBL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, LAYOUT);
        mainBL = new MainBL(this, binding);

        initToolbar("Main Menu");
        initNavigationMenu();

        // setup RecyclerView
        binding.rvList.setHasFixedSize(true);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.addItemDecoration(new SpacesItemDecoration(5));

    }

    @Override
    public void onActionComponent() {
        // load data
        String getUsernameFromSharedPref = SharedPref.getInstance().getString(CONSTANT.KEY_USERNAME, "");
        mainBL.getDataUsers(getUsernameFromSharedPref);
        mainBL.getDataUserRepos(getUsernameFromSharedPref);

        binding.btnFab.setOnClickListener(v -> {
            MDVK.DIALOG_TOOLS.showAboutDialog(this);
        });
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            mainBL.getDataUsers(getUsernameFromSharedPref);
            mainBL.getDataUserRepos(getUsernameFromSharedPref);
        });

        mainBL.adapter.setOnItemClickListener((view, obj, pos) -> {
            MDVK.DIALOG_TOOLS.showCustomDialog(this,
                    String.valueOf(obj.getFullName()),
                    "Language : " + obj.getLanguage() + "\n" +
                            "Star : " + obj.getSize(),
                    R.drawable.flag_question,
                    new MDVK.ActionDialogListener() {
                        @Override
                        public void executeNo() {
                            // ignored
                        }

                        @Override
                        public void executeYes() {
                            Toast.makeText(MainActivity.this, "Direct to WebBrowser to open Link ~", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

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
                    MDVK.DIALOG_TOOLS.showCustomDialog(MainActivity.this,
                            "Logout",
                            "Would you really like to logout?",
                            new MDVK.ActionDialogListener() {
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

}
