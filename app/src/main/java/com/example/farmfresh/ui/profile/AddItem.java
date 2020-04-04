package com.example.farmfresh.ui.profile;


import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.data.Item;
import com.example.farmfresh.model.data.enums.Key;
import com.example.farmfresh.model.data.State;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;


public class AddItem extends FragmentActivity {

    private static final int GALLERY_REQUEST_CODE = 100;

    private @Nullable String itemName;
    private @Nullable String price;
    private @Nullable String quantity;
    private @Nullable String imageBase64;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);
        State s = State.getInstance();

        final String username = (String) s.getUser().get(Key.User.USER_NAME);

        final TextInputEditText itemNameText = (TextInputEditText) findViewById(R.id.itemName);
        final TextInputEditText priceText = (TextInputEditText) findViewById(R.id.price);
        final TextInputEditText quantityText = (TextInputEditText) findViewById(R.id.quantity);
        Button imgSelectBtn = (Button) findViewById(R.id.imageSelectBtn);
        Button addItemBtn = (Button) findViewById(R.id.addItemBtn);

        imgSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                // select image
                Intent imageIntent = new Intent(Intent.ACTION_PICK);
                imageIntent.setType("image/*");
                String[] mimeType = {"image/jpeg", "image/png", "image/jpg"};
                imageIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType);
                startActivityForResult(imageIntent, GALLERY_REQUEST_CODE);
            }
        });

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                // don't use not null contract since we need it for deciding whether show toaster or not.
                itemName = itemNameText.getText().toString();
                price = priceText.getText().toString();
                quantity = quantityText.getText().toString();

                try {
                    Connect c = new Connect(AddItem.this);
                    Item newItem = new Item();
                    newItem.put(Key.Item.SELLER_NAME, username);
                    if (itemName != null && price != null && quantity != null && imageBase64 != null) {
                        newItem.put(Key.Item.ITEM_NAME, itemName);
                        newItem.put(Key.Item.PRICE, price);
                        newItem.put(Key.Item.QUANTITY, quantity);
                        newItem.put(Key.Item.IMAGE_BASE64, imageBase64);
                        System.out.println(newItem.toJson());
                        c.add(newItem, Item.class);
                        c.sync();
                        finish();
                    } else {
                        String toasterMsg = "invalid input, try again";
                        Toast toast = Toast.makeText(AddItem.this, toasterMsg, Toast.LENGTH_SHORT);
                        toast.setMargin(50, 50);
                        toast.show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] byteArray;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                Uri selectedImage = data.getData();
                try {
                    assert selectedImage != null;
                    // Uri -> byteArray -> base64 String
                    InputStream is = getContentResolver().openInputStream(selectedImage);
                    byteArray = getBytes(is);
                    imageBase64 = encoder.encodeToString(byteArray);
                    System.out.println(imageBase64);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // change image View
                ImageView itemImageView = (ImageView) findViewById(R.id.itemImage);
                itemImageView.setImageURI(selectedImage);

            }
        }
    }

    private byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int buffersz = 1024;
        byte[] buffer = new byte[buffersz];

        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return  byteBuffer.toByteArray();
    }

}
