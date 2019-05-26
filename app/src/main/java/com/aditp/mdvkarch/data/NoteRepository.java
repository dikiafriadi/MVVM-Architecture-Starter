package com.aditp.mdvkarch.data;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.aditp.mdvkarch.data.local.Note;
import com.aditp.mdvkarch.data.local.NoteDao;
import com.aditp.mdvkarch.data.local.NoteDatabase;

import java.util.List;

public class NoteRepository implements NoteDao {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    @Override
    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    @Override
    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    @Override
    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    @Override
    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    @Override
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }


    // ------------------------------------------------------------------------
    // INNER CLASS
    // ------------------------------------------------------------------------
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}