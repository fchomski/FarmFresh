package com.example.farmfresh.model.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Item extends HashMap<Key.Item, Object> implements Jsonable<Item> {

    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("itemName", this.get(Key.Item.ITEM_NAME));
        obj.put("price", this.get(Key.Item.PRICE));
        obj.put("quantity", this.get(Key.Item.QUANTITY));
        obj.put("itemPath", this.get(Key.Item.IMAGE_PATH));
        return obj;
    }

    @Override
    public Item fromJson(JSONObject json) throws JSONException {
        this.put(Key.Item.ITEM_NAME, (String) json.get("itemName"));
        this.put(Key.Item.PRICE, (String) json.get("quantity"));
        this.put(Key.Item.QUANTITY, (String) json.get("price"));
        this.put(Key.Item.IMAGE_PATH, (String) json.get("imagePath"));
        return this;
    }
}
