package com.example.android.ambikacloth;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.android.ambikacloth.entryContract.FeedEntry;


public class DisplayActivity extends AppCompatActivity {


    public static  ArrayList<Entri> entries = new ArrayList<Entri>();
    public static final ArrayList<Entri> entries_rev = new ArrayList<Entri>();
    public EntryAdapter Adapter,Adapter2;
    public ListView listView;


    FeedEntryDbHelper mDbHelper = new FeedEntryDbHelper(this);

    public static Cursor c;
    public static int toggle = 0;

    public static Cursor getCursor() {
        return c;
    }

    public static void setCursor(Cursor a) {
        c = a;

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        entries.clear();


        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        final String[] projection = {
                FeedEntry.COLUMN_ID,
                FeedEntry.COLUMN_NAME,
                FeedEntry.COLUMNN_AMOUNT,
                FeedEntry.COLUMN_MOBILE,
                FeedEntry.COLUMN_ADDRESS,
                FeedEntry.COLUMN_DATE,
                FeedEntry.COLUMN_DETAILS,
        };


        final Cursor cursor = db.query(FeedEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            int custID = cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_ID));
            String custName = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME));
            String custAmount = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMNN_AMOUNT));
            String custMob = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_MOBILE));
            String custAdd = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_ADDRESS));
            String custDate = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_DATE));
            String custDetails = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_DETAILS));

            entries.add(new Entri(custID, custName, custAmount, custMob, custAdd, custDate, custDetails));

        }
        cursor.close();


        Adapter = new EntryAdapter(this, entries);



        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(Adapter);


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(toggle == 0){
                   toggle = 1;
                   entries_rev.clear();
                   for(int i = entries.size()-1;i>=0;i--){
                   entries_rev.add(entries.get(i));

                   //Adapter2 = new EntryAdapter(DisplayActivity.this,entries_rev);
                   //listView.setAdapter(Adapter2);
                   listAdapter(toggle);
                   }
               }
               else{
                   toggle = 0;
                   listAdapter(toggle);
               }
            }
        });






        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                getIntent().getData();

                Intent intent = new Intent(DisplayActivity.this, EntryActivity.class);
                Uri CurrentEntryUri = ContentUris.withAppendedId(Uri.parse("content://com.example.android.ambikacloth.FeedEntry"), l);
                intent.setData(CurrentEntryUri);

                TextView idTextView = (TextView) view.findViewById(R.id.custID_view);
                String id = idTextView.getText().toString().trim();

                String selection = FeedEntry.COLUMN_ID + " LIKE ?";

                String[] selectionArgs = {id};
                final Cursor cursor1 = db.query(FeedEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, null);

                setCursor(cursor1);


                startActivity(intent);
                finish();

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {


                TextView textView2 = (TextView) view.findViewById(R.id.custID_view);
                String id = textView2.getText().toString().trim();


                String selection = FeedEntry.COLUMN_ID + " LIKE ?";

                String[] selectionArgs = {id};

                db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);


                Toast.makeText(DisplayActivity.this,
                        "entry deleted", Toast.LENGTH_SHORT).show();

                finish();
                return true;

            }
        });


    }

    private void listAdapter(int toggle){
        if(toggle == 0){
            listView.setAdapter(Adapter);
        }
        else if(toggle == 1){
            Adapter2 = new EntryAdapter(this,entries_rev);
            listView.setAdapter(Adapter2);
        }
    }



}

