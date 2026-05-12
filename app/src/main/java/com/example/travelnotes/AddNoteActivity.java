package com.example.travelnotes;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelnotes.db.DBHelper;

public class AddNoteActivity extends AppCompatActivity {

    private EditText etPlaceName, etCity, etDescription, etVisitDate, etRating;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etPlaceName = findViewById(R.id.etPlaceName);
        etCity = findViewById(R.id.etCity);
        etDescription = findViewById(R.id.etDescription);
        etVisitDate = findViewById(R.id.etVisitDate);
        etRating = findViewById(R.id.etRating);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnClear = findViewById(R.id.btnClear);

        dbHelper = new DBHelper(this);

        btnSave.setOnClickListener(v -> saveNote());
        btnClear.setOnClickListener(v -> clearFields());
    }

    private void saveNote() {
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

        dbHelper.addNote(placeName, city, description, visitDate, rating);
        Toast.makeText(this, "Заметка сохранена", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void clearFields() {
        etPlaceName.setText("");
        etCity.setText("");
        etDescription.setText("");
        etVisitDate.setText("");
        etRating.setText("");
    }
}