package com.example.travelnotes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travelnotes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "travel_notes.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NOTES = "notes";

    public static final String COL_ID = "id";
    public static final String COL_PLACE_NAME = "place_name";
    public static final String COL_CITY = "city";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_VISIT_DATE = "visit_date";
    public static final String COL_RATING = "rating";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NOTES + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_PLACE_NAME + " TEXT, "
                + COL_CITY + " TEXT, "
                + COL_DESCRIPTION + " TEXT, "
                + COL_VISIT_DATE + " TEXT, "
                + COL_RATING + " INTEGER"
                + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public void addNote(String placeName, String city, String description, String visitDate, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PLACE_NAME, placeName);
        values.put(COL_CITY, city);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_VISIT_DATE, visitDate);
        values.put(COL_RATING, rating);
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES + " ORDER BY " + COL_ID + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String placeName = cursor.getString(cursor.getColumnIndexOrThrow(COL_PLACE_NAME));
                String city = cursor.getString(cursor.getColumnIndexOrThrow(COL_CITY));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION));
                String visitDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_VISIT_DATE));
                int rating = cursor.getInt(cursor.getColumnIndexOrThrow(COL_RATING));

                noteList.add(new Note(id, placeName, city, description, visitDate, rating));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return noteList;
    }

    public Note getNoteById(int noteId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NOTES + " WHERE " + COL_ID + "=?",
                new String[]{String.valueOf(noteId)}
        );

        Note note = null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
            String placeName = cursor.getString(cursor.getColumnIndexOrThrow(COL_PLACE_NAME));
            String city = cursor.getString(cursor.getColumnIndexOrThrow(COL_CITY));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION));
            String visitDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_VISIT_DATE));
            int rating = cursor.getInt(cursor.getColumnIndexOrThrow(COL_RATING));

            note = new Note(id, placeName, city, description, visitDate, rating);
        }

        cursor.close();
        db.close();
        return note;
    }

    public void updateNote(int id, String placeName, String city, String description, String visitDate, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_PLACE_NAME, placeName);
        values.put(COL_CITY, city);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_VISIT_DATE, visitDate);
        values.put(COL_RATING, rating);

        db.update(TABLE_NOTES, values, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}