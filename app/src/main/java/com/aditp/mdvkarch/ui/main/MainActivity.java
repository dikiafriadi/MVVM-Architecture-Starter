package com.aditp.mdvkarch.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseActivity;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.data.remote.api_response.ResponseProjectList;
import com.aditp.mdvkarch.databinding.ActivityMainBinding;
import com.aditp.mdvkarch.helper.GlideHelper;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.aditp.mdvkarch.helper.utils.SpacesItemDecoration;
import com.aditp.mdvkarch.ui.login.LoginActivity;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    private MainAdapter adapter;

    @Override
    public int LAYOUT() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel viewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar("Main Menu");
        initNavigationMenu();

        // setup RecyclerView
        binding.rvList.setHasFixedSize(true);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.addItemDecoration(new SpacesItemDecoration(5));

    }


    @Override
    public void onActionComponent() {
        updateUI();
        binding.btnFab.setOnClickListener(v -> MDVKHelper.DIALOG_HELPER.showAboutDialog(this));
        binding.swipeRefreshLayout.setOnRefreshListener(this::updateUI);



    }

    private synchronized void updateUI() {
        getUserProfile(viewModel());
        getProjectList(viewModel());
        binding.swipeRefreshLayout.setRefreshing(false);

    }

    private synchronized void getProjectList(MainViewModel viewModel) {
        viewModel.getUserProjectListObservable(this).observe(this, projects -> {
            try {
                if (projects.size() > 0) {
                    binding.noItem.root.setVisibility(View.GONE);
                    adapter = new MainAdapter(MainActivity.this, projects);
                    binding.rvList.setAdapter(adapter);
                    // item adapter click
                    adapter.setOnItemClick((view, obj, pos) -> {
                        Toast.makeText(this, obj.getLanguage(), Toast.LENGTH_SHORT).show();
                    });

                } else {
                    binding.noItem.root.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                Log.d("err", "onActionComponent: " + e.getMessage());
            }

        });
    }

    private synchronized void getUserProfile(MainViewModel viewModel) {
        viewModel.getUserProfileObservable(this).observe(this, responseObject -> {
            try {
                binding.tvname.setText(responseObject.getName());
                binding.tvCompany.setText(responseObject.getCompany());
                binding.tvBio.setText(responseObject.getBio());
                GlideHelper.loadRound(this, responseObject.getAvatarUrl(), binding.ivSelfie);
            } catch (Exception e) {
                Log.d("err", "onActionComponent: " + e.getMessage());
            }
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
                    MDVKHelper.DIALOG_HELPER.showCustomDialog(MainActivity.this,
                            "Logout",
                            "Would you really like to logout?",
                            new MDVKHelper.ActionDialogListener() {
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
