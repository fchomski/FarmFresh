package com.example.farmfresh.model.data;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/* Data connection APIs.
   All data will be accessed from here.
 */

public class Connect {
    private static String path = "data.json";  // TODO: change this.
    private JSONObject jsonData;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Connect(Context context) throws JSONException {
        this.context = context;
        try {
            FileInputStream in = context.openFileInput(path);
            InputStreamReader inreader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(inreader);

            String line;
            String raw = reader.readLine();

            System.out.println(raw);
            if (raw == null) {
                this.jsonData = defaultJsondata();
            } else {
                this.jsonData = new JSONObject((String)raw);
            }

        } catch (FileNotFoundException | JSONException e) {
            e.printStackTrace();
            this.jsonData = defaultJsondata();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("---------------------->");
        System.out.println(this.jsonData);
    }

    public void close() {
        this.sync();
    }

    /* search by key
       usage:
            new Connect.filter("userName", (e) -> e =="Bob", User.class);
            new Connect.filter("price", (e) -> e > 200 && e < 500, Item.class);
     */
    public <T extends HashMap & Jsonable> ArrayList<T>
    filter(Enum key, Function<Object, Boolean> predicate, Class<T> cls) throws JSONException, InstantiationException, IllegalAccessException {
        ArrayList<T> res = new ArrayList<>();
        String dataType = getJsonArryPropertyName(cls);

        JSONArray jarray = this.jsonData.getJSONArray(dataType);
        for (int i = 0; i < jarray.length(); ++i) {
            JSONObject obj = jarray.getJSONObject(i);
            System.out.println(":>> " + obj.toString());
            T data = cls.newInstance();
            data.fromJson(obj);
            Object ele = data.get(key);
            assert ele != null;
            System.out.println("=>> " + ele.toString());


            if (predicate.apply(ele)) {
                res.add(data);
            }
        }
        return res;
    }

    /*
        Usage:
            if (new Connect.add(new User().fromJson("{...}"))) {...}
     */
    public <T extends  Jsonable> void add(T ele, Class<T> cls) throws JSONException {
        String dataType = getJsonArryPropertyName(cls);

        assert dataType != null;
        JSONArray jarry = this.jsonData.getJSONArray(dataType);
        try {
            jarry.put(ele.toJson());
            this.jsonData.put(dataType, jarry);
        } catch (JSONException e) {
            System.err.println("add failed, can't get property type from JSON file");
            e.printStackTrace();
        }
    }

    // write change into file.
    public void sync() {
        String jstring = this.jsonData.toString();
        FileOutputStream out;
        try {
            out = context.openFileOutput(path, Context.MODE_PRIVATE);
            out.write(jstring.getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T extends Jsonable> String getJsonArryPropertyName(Class<T> cls) {
        if (cls == Item.class) return  "item";
        else if (cls == User.class) return  "user";
        return null;
    }

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

    private JSONObject defaultJsondata() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("user", new JSONArray());
        obj.put("item", new JSONArray());
        return obj;
    }
}
