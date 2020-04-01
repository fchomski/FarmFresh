package com.example.farmfresh.model.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Item extends HashMap<String, Object> implements Jsonable<Item> {

    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("itemName", this.get("itemName"));
        obj.put("price", this.get("price"));
        obj.put("quantity", this.get("quantity"));
        obj.put("itemPath", this.get("imagePath"));
        return obj;
    }

    @Override
    public Item fromJson(JSONObject json) throws JSONException {
        this.put("itemName", (String) json.get("itemName"));
        this.put("quantity", (String) json.get("quantity"));
        this.put("price", (String) json.get("price"));
        this.put("imagePath", (String) json.get("imagePath"));
        return this;
    }
}
