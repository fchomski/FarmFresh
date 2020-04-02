package com.example.farmfresh.model.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

// Jsonable Enable a class to be transformed from and to JSONObject.

public interface Jsonable<T> {
    public JSONObject toJson() throws JSONException;
    public T fromJson(JSONObject json) throws JSONException;
}
