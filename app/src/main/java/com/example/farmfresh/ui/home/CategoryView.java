package com.example.farmfresh.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmfresh.R;
import com.example.farmfresh.model.adaptor.ItemCardAdaptor;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.data.Item;
import com.example.farmfresh.model.data.enums.Category;
import com.example.farmfresh.model.data.enums.Key;
import com.example.farmfresh.ui.search.SearchViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryView extends FragmentActivity {

    private SearchViewModel searchViewModel;
    private RecyclerView recyclerView;
    private ItemCardAdaptor adaptor;
    private ArrayList<Item> items;
    private Button searchBtn;
    private TextInputEditText searchText;

    private Intent intent;
    private Category category;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchText = findViewById(R.id.searchText);
        intent = getIntent();
        category = Category.parseString(intent.getStringExtra("category"));

        // search result
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                try {
                    Connect c = new Connect(getApplicationContext());
                    ArrayList<Item> filterd = c.filter(Key.Item.ITEM_NAME,
                            new Function<Object, Boolean>() {
                                @Override
                                public Boolean apply(Object e) {
                                    String searchString = searchText.getText().toString();
                                    if (searchString == null || searchString.equals("")) return true;
                                    return Objects.equals(e, searchString);
                                }
                            },
                            Item.class);
                    items.clear();
                    items.addAll(filterd);
                    adaptor.notifyDataSetChanged();
                    System.out.println(items.toString());
                } catch (JSONException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (java.lang.InstantiationException ex) {
                    ex.printStackTrace();
                }
            }
        });

        try {
            initView();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        System.out.println(items.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() throws JSONException, IllegalAccessException, InstantiationException {
        items = new ArrayList<>();
        System.out.println("initing");
        recyclerView = (RecyclerView) findViewById(R.id.itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Connect c = new Connect(Objects.requireNonNull(getApplicationContext()));

        items = c.filter(Key.Item.CATEGORY,
                new Function<Object, Boolean>() {
                    @Override
                    public Boolean apply(Object e) {
                        return Objects.equals(e, category);
                    }
                },
                Item.class);

        adaptor = new ItemCardAdaptor(this, items);
        recyclerView.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
    }

    // TODO select event

}
