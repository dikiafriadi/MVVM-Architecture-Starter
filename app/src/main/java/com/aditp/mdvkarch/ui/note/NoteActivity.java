package com.aditp.mdvkarch.ui.note;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.MyActivity;
import com.aditp.mdvkarch.databinding.ActivityNoteBinding;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.aditp.mdvkarch.utils.SpacesItemDecoration;

public class NoteActivity extends MyActivity {
    ActivityNoteBinding binding;
    NoteViewModel noteViewModel;
    NoteAdapter adapter;
    int LAYOUT = R.layout.activity_note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, LAYOUT);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        initToolbar("Database Notes");
        // setup RecyclerView
        binding.rvList.setHasFixedSize(true);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.addItemDecoration(new SpacesItemDecoration(5));

        // setup adapter
        adapter = new NoteAdapter(this);
        binding.rvList.setAdapter(adapter);

        noteViewModel.getAllNotes().observe(this, notes -> {
            adapter.setNotes(notes);
            initToolbar("Database Notes (" + adapter.getItemCount() + ")");
        });

    }

    @Override
    public void onActionComponent() {
        adapter.setOnItemClickListener((view, obj, pos) -> {
            MDVKHelper.DIALOG_TOOLS.showCustomDialog(this,
                    "Priority" + obj.getPriority(),
                    "Title : " + obj.getTitle() + "\n" +
                            "Description : " + obj.getDescription(),
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
        binding.btnFab.setOnClickListener(view -> {
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
