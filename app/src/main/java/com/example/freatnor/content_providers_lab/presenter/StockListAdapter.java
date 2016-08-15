package com.example.freatnor.content_providers_lab.presenter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.freatnor.content_providers_lab.R;
import com.example.freatnor.content_providers_lab.StockPortfolioContract;

/**
 * Created by Jonathan Taylor on 8/14/16.
 */
public class StockListAdapter extends CursorAdapter{


    public StockListAdapter(Context context, Cursor c) {
        super(context, c, true);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.inner_stock_content_main, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView symbol = (TextView) view.findViewById(R.id.symbol);
        TextView name = (TextView) view.findViewById(R.id.company_name);
        TextView quantity = (TextView) view.findViewById(R.id.stock_quantity);
        TextView exchange = (TextView) view.findViewById(R.id.stock_exchange);
        symbol.setText(cursor.getString(cursor.getColumnIndex(StockPortfolioContract.StockPortfolio.COLUMN_SYMBOL)));
        name.setText(cursor.getString(cursor.getColumnIndex(StockPortfolioContract.StockPortfolio.COLUMN_COMPANY_NAME)));
        quantity.setText(cursor.getString(cursor.getColumnIndex(StockPortfolioContract.StockPortfolio.COLUMN_QUANTITY)));
        exchange.setText(cursor.getString(cursor.getColumnIndex(StockPortfolioContract.StockPortfolio.COLUMN_EXCHANGE_NAME)));
    }

}
