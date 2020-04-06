package com.example.farmfresh.ui.home;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.farmfresh.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    private ArrayList<HashMap<String, Object>> list ;
    private int[] mImgIds=new int[] { R.drawable.fruit, R.drawable.vegetable, R.drawable.protein,
            R.drawable.dairy, R.drawable.sweets, R.drawable.grains, R.drawable.plant,
            R.drawable.jewelry, R.drawable.clothing };
    String[] text={
            "Fruit",
            "Veggies",
            "Protein",
            "Dairy",
            "Sweets",
            "Grains",
            "Plants",
            "Jewelry",
            "Others"
    };
    private final int DATA_SIZE = 9;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int ll_width = (int) ((100+5) * density * DATA_SIZE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ll_width, RelativeLayout.LayoutParams.WRAP_CONTENT);
        GridView gv = root.findViewById(R.id.gv);
        gv.setLayoutParams(params);
        gv.setNumColumns(DATA_SIZE);
        getData();
        String[] text={"Fruit","Veggies","Protein","Dairy","Sweets","Grains","Plants","Jewelry","Others"};
        ListAdapter adapter = new SimpleAdapter(root.getContext(), list, R.layout.grid_item,
                new String[]{"itemImage","itemName"},
                new int[]{R.id.itemImage,R.id.itemName});

        gv.setAdapter(adapter);
        return root;
    }

    private void getData() {
        list = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < DATA_SIZE; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", mImgIds[i]);
            map.put("itemName", text[i]);
            list.add(map);
        }
    }
}
