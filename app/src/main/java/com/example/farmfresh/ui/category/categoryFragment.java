package com.example.farmfresh.ui.category;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.farmfresh.R;
import com.example.farmfresh.ui.search.SearchViewModel;
@RequiresApi(api = Build.VERSION_CODES.O)
public class categoryFragment extends Fragment {
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_categorypage, container, false);

        return inflater.inflate(R.layout.activity_categorypage, container, false);}

}
