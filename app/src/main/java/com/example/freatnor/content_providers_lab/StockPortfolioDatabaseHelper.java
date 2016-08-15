package com.example.freatnor.content_providers_lab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Jonathan Taylor on 8/9/16.
 */
public class StockPortfolioDatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "stockportfolioDB.db";

    public static final String TABLE_STOCKS = StockPortfolioContract.StockPortfolio.TABLE_STOCKS;
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_SYMBOL = StockPortfolioContract.StockPortfolio.COLUMN_SYMBOL;
    public static final String COLUMN_COMPANY_NAME = StockPortfolioContract.StockPortfolio.COLUMN_COMPANY_NAME;
    public static final String COLUMN_QUANTITY = StockPortfolioContract.StockPortfolio.COLUMN_QUANTITY;
    public static final String COLUMN_EXCHANGE_NAME = StockPortfolioContract.StockPortfolio.COLUMN_EXCHANGE_NAME;

    private static StockPortfolioDatabaseHelper mInstance;

    public static StockPortfolioDatabaseHelper getInstance(Context context) {
        if(mInstance == null){
            mInstance = new StockPortfolioDatabaseHelper(context);
        }
        return mInstance;
    }

    private StockPortfolioDatabaseHelper(Context context){
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE "
                + TABLE_STOCKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_SYMBOL + " TEXT,"
                + COLUMN_COMPANY_NAME + " TEXT,"
                + COLUMN_QUANTITY + " INTEGER,"
                + COLUMN_EXCHANGE_NAME + " TEXT"+ ")";
        sqLiteDatabase.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCKS);
        onCreate(sqLiteDatabase);
    }

    public long addStock(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long insertedRow = db.insert(TABLE_STOCKS, null, values);
        return insertedRow;
    }

    public Cursor getStockPortfolio(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_STOCKS, null, null, null, null, null, COLUMN_SYMBOL);
        return cursor;
    }

    public int deleteStock(String selection, String[] selectionArgs, String id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_STOCKS, selection, selectionArgs);

        return rowsDeleted;
    }
}
