package com.aditp.mdvkarch.ui.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.data.local.note.Note;
import com.aditp.mdvkarch.databinding.ItemRepoBinding;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.onSetUpBindingComponent> {

    private Context context;
    private List<Note> notes = new ArrayList<>();
    private ItemRepoBinding binding;
    private int LAYOUT = R.layout.item_repo;
    private OnItemClickListener onItemClickListener;


    public NoteAdapter(Context context) {
        this.context = context;
    }

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
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
        final Note items = this.notes.get(position);
        holder.binding.tvRepoName.setText(items.getTitle());
        holder.binding.tvRepoDesc.setText(items.getDescription());


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getRandomColor();
        String firstWord = items.getTitle().substring(0, 1);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRect(firstWord, color);
        holder.binding.ivRepoImage.setImageDrawable(drawable);


        holder.binding.lytParent.setOnClickListener(view1 -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view1, this.notes.get(position), position);
            }

        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return notes.get(position);
    }

    // ------------------------------------------------------------------------
    // INTERFACE
    // ------------------------------------------------------------------------
    public interface OnItemClickListener {
        void onItemClick(View view, Note obj, int pos);
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