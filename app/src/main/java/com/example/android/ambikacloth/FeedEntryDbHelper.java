package com.example.android.ambikacloth;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.ambikacloth.entryContract.FeedEntry;

/**
 * Created by lucifer on 28/8/17.
 */

public class FeedEntryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedEntry.db";

    public FeedEntryDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+ FeedEntry.TABLE_NAME +"(" +
            FeedEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            FeedEntry.COLUMN_NAME + " TEXT NOT NULL, "+
            FeedEntry.COLUMNN_AMOUNT +" TEXT NOT NULL, "+
            FeedEntry.COLUMN_MOBILE +" TEXT, "+
            FeedEntry.COLUMN_ADDRESS +" TEXT, "+
            FeedEntry.COLUMN_DATE + " TEXT, "+
            FeedEntry.COLUMN_DETAILS + " TEXT);";


    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {




    }
}
