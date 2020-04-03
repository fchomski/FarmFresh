package com.example.farmfresh.ui.search;

import android.content.Intent;
import android.view.View;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.farmfresh.Category_Fruit;

public class SearchViewModel extends ViewModel {

public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the search fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }

}