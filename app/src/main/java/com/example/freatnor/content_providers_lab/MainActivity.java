package com.example.freatnor.content_providers_lab;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.example.freatnor.content_providers_lab.interfaces.OnStockInsertedListener;
import com.example.freatnor.content_providers_lab.models.StockItem;
import com.example.freatnor.content_providers_lab.presenter.StockListAdapter;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnStockInsertedListener {

    public static final String URL = "http://dev.markitondemand.com/MODApis/Api/v2/Lookup/jsonp?input=";
    public static final String CALLBACK = "lab";
    private static final String TAG = "MainActivity";

    private ListView mListView;
    private StockListAdapter mAdapter;

    private StockCursorLoader mLoader;

    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_stock_dialog, null);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add a new stock to the Portfolio")
                        .setView(dialogView)
                        .setPositiveButton("Add Stock", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText symbol = (EditText) dialogView.findViewById(R.id.stock_symbol_input);
                                EditText quantity = (EditText) dialogView.findViewById(R.id.quantity_input);
                                getStock(symbol.getText().toString(), quantity.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });

        mListView = (ListView) findViewById(R.id.stock_list_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoader = new StockCursorLoader(this, StockPortfolioContract.StockPortfolio.CONTENT_URI, this);

        mAdapter = new StockListAdapter(this, null);
        mListView.setAdapter(mAdapter);
    }

    private void getStock(String stockSymbol, final String quantity) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL + stockSymbol + "&callback=" + CALLBACK)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Bad or empty response. Unexpected Code " + response);
                }
                String body = response.body().string();
                Log.d(TAG, "onResponse: response body - " + body);
                body = body.substring("lab(".length(), body.length() - 1);
                Log.d(TAG, "onResponse: response body after trimming - " + body);
                if (!body.equals("")) {
                    Gson gson = new Gson();
                    StockItem[] stocks = gson.fromJson(body, StockItem[].class);
                    Log.d(TAG, "onResponse: array length = " + stocks.length);
                    StockItem theStock = stocks[0];
                    theStock.setQuantity(quantity);
                    insert(theStock);
                }
            }
        });
    }

    private void insert(StockItem theStock) {
        ContentValues values = new ContentValues();
        values.put(StockPortfolioContract.StockPortfolio.COLUMN_SYMBOL, theStock.getSymbol());
        values.put(StockPortfolioContract.StockPortfolio.COLUMN_COMPANY_NAME, theStock.getName());
        values.put(StockPortfolioContract.StockPortfolio.COLUMN_EXCHANGE_NAME, theStock.getExchange());
        values.put(StockPortfolioContract.StockPortfolio.COLUMN_QUANTITY, theStock.getQuantity());
        mCursor = mLoader.loadInBackground();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void stockInserted() {
        if (mCursor != null) {
            mAdapter.swapCursor(mCursor);
            mAdapter.notifyDataSetChanged();
            Log.d(TAG, "stockInserted: reloading the cursor and listview");
        }
    }
}
