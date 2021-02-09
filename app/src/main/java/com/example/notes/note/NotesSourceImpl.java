package com.example.notes.note;

import android.content.res.Resources;

import com.example.notes.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotesSourceImpl implements NotesSource {
    private List<Note> notesSource;
    private Resources resources;

    public NotesSourceImpl(Resources resources) {
        notesSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public NotesSourceImpl init() {
        String[] names = resources.getStringArray(R.array.names);
        // строки описаний из ресурсов
        String[] texts = resources.getStringArray(R.array.texts);
        // изображения
        for (int i = 0; i < texts.length; i++) {
            notesSource.add(new Note(names[i], texts[i], false, Calendar.getInstance().getTime()));
        }
        return this;
    }

    public Note getNote(int position) {
        return notesSource.get(position);
    }

    public int size() {
        return notesSource.size();
    }

    @Override
    public void deleteNote(int position) {
        notesSource.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        notesSource.set(position, note);
    }

    @Override
    public void addNote(Note note) {
        notesSource.add(note);
    }

    @Override
    public void clearNote() {
        notesSource.clear();
    }
}





