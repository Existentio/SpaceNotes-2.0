package com.existentio.spacenotesmvc.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.existentio.spacenotesmvc.model.Notes;
import com.existentio.spacenotesmvc.ui.MainActivity;

import java.util.List;

/**
 * Created by Георгий on 19.09.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sn.db";
    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final int DB_VERSION = 2;

    private static final String CREATE_TABLE_NOTES =
            "CREATE TABLE " + TABLE_NOTES + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                    COLUMN_DATE + " TEXT NOT NULL)";

    private static final String SQL_DELETE = "DROP TABLE IF EXISTS ";

    private SQLiteDatabase db;
    private Context context;
    private List<Notes> notes = MainActivity.notes;
    private Cursor cursor;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
        this.context = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE + TABLE_NOTES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addNote(String description, String date) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_DATE, date);
        db.insert(TABLE_NOTES, null, cv);
    }

    public boolean delNote(long id) {
        return db.delete(TABLE_NOTES, COLUMN_ID + "=" + id, null) > 0;
    }

    public void updRec(String description, String date, long id) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_DESCRIPTION, description);
        args.put(COLUMN_DATE, date);
        db.update(TABLE_NOTES, args, COLUMN_ID + " = " + id, null);
    }

    public Cursor getAllItems(List<Notes> list) {
        if (list == notes) {
            String[] notesArray = {COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_DATE};
            cursor = db.query(
                    TABLE_NOTES,
                    notesArray,
                    null,
                    null,
                    null,
                    null,
                    null);
        }
        return cursor;
    }

    public List<Notes> getNotes(List<Notes> list) {
        try {
            list.clear();
            cursor = getAllItems(list);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String description = cursor.getString(1);
                String date = cursor.getString(2);
                Notes appInfo = new Notes(description, date, id);
                list.add(appInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

}
