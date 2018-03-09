package com.example.android.ambikacloth;

import android.provider.BaseColumns;

/**
 * Created by lucifer on 28/8/17.
 */

public class entryContract {

    private entryContract(){}

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "new_entry";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ID = "cust_id";
        public static final String COLUMN_MOBILE = "mobile";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMNN_AMOUNT = "amount";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DETAILS = "details";
    }
}
