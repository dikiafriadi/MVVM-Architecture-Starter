package com.aditp.mdvkarch.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.data.remote.api_response.ResponseProjectList;
import com.aditp.mdvkarch.databinding.ItemRepoBinding;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.onBindingViewHolder> {
    private Context context;
    private List<ResponseProjectList> items;

    private setOnItemClick onItemClick;


    public void setOnItemClick(final setOnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


    public MainAdapter(Context context, List<ResponseProjectList> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public onBindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int LAYOUT = R.layout.item_repo;
        ItemRepoBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), LAYOUT, parent, false);
        return new onBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull onBindingViewHolder holder, int position) {
        final ResponseProjectList obj = this.items.get(position);
        holder.binding.tvRepoName.setText(obj.getName());
        holder.binding.tvRepoDesc.setText(obj.getDescription());


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getRandomColor();
        String firstWord = obj.getName().substring(0, 1);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRect(firstWord, color);
        holder.binding.ivRepoImage.setImageDrawable(drawable);


        holder.binding.lytParent.setOnClickListener(view1 -> {
            if (onItemClick != null) {
                onItemClick.onClicked(view1, obj, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ------------------------------------------------------------------------
    // INNER CLASS
    // ------------------------------------------------------------------------
    public class onBindingViewHolder extends RecyclerView.ViewHolder {
        ItemRepoBinding binding;

        public onBindingViewHolder(ItemRepoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    // ------------------------------------------------------------------------
    // LISTENER
    // ------------------------------------------------------------------------
    public interface setOnItemClick {
        void onClicked(View view, ResponseProjectList obj, int pos);
    }
}