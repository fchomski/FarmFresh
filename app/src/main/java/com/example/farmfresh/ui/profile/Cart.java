package com.example.farmfresh.ui.profile;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmfresh.R;
import com.example.farmfresh.model.adaptor.ItemCartAdaptor;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.Item;
import com.example.farmfresh.model.adaptor.ItemCardAdaptor;
import com.example.farmfresh.model.data.State;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class Cart extends FragmentActivity {
    private RecyclerView recyclerView;
    private ItemCartAdaptor adaptor;
    private ArrayList<Item> items;
    private Button searchBtn;
    private State state;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        state = State.getInstance();

        try {
            initView();
            System.out.println(items.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() throws JSONException {
        System.out.println("initing");
        recyclerView = (RecyclerView) findViewById(R.id.cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        items = state.getCart();
        System.out.println(state.getCart().toString());
        adaptor = new ItemCartAdaptor(this, items);
        recyclerView.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
    }

}
