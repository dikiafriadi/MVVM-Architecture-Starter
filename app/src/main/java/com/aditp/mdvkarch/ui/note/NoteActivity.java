package com.aditp.mdvkarch.ui.note;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.core.BaseActivity;
import com.aditp.mdvkarch.data.local.Note;
import com.aditp.mdvkarch.databinding.ActivityNoteBinding;
import com.aditp.mdvkarch.databinding.DialogAddNoteBinding;
import com.aditp.mdvkarch.helper.MDVKHelper;
import com.aditp.mdvkarch.helper.utils.SpacesItemDecoration;

import java.util.Objects;

public class NoteActivity extends BaseActivity<ActivityNoteBinding, NoteViewModel> {
    // @Inject
    NoteViewModel noteViewModel;
    NoteAdapter adapter;

    @Override
    public int LAYOUT() {
        return R.layout.activity_note;
    }

    @Override
    public void setBLClass() {
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar("Database Notes");

        // setup RecyclerView
        binding.rvList.setHasFixedSize(true);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.addItemDecoration(new SpacesItemDecoration(5));

        // setup adapter
        adapter = new NoteAdapter(this);
        binding.rvList.setAdapter(adapter);

    }

    private void showAddNoteDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        DialogAddNoteBinding binding;
        int LAYOUT = R.layout.dialog_add_note;

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), LAYOUT, null, false);
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        binding.btnYes.setText(getString(R.string.add));
        binding.btnNo.setText("Delete All Note");

        binding.btnYes.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString();
            String desc = binding.etDesc.getText().toString();
            if ((title.isEmpty()) || (desc.isEmpty())) {
                Toast.makeText(context, "Field still empty", Toast.LENGTH_SHORT).show();
            } else {
                noteViewModel.insert(new Note(title, desc, 100));
            }
            dialog.dismiss();
        });

        binding.btnNo.setOnClickListener(v -> {
            noteViewModel.deleteAllNotes();
            dialog.dismiss();
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    @Override
    public void onActionComponent() {
        noteViewModel.getAllNotes().observe(this, notes -> {
            adapter.setNotes(notes);
            initToolbar("Database Notes (" + adapter.getItemCount() + ")");
        });

        binding.btnFab.setOnClickListener(view -> showAddNoteDialog(this));
        adapter.setOnItemClickListener((view, obj, pos) -> {
            MDVKHelper.DIALOG_TOOLS.showCustomDialog(this,
                    "Delete Note " + obj.getTitle(),
                    "Title : " + obj.getDescription() + "\n" +
                            "Description : " + obj.getPriority(),
                    R.drawable.flag_question,
                    new MDVKHelper.ActionDialogListener() {
                        @Override
                        public void executeNo() {

                        }

                        @Override
                        public void executeYes() {
                            noteViewModel.delete(adapter.getNoteAt(pos));
                        }
                    });
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
