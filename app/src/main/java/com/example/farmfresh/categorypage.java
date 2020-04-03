package com.example.farmfresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.farmfresh.data.PProducts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class categorypage extends AppCompatActivity {
    private GridView gridView;
    private List<Map<String, Object>> data_list;
    private androidx.appcompat.widget.SearchView.SearchAutoComplete mSearchAutoComplete;
    private SimpleAdapter adapter;
    private Toolbar toolbar;
    private List<product> products=new ArrayList<product>();
    private SearchView mSearchView;
    private int[] icons={R.drawable.watermelon,R.drawable.vegetable,R.drawable.protein,R.drawable.dairy,R.drawable.sweets,R.drawable.grains,R.drawable.plant,R.drawable.clothing};
    private String[] text={"Fruit","Veggies","Protein","Dairy","Sweets","Grains","Plants","Others"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorypage);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridView = (GridView) findViewById(R.id.gridView);
        data_list = new ArrayList<Map<String, Object>>();
        getData();

        String[] form = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        adapter = new SimpleAdapter(this, data_list, R.layout.grid_item, form, to);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
                    SimpleCursorAdapter ss=new SimpleCursorAdapter(categorypage.this, R.layout.item_layout, cursor, new String[]{"name"}, new int[]{R.id.text1},1);

                    mSearchView.setSuggestionsAdapter(ss);
                } else {
                    mSearchView.getSuggestionsAdapter().changeCursor(cursor);

                }

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

}
    public List<Map<String, Object>>  getData(){

        for (int i = 0; i < icons.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icons[i]);
            map.put("text", text[i]);
            data_list.add(map);
        }
        return data_list;
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

    }}
