package com.example.android.ambikacloth;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import static com.example.android.ambikacloth.DisplayActivity.getCursor;
import com.example.android.ambikacloth.entryContract.FeedEntry;
public class EntryActivity extends AppCompatActivity {

    public static int flag;
    FeedEntryDbHelper mdbHelper = new FeedEntryDbHelper(this);
    SQLiteDatabase db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_delete: {
                deleteEntry();
                return true;
            }

            case R.id.action_save: {
                saveEntry();
                return true;
            }

            case R.id.action_dial: {
                EditText phoneNo = (EditText)findViewById(R.id.mob_field);
                String mobileNo = phoneNo.getText().toString().trim();
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+mobileNo));
                startActivity(dialIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        Intent intent = getIntent();
        Uri CurrentEntryUri = intent.getData();

        if (CurrentEntryUri == null) {
            flag = 0;
            setTitle("Add New Entry");
        } else {
            flag = 1;
            setTitle("Edit Entry");

            Cursor crsr = getCursor();
            crsr.moveToFirst();
            int custID = crsr.getInt(crsr.getColumnIndexOrThrow(FeedEntry.COLUMN_ID));
            String custName = crsr.getString(crsr.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME));
            String custAmount = crsr.getString(crsr.getColumnIndexOrThrow(FeedEntry.COLUMNN_AMOUNT));
            String custMob = crsr.getString(crsr.getColumnIndexOrThrow(FeedEntry.COLUMN_MOBILE));
            String custAdd = crsr.getString(crsr.getColumnIndexOrThrow(FeedEntry.COLUMN_ADDRESS));
            String custDate = crsr.getString(crsr.getColumnIndexOrThrow(FeedEntry.COLUMN_DATE));
            String custDetails = crsr.getString(crsr.getColumnIndexOrThrow(FeedEntry.COLUMN_DETAILS));

            EditText mNameText = (EditText) findViewById(R.id.name_field);
            EditText mAmountText = (EditText) findViewById(R.id.amount_field);
            EditText mMobileText = (EditText) findViewById(R.id.mob_field);
            EditText mAddressText = (EditText) findViewById(R.id.add_field);
            EditText mDetailsText = (EditText) findViewById(R.id.details_text);

            mNameText.setText(custName, TextView.BufferType.EDITABLE);
            mAmountText.setText(custAmount, TextView.BufferType.EDITABLE);
            mMobileText.setText(custMob, TextView.BufferType.EDITABLE);
            mAddressText.setText(custAdd, TextView.BufferType.EDITABLE);
            mDetailsText.setText(custDetails, TextView.BufferType.EDITABLE);

            crsr.close();


        }




        Button clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameText = (EditText) findViewById(R.id.name_field);
                nameText.setText("");

                EditText amountText = (EditText) findViewById(R.id.amount_field);
                amountText.setText("");

                EditText mobText = (EditText) findViewById(R.id.mob_field);
                mobText.setText("");

                EditText addText = (EditText) findViewById(R.id.add_field);
                addText.setText("");

                EditText detailText = (EditText) findViewById(R.id.details_text);
                detailText.setText("");


            }
        });

        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveEntry();


            }
        });

        Button displayButton = (Button) findViewById(R.id.display_button);
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(EntryActivity.this, DisplayActivity.class);
                startActivity(intent3);
            }
        });
    }

    private void saveEntry() {

        EditText nameText = (EditText) findViewById(R.id.name_field);
        String name = nameText.getText().toString();

        EditText amountText = (EditText) findViewById(R.id.amount_field);
        String amount = amountText.getText().toString();

        EditText mobText = (EditText) findViewById(R.id.mob_field);
        String mobile = mobText.getText().toString();

        EditText addText = (EditText) findViewById(R.id.add_field);
        String address = addText.getText().toString();

        EditText detailsText = (EditText) findViewById(R.id.details_text);
        String details = detailsText.getText().toString();

        db = mdbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME, name);
        values.put(FeedEntry.COLUMNN_AMOUNT, amount);
        values.put(FeedEntry.COLUMN_MOBILE, mobile);
        values.put(FeedEntry.COLUMN_ADDRESS, address);
        values.put(FeedEntry.COLUMN_DETAILS,details);

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        values.put(FeedEntry.COLUMN_DATE, date);

        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);

        if (flag == 1) {
            final String[] projection = {
                    FeedEntry.COLUMN_ID
            };

            final Cursor cursor = db.query(FeedEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null);

            cursor.moveToLast();
            int custID = cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_ID));

            String selection = FeedEntry.COLUMN_ID + " LIKE ?";
            int cust = custID - 1;

            String[] selectionArgs = {Integer.toString(cust)};

            db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);


            Toast t = Toast.makeText(EntryActivity.this, "Entries Updated", Toast.LENGTH_SHORT);
            t.show();
            cursor.close();

        } else {


            Toast t = Toast.makeText(EntryActivity.this, "Entries Saved", Toast.LENGTH_SHORT);
            t.show();
        }

        finish();

    }

    private void deleteEntry() {

        EditText nameText = (EditText) findViewById(R.id.name_field);
        String name = nameText.getText().toString().trim();

        EditText amountText = (EditText) findViewById(R.id.amount_field);
        String amount = amountText.getText().toString().trim();

        db = mdbHelper.getWritableDatabase();
        final String projection[] = {FeedEntry.COLUMN_ID, FeedEntry.COLUMN_NAME, FeedEntry.COLUMNN_AMOUNT};
        String selection = FeedEntry.COLUMN_NAME + " LIKE ?";
        String selectionArgsName[] = {name};

        final Cursor cursorDel = db.query(FeedEntry.TABLE_NAME, projection, selection, selectionArgsName,
                null, null, null);
        cursorDel.moveToFirst();
        while (true) {
            String nameFromCursor = cursorDel.getString(cursorDel.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME));
            if (nameFromCursor.equals(name)) {
                break;
            }
            cursorDel.moveToNext();
        }
        int CustID = cursorDel.getInt(cursorDel.getColumnIndexOrThrow(FeedEntry.COLUMN_ID));
        String selectionDel = FeedEntry.COLUMN_ID + " LIKE ?";
        String selectionArgsToDelete[] = {Integer.toString(CustID)};

        db.delete(FeedEntry.TABLE_NAME, selectionDel, selectionArgsToDelete);

        Toast t = Toast.makeText(this, "Entry Deleted", Toast.LENGTH_SHORT);
        t.show();
        cursorDel.close();
        finish();
    }


}




