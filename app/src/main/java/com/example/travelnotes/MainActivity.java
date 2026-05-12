package com.example.travelnotes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelnotes.adapter.NoteAdapter;
import com.example.travelnotes.db.DBHelper;
import com.example.travelnotes.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<Note> noteList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerNotes);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        dbHelper = new DBHelper(this);
        noteList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter(noteList, note -> {
            Intent intent = new Intent(MainActivity.this, NoteDetailsActivity.class);
            intent.putExtra("note_id", note.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        noteList.clear();
        noteList.addAll(dbHelper.getAllNotes());
        adapter.notifyDataSetChanged();
    }
}