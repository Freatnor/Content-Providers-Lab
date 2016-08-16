package com.example.freatnor.content_providers_lab;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.UiThread;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.example.freatnor.content_providers_lab.interfaces.OnStockInsertedListener;

/**
 * Created by Jonathan Taylor on 8/14/16.
 */
public class StockCursorLoader extends CursorLoader {
    private static final String TAG = "StockCursorLoader";


    public StockCursorLoader(Context context, Uri uri) {
        super(context, uri, null, null, null, null);
    }


}
