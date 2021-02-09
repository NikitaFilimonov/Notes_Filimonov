package com.example.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.notes.note.Note;

public class NoteExpandedFragment extends Fragment {

    public static final String ARG_NOTE = "agr_note";
    private Note note;

    public static com.example.notes.NoteExpandedFragment newInstance(Note note) {
        com.example.notes.NoteExpandedFragment fragment = new com.example.notes.NoteExpandedFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_expanded, container, false);

        TextView tvNoteName = view.findViewById(R.id.tv_name);
        tvNoteName.setText(note.getName());

        TextView tvNoteText = view.findViewById(R.id.tv_text);
        tvNoteText.setText(note.getText());

        DatePicker dpDateCreated = view.findViewById(R.id.dp_date_created);
        dpDateCreated.setMinDate(note.getDateCreated().getTime());
        dpDateCreated.setMaxDate(note.getDateCreated().getTime());

        return view;
    }
}