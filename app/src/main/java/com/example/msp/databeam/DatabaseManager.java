package com.example.msp.databeam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by MSP on 12/25/2016.
 */
public class DatabaseManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Databeam.db";
    public static final String TABLE_NAME = "Databeam_History";
    public static final String COL1 = "THUMBNAIL";
    public static final String COL2 = "NAME";
    public static final String COL3 = "SIZE";


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("databasemanager123", "constructor");
        //Toast.makeText(context, "DB CREATED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(THUMBNAIL BLOB, NAME TEXT, SIZE TEXT)");
        Log.d("databasemanager123", "oncreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.d("databasemanager123", "onupgrade");
    }

    public boolean insertData(byte thumbnail, String name, String size) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, thumbnail);
        contentValues.put(COL2, name);
        contentValues.put(COL3, size);
        Long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME, null);
        return res;
    }

    public int deleteData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("DBMANAGER", "onfo");
        return db.delete(TABLE_NAME, "NAME = ?", new String[]{name});
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

}
