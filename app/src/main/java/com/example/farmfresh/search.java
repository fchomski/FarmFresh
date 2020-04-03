package com.example.farmfresh;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmfresh.data.PProducts;
import com.example.farmfresh.ui.search.SingleItem;

import java.util.ArrayList;
import java.util.List;


public class search extends AppCompatActivity {

    private SearchView mSearchView;
    private androidx.appcompat.widget.SearchView.SearchAutoComplete mSearchAutoComplete;
    private Toolbar toolbar;
    private List<product> products=new ArrayList<product>();
    private RecyclerView productList;
    private searchAdapter adap;
    private TextView price;
    private TextView pname;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        productList=findViewById(R.id.list);
        price=findViewById(R.id.pprice);
        pname=findViewById(R.id.pname);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        productList.setLayoutManager(linearLayoutManager);
        adap=new searchAdapter(this,products);
        productList.setAdapter(adap);


        adap.setOnItemClickListener(new searchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent it=new Intent(search.this, SingleItem.class);
                it.putExtra("item", adap.getName(position));

                startActivity(it);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });








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
                Cursor cursor = TextUtils.isEmpty(s) ? null : searchData(s);
                List<String> name=new ArrayList<>();
                try{

               while( cursor.moveToNext()){
                name.add(cursor.getString(cursor.getColumnIndex("name")));
                Log.e("CSDN_LQR", "querySql = " );
               }


                }catch(Exception e){
                    e.printStackTrace();
                }

                for(int i=0;i<name.size();i++){
                products.add(new product(name.get(i),null,5,null));
                    Log.e("name1: ", name.get(i) );
                }



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








