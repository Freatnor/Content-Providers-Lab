package com.example.freatnor.content_providers_lab;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.example.freatnor.content_providers_lab.interfaces.OnStockInsertedListener;

/**
 * Created by Jonathan Taylor on 8/14/16.
 */
public class StockCursorLoader extends CursorLoader {

    private OnStockInsertedListener mListener;
    private Cursor mCursor;

    public StockCursorLoader(Context context, Uri uri, OnStockInsertedListener listener) {
        super(context, uri, null, null, null, null);
        mListener = listener;
    }


    @Override
    protected void onStopLoading() {
        mListener.stockInserted();
        super.onStopLoading();
    }
}
