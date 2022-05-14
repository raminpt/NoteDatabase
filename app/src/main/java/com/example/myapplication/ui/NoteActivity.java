package com.example.myapplication.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RecyclerViewAdapter;
import com.example.myapplication.dao.NoteStore;
import com.example.myapplication.database.NoteDatabase;
import com.example.myapplication.entity.Note;
import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserNotes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = "database";

    NoteDatabase noteDatabase;
    NoteStore noteStore;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    Bundle bundle;
    User user;
    TextView usernameTextView;
    ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        bundle = getIntent().getExtras();
        user = bundle.getParcelable("user");

        init();
        loadAllNotes(user);
        usernameTextView.setText(user.getUsername());

        adapter = new RecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(v -> {
            LayoutInflater inflater = getLayoutInflater();
            View textBox = inflater.inflate(R.layout.text_box_layout, null);
            EditText editTextBox = textBox.findViewById(R.id.edit_text_note);
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setView(textBox)
                    .setPositiveButton("OK", (dialog, which) -> {
                        Note note = new Note(editTextBox.getText().toString(), user.getId());
                        saveNote(note);
                    })
                    .setNegativeButton("CANCEL", null)
                    .create()
                    .show();
        });

        profileImageView.setOnClickListener(v -> {
            Intent intent = new Intent(NoteActivity.this, ProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
    }

    public void init() {
        noteDatabase = NoteDatabase.getNoteDatabase(this);
        Log.d(TAG, "database instance in NoteActivity" + noteDatabase);
        noteStore = noteDatabase.noteStore();
        floatingActionButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler_view);
        usernameTextView = findViewById(R.id.text_username);
        profileImageView = findViewById(R.id.image_view_profile);
    }

    public void saveNote(Note note) {
        Completable insertNote = noteStore.insertNote(note);
        insertNote.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                            adapter.clearData();
                            loadAllNotes(user);
                        },
                        throwable -> Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void loadAllNotes(User user) {
        Single<UserNotes> userNotesSingle = noteStore.loadNotes(user.getId());
        userNotesSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userNotes -> {
                    adapter.setData(userNotes.noteList);
                });
    }

}