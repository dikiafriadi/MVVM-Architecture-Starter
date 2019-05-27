package com.aditp.mdvkarch.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.aditp.mdvkarch.data.remote.Endpoint;
import com.aditp.mdvkarch.data.remote.RetrofitClient;
import com.aditp.mdvkarch.data.remote.api_response.ResponseArray;
import com.aditp.mdvkarch.data.remote.api_response.ResponseObject;
import com.aditp.mdvkarch.databinding.ActivityMainBinding;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.aditp.mdvkarch.helper.PicassoHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("WeakerAccess")
public class MainBL {
    private Context context;
    private ActivityMainBinding binding;
    private MainAdapter adapter;
    private List<ResponseArray> items;

    public MainBL(Context context, ActivityMainBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    public void getDataUsers(String username) {
        Dialog dialog = MDVKHelper.DIALOG_TOOLS.showProgressDialog(context, "Load Data User ..");
        dialog.show();
        dialog.setCancelable(false);
        Endpoint endpoint = RetrofitClient.getClient().create(Endpoint.class);
        Call<ResponseObject> call = endpoint.getUser(username);
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(@NonNull Call<ResponseObject> call, @NonNull Response<ResponseObject> response) {
                if (response.isSuccessful()) {
                    binding.tvname.setText(response.body().getName());
                    binding.tvCompany.setText(response.body().getCompany());
                    binding.tvBio.setText(response.body().getBio());
                    PicassoHelper.load(response.body().getAvatarUrl(), binding.ivSelfie);
                }
                binding.swipeRefreshLayout.setRefreshing(false);
                dialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.swipeRefreshLayout.setRefreshing(false);
                dialog.dismiss();
            }
        });
    }

    public void getDataUserRepos(String username) {
        Dialog dialog = MDVKHelper.DIALOG_TOOLS.showProgressDialog(context, "Load Repo User ..");
        dialog.show();
        dialog.setCancelable(false);
        Endpoint endpoint = RetrofitClient.getClient().create(Endpoint.class);
        Call<List<ResponseArray>> call = endpoint.listRepos(username);
        call.enqueue(new Callback<List<ResponseArray>>() {
            @Override
            public void onResponse(Call<List<ResponseArray>> call, Response<List<ResponseArray>> response) {
                if (response.isSuccessful()) {
                    items = response.body();
                    adapter = new MainAdapter(context, items);
                    binding.rvList.setAdapter(adapter);
                }
                binding.swipeRefreshLayout.setRefreshing(false);
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<ResponseArray>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.swipeRefreshLayout.setRefreshing(false);
                dialog.dismiss();
            }
        });
    }

}
