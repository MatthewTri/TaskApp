package c14220080.example.taskapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "TaskApp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_tasks";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "task_name";
    private static final String COLUMN_DESCRIPTION = "task_description";
    private static final String COLUMN_DATE = "task_date";
    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT);";

//        String query = "CREATE TABLE " + TABLE_NAME +
//                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_NAME + " TEXT, " +
//                COLUMN_DESCRIPTION + " TEXT, " +
//                COLUMN_DATE + " TEXT, " +
//                "is_started INTEGER DEFAULT 0);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add a task to the database
    void addTask (String name, String description, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME , name);
        cv.put(COLUMN_DESCRIPTION , description);
        cv.put(COLUMN_DATE , date);
//        cv.put("is_started", 0); // Nilai default saat task ditambahkan
        long result = db.insert(TABLE_NAME, null, cv);

        if(result == -1) { // -1 itu kalo fail insert
            Toast.makeText(context, "Failed to insert data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully inserted data", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    void updateData(String row_id, String name, String date, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to update data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated data", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to delete data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted data", Toast.LENGTH_SHORT).show();
        }
    }

//    public void updateTaskStatus(String taskId, boolean isStarted) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("is_started", isStarted ? 1 : 0); // Simpan 1 untuk "started", 0 untuk "not started"
//        db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{taskId});
//    }





}
