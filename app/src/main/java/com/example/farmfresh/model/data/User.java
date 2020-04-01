package com.example.farmfresh.model.data;

import android.view.textclassifier.TextClassifierEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class User extends HashMap<String, Object> implements Jsonable<User> {

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("userName", (String) this.get("userName"));
        obj.put("userPassword", (String) this.get("userPassword"));
        obj.put("userType", (String) this.get("userType"));
        Coordinate userLocation = (Coordinate) this.get("userLocation");
        if (userLocation != null) obj.put("userLocation", userLocation.toJson());
        return null;
    }

    @Override
    public User fromJson(JSONObject json) throws JSONException {
        this.put("userName", (String) json.get("userName"));
        this.put("userPassword", (String) json.get("userPassword"));
        this.put("userType", (String) json.get("UserType"));
        try {
            JSONObject coordinate = json.getJSONObject("userLocation");
            this.put("userLocation" ,(new Coordinate()).fromJson(coordinate));
        } catch (JSONException ignored) {
            this.put("userLocation", null);
        }
        return this;
    }
}

class Coordinate extends HashMap<String, Double> implements Jsonable<Coordinate>{

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        Double lng = this.get("lng");
        Double lat = this.get("lat");
        if (lng != null && lat != null) {
            obj.put("lng", lng);
            obj.put("lat", lat);
            return  obj;
        }
        return null;
    }

    @Override
    public Coordinate fromJson(JSONObject json) throws JSONException {
        this.put("lng", (double) json.get("lng"));
        this.put("lat", (double) json.get("lat"));
        return this;
    }
}
