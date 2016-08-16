package com.example.freatnor.content_providers_lab.interfaces;

import android.database.Cursor;

/**
 * Created by Jonathan Taylor on 8/14/16.
 */
public interface OnStockInsertedListener {
    void stockInserted(Cursor cursor);
}
