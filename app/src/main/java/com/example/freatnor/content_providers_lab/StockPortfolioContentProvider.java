package com.example.freatnor.content_providers_lab;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Jonathan Taylor on 8/9/16.
 */
public class StockPortfolioContentProvider extends ContentProvider {
    private StockPortfolioDatabaseHelper mHelper;

    public static final int STOCKS = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(StockPortfolioContract.AUTHORITY, StockPortfolioContract.StockPortfolio.TABLE_STOCKS, STOCKS);
    }


    @Override
    public boolean onCreate() {
        mHelper = StockPortfolioDatabaseHelper.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        int uriType = sUriMatcher.match(uri);
        Cursor cursor;
        switch (uriType){
            case STOCKS:
                cursor = mHelper.getStockPortfolio();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch(sUriMatcher.match(uri)){
        case STOCKS:
            return StockPortfolioContract.StockPortfolio.CONTENT_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        long id;
        switch (uriType){
            case STOCKS:
                id = mHelper.addStock(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(StockPortfolioContract.StockPortfolio.CONTENT_URI, id);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int deletedRows;
        switch (sUriMatcher.match(uri)){
            case STOCKS:
                deletedRows = mHelper.deleteStock(s, strings, null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return deletedRows;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
