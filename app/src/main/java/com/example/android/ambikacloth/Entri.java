package com.example.android.ambikacloth;

import java.util.ArrayList;

import static android.R.attr.entries;

/**
 * Created by lucifer on 18/8/17.
 */

public class Entri {

    private String mName, mAddress,mAmount,mMobile,mDate,mDetails;
    private int mCustID;


    public Entri(int CustID, String name, String amount, String mobile, String address, String date,String details) {
        mName = name;
        mAmount = amount;
        mMobile = mobile;
        mAddress = address;
        mDate = date;
        mCustID = CustID;
        mDetails = details;

    }

    String getmName() {
        return mName;
    }

    String getmAmount() {
        return mAmount;
    }

    String getmMobile() {
        return mMobile;
    }

    String getmAddress() {
        return mAddress;
    }

    String getmDate(){return  mDate;}

    String getmDetails(){return mDetails;}

    int getmCustID(){return  mCustID;}


}
