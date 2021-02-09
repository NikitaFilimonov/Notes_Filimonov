package com.example.notes.observe;

import com.example.notes.note.Note;

public interface Observer {
    void updateNote(Note note);

    void updateCardData(Note note);
}
