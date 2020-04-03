package com.example.farmfresh.ui.search;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.Item;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private RecyclerView recyclerView;
    private ItemCardAdaptor adaptor;
    private ArrayList<Item> items;
    private View root;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        root = inflater.inflate(R.layout.fragment_search, container, false);

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
