package com.example.baby.firstgame.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denise on 16.05.2017.
 */

public class DatabaseStorage extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Inventory.db";
    public static final String TABLE_NAME = "ItemObjects";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_QNTY = "QNTY";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_QNTY + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public DatabaseStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {/*do nothing*/ }

    //---------------------------------------------------------------------------------------
    public void insertData(ItemObject item){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();
    // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, item.getId());
        values.put(COLUMN_NAME, item.getImage());
        values.put(COLUMN_QNTY, item.getQuanity());

    // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, values);
    }
    public void getData(String srcArg){
        SQLiteDatabase db = getReadableDatabase();
    // Define a projection that specifies which columns from the database
    // you will actually use after this query.
        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_QNTY
        };
    // Filter results WHERE 'name' = searchArgument
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = { srcArg };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                COLUMN_ID + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(COLUMN_ID));
            itemIds.add(itemId);
        }
        cursor.close();

    }
    public void removeData(String srcArg){
        SQLiteDatabase db = getReadableDatabase();
        // Define 'where' part of query.
        String selection = COLUMN_NAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { srcArg };
        // Issue SQL statement.
        db.delete(TABLE_NAME, selection, selectionArgs);

    }
    public void updateData(String srcArg, String newValue) {
        SQLiteDatabase db = getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newValue);

        // Which row to update, based on the title
        String selection = COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = {srcArg};

        int count = db.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


}