package com.aditp.mdvkarch.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseActivity;
import com.aditp.mdvkarch.core.SharedPref;
import com.aditp.mdvkarch.data.remote.response.ResponseProjectList;
import com.aditp.mdvkarch.databinding.ActivityMainBinding;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.aditp.mdvkarch.helper.utils.SpacesItemDecoration;
import com.aditp.mdvkarch.ui.login.LoginActivity;
import com.aditp.mdvkarch.ui.note.NoteActivity;

import java.util.List;

import static com.aditp.mdvkarch.helper.MDVKHelper.DIALOG_HELPER.showAlertDialog;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    private MainAdapter adapter;
    private List<ResponseProjectList> items;

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
        loadData();
        binding.btnFab.setOnClickListener(v -> MDVKHelper.DIALOG_HELPER.showAboutDialog(this));
        binding.btnOpenNotes.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, NoteActivity.class)));
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            loadData();
            binding.swipeRefreshLayout.setRefreshing(false);
        });


    }

    private void loadData() {
        getProjectList(viewModel());
        getUserProfile(viewModel());
    }

    private void getProjectList(MainViewModel viewModel) {
        // Update the list when the data changes
//        viewModel.getProjectListObservable().observe(this, projects -> {
//            if (projects != null) {
//                adapter = new MainAdapter(MainActivity.this, projects);
//                binding.rvList.setAdapter(adapter);
//            }
//        });
    }

    private void getUserProfile(MainViewModel viewModel) {
        viewModel.getUserProfileObservable().observe(this, responseObject -> {
            binding.tvname.setText(responseObject.getName());
            binding.tvCompany.setText(responseObject.getCompany());
            binding.tvBio.setText(responseObject.getBio());
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
