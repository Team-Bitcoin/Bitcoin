package com.example.myapplication;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ExampleItem {
    private int mImageResource;
    private String mName;
    private String mPrice;
    private String mMarketCap;



    public ExampleItem(int imageResource, String name,String price , String marketcap) {
        mImageResource = imageResource;
        mName = name;
        mPrice = price;
        mMarketCap = marketcap;
    }

    public void changeText(String text) {
        mName = text;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmName() {
        return mName;
    }

    public String getmPice() {
        return mPrice;
    }

    public String getmMarketCap() {
        return mMarketCap;
    }
}