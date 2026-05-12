package com.example.travelnotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelnotes.db.DBHelper;
import com.example.travelnotes.model.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        TextView tvPlaceName = findViewById(R.id.tvDetailsPlaceName);
        TextView tvCity = findViewById(R.id.tvDetailsCity);
        TextView tvDescription = findViewById(R.id.tvDetailsDescription);
        TextView tvVisitDate = findViewById(R.id.tvDetailsVisitDate);
        TextView tvRating = findViewById(R.id.tvDetailsRating);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DBHelper(this);
        noteId = getIntent().getIntExtra("note_id", -1);

        Note note = dbHelper.getNoteById(noteId);

        if (note != null) {
            tvPlaceName.setText(note.getPlaceName());
            tvCity.setText(note.getCity());
            tvDescription.setText(note.getDescription());
            tvVisitDate.setText(note.getVisitDate());
            tvRating.setText("Рейтинг: " + note.getRating());
        }

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(NoteDetailsActivity.this, EditNoteActivity.class);
            intent.putExtra("note_id", noteId);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Удаление")
                .setMessage("Удалить заметку?")
                .setPositiveButton("Да", (dialog, which) -> {
                    dbHelper.deleteNote(noteId);
                    finish();
                })
                .setNegativeButton("Нет", null)
                .show());
    }
}