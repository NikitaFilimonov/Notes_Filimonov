package com.example.notes.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.MainActivity;
import com.example.notes.Navigation;
import com.example.notes.R;
import com.example.notes.note.Note;
import com.example.notes.note.NotesSource;
import com.example.notes.note.NotesSourceFirebaseImpl;
import com.example.notes.note.NotesSourceImpl;
import com.example.notes.note.NotesSourceResponse;
import com.example.notes.observe.Observer;
import com.example.notes.observe.Publisher;

public class SocialNetworkFragment extends Fragment {
    private static final int MY_DEFAULT_DURATION = 1000;
    private NotesSource data;
    private SocialNetworkAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToFirstPosition;

    public static SocialNetworkFragment newInstance() {
        return new SocialNetworkFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initView(view);
        setHasOptionsMenu(true);
        data = new NotesSourceFirebaseImpl().init(new NotesSourceResponse() {
            public void initialized(NotesSource notes) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setNotesSource(data);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SocialNetworkAdapter(this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);

        if (moveToFirstPosition && data.size() > 0) {
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }
    }
//        adapter.SetOnItemClickListener(new SocialNetworkAdapter.OnItemClickListener()
//    {
//        @Override
//        public void onItemClick (View view,int position){
//        Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
//    }
//
//        @Override
//        public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
//
//    }
//    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }

    private boolean onItemSelected(int menuItemId){
        switch (menuItemId){
            case R.id.action_add:
                navigation.addFragment(NoteFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNote(Note note) {

                    }

                    @Override
                    public void updateCardData(Note note) {
                        data.addNote(note);
                        adapter.notifyItemInserted(data.size() - 1);
                        moveToFirstPosition = true;
                    }
                });
                return true;
            case R.id.action_update:
                final int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(NoteFragment.newInstance(data.getNote(updatePosition)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNote(Note note) {

                    }

                    @Override
                    public void updateCardData(Note note) {
                        data.updateNote(updatePosition, note);
                        adapter.notifyItemChanged(updatePosition);
                    }
                });
                return true;
            case R.id.action_delete:
                int deletePosition = adapter.getMenuPosition();
                data.deleteNote(deletePosition);
                adapter.notifyItemRemoved(deletePosition);
                return true;
            case R.id.action_clear:
                data.clearNote();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }
}
