package com.aditp.mdvkarch.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.data.response.ResponseProjectList;
import com.aditp.mdvkarch.databinding.ItemRepoBinding;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.onSetUpBindingComponent> {

    private Context context;
    private List<ResponseProjectList> items;
    private ItemRepoBinding binding;
    private int LAYOUT = R.layout.item_repo;
    private OnItemClickListener onItemClickListener;

    public MainAdapter(Context context) {
        this.context = context;
    }

    public MainAdapter(Context context, List<ResponseProjectList> items) {
        this.context = context;
        this.items = items;
    }

    // Trigger Technique
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public onSetUpBindingComponent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), LAYOUT, parent, false);
        return new onSetUpBindingComponent(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull onSetUpBindingComponent holder, final int position) {
        final ResponseProjectList items = this.items.get(position);
        holder.binding.tvRepoName.setText(items.getName());
        holder.binding.tvRepoDesc.setText(items.getDescription());


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getRandomColor();
        String firstWord = items.getName().substring(0, 1);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRect(firstWord, color);
        holder.binding.ivRepoImage.setImageDrawable(drawable);


        holder.binding.lytParent.setOnClickListener(view1 -> {
            MDVKHelper.DIALOG_TOOLS.showCustomDialog(context,
                    String.valueOf(items.getFullName()),
                    "Language : " + items.getLanguage() + "\n" +
                            "Star : " + items.getStargazersUrl(),
                    R.drawable.flag_question,
                    new MDVKHelper.ActionDialogListener() {
                        @Override
                        public void executeNo() {

                        }

                        @Override
                        public void executeYes() {

                        }
                    });
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<ResponseProjectList> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    // ------------------------------------------------------------------------
    // INTERFACE
    // ------------------------------------------------------------------------
    public interface OnItemClickListener {
        void onItemClick(View view, ResponseProjectList obj, int pos);
    }

    // ------------------------------------------------------------------------
    // INNER CLASS
    // ------------------------------------------------------------------------
    public class onSetUpBindingComponent extends RecyclerView.ViewHolder {
        public ItemRepoBinding binding;

        onSetUpBindingComponent(ItemRepoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}