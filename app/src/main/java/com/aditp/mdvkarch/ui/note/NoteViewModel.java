package com.aditp.mdvkarch.ui.note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aditp.mdvkarch.data.local.NoteRepository;
import com.aditp.mdvkarch.data.local.Note;
import com.aditp.mdvkarch.data.local.NoteDao;

import java.util.List;


public class NoteViewModel extends AndroidViewModel implements NoteDao {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }


    @Override
    public void insert(Note note) {
        repository.insert(note);
    }

    @Override
    public void update(Note note) {
        repository.update(note);
    }

    @Override
    public void delete(Note note) {
        repository.delete(note);
    }

    @Override
    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    @Override
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}