package com.example.travelnotes;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelnotes.db.DBHelper;
import com.example.travelnotes.model.Note;

public class EditNoteActivity extends AppCompatActivity {

    private EditText etPlaceName, etCity, etDescription, etVisitDate, etRating;
    private DBHelper dbHelper;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        etPlaceName = findViewById(R.id.etPlaceName);
        etCity = findViewById(R.id.etCity);
        etDescription = findViewById(R.id.etDescription);
        etVisitDate = findViewById(R.id.etVisitDate);
        etRating = findViewById(R.id.etRating);
        Button btnUpdate = findViewById(R.id.btnUpdate);

        dbHelper = new DBHelper(this);
        noteId = getIntent().getIntExtra("note_id", -1);

        Note note = dbHelper.getNoteById(noteId);
        if (note != null) {
            etPlaceName.setText(note.getPlaceName());
            etCity.setText(note.getCity());
            etDescription.setText(note.getDescription());
            etVisitDate.setText(note.getVisitDate());
            etRating.setText(String.valueOf(note.getRating()));
        }

        btnUpdate.setOnClickListener(v -> updateNote());
    }

    private void updateNote() {
        String placeName = etPlaceName.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String visitDate = etVisitDate.getText().toString().trim();
        String ratingStr = etRating.getText().toString().trim();

        if (TextUtils.isEmpty(placeName) || TextUtils.isEmpty(city) || TextUtils.isEmpty(visitDate) || TextUtils.isEmpty(ratingStr)) {
            Toast.makeText(this, "Заполни обязательные поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int rating;
        try {
            rating = Integer.parseInt(ratingStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Рейтинг должен быть числом", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.updateNote(noteId, placeName, city, description, visitDate, rating);
        Toast.makeText(this, "Заметка обновлена", Toast.LENGTH_SHORT).show();
        finish();
    }
}