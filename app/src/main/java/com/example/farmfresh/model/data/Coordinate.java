package com.example.farmfresh.model.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Coordinate<getter> extends HashMap<Key.Coordinate, Double> implements Jsonable<Coordinate>{

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        Double lng = this.get(Key.Coordinate.LNG);
        Double lat = this.get(Key.Coordinate.LAT);
        if (lng != null && lat != null) {
            obj.put("lng", lng);
            obj.put("lat", lat);
            return  obj;
        }
        return null;
    }

    @Override
    public Coordinate fromJson(JSONObject json) throws JSONException {
        this.put(Key.Coordinate.LNG, (double) json.get("lng"));
        this.put(Key.Coordinate.LAT, (double) json.get("lat"));
        return this;
    }

}
