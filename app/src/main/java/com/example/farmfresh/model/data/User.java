package com.example.farmfresh.model.data;

import android.os.Build;
import android.sax.StartElementListener;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

//TODO : need a location to coordinate table to show correct map.


public class User extends HashMap<Key.User, Object> implements Jsonable<User> {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("userName", (String) this.get(Key.User.USER_NAME));
        obj.put("userPassword", (String) this.get(Key.User.USER_PASSWORD));
        obj.put("userType", this.get(Key.User.USER_TYPE) == UserType.BUYER ? "BUYER" : "SELLER");
        Coordinate userLocation = (Coordinate) this.get(Key.User.USER_LOCATION);
        if (userLocation != null) obj.put("userLocation", userLocation.toJson());
        return obj;
    }

    @Override
    public User fromJson(JSONObject json) throws JSONException {
        this.put(Key.User.USER_NAME, (String) json.get("userName"));
        this.put(Key.User.USER_PASSWORD, (String) json.get("userPassword"));
        this.put(Key.User.USER_TYPE, json.get("userType").equals("BUYER") ? UserType.BUYER : UserType.SELLER);
        try {
            JSONObject coordinate = json.getJSONObject("userLocation");
            this.put(Key.User.USER_LOCATION ,(new Coordinate()).fromJson(coordinate));
        } catch (JSONException ignored) {
            this.put(Key.User.USER_LOCATION, null);
        }
        return this;
    }
}

