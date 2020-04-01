package com.example.farmfresh;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.farmfresh.data.PProducts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class search extends AppCompatActivity {

    private SearchView mSearchView;
    private androidx.appcompat.widget.SearchView.SearchAutoComplete mSearchAutoComplete;
    private Toolbar toolbar;
    private List<product> products=new ArrayList<product>();
    private RecyclerView productList;
    private searchAdapter adap;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        productList=findViewById(R.id.list);





    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);


        mSearchView = (SearchView)searchItem.getActionView();


        mSearchAutoComplete = mSearchView.findViewById(R.id.search_src_text);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();

            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Cursor cursor = TextUtils.isEmpty(s) ? null : searchData(s);

                if (mSearchView.getSuggestionsAdapter() == null) {
                    SimpleCursorAdapter ss=new SimpleCursorAdapter(search.this, R.layout.item_layout, cursor, new String[]{"name"}, new int[]{R.id.text1},1);

                    mSearchView.setSuggestionsAdapter(ss);
                } else {
                    mSearchView.getSuggestionsAdapter().changeCursor(cursor);

                }

                return false;
            }
        });







        return super.onCreateOptionsMenu(menu);
    }

    private Cursor searchData(String key) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir() + "product.db", null);
        Cursor cursor = null;
        try {
            String querySql = "select * from pro_name where name like '%" + key + "%'";
            cursor = db.rawQuery(querySql, null);
            Log.e("CSDN_LQR", "querySql = " + querySql);
        } catch (Exception e) {
            e.printStackTrace();
            String createSql = "create table pro_name (_id integer primary key autoincrement,name varchar(100))";
            db.execSQL(createSql);

            String insertSql = "insert into pro_name values (null,?)";
            for (int i = 0; i < PProducts.productsname.length; i++) {
                db.execSQL(insertSql, new String[]{PProducts.productsname[i]});
            }

            String querySql = "select * from pro_name where name like '%" + key + "%'";
            cursor = db.rawQuery(querySql, null);

            Log.e("e1", "createSql = " + createSql);
            Log.e("e2", "querySql = " + querySql);
        }
        return cursor;

    }








}








