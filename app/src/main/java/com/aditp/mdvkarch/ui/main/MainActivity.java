package com.aditp.mdvkarch.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseActivity;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.data.remote.api_response.ResponseArray;
import com.aditp.mdvkarch.databinding.ActivityMainBinding;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.aditp.mdvkarch.helper.utils.SpacesItemDecoration;
import com.aditp.mdvkarch.ui.login.LoginActivity;
import com.aditp.mdvkarch.ui.note.NoteActivity;

import java.util.List;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    private MainAdapter adapter;
    private List<ResponseArray> items;

    @Override
    public int LAYOUT() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel bl() {
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

        // init


    }

    private void observeViewModel(MainViewModel bl) {
        // Update the list when the data changes
        bl.getProjectListObservable().observe(this, new Observer<List<ResponseArray>>() {
            @Override
            public void onChanged(@Nullable List<ResponseArray> projects) {
                if (projects != null) {
                    adapter = new MainAdapter(MainActivity.this);
                    adapter.setItems(projects);
                    binding.rvList.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onActionComponent() {
        observeViewModel(bl());
        binding.btnFab.setOnClickListener(v -> MDVKHelper.DIALOG_TOOLS.showAboutDialog(this));
        binding.btnOpenNotes.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, NoteActivity.class)));
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            observeViewModel(bl());
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
                    MDVKHelper.DIALOG_TOOLS.showCustomDialog(MainActivity.this,
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
