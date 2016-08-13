package com.example.freatnor.content_providers_lab;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Jonathan Taylor on 8/9/16.
 */
public final class StockPortfolioContract {
    public static final String AUTHORITY = "com.example.freatnor.content_providers_lab.StockPortfolioContentProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class StockPortfolio implements BaseColumns {
        public static final String TABLE_STOCKS = "stocks";
        public static final String COLUMN_SYMBOL = "symbol";
        public static final String COLUMN_COMPANY_NAME = "companyname";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_EXCHANGE_NAME = "stockexchangename";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_STOCKS);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.example.freatnor.content_providers_lab.stocks";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.example.freatnor.content_providers_lab.stocks";
    }
}
