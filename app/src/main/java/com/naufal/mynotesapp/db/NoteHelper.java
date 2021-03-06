package com.naufal.mynotesapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;

import com.naufal.mynotesapp.entity.Note;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.naufal.mynotesapp.db.DatabaseContract.NoteColumns.TABLE_NAME;
import static com.naufal.mynotesapp.db.DatabaseContract.NoteColumns.DATE;
import static com.naufal.mynotesapp.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.naufal.mynotesapp.db.DatabaseContract.NoteColumns.TITLE;

public class NoteHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static NoteHelper INSTANCE;

    private static SQLiteDatabase database;

    private NoteHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static NoteHelper getInstance(Context context){
        if(INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if(INSTANCE == null){
                    INSTANCE = new NoteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException{
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if(database.isOpen()){
            database.close();
        }
    }

    public Cursor queryAll(){
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC"
        );
    }

    public Cursor queryById(String id){
        return database.query(DATABASE_TABLE, null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }

    public long insert(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values){
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(String id){
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
