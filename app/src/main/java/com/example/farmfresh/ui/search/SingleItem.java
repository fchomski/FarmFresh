package com.example.farmfresh.ui.search;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.Item;
import com.example.farmfresh.model.data.Key;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class SingleItem extends FragmentActivity {

    private Intent intent;
    private Item item;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item_activity);
        intent = getIntent();
        item = new Item();
        try {
            String str = intent.getExtras().getString("item");
            System.out.println(str);
            item.fromJson(new JSONObject(str));
            System.out.println(item.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Button back = (Button) findViewById(R.id.single_backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                finish();
            }
        });

        final TextView itemName = (TextView) findViewById(R.id.single_itemName);
        final TextView itemPrice = (TextView) findViewById(R.id.single_price);
        final TextView itemQuantity = (TextView) findViewById(R.id.single_quantity);
        final TextView itemSeller = (TextView) findViewById(R.id.single_seller);
        final ImageView itemImage = (ImageView) findViewById(R.id.single_itemImage);

        System.out.println("::");
        System.out.println(item.get(Key.Item.ITEM_NAME));
        itemName.setText((String) item.get(Key.Item.ITEM_NAME));
        itemPrice.setText((String) item.get(Key.Item.PRICE));
        itemSeller.setText((String) item.get(Key.Item.SELLER_NAME));
        itemQuantity.setText((String) item.get(Key.Item.QUANTITY));

        String base64Image = (String) item.get(Key.Item.IMAGE_BASE64);
        byte[] byteArray = base64ImgToByteArray(base64Image);
                itemImage.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private byte[] base64ImgToByteArray(String base64Image) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64Image);
    }
}


