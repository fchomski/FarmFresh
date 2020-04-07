package com.example.farmfresh.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.enums.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

@RequiresApi(api = Build.VERSION_CODES.N)
public class HomeFragment extends Fragment {
    private View root;
    private ArrayList<HashMap<String, Object>> list ;
    private int[] mImgIds=new int[] { R.drawable.fruit, R.drawable.vegetable, R.drawable.protein,
            R.drawable.dairy, R.drawable.sweets, R.drawable.grains, R.drawable.plant,
            R.drawable.jewelry, R.drawable.clothing };
    String[] cateKeys = Category.names();

    private final int DATA_SIZE = 9;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int ll_width = (int) ((100+5) * density * DATA_SIZE);
        RelativeLayout.LayoutParams params = new RelativeLayout
                .LayoutParams(ll_width, RelativeLayout.LayoutParams.WRAP_CONTENT);
        GridView gv = root.findViewById(R.id.gv);
        gv.setLayoutParams(params);
        gv.setNumColumns(DATA_SIZE);
        getData();
        ListAdapter adapter = new SimpleAdapter(
                root.getContext(),
                list,
                R.layout.grid_item,
                new String[]{"itemImage","itemName"},
                new int[]{R.id.itemImage,R.id.itemName});

        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = Category.parseString(cateKeys[position]);
                Intent intent = new Intent(root.getContext(), CategoryView.class);
                intent.putExtra("category", category.toString());
                root.getContext().startActivity(intent);
            }
        });

        return root;
    }

    private void getData() {
        list = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < DATA_SIZE; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", mImgIds[i]);
            map.put("itemName", cateKeys[i]);
            list.add(map);
        }
    }
}
