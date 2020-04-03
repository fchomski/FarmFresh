package com.example.farmfresh.model.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataModel {
    private ArrayList<Item> items;
    private ArrayList<User> users;
    private JSONObject jsonData;

    public DataModel(JSONObject obj) throws JSONException {
        jsonData = obj;
        items = createList(jsonData, Item.class);
        users = createList(jsonData, User.class);
    }

    <T extends HashMap & Jsonable> ArrayList<T> getList(Class<T> cls) throws JSONException {
        if (cls == Item.class) return (ArrayList<T>) getItems();
        else if (cls == User.class) return (ArrayList<T>) getUsers();
        else return new ArrayList<T>();
    }

    private <T extends HashMap & Jsonable> ArrayList<T> createList(JSONObject jsonObj, Class<T> cls) throws JSONException {
        String dataType = getJsonArrayPropertyName(cls);
        ArrayList<T> res = new ArrayList<>();
        assert dataType != null;
        JSONArray jsonarray = jsonObj.getJSONArray(dataType);

        for (int i = 0; i < jsonarray.length(); ++i) {
            JSONObject obj = (JSONObject) jsonarray.get(i);
            T data;
            try {
                data = cls.newInstance();
                data.fromJson(obj);
                res.add(data);
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("getList ::" + res.toString());
        return res;
    }

    // write change into file.
    void sync(Context context, String path) {
        String jsonString = this.jsonData.toString();
        FileOutputStream out;
        try {
            out = context.openFileOutput(path, Context.MODE_PRIVATE);
            out.write(jsonString.getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }


    public JSONObject getJsonData() {
        return jsonData;
    }

    public static <T extends Jsonable> String getJsonArrayPropertyName(Class<T> cls) {
        if (cls == Item.class) return  "item";
        else if (cls == User.class) return  "user";
        return null;
    }
}
