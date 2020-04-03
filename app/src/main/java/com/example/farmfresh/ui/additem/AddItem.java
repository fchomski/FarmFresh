package com.example.farmfresh.ui.additem;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.sax.TextElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.Item;
import com.example.farmfresh.model.data.Key;
import com.example.farmfresh.model.data.State;
import com.example.farmfresh.model.data.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.util.Base64;


public class AddItem extends Fragment {

    private AddItemViewModel mViewModel;

    public static AddItem newInstance() {
        return new AddItem();
    }

    private static final int GALLERY_REQUEST_CODE = 100;

    private @Nullable String itemName;
    private @Nullable String price;
    private @Nullable String quantity;
    private @Nullable String imageBase64;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        State s = State.getInstance();
        final View root = inflater.inflate(R.layout.add_item_fragment, container, false);

        final String username = (String) s.getUser().get(Key.User.USER_NAME);

        final TextInputEditText itemNameText = (TextInputEditText) root.findViewById(R.id.itemName);
        final TextInputEditText priceText = (TextInputEditText) root.findViewById(R.id.price);
        final TextInputEditText quantityText = (TextInputEditText) root.findViewById(R.id.quantity);
        Button imgSelectBtn = (Button) root.findViewById(R.id.imageSelectBtn);
        Button addItemBtn = (Button) root.findViewById(R.id.addItemBtn);

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
                itemName    = itemNameText.getText().toString();
                price       = priceText.getText().toString();
                quantity    = quantityText.getText().toString();

                try {
                    Connect c = new Connect(root.getContext());
                    Item newItem = new Item();
                    newItem.put(Key.Item.SELLER_NAME, username);
                    if (itemName    != null &&
                        price       != null &&
                        quantity    != null &&
                        imageBase64 != null) {
                        newItem.put(Key.Item.ITEM_NAME, itemName);
                        newItem.put(Key.Item.PRICE, price);
                        newItem.put(Key.Item.QUANTITY, quantity);
                        newItem.put(Key.Item.IMAGE_BASE64, imageBase64);
                    } else {
                        String toasterMsg = "invalid input, try again";
                        Toast toast = Toast.makeText(root.getContext(), toasterMsg, Toast.LENGTH_SHORT);
                        toast.setMargin(50, 50);
                        toast.show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
        // TODO: Use the ViewModel
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Context context = getContext();
        Base64.Encoder encoder = Base64.getEncoder();
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                assert selectedImage != null;
                assert context != null;

                @SuppressLint("Recycle") Cursor cursor =
                        context.getContentResolver()
                               .query(selectedImage,
                                      filePathColumn,
                                      null, null, null);

                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                BitmapFactory
                        .decodeFile(imgDecodableString)
                        .compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                imageBase64 = encoder.encodeToString(byteArray);
            }
        }

    }

}
