package com.example.farmfresh.ui.search;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmfresh.MainActivity;
import com.example.farmfresh.R;
import com.example.farmfresh.model.adaptor.ItemCardAdaptor;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.Item;
import com.example.farmfresh.model.data.Key;
import com.example.farmfresh.search;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private RecyclerView recyclerView;
    private ItemCardAdaptor adaptor;
    private ArrayList<Item> items;
    private View root;
    private Button searchBtn;
    private TextInputEditText searchText;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        root = inflater.inflate(R.layout.fragment_search, container, false);
        searchBtn = (Button) root.findViewById(R.id.searchBtn);
        searchText = root.findViewById(R.id.searchText);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getActivity(), search.class);
                startActivity(it);
            }
        });

        // search result
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                try {
                    Connect c = new Connect(getContext());
                    ArrayList<Item> filterd =  c.filter(Key.Item.ITEM_NAME,
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
        }
        System.out.println(items.toString());

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() throws JSONException {
        System.out.println("initing");
        recyclerView = (RecyclerView) root.findViewById(R.id.itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Connect c = new Connect(Objects.requireNonNull(getContext()));
        items = c.<Item>getList(Item.class);
        adaptor = new ItemCardAdaptor(getActivity(), items);
        recyclerView.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
    }

    // TODO select event

}
