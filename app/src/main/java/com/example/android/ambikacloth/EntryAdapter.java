package com.example.android.ambikacloth;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lucifer on 19/8/17.
 */

public class EntryAdapter extends ArrayAdapter<Entri> {
    private static final String LOG_TAG = EntryAdapter.class.getSimpleName();

    public EntryAdapter(Activity context, ArrayList<Entri> entries) {
        super(context, 0, entries);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        final Entri currentEntry = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameField = (TextView) listItemView.findViewById(R.id.set_text);
        nameField.setText(currentEntry.getmName());

        TextView amountField = (TextView) listItemView.findViewById(R.id.set_amount);
        amountField.setText("₹ " + currentEntry.getmAmount());

        TextView mobField = (TextView) listItemView.findViewById(R.id.set_mobile);
        mobField.setText(currentEntry.getmMobile());

        TextView addField = (TextView) listItemView.findViewById(R.id.set_address);
        addField.setText(currentEntry.getmAddress());

        TextView dateField = (TextView) listItemView.findViewById(R.id.date_field);
        dateField.setText(currentEntry.getmDate());


        TextView id = (TextView) listItemView.findViewById(R.id.custID_view);
        id.setText(Integer.toString(currentEntry.getmCustID()).trim());


        return listItemView;
    }
}
