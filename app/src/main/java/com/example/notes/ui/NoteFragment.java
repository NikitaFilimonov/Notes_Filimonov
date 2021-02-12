package com.example.notes.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.note.Note;
import com.example.notes.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class NoteFragment extends Fragment {

    private boolean isLandscape;
    private Note currentNote;
    private Publisher publisher;
    private static final String ARG_NOTE = "Param_Note";

    private TextInputEditText name;
    private TextInputEditText text;
    private DatePicker datePicker;

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentNote = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_expanded, container, false);
        initView(view);
        if (currentNote != null) {
            populateView();
        }
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        currentNote = collectNote();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(currentNote);
    }

    private Note collectNote() {
        String name = this.name.getText().toString();
        String text = this.text.getText().toString();
        Date date = getDateFromDatePicker();
//        boolean like;
        if (currentNote != null) {
            Note answer;
            answer = new Note(name, text, currentNote.isLike(), date);
            answer.setId(currentNote.getId());
            return answer;

//            like = currentNote.isLike();
        } else {
//            like = false;
            return new Note(name, text,false, date);

        }
//        return new Note(name, text, like, date);
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    private void initView(View view) {
        name = view.findViewById(R.id.inputName);
        text = view.findViewById(R.id.inputText);
        datePicker = view.findViewById(R.id.inputDate);
    }

    private void populateView() {
        name.setText(currentNote.getName());
        text.setText(currentNote.getText());
        initDatePicker(currentNote.getDateCreated());
    }

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

}


//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
//
//        if (savedInstanceState != null) {
//
//            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
//        } else {
//            if (notes != null && notes.size() > 0) {
//                currentNote = notes.get(0);
//            }
//        }
//
//        if (isLandscape) {
//            showNoteDetails(currentNote);
//        }
//    }
//
//    private void showNoteDetails(Note currentNote) {
//        if (isLandscape) {
//            showLandNoteDetails(currentNote);
//        } else {
//            showPortNoteDetails(currentNote);
//        }
//
//    }
//
//    private void showLandNoteDetails(Note currentNote) {
//        if (currentNote != null) {
//            com.example.notes.NoteExpandedFragment noteExpandedFragment = com.example.notes.NoteExpandedFragment.newInstance(currentNote);
//            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.note_expanded, noteExpandedFragment);  // замена фрагмента
//            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            fragmentTransaction.commit();
//        }
//    }
//
//
//    private void showPortNoteDetails(Note currentNote) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), NoteExpandedActivity.class);
//        intent.putExtra(com.example.notes.NoteExpandedFragment.ARG_NOTE, currentNote);
//        startActivity(intent);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_note, container, false);
//    }
//}


