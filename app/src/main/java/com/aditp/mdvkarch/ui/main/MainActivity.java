package com.aditp.mdvkarch.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseActivity;
import com.aditp.mdvkarch.databinding.ActivityMainBinding;
import com.aditp.mdvkarch.helper.CONSTANT;
import com.aditp.mdvkarch.helper.GlideHelper;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.aditp.mdvkarch.helper.utils.SharedPref;
import com.aditp.mdvkarch.helper.utils.SpacesItemDecoration;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    private boolean isBackExit = false;
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
        MainPartialMethod method = new MainPartialMethod(this, binding);
        method.initNavigationMenu();
        String usernameSaved = SharedPref.getInstance().getString(CONSTANT.KEY_USERNAME, "abehbatre");
        binding.searchBar.searchText.setText(usernameSaved);

        // setup RecyclerView
        binding.rvList.setHasFixedSize(true);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.addItemDecoration(new SpacesItemDecoration(5));

    }

    @Override
    public void onActionComponent() {
        binding.swipeRefreshLayout.setRefreshing(true);
        binding.swipeRefreshLayout.post(this::updateUI);
        binding.swipeRefreshLayout.setOnRefreshListener(this::updateUI);

        // bt click
        binding.btnFab.setOnClickListener(v -> MDVKHelper.DIALOG_HELPER.showAboutDialog(this));
        binding.toolbarContainer.ivSelfie.setOnClickListener(view -> binding.drawerLayout.openDrawer(GravityCompat.START));

        // saerch repo
        binding.searchBar.searchText.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String cat = binding.searchBar.category.getText().toString();
                if (cat.equalsIgnoreCase("user")) getUserProfile(viewModel());
                getProjectList(viewModel());
                MDVKHelper.WINDOW_HELPER.forceCloseKeyboard(this, binding.searchBar.searchText);
                return true;
            }
            return false;
        });
    }

    private synchronized void updateUI() {
        // ui
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        binding.btnFab.setBackgroundColor(color1);
        binding.lytParent.setBackgroundColor(color1);
        binding.toolbarContainer.toolbarTitle.setText(getResources().getString(R.string.app_title_dashboard));
        YoYo.with(Techniques.SlideInUp).duration(1500).playOn(binding.toolbarContainer.toolbarTitle);
        YoYo.with(Techniques.SlideInUp).duration(1500).playOn(binding.toolbarContainer.ivSelfie);
        // set Data
        getUserProfile(viewModel());
        getProjectList(viewModel());

    }

    private synchronized void getProjectList(MainViewModel viewModel) {
        binding.rvList.showShimmerAdapter();
        String cat = binding.searchBar.category.getText().toString();
        String q = binding.searchBar.searchText.getText().toString();
        SharedPref.getInstance().saveString(CONSTANT.KEY_USERNAME, q);
        String finalSearch = cat;
        if (cat.isEmpty()) {
            finalSearch = "user";
        }
        viewModel.getSearchRepoObservable(this, finalSearch + ":" + q).observe(this, responseSearchRepositories -> {
            if (responseSearchRepositories != null) {
                binding.noItem.root.setVisibility(View.GONE);
                adapter = new MainAdapter(MainActivity.this, responseSearchRepositories.getItems());
                binding.rvList.setAdapter(adapter);
                // item adapter click
                adapter.setOnItemClick((view, obj, pos) -> {
                    Toast.makeText(MainActivity.this, obj.getLanguage(), Toast.LENGTH_SHORT).show();
                });
            } else { // <- show noData Layout
                binding.noItem.root.setVisibility(View.VISIBLE);
                binding.noItem.btnNoData.setOnClickListener(view -> updateUI());
            }
            binding.rvList.hideShimmerAdapter();
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    private synchronized void getUserProfile(MainViewModel viewModel) {
        String q = binding.searchBar.searchText.getText().toString();
        viewModel.getUserProfileObservable(this, q).observe(this, responseObject -> {
            binding.tvname.setText(responseObject.getName());
            binding.tvBio.setText(responseObject.getBio());
            GlideHelper.loadRound(this, responseObject.getAvatarUrl(), binding.toolbarContainer.ivSelfie);
            GlideHelper.loadRound(this, responseObject.getAvatarUrl(), binding.ivSelfie);
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (isBackExit) {
                super.onBackPressed();
                return;
            }
            this.isBackExit = true;
            Toast.makeText(this, "Klik sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> isBackExit = false, 2000);
        }
    }

}
