package com.example.farmfresh.model.data;

import android.os.Build;
import android.os.FileUtils;
import android.util.JsonReader;

import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* Data connection APIs.
   All data will be accessed from here.
 */

public class Connect {
    private static String path = "data.json";
    private JSONObject jsonData;

    @RequiresApi(api = Build.VERSION_CODES.O)
    Connect(String path) throws IOException, JSONException {
        this.jsonData = fromJson(path);
    }

    /* search by key
       usage:
            new Connect.filter<User>("userName", (e) -> e =="Bob", User.class);
            new Connect.filter<Item>("price", (e) -> e > 200 && e < 500, Item.class);
     */
    public <T extends Jsonable> ArrayList<T>
    filter(String key, Function<Object, Boolean> predicate, Class<T> cls) throws JSONException {
        String dataType;
        ArrayList<T> res = null;

        if (cls == Item.class) dataType = "item";
        else if (cls == User.class) dataType = "user";
        else return  null;

        JSONArray jarray = this.jsonData.getJSONArray(dataType);
        for (int i = 0; i < jarray.length(); ++i) {
            JSONObject obj = jarray.getJSONObject(i);

            if (predicate.apply(obj.get(key))) {
                try {
                    T e;
                    e = cls.newInstance();
                    res.add(e);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    // TODO: insert()

    /* Json reader. there will be one json file as our database.
       Json Format:
       {
          "item": [Item.toJson()],
          "user": [User.toJson()]
       }
       Image is stored separately and will be accessed via the path.
    */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private JSONObject fromJson(String path) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        return new JSONObject(content);
    }
}
