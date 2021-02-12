package com.example.notes.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.note.Note;
import com.example.notes.note.NotesSource;

import java.text.SimpleDateFormat;

public class SocialNetworkAdapter
        extends RecyclerView.Adapter<SocialNetworkAdapter.ViewHolder> {

    private final static String TAG = "SocialNetworkAdapter";
    private NotesSource notesSource;
    private final Fragment fragment;
    private AdapterView.OnItemClickListener itemClickListener;  // Слушатель будет устанавливаться извне
    private int menuPosition;

    public SocialNetworkAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setNotesSource(NotesSource notesSource){
        this.notesSource = notesSource;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SocialNetworkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                              int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SocialNetworkAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setData(notesSource.getNote(i));
        Log.d(TAG, "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return notesSource.size();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    public interface OnItemClickListener extends AdapterView.OnItemClickListener {
        void onItemClick(View view, int position);

        @Override
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView text;
        private CheckBox like;
        private TextView date;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            text = itemView.findViewById(R.id.tv_text);
            like = itemView.findViewById(R.id.like);
            date = itemView.findViewById(R.id.tv_date_created);

            registerContextMenu(itemView);
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(v -> {
                    menuPosition = getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public void setData(Note note) {
            name.setText(note.getName());
            text.setText(note.getText());
            like.setChecked(note.isLike());
            date.setText(new SimpleDateFormat("dd-MM-yy").format(note.getDateCrated()));
        }
    }
}


